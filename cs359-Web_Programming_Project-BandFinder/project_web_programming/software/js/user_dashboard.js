// js/user_dashboard.js

import { SERVER_URL } from './config.js';
//global user coordinates
let userlat = null;
let userlon = null;
let currentUsername = '';

document.addEventListener('DOMContentLoaded', () => {
    fetchUserData();
    loadPublicEvents();
    loadBands();
    document.getElementById('booking-form').addEventListener('submit', handleBooking);
    document.getElementById('review-form').addEventListener('submit', submitReview);
});

//navigation
window.showTab = function(tabName){
    document.querySelectorAll('.tab-content').forEach(el => el.classList.remove('active'));
    document.querySelectorAll('.nav-items button').forEach(el => el.classList.remove('active'));
    document.getElementById(`tab-${tabName}`).classList.add('active');
    document.getElementById(`btn-${tabName}`).classList.add('active');
    if(tabName === 'my-events') loadMyEvents();
}

//logout
window.logout = function(){
    fetch(`${SERVER_URL}/logout`, {method: 'POST'})
    .then(() => window.location.href = 'login.html');
}

//fetch user coords
function fetchUserData(){
    fetch(`${SERVER_URL}/get-user`)
    .then(res => res.json())
    .then(data => {
        if(data.user){
            userlat = data.user.lat;
            userlon = data.user.lon;
            currentUsername = data.user.username;
        }else{
            window.location.href = 'login.html';
        }
    });
}

//public events and distance
//haversine formula to calculate dist in km
function getDistance(lat1, lon1, lat2, lon2){
    if(!lat1 || !lat2 || !lon1 || !lon2) return 99999;
    //earth radius
    const R = 6371;
    const dlat = deg2rad(lat2 - lat1);
    const dlon = deg2rad(lon2 - lon1);
    const a = (Math.sin(dlat/2) * Math.sin(dlat/2)) + (Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dlon/2) * Math.sin(dlon/2));
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    const d = R * c;
    return d;
}

function deg2rad(deg){
    return deg * (Math.PI/180);
}

function loadPublicEvents(){
    fetch(`${SERVER_URL}/api/public-events`)
    .then(res => res.json())
    .then(events => {
        window.publicEvents = events;
        renderPublicEvents(events);
    });
}

function renderPublicEvents(events){
    const container = document.getElementById('public-events-list');
    container.innerHTML = '';
    if(events.length === 0){
        container.innerHTML = '<p>No public events found!</p>';
        return;
    }
    events.forEach(event => {
        const div = document.createElement('div');
        div.className = 'event-card';
        //add distance
        let distHtml = '';
        if(event.distance !== undefined){
            distHtml = `<br><strong>Distance:</strong> ${event.distance.toFixed(2)} km`;
        }
        const reviewBtn = `
        <button class="verify-btn" onclick="openReview('${event.band_name}')" style="margin-top:10px;">Review Band</button>`;
        div.innerHTML = `
        <div class="event-header">
        <h3>${event.title || 'Event'} by ${event.band_name}</h3>
        <span class="status-badge status-done">Public</span>
        </div>
        <p>${event.description || 'No description'}</p>
        <p><strong>Location:</strong> ${event.city} ${distHtml}</p>
        <p><strong>Date:</strong> ${new Date(event.event_date).toLocaleString()}</p>
        ${reviewBtn}`;
        container.appendChild(div);
    });
}

window.sortEventsByDistance = function(){
    if(!userlat || !userlon){
        alert("Your location isnt set in your profile.");
        return;
    }
    //calculate dits for each event
    window.publicEvents.forEach(ev => {
        ev.distance = getDistance(userlat, userlon, ev.lat, ev.lon);
    });
    //sort
    window.publicEvents.sort((a, b) => a.distance - b.distance);
    //rerender
    renderPublicEvents(window.publicEvents);
}

//booking
function loadBands(){
    fetch(`${SERVER_URL}/api/bands`)
    .then(res => res.json())
    .then(bands => {
        const select = document.getElementById('band-select');
        select.innerHTML = '';
        bands.forEach(band => {
            const opt = document.createElement('option');
            opt.value = band.id;
            opt.textContent = `${band.band_name} (${band.music_genres})`;
            select.appendChild(opt);
        });
    });
}

function handleBooking(e){
    e.preventDefault();
    const formData = new FormData(e.target);
    const data = Object.fromEntries(formData.entries());

    fetch(`${SERVER_URL}/api/request-event`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    })
    .then(res => res.json())
    .then(result => {
        alert(result.message);
        e.target.reset();
        showTab('my-events');
    })
    .catch(err => alert("Error booking event"));
}

