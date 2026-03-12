//  backend/database.js

const mysql = require('mysql2/promise');

async function getConnection(){
    const connection = await mysql.createConnection({
        host: 'localhost', 
        user: 'root', 
        password: '', 
        multipleStatements: true
    })
    //create database if dont exist already
    await connection.query(`CREATE DATABASE IF NOT EXISTS band_finder_db`);
    await connection.query(`USE band_finder_db`);

    //create user table
    const createUsers = `
    CREATE TABLE IF NOT EXISTS users (
    id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, 
    username VARCHAR(30) NOT NULL, 
    email VARCHAR(50) NOT NULL, 
    password VARCHAR(14) NOT NULL, 
    firstname VARCHAR(30), 
    lastname VARCHAR(30), 
    birthdate DATE, 
    gender VARCHAR(10), 
    country VARCHAR(50), 
    city VARCHAR(30), 
    address VARCHAR(150), 
    telephone VARCHAR(10), 
    lat DOUBLE, 
    lon DOUBLE, 
    reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP)`;

    //create bands table
    const createBands = `
    CREATE TABLE IF NOT EXISTS bands (
    id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, 
    username VARCHAR(30) NOT NULL, 
    email VARCHAR(50) NOT NULL, 
    password VARCHAR(14) NOT NULL, 
    band_name VARCHAR(50), 
    music_genres TEXT, 
    band_description TEXT, 
    members_number INT, 
    founded_year INT, 
    city VARCHAR(30), 
    telephone VARCHAR(10), 
    webpage VARCHAR(255), 
    photo VARCHAR(255), 
    reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP)`;

    //table for reviews for assignment 3.4
    const createReviews = `
    CREATE TABLE IF NOT EXISTS reviews (
    id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, 
    band_name VARCHAR(50) NOT NULL, 
    sender_name VARCHAR(50) NOT NULL, 
    review TEXT NOT NULL, 
    rating INT NOT NULL, 
    status VARCHAR(20) DEFAULT 'pending', 
    datetime DATETIME DEFAULT CURRENT_TIMESTAMP)`;

    //table for events
    const createEvents = `
    CREATE TABLE IF NOT EXISTS events (
    id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, 
    band_id INT(6) UNSIGNED, 
    user_id INT(6) UNSIGNED, 
    type VARCHAR(20) NOT NULL, 
    event_category VARCHAR(20), 
    title VARCHAR(100), 
    description TEXT, 
    status VARCHAR(20) DEFAULT 'scheduled', 
    justification TEXT, 
    cost DECIMAL(10, 2) DEFAULT 0.00, 
    event_date DATETIME, 
    city VARCHAR(30), 
    lat DOUBLE, 
    lon DOUBLE)`;

    //table for messages
    const createMessages = `
    CREATE TABLE IF NOT EXISTS messages (
    id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, 
    event_id INT(6) UNSIGNED NOT NULL, 
    sender_type VARCHAR(10) NOT NULL, 
    message TEXT NOT NULL, 
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)`;

    await connection.query(createUsers);
    await connection.query(createBands);
    await connection.query(createReviews);
    await connection.query(createEvents);
    await connection.query(createMessages);

    //insert admin (if not exists)
    const [admin] = await connection.execute("SELECT * FROM users WHERE username = 'admin'");
    if(admin.length === 0){
        const sql = `INSERT INTO users (username, email, password, firstname, lastname, country, city, address) 
        VALUES ('admin', 'admin@csd.uoc.gr', 'admiN12@*', 'Admin', 'Adminakis', 'GR', 'Heraklion', 'Agia Varvara Crete')`;
        await connection.execute(sql);
        console.log("Admin Created!");
    }
    
    return connection;
}

module.exports = { getConnection };