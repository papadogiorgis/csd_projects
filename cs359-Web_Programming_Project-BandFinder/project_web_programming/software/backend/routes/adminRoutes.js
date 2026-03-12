// backend/routes/adminRoutes.js

const express = require('express');
const router = express.Router();
const { getConnection } = require('../database');

//check if user is admin
function isAdmin(req, res, next){
    if(req.session.user && req.session.user.username === 'admin'){
        return next();
    }
    return res.status(403).json({ message: "Unauthorized! Admins only!"});
}

//admin login
router.post('/login', async(req, res) => {
    const {username, password} = req.body;
    if((username === 'admin')&&(password === 'admiN12@*')){
        try{
            const conn = await getConnection();
            const [rows] = await conn.execute('SELECT * FROM users WHERE username = ?', ['admin']);
            if(rows.length > 0){
                req.session.user = rows[0];
                req.session.type = 'admin';
                return res.status(200).json({message: "Correct Admin Login Credentials!"});
            }
        }catch (error){
            return res.status(500).json({message: "Server Error during Login!"});
        }
    }
    res.status(401).json({message: "Incorrect Login Credentials!"});
});

//get all users
router.get('/users', isAdmin, async(req, res) => {
    try{
        const conn = await getConnection();
        const [users] = await conn.execute("SELECT id, username, email, firstname, lastname FROM users WHERE username != 'admin'");
        res.status(200).json(users);
    }catch (error){
        res.status(500).json({message: error.message});
    }
});

//delete user
router.delete('/delete-user/:id', isAdmin, async(req, res) => {
    try{
        const userId = req.params.id;
        const conn = await getConnection();
        await conn.execute('DELETE FROM users WHERE id = ?', [userId]);
        res.status(200).json({message: `User ${userId} deleted by Admin!`});
    }catch (error){
        res.status(500).json({message: error.message});
    }
});

//get all pending reviews
router.get('/pending-reviews', isAdmin, async(req, res) => {
    try{
        const conn = await getConnection();
        const [rev] = await conn.execute("SELECT * FROM reviews WHERE status = 'pending'");
        res.status(200).json(rev);
    }catch (error){
        res.status(500).json({message: error.message});
    }
});

//admin can approve or reject reviews
router.post('/review-action', isAdmin, async(req, res) => {
    try{
        const {id, action} = req.body;
        if((action !== 'published')&&(action !== 'rejected')){
            return res.status(400).json({message: "Invalid Action!"});
        }
        const conn = await getConnection();
        await conn.execute('UPDATE reviews SET status = ? WHERE id = ?', [action, id]);
        res.status(200).json({message: `Review ${action}`});
    }catch (error){
        res.status(500).json({message: error.message});
    }
});

//get stats
router.get('/stats', isAdmin, async(req, res) => {
    try{
        const conn = await getConnection();
        //bands per city
        const [bandspercity] = await conn.execute("SELECT city, COUNT(*) as count FROM bands GROUP BY city");
        //events per type
        const [eventtypes] = await conn.execute("SELECT type, COUNT(*) as count FROM events GROUP BY type");
        //number of users and bands
        const [usercount] = await conn.execute("SELECT COUNT(*) as count FROM users WHERE username != 'admin'");
        const [bandcount] = await conn.execute("SELECT COUNT(*) as count FROM bands");
        //earnings
        const [earnings] = await conn.execute("SELECT SUM(cost) as total FROM events WHERE type = 'private' AND status = 'done'");
        const cash = earnings[0].total ? (earnings[0].total * 0.15).toFixed(2) : 0;
        res.status(200).json({
            bandspercity,
            eventtypes,
            counts: {users: usercount[0].count, bands: bandcount[0].count},
            earnings: cash
        });
    }catch (error){
        console.error(error);
        res.status(500).json({message: error.message});
    }
});

module.exports = router;