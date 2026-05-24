window.addEventListener('DOMContentLoaded', function() {
    runSplash();
});

function runSplash() {
    var messages = [
        'Initializing...',
        'Loading...',
        'Almost ready...',
        'Welcome!'
    ];

    var bar     = document.getElementById('loader-bar');
    var text    = document.getElementById('loading-text');
    var percent = 0;

    var interval = setInterval(function() {
        percent += 1;
        bar.style.width = percent + '%';

        var msgIndex = Math.floor(
            (percent / 100) * messages.length);
        if (msgIndex < messages.length) {
            text.textContent = messages[msgIndex];
        }

        if (percent >= 100) {
            clearInterval(interval);
            text.textContent = 'Welcome!';
            setTimeout(function() {
                showLoginPage();
            }, 300);
        }
    }, 30);
}

function showLoginPage() {
    var splash = document.getElementById('splash-screen');
    var login  = document.getElementById('login-page');

    splash.style.animation =
        'fadeOut .5s ease forwards';

    setTimeout(function() {
        splash.classList.add('hidden');
        login.classList.remove('hidden');
        login.style.animation = 'fadeIn .5s ease';
        document.getElementById('username').focus();
    }, 500);
}

function togglePassword() {
    var input = document.getElementById('password');
    var btn   = document.getElementById('eye-btn');
    if (input.type === 'password') {
        input.type      = 'text';
        btn.textContent = '🙈';
    } else {
        input.type      = 'password';
        btn.textContent = '👁';
    }
}

function handleLogin() {
    clearErrors();

    var username = document.getElementById('username')
                           .value.trim();
    var password = document.getElementById('password')
                           .value.trim();

    var valid = true;
    if (!username) {
        showFieldError('err-username',
                       'Username is required.');
        valid = false;
    }
    if (!password) {
        showFieldError('err-password',
                       'Password is required.');
        valid = false;
    }
    if (!valid) return;

    document.getElementById('login-btn')
            .disabled = true;
    document.getElementById('login-label')
            .textContent = 'Signing in...';
    document.getElementById('login-spinner')
            .classList.remove('hidden');

    fetch('api/login', {
        method:  'POST',
        headers: { 'Content-Type': 'application/json' },
        body:    JSON.stringify({
            username: username,
            password: password
        })
    })
    .then(function(res) { return res.json(); })
    .then(function(data) {
        if (data.success) {
            showLoginSuccess(
                '✅ Login successful! Redirecting...');
            setTimeout(function() {
                window.location.href = 'dashboard.html';
            }, 800);
        } else {
            if (data.field === 'username') {
                showFieldError('err-username',
                               data.message);
            } else if (data.field === 'password') {
                showFieldError('err-password',
                               data.message);
            } else {
                showLoginError(
                    data.message || 'Login failed.');
            }
        }
    })
    .catch(function() {
        showLoginError(
            'Network error. Please try again.');
    })
    .finally(function() {
        document.getElementById('login-btn')
                .disabled = false;
        document.getElementById('login-label')
                .textContent = 'Sign In';
        document.getElementById('login-spinner')
                .classList.add('hidden');
    });
}


function showFieldError(id, msg) {
    var el = document.getElementById(id);
    if (el) el.textContent = msg;
}

function showLoginError(msg) {
    var el              =
        document.getElementById('login-error');
    el.textContent      = msg;
    el.style.background = '#fee2e2';
    el.style.color      = '#dc2626';
    el.style.border     = '1px solid #fca5a5';
    el.classList.remove('hidden');
}

function showLoginSuccess(msg) {
    var el              =
        document.getElementById('login-error');
    el.textContent      = msg;
    el.style.background = '#dcfce7';
    el.style.color      = '#16a34a';
    el.style.border     = '1px solid #86efac';
    el.classList.remove('hidden');
}

function clearErrors() {
    var errorBox =
        document.getElementById('login-error');
    errorBox.classList.add('hidden');
    errorBox.textContent = '';
    document.getElementById('err-username')
            .textContent = '';
    document.getElementById('err-password')
            .textContent = '';
}

document.addEventListener('keydown', function(e) {
    if (e.key === 'Enter') handleLogin();
});