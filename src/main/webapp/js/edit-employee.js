var empId = new URLSearchParams(window.location.search).get('id');

window.addEventListener('DOMContentLoaded', function() {
    if (!empId) {
        window.location.href = 'dashboard.html';
        return;
    }

    document.getElementById('edit-subtitle')
            .textContent = 'Editing: ' + empId;

    getJSON('api/employees/edit?id=' +
            encodeURIComponent(empId))
    .then(function(emp) {
        if (!emp) return;

        document.getElementById('employee_id').value     =
            emp.employee_id;
        document.getElementById('first_name').value      =
            emp.first_name;
        document.getElementById('last_name').value       =
            emp.last_name;
        document.getElementById('dob').value             =
            emp.dob;
        document.getElementById('email').value           =
            emp.email;
        document.getElementById('phone').value           =
            emp.phone;
        document.getElementById('department').value      =
            emp.department;
        document.getElementById('designation').value     =
            emp.designation;
        document.getElementById('date_of_joining').value =
            emp.date_of_joining;
        document.getElementById('salary').value          =
            emp.salary;
        document.getElementById('address').value         =
            emp.address || '';
    })
    .catch(function() {
        showToast('Failed to load employee data.', 'error');
    });
});

function submitEditEmployee() {
    if (!validateEmployeeForm(true)) return;

    var emp        = collectFormData();
    emp.employee_id = empId;

    document.querySelector('.btn-primary').disabled = true;
    document.getElementById('submit-label')
            .textContent = 'Saving...';
    document.getElementById('submit-spinner')
            .classList.remove('hidden');

    postJSON('api/employees/edit', emp)
    .then(function(res) {
        if (res.data.success) {
            showToast('Employee updated successfully! ✓',
                      'success');
            setTimeout(function() {
                window.location.href = 'dashboard.html';
            }, 1200);
        } else {
            showToast(res.data.message || 'Update failed.',
                      'error');
        }
    })
    .catch(function() {
        showToast('Network error. Please try again.', 'error');
    })
    .finally(function() {
        document.querySelector('.btn-primary').disabled = false;
        document.getElementById('submit-label')
                .textContent = 'Save Changes';
        document.getElementById('submit-spinner')
                .classList.add('hidden');
    });
}