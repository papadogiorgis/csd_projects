// js/visitor.js

import {SERVER_URL} from './config.js';

OpenLayers.ImgPath = "https://cdnjs.cloudflare.com/ajax/libs/openlayers/2.13.1/img";
let map = null;
let markersLayer = null;

document.addEventListener('DOMContentLoaded', () => {
    loadBands();
});

window.switchVisitorTab = function(tabName){
    document.querySelectorAll('.tab-content').forEach(el => el.classList.remove('active'));
    document.querySelectorAll('.tabs button').forEach(el => el.classList.remove('active'));
    document.getElementById(`tab-${tabName}`).classList.add('active');
    document.getElementById(`btn-${tabName}`).classList.add('active');
    if(tabName === 'events') loadEvents();
    if(tabName === 'map') initEventsMap();
}

//bands
window.loadBands = function(){
    const genre = document.getElementById('filter-genre').value;
    const city = document.getElementById('filter-city').value;
    const year = document.getElementById('filter-year').value;
    let url = `${SERVER_URL}/public/bands?`;

    if(genre) url+= `genre=${genre}&`;
    if(city) url+= `city=${city}&`;
    if(year) url+= `year=${year}`;

    fetch(url)
    .then(res => res.json())
    .then(bands => {
        const grid = document.getElementById('bands-grid');
        grid.innerHTML = '';
        if(bands.length === 0) grid.innerHTML = '<p>No bands found.</p>';

        bands.forEach(b => {
            const div = document.createElement('div');
            div.className = 'card';
            //show availabiliity if user signed in
            //for visitor just profile info
            div.innerHTML = `
            <h3>${b.band_name}</h3>
            <p><strong>Music Genres: </strong> ${b.music_genres}</p>
            <p><strong>City: </strong> ${b.city}</p>
            <p><strong>Founded Year: </strong> ${b.founded_year}</p>
            <p><i>${b.band_description}</i></p>
            ${b.photo ? `<img src="${b.photo}" style="width:100%; max-height:150px; object-fit:cover;">` : ''}
            <br>
            <small style="color:red;">Login to request an event</small>`;
            grid.appendChild(div);
        });
    });
}

//events
window.loadEvents = function(){
    const genre = document.getElementById('ev-filter-genre').value;
    const city = document.getElementById('ev-filter-city').value;
    const date = document.getElementById('ev-filter-date').value;
    let url = `${SERVER_URL}/public/events?`;

    if(genre) url+= `genre=${genre}&`;
    if(city) url+= `city=${city}&`;
    if(date) url+= `date=${date}`;

    fetch(url)
    .then(res => res.json())
    .then(events => {
        const grid = document.getElementById('events-grid');
        grid.innerHTML = '';
        if(events.length === 0) grid.innerHTML = '<p>No events found.</p>';

        events.forEach(ev => {
            const div = document.createElement('div');
            div.className = 'card';
            div.innerHTML = `
            <h3>${ev.title}</h3>
            <p><strong>Band: </strong> ${ev.band_name}</p>
            <p><strong>When: </strong> ${new Date(ev.event_date).toLocaleString()}</p>
            <p><strong>Where: </strong> ${ev.city}</p>
            <p>${ev.description}</p>`;
            grid.appendChild(div);
        });
    });
}

//map
window.initEventsMap = function(){
    //if map dont exist, create new
    if(!map){
        map = new OpenLayers.Map('map-large');
        const osm = new OpenLayers.Layer.OSM();
        markersLayer = new OpenLayers.Layer.Markers("Events");
        map.addLayers([osm, markersLayer]);
        //center map, greece approx
        const lonlat = new OpenLayers.LonLat(23.7275, 37.9838)
        .transform(
            new OpenLayers.Projection("EPSG:4326"),
            map.getProjectionObject()
        );
        map.setCenter(lonlat, 6);
    }

    fetch(`${SERVER_URL}/public/events`)
    .then(res => res.json())
    .then(events => {
        markersLayer.clearMarkers();
        events.forEach(ev => {
            if(ev.lat && ev.lon){
                const lonlat = new OpenLayers.LonLat(ev.lon, ev.lat)
                .transform(
                    new OpenLayers.Projection("EPSG:4326"),
                    map.getProjectionObject()
                );
                const size = new OpenLayers.Size(21, 25);
                const offset = new OpenLayers.Pixel(-(size.w / 2), -size.h);
                const icon = new OpenLayers.Icon('https://cdnjs.cloudflare.com/ajax/libs/openlayers/2.13.1/img/marker.png', size, offset);
                const marker = new OpenLayers.Marker(lonlat, icon);

                //popup on click
                marker.events.register('mousedown', marker, function(evt){
                    alert(`Event: ${ev.title}\nBand: ${ev.band_name}\nDate: ${new Date(ev.event_date).toLocaleDateString()}`);
                    OpenLayers.Event.stop(evt);
                });

                markersLayer.addMarker(marker);
            }
        });
    });
}

//ai search, natural language to sql
window.runAiSearch = function(){
    const query = document.getElementById('nl-input').value;
    if(!query) return;

    fetch(`${SERVER_URL}/public/ai-search`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({query})
    })
    .then(res => res.json())
    .then(data => {
        const resDiv = document.getElementById('nl-result');
        let html = `<p><strong>AI Thought:</strong> ${data.explanation}</p>`;
        html += `<p><strong>Generated SQL: </strong><code style="background:#eee;">${data.sql_generated}</code></p>`;

        if(data.results.length > 0){
            html += `<h4>Results (${data.results.length}):</h4><ul>`;
            data.results.forEach(b => {
                html += `<li>${b.band_name} (${b.music_genres}) - ${b.city}</li>`;
            });
            html += `</ul>`;
        }else{
            html += `<p>No results found.</p>`;
        }
        resDiv.innerHTML = html;
    });
}

//ai music qa
window.askMusicAi = function(){
    const question = document.getElementById('music-qa-input').value;
    if(!question) return;
    const history = document.getElementById('music-chat-history');

    //add user message
    const udiv = document.createElement('div');
    udiv.className = 'user-msg';
    udiv.textContent = question;
    history.appendChild(udiv);

    fetch(`${SERVER_URL}/public/ai-ask`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({question})
    })
    .then(res => res.json())
    .then(data => {
        //add bot response
        const bdiv = document.createElement('div');
        bdiv.className = 'bot-msg';
        bdiv.textContent = data.answer;
        history.appendChild(bdiv);
        history.scrollTop = history.scrollHeight;
        document.getElementById('music-qa-input').value = '';
    });
}