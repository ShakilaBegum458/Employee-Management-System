var currentPage    = 1;
var PAGE_SIZE      = 10;
var searchTimer    = null;
var pendingDeleteId = null;

window.addEventListener('DOMContentLoaded', function() {
    loadEmployees(1);
});
function loadEmployees(page) {
    currentPage = page;
    showTableLoading();

    getJSON('api/employees?page=' + page + '&size=' + PAGE_SIZE)
    .then(function(data) {
        if (!data) return;
        renderTable(data.data);
        renderPagination(data.totalPages, data.page);
        document.getElementById('record-count').textContent =
            data.total + ' employee' +
            (data.total !== 1 ? 's' : '') + ' found';
    })
    .catch(function() {
        showTableError('Failed to load employees.');
    });
}
function handleSearch(q) {
    clearTimeout(searchTimer);
    searchTimer = setTimeout(function() {
        if (q.trim() === '') {
            loadEmployees(1);
            return;
        }
        showTableLoading();
        getJSON('api/employees/search?q=' +
                encodeURIComponent(q.trim()))
        .then(function(results) {
            if (!results) return;
            renderTable(results);
            document.getElementById('record-count').textContent =
                results.length + ' result' +
                (results.length !== 1 ? 's' : '') +
                ' for "' + q + '"';
            document.getElementById('pagination').innerHTML = '';
        })
        .catch(function() {
            showTableError('Search failed.');
        });
    }, 300);
}
function renderTable(employees) {
    var tbody = document.getElementById('emp-body');

    if (!employees || employees.length === 0) {
        tbody.innerHTML =
            '<tr><td colspan="8" class="loading-row">' +
            'No employees found.</td></tr>';
        return;
    }

    var html = '';
    for (var i = 0; i < employees.length; i++) {
        var e = employees[i];
        html +=
            '<tr>' +
            '<td><strong>' + esc(e.employee_id) +
            '</strong></td>' +
            '<td>' + esc(e.first_name) + ' ' +
            esc(e.last_name) + '</td>' +
            '<td>' + esc(e.dob) + '</td>' +
            '<td>' + esc(e.department) + '</td>' +
            '<td>' + esc(e.designation) + '</td>' +
            '<td>' + esc(e.email) + '</td>' +
            '<td>' + esc(e.phone) + '</td>' +
            '<td class="actions-cell">' +
            '<button class="btn btn-sm btn-primary" ' +
            'onclick="window.location.href=' +
            '\'edit-employee.html?id=' +
            esc(e.employee_id) + '\'">' +
            '✏️ Edit</button>' +
            '<button class="btn btn-sm btn-danger" ' +
            'onclick="confirmDeleteOne(\'' +
            esc(e.employee_id) + '\')">' +
            '🗑</button>' +
            '</td>' +
            '</tr>';
    }
    tbody.innerHTML = html;
}
function renderPagination(totalPages, current) {
    var container = document.getElementById('pagination');
    if (totalPages <= 1) {
        container.innerHTML = '';
        return;
    }

    var html = '<button class="page-btn" ' +
        (current === 1 ? 'disabled' : '') +
        ' onclick="loadEmployees(' + (current - 1) +
        ')">‹ Prev</button>';

    for (var i = 1; i <= totalPages; i++) {
        html += '<button class="page-btn ' +
            (i === current ? 'active' : '') +
            '" onclick="loadEmployees(' + i + ')">' +
            i + '</button>';
    }

    html += '<button class="page-btn" ' +
        (current === totalPages ? 'disabled' : '') +
        ' onclick="loadEmployees(' + (current + 1) +
        ')">Next ›</button>';

    container.innerHTML = html;
}
function confirmDeleteOne(id) {
    pendingDeleteId = id;
    document.getElementById('delete-emp-id').textContent = id;
    document.getElementById('delete-one-modal')
            .classList.remove('hidden');
}

function executeDeleteOne() {
    closeModal();
    if (!pendingDeleteId) return;

    postJSON('api/employees/delete', { id: pendingDeleteId })
    .then(function(res) {
        if (res.data.success) {
            showToast('Employee deleted successfully.', 'success');
            loadEmployees(currentPage);
        } else {
            showToast(res.data.message || 'Delete failed.', 'error');
        }
    })
    .catch(function() {
        showToast('Network error.', 'error');
    });

    pendingDeleteId = null;
}
function confirmDeleteAll() {
    document.getElementById('delete-all-modal')
            .classList.remove('hidden');
}

function executeDeleteAll() {
    closeModal();

    postJSON('api/employees/delete', { deleteAll: true })
    .then(function(res) {
        if (res.data.success) {
            showToast('All employees deleted.', 'success');
            loadEmployees(1);
        } else {
            showToast(res.data.message || 'Delete failed.', 'error');
        }
    })
    .catch(function() {
        showToast('Network error.', 'error');
    });
}

function closeModal() {
    document.querySelectorAll('.modal-overlay')
            .forEach(function(m) {
                m.classList.add('hidden');
            });
}

document.addEventListener('click', function(e) {
    if (e.target.classList.contains('modal-overlay')) {
        closeModal();
    }
});

function showTableLoading() {
    document.getElementById('emp-body').innerHTML =
        '<tr><td colspan="8" class="loading-row">' +
        'Loading...</td></tr>';
}

function showTableError(msg) {
    document.getElementById('emp-body').innerHTML =
        '<tr><td colspan="8" class="loading-row" ' +
        'style="color:var(--danger)">' + msg + '</td></tr>';
}

function esc(str) {
    return String(str || '')
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#39;');
}