// backend/routes/authRoutes.js

const express = require('express');
const router = express.Router();
const { getConnection } = require('../database');

//function to check for duplicates
async function checkForDuplicates(field, value){
    const conn = await getConnection();
    const [userRows] = await conn.execute(`SELECT id FROM users WHERE ${field} = ?`, [value]);
    const [bandRows] = await conn.execute(`SELECT id FROM bands WHERE ${field} = ?`, [value]);
    return (userRows.length > 0 || bandRows.length > 0);
}

//check duplicates (ajax)
router.post('/check-duplicate', async (req, res) => {
    try{
        const {type, value} = req.body;
        if(!type || !value) return res.status(400).json({message: "Missing parameters"});
        const taken = await checkForDuplicates(type, value);
        if(taken){
            return res.status(403).json({message: `This ${type} is already taken!`});
        }else{
            return res.status(200).json({message: "Available"});
        }
    }catch (error){
        console.error(error);
        res.status(500).json({message: "Server Error: " + error.message});
    }
});

//register user
router.post('/register-user', async (req, res) => {
    try {
        const data = req.body;
        //duplicate check
        if (await checkForDuplicates('username', data.username) || await checkForDuplicates('email', data.email)){
            return res.status(403).json({message: "User or Email already taken!"});
        }
        //set defaults for lat/lon if missing
        const lat = data.latitude || 35.33;
        const lon = data.longitude || 25.14;
        const conn = await getConnection();
        //insert into database
        const sql = `
            INSERT INTO users (username, email, password, firstname, lastname, birthdate, gender, country, city, address, telephone, lat, lon) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)`;
        await conn.execute(sql, [
            data.username, data.email, data.password, 
            data.firstname, data.lastname, data.birthdate, 
            data.gender, data.country, data.city, 
            data.address, data.telephone, lat, lon
        ]);
        res.status(200).json({message: "User registration was successful!", data: data});
    }catch (error){
        console.error("User Registration Error: ", error);
        res.status(500).json({message: "Database Error: " + error.message});
    }
});

//register band
router.post('/register-band', async (req, res) => {
    try{
        const data = req.body;
        //duplicate check
        if (await checkForDuplicates('username', data.username) || await checkForDuplicates('email', data.email)){
            return res.status(403).json({message: "User or Email already taken!"});
        }

        const conn = await getConnection();
        //insert into database
        const sql = `
            INSERT INTO bands (username, email, password, band_name, music_genres, band_description, members_number, founded_year, city, telephone, webpage, photo) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)`;
        await conn.execute(sql, [
            data.username, data.email, data.password, 
            data.band_name, data.music_genres, data.band_description, 
            data.members_number, data.foundedYear, data.band_city, 
            data.telephone, data.webpage, data.photo
        ]);
        res.status(200).json({message: "Band registration was successful!", data: data});
    }catch (error){
        console.error("Registration Error: ", error);
        res.status(500).json({message: "Database Error: " + error.message});
    }
});

//login
router.post('/login', async (req, res) => {
    const {username, password} = req.body;
    try{
        const conn = await getConnection();
        //check user table
        const [users] = await conn.execute('SELECT * FROM users WHERE username = ? AND password = ?', [username, password]);
        if(users.length > 0){
            req.session.user = users[0];
            req.session.type = 'user';
            return res.status(200).json({message: "Correct Login Credentials!", type: 'user'});
        }
        //ψηεψκ βανδ ταβλε
        const [bands]= await conn.execute('SELECT * FROM bands WHERE username = ? AND password = ?', [username, password]);
        if(bands.length > 0){
            req.session.user = bands[0];
            req.session.type = 'band';
            return res.status(200).json({message: "Correct Login Credentials!", type: 'band'});
        }
        res.status(401).json({message: "Incorrect Login Credentials!"});
    }catch(error){
        console.error(error);
        res.status(500).json({message: "Server Error during Login!"});
    }
});

//logout
router.post('/logout', (req, res) => {
    req.session.destroy(err => {
        if (err) return res.status(500).json({message: "Server Error during Logout!"});
        res.status(200).json({message: "Logged out!"});
    });
});

//gets current user data
router.get('/get-user', (req, res) => {
    if(!req.session.user) {
        return res.status(401).json({message: "Not authorized!"});
    }
    res.status(200).json({user: req.session.user, type: req.session.type});
});

//update user profile
router.post('/update-user', async (req, res) => {
    if(!req.session.user){
        return res.status(403).json({message: "Not authorized!"});
    }

    const data = req.body;
    const userId = req.session.user.id;
    const type = req.session.type;
    const conn = await getConnection();

    try{
        if(type === 'user'){
            //can update anything except username and email
            const sql = `
            UPDATE users 
            SET password=?, firstname=?, lastname=?, birthdate=?, gender=?, country=?, city=?, address=?, telephone=?, lat=?, lon=? 
            WHERE id=?`;

            await conn.execute(sql, [data.password, data.firstname, data.lastname, data.birthdate, 
            data.gender, data.country, data.city, data.address, data.telephone, data.latitude, data.longitude, userId]);

            //refresh session
            const [u] = await conn.execute("SELECT * FROM users WHERE id=?", [userId]);
            req.session.user = u[0];
        }else{
            //band profile update
            const sql = `
            UPDATE bands 
            SET password=?, band_name=?, music_genres=?, band_description=?, members_number=?, founded_year=?, city=?, telephone=?, webpage=?, photo=? 
            WHERE id=?`;

            await conn.execute(sql, [data.password, data.band_name, data.music_genres, data.band_description, 
            data.members_number, data.foundedYear, data.band_city, data.telephone, data.webpage, data.photo, userId]);

            //refresh session
            const [b] = await conn.execute("SELECT * FROM bands WHERE id=?", [userId]);
            req.session.user = b[0];
        }
        res.status(200).json({message: "Profile Updated!"});
    }catch (error){
        console.error(error);
        res.status(500).json({message: "Profile update failed: " + error.message});
    }
});

module.exports = router;