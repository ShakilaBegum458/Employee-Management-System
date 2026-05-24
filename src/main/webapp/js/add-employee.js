function submitAddEmployee() {
    if (!validateEmployeeForm(false)) return;

    var emp = collectFormData();

    document.querySelector('.btn-primary').disabled = true;
    document.getElementById('submit-label').textContent = 'Saving...';
    document.getElementById('submit-spinner')
            .classList.remove('hidden');

    postJSON('api/employees/add', emp)
    .then(function(res) {
        if (res.data.success) {
            showToast('Employee added successfully! ✓', 'success');
            resetForm();
        } else {
            showToast(res.data.message || 'Failed to add employee.',
                      'error');
        }
    })
    .catch(function() {
        showToast('Network error. Please try again.', 'error');
    })
    .finally(function() {
        document.querySelector('.btn-primary').disabled = false;
        document.getElementById('submit-label')
                .textContent = 'Add Employee';
        document.getElementById('submit-spinner')
                .classList.add('hidden');
    });
}

function resetForm() {
    var fields = [
        'employee_id', 'first_name', 'last_name', 'dob',
        'email', 'phone', 'department', 'designation',
        'date_of_joining', 'salary', 'address'
    ];
    fields.forEach(function(id) {
        var el = document.getElementById(id);
        if (el) el.value = '';
    });
    clearAllErrors();
}