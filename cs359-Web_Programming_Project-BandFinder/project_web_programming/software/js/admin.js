// js/admin.js

const SERVER_URL = 'http://localhost:3000';

//init
document.addEventListener('DOMContentLoaded', () => {
    loadUsers();
    google.charts.load('current', {'packages': ['corechart']});
});

window.switchTab = function(tabName){
    //hide tabs
    document.querySelectorAll('.tab-content').forEach(el => el.classList.remove('active'));
    document.querySelectorAll('.nav-links button').forEach(el => el.classList.remove('active'));
    //show selected
    document.getElementById(`tab-${tabName}`).classList.add('active');
    document.getElementById(`btn-${tabName}`).classList.add('active');
    //trigger data load
    if(tabName === 'users') loadUsers();
    if(tabName === 'reviews') loadReviews();
    if(tabName === 'stats') loadStats();
}

//logout
window.logout = function(){
    fetch(`${SERVER_URL}/logout`, {method: 'POST'})
    .then(() => window.location.href = 'admin_login.html');
}

//users
function loadUsers(){
    fetch(`${SERVER_URL}/admin/users`)
    .then(res => res.json())
    .then(users => {
        const tbody = document.getElementById('users-table-body');
        tbody.innerHTML = '';
        if(users.message){
            alert("Session Expired!");
            return window.location.href = 'admin_login.html';
        }
        users.forEach(user => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.email}</td>
            <td>${user.firstname || '-'} ${user.lastname || '-'}</td>
            <td><button class="btn-delete" onclick="deleteUser(${user.id})">Delete</button></td>`;
            tbody.appendChild(tr);
        });
    });
}

window.deleteUser = function(id){
    if(!confirm("Are you sure you want to delete them?")) return;
    fetch(`${SERVER_URL}/admin/delete-user/${id}`, {method: 'DELETE'})
    .then(res => res.json())
    .then(data => {
        alert(data.message);
        loadUsers();
    });
}

//reviews
function loadReviews(){
    fetch(`${SERVER_URL}/admin/pending-reviews`)
    .then(res => res.json())
    .then(reviews => {
        const tbody = document.getElementById('reviews-table-body');
        tbody.innerHTML = '';
        if(reviews.length === 0){
            tbody.innerHTML = '<tr><td colspan="6" style="text-align:center;">No Pending Reviews!</td></tr>';
            return;
        }
        reviews.forEach(review => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
            <td>${review.id}</td>
            <td>${review.band_name}</td>
            <td>${review.sender_name}</td>
            <td>${review.rating}/5</td>
            <td>${review.review}</td>
            <td>
            <button class="btn-approve" onclick="reviewAction(${review.id}, 'published')">Approve</button>
            <button class="btn-reject" onclick="reviewAction(${review.id}, 'rejected')">Reject</button>
            </td>`;
            tbody.appendChild(tr);
        });
    });
}

window.reviewAction = function(id, action){
    fetch(`${SERVER_URL}/admin/review-action`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({id, action})
    })
    .then(res => res.json())
    .then(data => {
        //refresh
        loadReviews();
    });
}

//stats
function loadStats(){
    fetch(`${SERVER_URL}/admin/stats`)
    .then(res => res.json())
    .then(data => {
        createCharts(data);
        document.getElementById('revenue-display').innerText = `${data.earnings}€`;
    });
}

function createCharts(data){
    //bands per city
    const cityData = [['City', 'Number of Bands']];
    data.bandspercity.forEach(item => cityData.push([item.city, item.count]));
    const cityTable = google.visualization.arrayToDataTable(cityData);
    const cityOptions = {title: 'Bands per City', pieHole: 0.4};
    const cityChart = new google.visualization.PieChart(document.getElementById('city-chart'));
    cityChart.draw(cityTable, cityOptions);

    //event types
    const eventData = [['Type', 'Count']];
    data.eventtypes.forEach(item => eventData.push([item.type, item.count]));
    const eventTable = google.visualization.arrayToDataTable(eventData);
    const eventOptions = {title: 'Events: Public vs Private', colors: ['#0000ff', '#ff0000']};
    const eventChart = new google.visualization.PieChart(document.getElementById('event-type-chart'));
    eventChart.draw(eventTable, eventOptions);

    //user vs band
    const countData = google.visualization.arrayToDataTable([
        ['Type', 'Count', {role: 'style'}],
        ['Simple Users', data.counts.users, 'color: #0000ff'],
        ['Bands', data.counts.bands, 'color: #ff0000']
    ]);
    const countOptions = {title: 'Registered Accounts', legend: {position: 'none'}};
    const countChart = new google.visualization.BarChart(document.getElementById('user-band-chart'));
    countChart.draw(countData, countOptions);
}