//my events
function loadMyEvents(){
    fetch(`${SERVER_URL}/api/my-events`)
    .then(res => res.json())
    .then(events => {
        const container = document.getElementById('my-events-list');
        container.innerHTML = '';
        if(events.length === 0){
            container.innerHTML = '<p>No requests yet.</p>';
            return;
        }
        events.forEach(ev => {
            let actionBtn = '';
            let chatBtn = '';
            let reviewBtn = '';
            let statusClass = `status-${ev.status.replace(/\s+/g, '')}`;

            if(ev.status === 'to be done'){
                chatBtn = `<button class="verify-btn" onclick="openChat(${ev.id})" style="margin-right:5px;">Chat</button>`;
                actionBtn = `<button class="submit-btn" style="width:auto; padding:5px 15px;" onclick="markDone(${ev.id})">Mark Done</button>`;
            }

            if(ev.status === 'done'){
                reviewBtn = `<button class="verify-btn" onclick="openReview('${ev.band_name}')">Review Band</button>`;
            }

            const div = document.createElement('div');
            div.className = 'event-card';
            div.innerHTML = `
            <div class="event-header">
            <h3>${ev.event_category? ev.event_category.toUpperCase() : 'Event'} with ${ev.band_name}</h3>
            <span class="status-badge ${statusClass}">${ev.status}</span></div>
            <p><strong>Cost:</strong> ${ev.cost}€</p>
            <p><strong>Date:</strong> ${new Date(ev.event_date).toLocaleString()}</p>
            <p><strong>Description:</strong> ${ev.description}</p>
            ${ev.justification ? `<p style="color:red;"><strong>Band Note:</strong> ${ev.justification}</p>` : ''}
            <div style="margin-top:10px;">
            ${chatBtn}
            ${actionBtn}
            ${reviewBtn}
            </div>`;

            container.appendChild(div);
        });
    });
}

window.markDone = function(id){
    if(!confirm("Did the event take place successfully?")) return;

    fetch(`${SERVER_URL}/api/complete-event/${id}`, {method: 'PUT'})
    .then(res => res.json())
    .then(data => {
        alert(data.message);
        loadMyEvents();
    });
}

//chat system
window.openChat = function(eventId){
    const modal = document.getElementById('chat-modal');
    document.getElementById('chat-event-id').value = eventId;
    modal.style.display = 'block';
    loadMessages(eventId);
    //check for new messages every 3 seconds
    window.chatInterval = setInterval(() => loadMessages(eventId), 3000);
}

window.closeChat = function(){
    document.getElementById('chat-modal').style.display = 'none';
    clearInterval(window.chatInterval);
}

function loadMessages(eventId){
    fetch(`${SERVER_URL}/api/messages/${eventId}`)
    .then(res => res.json())
    .then(msg => {
        const history = document.getElementById('chat-history');
        history.innerHTML = '';
        msg.forEach(m => {
            const div = document.createElement('div');
            div.className = `message ${m.sender_type === 'user' ? 'msg-user' : 'msg-band'}`;
            div.textContent = m.message;
            history.appendChild(div);
        });
        history.scrollTop = history.scrollHeight;
    });
}

window.sendMessage = function(){
    const input = document.getElementById('chat-input');
    const message = input.value;
    const eventId = document.getElementById('chat-event-id').value;
    if(!message) return;

    fetch(`${SERVER_URL}/api/send-message`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({event_id: eventId, message: message})
    })
    .then(() => {
        input.value = '';
        loadMessages(eventId);
    });
}

//reviews
window.openReview = function(bandName){
    const modal = document.getElementById('review-modal');
    document.getElementById('review-band-name').value = bandName;
    document.getElementById('review-band-name-display').textContent = bandName;
    //autofill username
    if(currentUsername){
        document.getElementById('review-sender-name').value = currentUsername;
    }
    modal.style.display = 'block';
}

window.closeReview = function(){
    document.getElementById('review-modal').style.display = 'none';
    document.getElementById('review-form').reset();
}

function submitReview(e){
    e.preventDefault();
    const formData = new FormData(e.target);
    const data = Object.fromEntries(formData.entries());

    fetch(`${SERVER_URL}/review`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    })
    .then(res => res.json())
    .then(result => {
        alert(result.message);
        closeReview();
    })
    .catch(err => alert("Error submitting your review!"));
}