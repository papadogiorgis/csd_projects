// js/api.js

import { SERVER_URL } from './config.js';
import { checkPasswordStrength } from './password.js';

//helper to show messages at the bottom of the form
//to replace alerts :(
//(btw alerts dont bite, keep that in mind)
function showGlobalMessage(message, type){
    const msgDiv = document.getElementById('submission-message');
    if(msgDiv){
        msgDiv.textContent = message;
        msgDiv.className = `submission-message ${type}`;
        msgDiv.style.display = 'block';
    }
}
//helper to show or hide errors for specific fields(ajax duplicates)
function showFieldError(elementId, message, isError){
    const input = document.getElementById(elementId);
    let errorSpan = document.getElementById(`${elementId}-api-error`);

    if(!errorSpan){
        errorSpan = document.createElement('div');
        errorSpan.id = `${elementId}-api-error`;
        errorSpan.className = 'field-error'
        //insert after input
        if(input && input.parentNode){
            input.parentNode.insertBefore(errorSpan, input.nextSibling);
        }
    }
    if(isError){
        errorSpan.textContent = message;
        errorSpan.style.display = 'block';
        input.style.borderColor = 'red';
    }else{
        errorSpan.style.display = 'none';
        input.style.borderColor = 'green';
    }
}

export function setupDuplicateCheck(type, elementId){
    const input = document.getElementById(elementId);

    if (!input) return;
    input.addEventListener('blur', function(){
        const value = this.value;
        if (value.length < 3) return;

        fetch(`${SERVER_URL}/check-duplicate`, {
            method: 'POST', 
            headers: {'Content-Type': 'application/json'}, 
            body: JSON.stringify({type: type, value: value})
        })
        .then(async res => {
            if (res.status === 403){
                const json = await res.json();
                showFieldError(elementId, json.message, true);
            }else if(res.status === 200){
                showFieldError(elementId, '', false)
            }
        })
        .catch(err => console.error("Check Error: ", err));
    });
}

export function setupFormSubmission(config){
    const form = document.getElementById(config.formId);

    if (!form) return;

    form.addEventListener('submit', function(e) {
        e.preventDefault();

        //clear prev glob msg
        const msgDiv = document.getElementById('submission-message');
        if(msgDiv) msgDiv.style.display = 'none';

        const passwordFieldId = config.formId === 'band-form' ? 'band-password' : 'password';
        const confirmFieldId = config.formId === 'band-form' ? 'band-confirm-password' : 'confirm-password';

        const passwordInput = document.getElementById(passwordFieldId);
        const confirmInput = document.getElementById(confirmFieldId);

        const password = passwordInput ? passwordInput.value : '';
        const confirmPassword = confirmInput ? confirmInput.value : '';

        //location check for simple users
        if (config.formId === 'simple-user-form'){
            const latitude = document.getElementById('latitude').value;
            const longitude = document.getElementById('longitude').value;
            if(!latitude || !longitude){
                showGlobalMessage('Please verify your address before submitting the form.', 'error');
                return;
            }
        }

        //weak password strength check
        if (password){
            const result = checkPasswordStrength(password);
            if(result.strength === 'weak'){
                showGlobalMessage('Cannot submit form with weak password.', 'error');
                return;
            }
        }

        //mismatch check
        if (password !== confirmPassword){
            showGlobalMessage('Passwords do not match!', 'error');
            return;
        }

        //ajax submission
        const formData = new FormData(form);
        const data = Object.fromEntries(formData.entries());
        fetch(config.registerUrl, {
            method: 'POST', 
            headers: {'Content-Type': 'application/json'}, 
            body: JSON.stringify(data)
        })
        .then(async response => {
            const json = await response.json();

            if(response.status === 200){
                showGlobalMessage(json.message, 'success');
                console.log("Server Data: ", json.data);
                form.reset();
            }else{
                showGlobalMessage("Error: " + json.message, 'error');
            }
        })
        .catch(error => {
            console.error('Error: ', error);
            showGlobalMessage("A network error occured!!!", 'error');
        });
    });
}