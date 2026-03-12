//  js/password.js

//password strength validation function
export function checkPasswordStrength(password){
    //check password length
    if(password.length < 8){
        return{ strength: 'weak', message: 'Password must be at least 8 characters long!'}
    }
    if(password.length > 14){
        return{ strength: 'weak', message: 'Password must be at most 14 characters long!'}
    }

    //forbidden words
    const forbiddenWords = ['band', 'music', 'mpanta', 'mousiki'];
    const passLowerCase = password.toLowerCase();

    //forbidden words check
    for(const word of forbiddenWords){
        if(passLowerCase.includes(word)){
            return{ strength:'weak', message: `Password contains forbidden word: "${word}"`};
        }
    }

    //count digits
    let n = 0;
    for(let i=0; i<password.length; i++){
        if(password.charAt(i) >= '0' && password.charAt(i) <= '9'){
            n++;
        }
    }

    //number of digits check
    if((n/password.length) >= 0.4){
        return{ strength: 'weak', message: 'Password has too many digits!'};
    }

    //count repeated characters
    const charCount = {};
    for(let i=0; i<password.length; i++){
        const char = password.charAt(i);
        charCount[char] = (charCount[char] || 0) + 1;
    }

    //repeated characters check
    const max = Math.max(...Object.values(charCount));
    if((max/password.length)>=0.5){
        return{ strength: 'weak', message: 'Password has too many repeated characters!'};
    }

    //strong password check
    const hasLowercase = /[a-z]/.test(password);
    const hasUppercase = /[A-Z]/.test(password);
    const hasDigit = /\d/.test(password);
    const hasSymbol = /[!@#$%&]/.test(password);

    if(hasLowercase && hasUppercase && hasDigit && hasSymbol){
        return{ strength: 'strong', message: 'Your password is Strong!'};
    }

    return{ strength: 'medium', message: 'Medium password'};
}

//password match validation
export function PasswordMatchValidation(passwordId, confirmPasswordId, messageId){
    const password = document.getElementById(passwordId).value;
    const confirmPassword = document.getElementById(confirmPasswordId).value;
    const message = document.getElementById(messageId);

    if(password && confirmPassword){
        if(password !== confirmPassword){
            message.textContent = 'Passwords do not match! Try again.';
            message.style.display = 'block';
            return false;
        }else{
            message.style.display = 'none';
            return true;
        }
    }
    return false;
}

export function setupPasswordFeatures(){
    //toggle password
    document.querySelectorAll('.toggle-password').forEach(button => {
        button.addEventListener('click', function() {
            const targetId = this.getAttribute('data-target');
            const passwordInput = document.getElementById(targetId);

            if(passwordInput.type === 'password'){
                passwordInput.type = 'text';
                this.textContent = 'Hide';
            }else{
                passwordInput.type = 'password';
                this.textContent = 'Show';
            }
        });
    });

    //event listeners for password match validation
    const confirmPasswordInput = document.getElementById('confirm-password');
    const bandconfirmPasswordInput = document.getElementById('band-confirm-password');
    if(confirmPasswordInput){
        confirmPasswordInput.addEventListener('input', function() {
            PasswordMatchValidation('password', 'confirm-password', 'password-match-message');
        });
    }

    if(bandconfirmPasswordInput){
        bandconfirmPasswordInput.addEventListener('input', function() {
            PasswordMatchValidation('band-password', 'band-confirm-password', 'band-password-match-message');
        });
    }

    //real-time password strength validation for simple users
    const passwordInput = document.getElementById('password');
    if(passwordInput){
        passwordInput.addEventListener('input', function(){
            const password = this.value;
            const strength = document.getElementById('password-strength');

            if(password.length > 0){
                const result = checkPasswordStrength(password);
                strength.textContent = result.message;
                strength.className = 'password-strength ' + result.strength;
                strength.style.display = 'block';
            }else{
                strength.style.display = 'none';
            }
        });
    }
    

    //real-time password strength validation for bands
    const bandPasswordInput = document.getElementById('band-password');
    if(bandPasswordInput){
        bandPasswordInput.addEventListener('input', function(){
            const password = this.value;
            const strength = document.getElementById('band-password-strength');

            if(password.length > 0){
                const result = checkPasswordStrength(password);
                strength.textContent = result.message;
                strength.className = 'band-password-strength ' + result.strength;
                strength.style.display = 'block';
            }else{
                strength.style.display = 'none';
            }
        });
    }
}