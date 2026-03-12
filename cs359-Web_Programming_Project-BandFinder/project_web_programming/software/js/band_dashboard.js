// js/band_dashboard.js

import {SERVER_URL} from './config.js';

document.addEventListener('DOMContentLoaded', () => {
    loadPublicEvents();
    document.getElementById('public-event-form').addEventListener('submit', createPublicEvent);

    //geocoding for event
    document.getElementById('verify-city-btn').addEventListener('click', () => {
        const city = document.getElementById('event_city').value;
        const resDiv = document.getElementById('city-result');
        const btn = document.getElementById('verify-city-btn');

        if(!city){alert("ENTER A CITY!"); return;}
        btn.textContent = "Searching City Coordinates...";

        fetch(`https://forward-reverse-geocoding.p.rapidapi.com/v1/search?q=${encodeURIComponent(city)}&format=json`, {
            method: 'GET',
            headers: {
                'X-RapidAPI-Key': 'fdca85054cmshaa64c117e38ccc4p13fb03jsn2a91d994b9dc',
                'X-RapidAPI-Host': 'forward-reverse-geocoding.p.rapidapi.com'
            }
        })
        .then(res => res.json())
        .then(data => {
            if(data.length > 0){
                document.getElementById('lat').value = data[0].lat;
                document.getElementById('lon').value = data[0].lon;
                resDiv.style.display = 'block';
                resDiv.style.display = 'color:green;';
                resDiv.textContent = `Found: ${data[0].display_name}`;
                btn.textContent = "Find City Coordinates";
            }else{
                resDiv.style.display = 'block';
                resDiv.style.display = 'color:red;';
                resDiv.textContent = 'Could not find city!';
                btn.textContent = "Find City Coordinates";
            }
        })
        .catch(err => {
            console.error(err);
        });
    });
});

window.showTab = function(tabName){
    document.querySelectorAll('.tab-content').forEach(el => el.classList.remove('active'));
    document.querySelectorAll('.nav-items button').forEach(el => el.classList.remove('active'));

    document.getElementById(`tab-${tabName}`).classList.add('active');
    document.getElementById(`btn-${tabName}`).classList.add('active');
    if(tabName === 'public') loadPublicEvents();
    if(tabName === 'private') loadPrivateRequests();
    if(tabName === 'profile') loadReviews();
}

window.logout = function(){
    fetch(`${SERVER_URL}/logout`, {method: 'POST'})
    .then(() => window.location.href = 'login.html');
}

function createPublicEvent(e){
    e.preventDefault();
    const formData = new FormData(e.target);
    const data = Object.fromEntries(formData.entries());

    if((!data.lat)||(!data.lon)){
        alert("Click 'Find City Coordinates' before creating the event.");
        return;
    }
    
    fetch(`${SERVER_URL}/band/public-event`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    })
    .then(res => res.json())
    .then(result => {
        alert(result.message);
        e.target.reset();
        document.getElementById('city-result').style.display='none';
        loadPublicEvents();
    });
}

function loadPublicEvents(){
    fetch(`${SERVER_URL}/band/my-public-events`)
    .then(res => res.json())
    .then(events => {
        const list = document.getElementById('public-events-list');
        list.innerHTML = '';
        events.forEach(ev => {
            const div = document.createElement('div');
            div.className = 'event-header';
            div.innerHTML = `
            <div class="event-header">
            <h4>${ev.title}</h4>
            <button class="btn-reject" onclick="deletePublicEvent(${ev.id})">Delete</button>
            </div>
            <p>Date: ${new Date(ev.event_date).toLocaleString()}</p>
            <p>City: ${ev.city}</p>
            <p>${ev.description}</p>`;
            list.appendChild(div);
        });
    });
}

window.deletePublicEvent = function(id){
    if(!confirm("Delete this event??")) return;

    fetch(`${SERVER_URL}/band/delete-event/${id}`, {method: 'DELETE'})
    .then(res => res.json())
    .then(data => {
        alert(data.message);
        loadPublicEvents();
    });
}

