// js/review_test.js

const SERVER_URL = "http://localhost:3000";

//helper func to dipslay results
function displayResult(elementId, data){
    document.getElementById(elementId).textContent = JSON.stringify(data, null, 2);
}

//post review
window.submitReview = function(){
    const data = {
        band_name: document.getElementById('post-band').value, 
        sender_name: document.getElementById('post-sender').value, 
        review: document.getElementById('post-text').value, 
        rating: document.getElementById('post-rating').value
    };

    fetch(`${SERVER_URL}/review`, {
        method: 'POST', 
        headers: {'Content-Type': 'application/json'}, 
        body: JSON.stringify(data)
    })
    .then(res => res.json().then(json => ({status: res.status, body: json})))
    .then(res => displayResult('post-result', res))
    .catch(err => displayResult('post-result', err));
};

//get reviwes
window.getReviews = function(){
    const band = document.getElementById('get-band').value || 'all';
    const from = document.getElementById('get-from').value;
    const to = document.getElementById('get-to').value;
    let url = `${SERVER_URL}/reviews/${band}`;
    const params = new URLSearchParams();

    if(from) params.append('ratingFrom', from);
    if(to) params.append('ratingTo', to);
    if(params.toString()) url += `?${params.toString()}`;

    fetch(url)
    .then(res => res.json().then(json => ({status: res.status, body: json})))
    .then(res => displayResult('get-result', res))
    .catch(err => displayResult('get-result', err));
};

//put status
window.updateStatus = function(){
    const id = document.getElementById('put-id').value;
    const status = document.getElementById('put-status').value;

    if(!id) return alert("Enter ID");

    fetch(`${SERVER_URL}/reviewStatus/${id}/${status}`, {method: 'PUT'})
    .then(res => res.json().then(json => ({status: res.status, body: json})))
    .then(res => displayResult('put-result', res))
    .catch(err => displayResult('put-result', err));
};

//delete review
window.deleteReview = function(){
    const id = document.getElementById('del-id').value;

    if(!id) return alert("Enter ID");

    fetch(`${SERVER_URL}/reviewDeletion/${id}`, {method: 'DELETE'})
    .then(res => res.json().then(json => ({status: res.status, body: json})))
    .then(res => displayResult('del-result', res))
    .catch(err => displayResult('del-result', err));
};