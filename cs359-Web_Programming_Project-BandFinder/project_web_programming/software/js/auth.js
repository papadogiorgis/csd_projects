// js/auth.js

import { SERVER_URL } from "./config.js";

function showGlobalMessage(message, type){
    const msgDiv = document.getElementById('submission-message');
    if(msgDiv){
        msgDiv.textContent = message;
        msgDiv.className = `submission-message ${type}`;
        msgDiv.style.display = 'block';
    }
}

export function setupLogin(){
    const loginForm = document.getElementById('login-form');
    if(!loginForm) return;

    loginForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const formData = new FormData(loginForm);
        const data = Object.fromEntries(formData.entries());

        fetch(`${SERVER_URL}/login`, {
            method: 'POST', 
            headers: {'Content-Type': 'application/json'}, 
            body: JSON.stringify(data)
        })
        .then(async res => {
            const json = await res.json();
            if(res.status === 200){
                //valid login
                showGlobalMessage(json.message, 'success');
                //wait 2 secs to show message and then go to profile.html if its a user
                setTimeout(() => {
                    if(json.type === 'user'){
                        window.location.href = 'user_dashboard.html';
                    }else if(json.type === 'band'){
                        window.location.href = 'band_dashboard.html';
                    }
                }, 2000);
            }else{
                showGlobalMessage(json.message, 'error');
            }
        })
        .catch(err => console.error(err));
    });
}

export function setupLogout(){
    const logoutBtn = document.getElementById('logout-btn');
    if(!logoutBtn) return;

    logoutBtn.addEventListener('click', () => {
        fetch(`${SERVER_URL}/logout`, {method: 'POST'})
        .then(() => {
            window.location.href = 'login.html';
        });
    });
}