function clearAllErrors() {
    document.querySelectorAll('.field-error')
            .forEach(function(el) { el.textContent = ''; });
}

function showFieldError(id, msg) {
    var el = document.getElementById(id);
    if (el) el.textContent = msg;
}

function validateEmployeeForm(isEdit) {
    clearAllErrors();
    var valid = true;

    function val(id) {
        var el = document.getElementById(id);
        return el ? el.value.trim() : '';
    }
    if (!isEdit) {
        if (!val('employee_id')) {
            showFieldError('err-employee_id',
                           'Employee ID is required.');
            valid = false;
        }
    }

    if (!val('first_name')) {
        showFieldError('err-first_name', 'First name is required.');
        valid = false;
    }

    if (!val('last_name')) {
        showFieldError('err-last_name', 'Last name is required.');
        valid = false;
    }

    var dob = val('dob');
    if (!dob) {
        showFieldError('err-dob', 'Date of birth is required.');
        valid = false;
    } else if (new Date(dob) > new Date()) {
        showFieldError('err-dob',
                       'Date of birth cannot be a future date.');
        valid = false;
    }

    var email      = val('email');
    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!email) {
        showFieldError('err-email', 'Email is required.');
        valid = false;
    } else if (!emailRegex.test(email)) {
        showFieldError('err-email', 'Enter a valid email address.');
        valid = false;
    }
    var phone = val('phone');
    if (!phone) {
        showFieldError('err-phone', 'Phone number is required.');
        valid = false;
    } else if (!/^\d{10}$/.test(phone)) {
        showFieldError('err-phone',
                       'Phone must be exactly 10 digits.');
        valid = false;
    }

    if (!val('department')) {
        showFieldError('err-department', 'Department is required.');
        valid = false;
    }

    if (!val('designation')) {
        showFieldError('err-designation', 'Designation is required.');
        valid = false;
    }

    if (!val('date_of_joining')) {
        showFieldError('err-date_of_joining',
                       'Date of joining is required.');
        valid = false;
    }

    var salaryEl = document.getElementById('salary');
    var salary   = salaryEl ? parseFloat(salaryEl.value) : NaN;
    if (isNaN(salary) || salary <= 0) {
        showFieldError('err-salary',
                       'Salary must be a positive number.');
        valid = false;
    }

    return valid;
}

function collectFormData() {
    function val(id) {
        var el = document.getElementById(id);
        return el ? el.value.trim() : '';
    }
    return {
        employee_id:     val('employee_id'),
        first_name:      val('first_name'),
        last_name:       val('last_name'),
        dob:             val('dob'),
        email:           val('email'),
        phone:           val('phone'),
        department:      val('department'),
        designation:     val('designation'),
        date_of_joining: val('date_of_joining'),
        salary:          parseFloat(
                           document.getElementById('salary').value),
        address:         val('address')
    };
}