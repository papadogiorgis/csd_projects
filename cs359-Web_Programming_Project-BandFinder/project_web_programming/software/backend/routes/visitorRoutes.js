// backend/routes/visitorRoutes.js

const express = require('express');
const router = express.Router();
const {getConnection} = require('../database');

//get bands with filters
router.get('/bands', async (req, res) => {
    try{
        const {genre, year, city} = req.query;
        let sql = "SELECT id, band_name, music_genres, city, founded_year, photo, band_description FROM bands WHERE 1=1";
        const params = [];

        if(genre){
            sql += " AND music_genres LIKE ?";
            params.push(`%${genre}%`);
        }
        if(year){
            sql += " AND founded_year = ?";
            params.push(year);
        }
        if(city){
            sql += " AND city LIKE ?";
            params.push(`%${city}%`);
        }

        const conn = await getConnection();
        const [bands] = await conn.execute(sql, params);
        res.status(200).json(bands);
    }catch (error){
        res.status(500).json({message: error.message});
    }
});

//see public events with filters
router.get('/events', async (req, res) => {
    try{
        const {date, city, genre} = req.query;
        //join with bands
        //to gte music genre
        let sql = `
        SELECT e.*, b.band_name, b.music_genres 
        FROM events e 
        JOIN bands b ON e.band_id = b.id 
        WHERE e.type = 'public' AND e.status = 'scheduled'`;
        const params = [];

        if(date){
            sql += " AND DATE(e.event_date) >= ?";
            params.push(date);
        }
        if(city){
            sql += " AND e.city LIKE ?";
            params.push(`%${city}%`);
        }
        if(genre){
            sql += " AND b.music_genres LIKE ?";
            params.push(`%${genre}%`);
        }

        sql += " ORDER BY e.event_date ASC";
        const conn = await getConnection();
        const [events] = await conn.execute(sql, params);
        res.status(200).json(events);
    }catch (error){
        res.status(500).json({message: error.message});
    }
});

//natural language to sql query
router.post('/ai-search', async (req, res) => {
    try{
        const {query} = req.body;
        if(!query) return res.status(400).json({message: "Empty Query!"});
        const lcasequery = query.toLowerCase();
        
        let sql = "SELECT * FROM bands WHERE 1=1";
        let explain = "Show all bands.";

        //simple keyword spoting
        //e.g. "Bands in Heraklion that play Rock"
        //search cities
        const citymatch = lcasequery.match(/(?:in|at)\s+(\w+)/i);
        if(citymatch){
            sql += ` AND city LIKE '%${citymatch[1]}%'`;
            explain = `Search in city: ${citymatch[1]}`;
        }
        //search genres
        const genres = ['rock', 'pop', 'jazz', 'metal', 'funk', 'punk', 'blues'];
        const genrematch = genres.find(g => lcasequery.includes(g));
        if(genrematch){
            sql +=  ` AND music_genres LIKE '%${genrematch}%'`;
            explain += `, and Music Genre is: ${genrematch}`;
        }
        //findd founded year
        const yearmatch = lcasequery.match(/\b(19|20)\d{2}\b/);
        if(yearmatch){
            sql += ` AND founded_year = '${yearmatch[0]}'`;
            explain += `, with Founded Year: ${yearmatch[0]}`;
        }

        const conn = await getConnection();
        const [results] = await conn.execute(sql);
        res.status(200).json({
            sql_generated: sql,
            explanation: explain,
            results: results
        });
    }catch (error){
        res.status(500).json({message: error})
    }
});

//answers to questions about music genres
router.post('/ai-ask', (req, res) => {
    const {question} = req.body;
    if(!question) return res.status(400).json({answer: "Ask a question."});

    let answer = "Sorry, i cannot answer that.";
    if(question.toLowerCase().includes('rock')){
        answer="Rock is a broad genre characterized by a strong beat, blues"+
        " influences, and an ensemble typically centered on electric guitars, bass and drums.";
    }else if(question.toLowerCase().includes('pop')){
        answer="Pop is short for 'popular', this genre focuses on catchy melodies,"+
        " polished production, and relatable lyrics designed for wide commercial appeal.";
    }else if(question.toLowerCase().includes('jazz')){
        answer="Jazz is a sophisticated genre rooted in African American communities that"+
        " prioritizes complex harmonies, syncopated rhythms, and a heavy emphasis on improvisation.";
    }else if(question.toLowerCase().includes('metal')){
        answer="Metal is a high-energy evolution of rock defined by distorted guitars, powerful"+
        " drumming, and an overall aesthetic of intensity and volume.";
    }else if(question.toLowerCase().includes('funk')){
        answer="Funk is a rhythmic genre that de-emphasizes melody and chord progressions"+
        " in favor of a strong, syncopated 'groove' driven by the bass guitar and drums.";
    }else if(question.toLowerCase().includes('punk')){
        answer="Punk is a subculture-driven genre known for its short, fast-paced songs,"+
        " raw DIY production, and often anti-establishment or political lyrics.";
    }else if(question.toLowerCase().includes('blues')){
        answer="Blues is a foundational genre originating in the Deep South of the US, "+
        "characterized by its soulful 'blue notes' and a repetitive call-and-response structure.";
    }

    res.status(200).json({answer});
});

module.exports = router;