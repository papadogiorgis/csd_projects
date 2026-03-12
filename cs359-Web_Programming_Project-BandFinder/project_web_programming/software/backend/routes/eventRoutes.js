// backend/routes/eventRoutes.js

const express = require('express');
const router = express.Router();
const { getConnection } = require('../database');

//helper to check if user logged in
function isUser(req, res, next){
    if(req.session.user && req.session.type === 'user'){
        return next();
    }
    return res.status(403).json({message: "Unauthorized! Users only!"});
}

//get all bands for selection in booking
router.get('/bands', async (req, res) => {
    try{
        const conn = await getConnection();
        const [bands] = await conn.execute("SELECT id, band_name, music_genres, city FROM bands");
        res.status(200).json(bands);
    }catch (error){
        console.error("Error fetching bands: ", error);
        res.status(500).json({message: error.message});
    }
});

//request private event
router.post('/request-event', isUser, async (req, res) => {
    try{
        const {band_id, event_category, description, event_date, city} = req.body;
        const user_id = req.session.user.id;
        //pricing
        let cost =0;
        if(event_category === 'baptism') cost = 700;
        else if(event_category === 'wedding') cost = 1000;
        else if(event_category === 'party') cost = 500;

        const conn = await getConnection();
        const sql = `INSERT INTO events (band_id, user_id, type, event_category, description, status, cost, event_date, city) 
        VALUES (?, ?, 'private', ?, ?, 'requested', ?, ?, ?)`;
        await conn.execute(sql, [band_id, user_id, event_category, description, cost, event_date, city]);
        res.status(200).json({message: "Event requested! Waiting for band's approval."});
    }catch (error){
        console.error(error);
        res.status(500).json({message: error.message});
    }
});

//get users private events
router.get('/my-events', isUser, async (req, res) => {
    try{
        const user_id = req.session.user.id;
        const conn = await getConnection();
        //join with bands table to get band name
        const sql = `
        SELECT e.*, b.band_name 
        FROM events e 
        JOIN bands b ON e.band_id = b.id 
        WHERE e.user_id = ? AND e.type = 'private' 
        ORDER BY e.event_date DESC`;
        const [events] = await conn.execute(sql, [user_id]);
        res.status(200).json(events);
    }catch (error){
        res.status(500).json({message: error.message});
    }
});

//mark events as done
router.put('/complete-event/:id', isUser, async (req, res) => {
    try{
        const eventId = req.params.id;
        const userId = req.session.user.id;
        const conn = await getConnection();
        //check if event belongs to user and is 'to be done'
        const [check] = await conn.execute("SELECT * FROM events WHERE id = ? AND user_id = ?", [eventId, userId]);
        if(check.length === 0) return res.status(404).json({message: "Event not found!"});
        if(check[0].status !== 'to be done') return res.status(400).json({message: "Event doesnt have status 'to be done'!"});
        await conn.execute("UPDATE events SET status = 'done' WHERE id = ?", [eventId]);
        res.status(200).json({message: "This event is marked as done!"});
    }catch (error){
        res.status(500).json({message: error.message});
    }
});

//get messages for an event
router.get('/messages/:eventId', isUser, async (req, res) => {
    try{
        const eventId = req.params.eventId;
        const userId = req.session.user.id;
        const conn = await getConnection();
        //verify owner
        const [check] = await conn.execute("SELECT id FROM events WHERE id = ? AND user_id = ?", [eventId, userId]);
        if(check.length === 0) return res.status(403).json({message: "Unauthorized!"});
        const [msg] = await conn.execute("SELECT * FROM messages WHERE event_id = ? ORDER BY created_at ASC", [eventId]);
        res.status(200).json(msg);
    }catch (error){
        res.status(500).json({message: error.message});
    }
});

//send messgae
router.post('/send-message', isUser, async (req, res) => {
    try{
        const {event_id, message} = req.body;
        const userId = req.session.user.id;
        const conn = await getConnection();
        //check event status
        //messages inly for 'to be done' status
        const [event] = await conn.execute("SELECT status FROM events WHERE id = ? AND user_id = ?", [event_id, userId]);
        if((event.length === 0)||(event[0].status !== 'to be done')){
            return res.status(400).json({message: "Chat only available for events with status 'to be done'."});
        }
        await conn.execute("INSERT INTO messages (event_id, sender_type, message) VALUES (?, 'user', ?)", [event_id, message]);
        res.status(200).json({message: "Message Sent"});
    }catch (error){
        res.status(500).json({message: error.message});
    }
});

//public events
//for sort by dist
router.get('/public-events', async (req, res) => {
    try{
        const conn = await getConnection();
        //join with bands
        //tio get badn name
        const sql = `
        SELECT e.*, b.band_name 
        FROM events e 
        JOIN bands b ON e.band_id = b.id 
        WHERE e.type = 'public' AND e.status = 'scheduled'`;
        const [events] = await conn.execute(sql);
        res.status(200).json(events);
    }catch (error){
        console.error("Error fetching public events: ", error);
        res.status(500).json({message: error.message});
    }
});

module.exports = router;