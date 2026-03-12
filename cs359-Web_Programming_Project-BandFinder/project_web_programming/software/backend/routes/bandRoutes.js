// backend/routes/bandRoutes.js

const express = require('express');
const router = express.Router();
const { getConnection } = require('../database');

//check if user is band
function isBand(req, res, next){
    if(req.session.user && req.session.type === 'band'){
        return next();
    }
    return res.status(403).json({ message: "Unauthorized! Bands only!"});
}

//create public events
router.post('/public-event', isBand, async (req, res) => {
    try{
        const {title, event_date, city, description, lat, lon} = req.body;
        const band_id = req.session.user.id;
        if(!lat || !lon) return res.status(400).json({message: "Location coordinates required for public events."});
        const conn = await getConnection();
        const sql = `INSERT INTO events (band_id, type, title, description, status, event_date, city, lat, lon) 
        VALUES (?, 'public', ?, ?, 'scheduled', ?, ?, ?, ?)`;
        await conn.execute(sql, [band_id, title, description, event_date, city, lat, lon]);
        res.status(200).json({message: "Public event created!"});
    }catch (error){
        res.status(500).json({message: error.message});
    }
});

//get bands public events
router.get('/my-public-events', isBand, async (req, res) => {
    try{
        const band_id = req.session.user.id;
        const conn = await getConnection();
        const [events] = await conn.execute("SELECT * FROM events WHERE band_id = ? AND type = 'public'", [band_id]);
        res.status(200).json(events);
    }catch (error){
        res.status(500).json({message: error.message});
    }
});

//delete public events
router.delete('/delete-event/:id', isBand, async (req, res) => {
    try{
        const event_id = req.params.id;
        const band_id = req.session.user.id;
        const conn = await getConnection();

        const [check] = await conn.execute("SELECT * FROM events WHERE id=? AND band_id=?", [event_id, band_id]);
        if(check.length === 0) return res.status(404).json({message: "Event not found!"});
        await conn.execute("DELETE FROM events WHERE id=?", [event_id]);
        res.status(200).json({message: "Event deleted!"});
    }catch (error){
        res.status(500).json({message: error.message});
    }
});

//get private requests
router.get('/private-requests', isBand, async (req, res) => {
    try{
        const band_id = req.session.user.id;
        const conn = await getConnection();
        //join with users to see who requested
        const sql = `
        SELECT e.*, u.username as requester 
        FROM events e 
        JOIN users u ON e.user_id = u.id 
        WHERE e.band_id = ? AND e.type = 'private' 
        ORDER BY e.event_date ASC`;
        const [requests] = await conn.execute(sql, [band_id]);
        res.status(200).json(requests);
    }catch (error){
        res.status(500).json({message: error.message});
    }
});

//manage requests
//accept or reject with justification
router.put('/request-action', isBand, async (req, res) => {
    try{
        const {event_id, action, justification} = req.body;
        const band_id = req.session.user.id;
        if(action !== 'rejected' && action !== 'to be done'){
            return res.status(400).json({message: "Invalid action."});
        }
        const conn = await getConnection();
        //verify owner
        const [check] = await conn.execute("SELECT id FROM events WHERE id=? AND band_id=?", [event_id, band_id]);
        if(check.length === 0) return res.status(403).json({message: "Unauthorzied!"});
        const sql = `UPDATE events SET status=?, justification=? WHERE id=?`;
        await conn.execute(sql, [action, justification, event_id]);
        res.status(200).json({message: `Request updated to ${action}`});
    }catch (error){
        res.status(500).json({message: error.message});
    }
});

//delete rejected private event
router.delete('/delete-private/:id', isBand, async (req, res) => {
    try{
        const event_id = req.params.id;
        const band_id = req.session.user.id;
        const conn = await getConnection();

        const [check] = await conn.execute("SELECT status FROM events WHERE id=? AND band_id=?", [event_id, band_id]);
        if(check.length === 0) return res.status(400).json({message: "Event not found!"});
        if(check[0].status !== 'rejected') return res.status(400).json({message: "You can only delete rejected events!"});
        
        await conn.execute("DELETE FROM events WHERE id=?", [event_id]);
        res.status(200).json({message: "Event deleted!"});
    }catch (error){
        res.status(500).json({message: error.message});
    }
});

//get chat messages
router.get('/messages/:eventId', isBand, async (req, res) => {
    try{
        const event_id = req.params.eventId;
        const band_id = req.session.user.id;
        const conn = await getConnection();

        const [check] = await conn.execute("SELECT id FROM events WHERE id=? AND band_id=?", [event_id, band_id]);
        if(check.length === 0) return res.status(403).json({message: "Unauthorized!"});

        const [msg] = await conn.execute("SELECT * FROM messages WHERE event_id=? ORDER BY created_at ASC", [event_id]);
        res.status(200).json(msg);
    }catch (error){
        res.status(500).json({message: error.message});
    }
});

//send messages
router.post('/send-message', isBand, async (req, res) => {
    try{
        const {event_id, message} = req.body;
        const band_id = req.session.user.id;
        const conn = await getConnection();

        const [event] = await conn.execute("SELECT status FROM events WHERE id=? AND band_id=?", [event_id, band_id]);
        if((event.length === 0) || (event[0].status !== 'to be done')){
            return res.status(400).json({message: "Chat only allowed for events with status 'to be done'."});
        }
        await conn.execute("INSERT INTO messages (event_id, sender_type, message) VALUES (?, 'band', ?)", [event_id, message]);
        res.status(200).json({message: "Sent"});
    }catch (error){
        res.status(500).json({message: error.message});
    }
});

//get reviews
router.get('/my-reviews', isBand, async (req, res) => {
    try{
        const band_name = req.session.user.band_name;
        const conn = await getConnection();

        const [reviews] = await conn.execute("SELECT * FROM reviews WHERE band_name=? AND status='published'", [band_name]);
        res.status(200).json(reviews);
    }catch (error){
        res.status(500).json({message: error.message});
    }
});

module.exports = router;