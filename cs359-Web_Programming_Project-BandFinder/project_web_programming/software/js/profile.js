// js/profile.js

import { SERVER_URL } from "./config.js";
import {checkPasswordStrength, setupPasswordFeatures} from "./password.js";

//global var to store user type so i know where to go back
let currentUserType = '';

function showGlobalMessage(message, type){
    const msgDiv = document.getElementById('submission-message');
    if(msgDiv){
        msgDiv.textContent = message;
        msgDiv.className = `submission-message ${type}`;
        msgDiv.style.display = 'block';
    }
}

//helper to enable/disable fields in a section
function toggleSectionInputs(sectionId, enable){
    const section = document.getElementById(sectionId);
    if(!section) return;

    section.style.display = enable? 'block' : 'none';
    const inputs = section.querySelectorAll('input, select, textarea');
    inputs.forEach(input => {
        input.disabled = !enable;
    });
}

export function loadUserProfile(){
    const profileForm = document.getElementById('profile-form');

    //run if form exists
    if(!profileForm) return;

    //fetch user data on page load
    fetch(`${SERVER_URL}/get-user`)
    .then(async res => {
        if(res.status === 401){
            //if theyre not locked in go to loginhtml
            window.location.href = 'login.html';
            return;
        }
        const data = await res.json();
        const user = data.user;
        currentUserType = data.type;

        //populate common fields
        document.getElementById('username').value = user.username;
        document.getElementById('email').value = user.email;
        document.getElementById('telephone').value = user.telephone || '';
        
        if(currentUserType === 'band'){
            toggleSectionInputs('band-section', true);
            toggleSectionInputs('user-section', false);
            populateBandForm(user);
        }else{
            toggleSectionInputs('band-section', false);
            toggleSectionInputs('user-section', true);
            populateUserForm(user);
        }

        setupPasswordFeatures();
    })
    .catch(err => console.error(err));

    //handle back button
    const backBtn = document.getElementById('back-btn');
    if(backBtn){
        backBtn.addEventListener('click', () => {
            if(currentUserType === 'user'){
                window.location.href = 'user_dashboard.html';
            }else if(currentUserType === 'band'){
                window.location.href = 'band_dashboard.html';
            }else if(currentUserType === 'admin'){
                window.location.href = 'admin_dashboard.html';
            }else{
                window.location.href = 'index.html';
            }
        });
    }

    //handles update submission
    profileForm.addEventListener('submit', (e) => {
        e.preventDefault();

        const password = document.getElementById('password').value;
        const confirm = document.getElementById('confirm-password').value;
        if(password !== confirm){
            showGlobalMessage("Passwords dont match!", "error");
            return;
        }
        const strength = checkPasswordStrength(password);
        if(strength.strength === 'weak'){
            showGlobalMessage("This password is too weak!", "error");
            return;
        }

        const formData = new FormData(profileForm);
        const data = Object.fromEntries(formData.entries());

        fetch(`${SERVER_URL}/update-user`, {
            method: 'POST', 
            headers: {'Content-Type': 'application/json'}, 
            body: JSON.stringify(data)
        })
        .then(async res => {
            const json = await res.json();
            if(res.status === 200){
                showGlobalMessage(json.message, 'success');
                setTimeout(() => location.reload(), 2000);
            }else{
                showGlobalMessage(json.message, 'error');
            }
        })
        .catch(err => showGlobalMessage("Network Error", 'error'));
    });
}

function populateUserForm(user){
    //i dont show the saved passwords for security
    document.getElementById('firstname').value = user.firstname || '';
    document.getElementById('lastname').value = user.lastname || '';
    //format date to yyyy-mm-dd
    if(user.birthdate){
        const dateObj = new Date(user.birthdate);
        //adjust for timezone so toISOString() doesnt shift to yesterday
        const localDate = new Date(dateObj.getTime() - (dateObj.getTimezoneOffset()*60000));
        document.getElementById('birthdate').value = localDate.toISOString().split('T')[0];
    }
    if(user.gender === 'Male') document.getElementById('male').checked = true;
    if(user.gender === 'Female') document.getElementById('female').checked = true;
    if(user.gender === 'Other') document.getElementById('other').checked = true;

    //setup dropdowns
    const countrySelect = document.getElementById('country');
    if(countrySelect && user.country) countrySelect.value = user.country;

    document.getElementById('city').value = user.city || '';
    document.getElementById('address').value = user.address || '';
    document.getElementById('latitude').value = user.lat || '';
    document.getElementById('longitude').value = user.lon || '';
}

function populateBandForm(band){
    document.getElementById('band_name').value = band.band_name || '';
    document.getElementById('music_genres').value = band.music_genres || '';
    document.getElementById('band_description').value = band.band_description || '';
    document.getElementById('members_number').value = band.members_number || '';
    document.getElementById('foundedYear').value = band.founded_year || '';
    document.getElementById('band_city').value = band.city || '';
    document.getElementById('webpage').value = band.webpage || '';
    document.getElementById('photo').value = band.photo || '';
}