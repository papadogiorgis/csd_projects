// backend/routes/reviewRoutes.js

const express = require('express');
const router = express.Router();
const { getConnection } = require('../database');

//create a new review
router.post('/review', async (req, res) => {
    try{
        const {band_name, sender_name, review, rating} = req.body;
        //check inputs
        if(!band_name || !sender_name || !review || !rating){
            return res.status(406).json({message: "Missing fields!"});
        }
        //rating 1-5
        if(rating<1 || rating>5){
            return res.status(406).json({message: "Rating must be 1-5!"});
        }

        const conn = await getConnection();
        //check if band exists
        const [bands] = await conn.execute('SELECT * FROM bands WHERE band_name = ?', [band_name]);
        if(bands.length < 1){
            return res.status(404).json({message: "Band not found"});
        }
        //insert review
        const sql = `INSERT INTO reviews (band_name, sender_name, review, rating, status, datetime) VALUES (?, ?, ?, ?, 'pending', NOW())`;
        await conn.execute(sql, [band_name, sender_name, review, rating]);
        res.status(200).json({message: "Review submitted, approval is pending"});
    } catch (error){
        console.error(error);
        res.status(500).json({message: "Server Error!"});
    }
});

//get reviews
router.get('/reviews/:band_name', async (req, res) => {
    try{
        const bandName = req.params.band_name;
        const {ratingFrom, ratingTo} = req.query;
        let sql = "SELECT * FROM reviews WHERE status = 'published'";
        const params = [];

        //check if specific band or all
        if(bandName !== 'all'){
            sql += " AND band_name = ?";
            params.push(bandName);
        }

        //filtering of reviews by a rating range
        if(ratingFrom && ratingTo){
            if(parseInt(ratingFrom) <= parseInt(ratingTo)){
                sql+= " AND rating >= ? AND rating <= ?";
                params.push(ratingFrom, ratingTo);
            }else{
                return res.status(406).json({message: "ratingFrom must be <= ratingTo"});
            }
        }
        
        const conn = await getConnection();
        const [rows] = await conn.execute(sql, params);
        res.status(200).json(rows);
    }catch (error){
        console.error(error);
        res.status(500).json({message: "Server Error"});
    }
});

//update review status
router.put('/reviewStatus/:review_id/:status', async (req, res) => {
    try{
        const {review_id, status} = req.params;

        //check allowed status
        if(status !== 'published' && status !== 'rejected'){
            return res.status(406).json({message: "Status must be 'published' or 'rejected'!"});
        }

        const conn = await getConnection();

        //check if review exists
        const [check] = await conn.execute('SELECT id FROM reviews WHERE id = ?', [review_id]);
        if(check.length < 1){
            return res.status(404).json({message: "Review ID not found!"});
        }

        //update
        await conn.execute('UPDATE reviews SET status = ? WHERE id = ?', [status, review_id]);
        res.status(200).json({message: `Review ${review_id} status updated to ${status}`});
    }catch (error){
        console.error(error);
        res.status(500).json({message: "Server Error!"});
    }
});

//delete review
router.delete('/reviewDeletion/:review_id', async (req, res) => {
    try{
        const {review_id} = req.params;
        const conn = await getConnection();

        //validatoin check
        const [check] = await conn.execute('SELECT id FROM reviews WHERE id = ?', [review_id]);
        if(check.length < 1){
            return res.status(404).json({message: "Review not found!"});
        }
        await conn.execute('DELETE FROM reviews WHERE id = ?', [review_id]);
        res.status(200).json({message: `Review ${review_id} deleted!`});
    }catch (error){
        console.error(error);
        res.status(500).json({message: "Server Error!"});
    }
});

module.exports = router;