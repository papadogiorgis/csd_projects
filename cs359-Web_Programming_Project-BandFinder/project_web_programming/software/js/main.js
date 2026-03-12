// js/main.js

import { getFormConfig } from './config.js';
import { setupDuplicateCheck, setupFormSubmission} from './api.js';
import { setupPasswordFeatures } from './password.js';
import { createMapFeatures } from './map.js';
import { setupLogin, setupLogout } from './auth.js';
import { loadUserProfile } from './profile.js';

//wait for DOM to be fully loaded before executing scripts
document.addEventListener('DOMContentLoaded',function(){
    //check if were ina login page
    setupLogin();
    //check if were in a profile page
    loadUserProfile();
    setupLogout();

    const config = getFormConfig();
    if(config){
        //attach the listeners
        setupDuplicateCheck('username', config.usernameId);
        setupDuplicateCheck('email', config.emailId);
        setupFormSubmission(config);
        //init passwor features
        setupPasswordFeatures();
    }

    //init map features
    if (document.getElementById('map-container')) {
        createMapFeatures();
    }
});