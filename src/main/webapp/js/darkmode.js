window.addEventListener('DOMContentLoaded', function() {
    var saved = localStorage.getItem('ems-dark-mode');
    if (saved === 'true') {
        enableDark();
    }
});

function toggleDarkMode() {
    var html = document.getElementById('html-root');
    if (html.classList.contains('dark')) {
        disableDark();
    } else {
        enableDark();
    }
}

function enableDark() {
    var html = document.getElementById('html-root');
    html.classList.add('dark');
    localStorage.setItem('ems-dark-mode', 'true');
    var btn = document.getElementById('dark-toggle');
    if (btn) btn.textContent = '☀️';  
}

function disableDark() {
    var html = document.getElementById('html-root');
    html.classList.remove('dark');
    localStorage.setItem('ems-dark-mode', 'false');
    var btn = document.getElementById('dark-toggle');
    if (btn) btn.textContent = '🌙';   
}

function exportCSV() {
    window.location.href = 'api/export/csv';
}

function exportPDF() {
    window.location.href = 'api/export/pdf';
}