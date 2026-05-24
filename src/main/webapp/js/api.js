function showToast(message, type, duration) {
    type     = type     || 'success';
    duration = duration || 3000;

    var t = document.getElementById('toast');
    if (!t) return;

    t.textContent = message;
    t.className   = 'toast toast-' + type;
    t.classList.remove('hidden');

    clearTimeout(t._timer);
    t._timer = setTimeout(function() {
        t.classList.add('hidden');
    }, duration);
}

function postJSON(url, data) {
    return fetch(url, {
        method:  'POST',
        headers: { 'Content-Type': 'application/json' },
        body:    JSON.stringify(data)
    }).then(function(res) {
        return res.json().then(function(json) {
            return { status: res.status, data: json };
        });
    });
}

function getJSON(url) {
    return fetch(url).then(function(res) {
        if (res.status === 401) {
            window.location.href = 'index.html';
            return null;
        }
        return res.json();
    });
}

function logout() {
    window.location.href = 'api/logout';
}