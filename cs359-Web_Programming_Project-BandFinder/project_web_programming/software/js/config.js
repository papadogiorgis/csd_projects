//  js/config.js

//point to the node.js server port 3000
export const SERVER_URL = "http://localhost:3000"

export function getFormConfig(){
    const isBandPage = document.getElementById('band-form') !== null;
    if(isBandPage){
        return {
            formId: 'band-form', 
            usernameId: 'band-username', 
            emailId: 'band-email', 
            registerUrl: `${SERVER_URL}/register-band`
        }
    }else{
        return {
            formId: 'simple-user-form', 
            usernameId: 'username', 
            emailId: 'email', 
            registerUrl: `${SERVER_URL}/register-user`
        }
    }
}