//private requests
function loadPrivateRequests(){
    fetch(`${SERVER_URL}/band/private-requests`)
    .then(res => res.json())
    .then(reqs => {
        const list = document.getElementById('requests-list');
        list.innerHTML = '';
        if(reqs.length === 0) list.innerHTML = "<p>No requests yet!</p>";

        reqs.forEach(r => {
            let actions = '';
            if(r.status === 'requested'){
                actions = `
                <button class="btn-accept" onclick="openJustify(${r.id}, 'to be done')">Accept</button>
                <button class="btn-reject" onclick="openJustify(${r.id}, 'rejected')">Reject</button>`;
            }else if(r.status === 'rejected'){
                actions = `<button class="btn-reject" onclick="deletePrivateRequest(${r.id})">Delete</button>`;
            }else if(r.status === 'to be done'){
                actions = `<button class="verify-btn" onclick="openChat(${r.id})">Chat</button>`;
            }else if(r.status === 'done'){
                actions = `<span>Completed</span>`;
            }

            const div = document.createElement('div');
            div.className = 'event-card';
            div.innerHTML = `
            <div class="event-header">
            <h4>${r.event_category.toUpperCase()} request from ${r.requester}</h4>
            <span>Status: ${r.status}</span>
            </div>
            <p>Date: ${new Date(r.event_date).toDateString()}</p>
            <p>City: ${r.city}</p>
            <p>Description: ${r.description}</p>
            <p>Cost: ${r.cost}€</p>
            <div style="margin-top:10px;">${actions}</div>`;
            list.appendChild(div);
        });
    });
}

window.openJustify = function(id, type){
    document.getElementById('justify-modal').style.display = 'block';
    document.getElementById('action-event-id').value = id;
    document.getElementById('action-type').value = type;
}

window.closeJustify = function(){
    document.getElementById('justify-modal').style.display = 'none';
}

window.submitAction = function(){
    const id = document.getElementById('action-event-id').value;
    const type = document.getElementById('action-type').value;
    const text = document.getElementById('justify-text').value;

    fetch(`${SERVER_URL}/band/request-action`, {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({event_id: id, action: type, justification: text})
    })
    .then(res => res.json())
    .then(data => {
        alert(data.message);
        closeJustify();
        loadPrivateRequests();
    });
}

window.deletePrivateRequest = function(id){
    if(!confirm("You sure you want to delete this request?")) return;

    fetch(`${SERVER_URL}/band/delete-private/${id}`, {method: 'DELETE'})
    .then(res => res.json())
    .then(data => {
        alert(data.message);
        loadPrivateRequests();
    });
}

//reviews
function loadReviews(){
    fetch(`${SERVER_URL}/band/my-reviews`)
    .then(res => res.json())
    .then(reviews => {
        const list = document.getElementById('reviews-list');
        list.innerHTML = '';
        if(reviews.length === 0) list.innerHTML = "<p>No reviews yet!</p>";

        reviews.forEach(r => {
            const div = document.createElement('div');
            div.className = 'event-card';
            div.innerHTML = `
            <p><strong>${r.sender_name}</strong> rated: ${r.rating}/5</p>
            <p>"${r.review}"</p>`;
            list.appendChild(div);
        });
    });
}

//chat
window.openChat = function(eventId){
    const modal = document.getElementById('chat-modal');
    document.getElementById('chat-event-id').value = eventId;
    modal.style.display = 'block';
    loadMessages(eventId);
    window.chatInterval = setInterval(() => loadMessages(eventId), 3000);
}

window.closeChat = function(){
    document.getElementById('chat-modal').style.display = 'none';
    clearInterval(window.chatInterval);
}

function loadMessages(eventId){
    fetch(`${SERVER_URL}/band/messages/${eventId}`)
    .then(res => res.json())
    .then(msg => {
        const history = document.getElementById('chat-history');
        history.innerHTML = '';
        msg.forEach(m => {
            const div = document.createElement('div');
            div.className = `message ${m.sender_type === 'band' ? 'msg-band' : 'msg-user'}`;
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

    fetch(`${SERVER_URL}/band/send-message`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({event_id: eventId, message: message})
    })
    .then(() => {
        input.value = '';
        loadMessages(eventId);
    });
}