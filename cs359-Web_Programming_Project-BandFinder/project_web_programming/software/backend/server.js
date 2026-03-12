//  backend/server.js

const express = require('express');
const cors = require('cors');
const path = require('path');
const session = require('express-session');
const app = express();
//my server will run on port 3000
const PORT = 3000;

//import routes
const authRoutes = require('./routes/authRoutes');
const reviewRoutes = require('./routes/reviewRoutes');
const adminRoutes = require('./routes/adminRoutes');
const eventRoutes = require('./routes/eventRoutes');
const bandRoutes = require('./routes/bandRoutes');
const visitorRoutes = require('./routes/visitorRoutes');

//allow html page to talk to this server and the server to understand json
app.use(cors());
app.use(express.json());
app.use(express.static(path.join(__dirname, '../')));

//configure session
app.use(session({
    secret: 'secret-key-band-finder', 
    resave: false, 
    saveUninitialized: true, 
    cookie: {secure: false}
}));

//use routes
app.use('/', authRoutes);
app.use('/', reviewRoutes);
app.use('/admin', adminRoutes);
app.use('/api', eventRoutes);
app.use('/band', bandRoutes);
app.use('/public', visitorRoutes);

//start server
app.listen(PORT, () => {
    console.log(`Server running on http://localhost:${PORT}`);
    console.log(`Open http://localhost:${PORT}/html/index.html to view your site.`);
    console.log(`Open http://localhost:3000/html/admin_login.html to login as admin.`);
});