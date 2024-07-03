document.addEventListener('DOMContentLoaded', function () {
    const dataForm = document.getElementById('data-form');
    const continueBtn = document.getElementById('continue-btn');
    const formInputs = dataForm.querySelectorAll('input');
    const confirmationForm = document.getElementById('confirmation-form');
    const passwordForm = document.getElementById('password-form');
    const tabs = document.querySelectorAll('.tab');
    const formContainers = document.querySelectorAll('.form-container');
    const contactMethodInputs = document.querySelectorAll('input[name="contact-method"]');
    
    let step = 1;

    function checkFormCompletion() {
        let allFilled = true;
        formInputs.forEach(input => {
            if (!input.value) {
                allFilled = false;
            }
        });
        continueBtn.disabled = !allFilled || !isContactMethodSelected();
    }

    function isContactMethodSelected() {
        let selected = false;
        contactMethodInputs.forEach(input => {
            if (input.checked) {
                selected = true;
            }
        });
        return selected;
    }

    contactMethodInputs.forEach(input => {
        input.addEventListener('change', checkFormCompletion);
    });

    formInputs.forEach(input => {
        input.addEventListener('input', checkFormCompletion);
    });

    dataForm.addEventListener('submit', function (event) {
        event.preventDefault();
        step = 2;
        switchStep(step);
        if (isContactMethodSelected() && document.querySelector('input[name="contact-method"]:checked').value === "phone") {
            // Изпратете SMS с код
        } else {
            // Изпратете имейл с код
        }
    });

    confirmationForm.addEventListener('submit', function (event) {
        event.preventDefault();
        step = 3;
        switchStep(step);
    });

    passwordForm.addEventListener('submit', function (event) {
        event.preventDefault();
        showPopup('Промените са запазени!');
        setTimeout(function() {
            window.location.href = 'login.html';
        }, 2000); // Redirect after 2 seconds
    });

    tabs.forEach(tab => {
        tab.addEventListener('click', function () {
            const targetStep = parseInt(tab.getAttribute('data-step'));
            if (targetStep <= step) {
                switchStep(targetStep);
            }
        });
    });

    function switchStep(step) {
        tabs.forEach(tab => {
            const tabStep = parseInt(tab.getAttribute('data-step'));
            tab.classList.toggle('active', tabStep === step);
            tab.classList.toggle('disabled', tabStep > step);

            if (tabStep === step || tabStep < step) {
                tab.style.pointerEvents = 'auto';
                tab.style.cursor = 'pointer';
            } else {
                tab.style.pointerEvents = 'none';
                tab.style.cursor = 'default';
            }
        });
        formContainers.forEach(container => {
            container.style.display = parseInt(container.getAttribute('data-step')) === step ? 'block' : 'none';
        });
    }
    
    function showPopup(message) {
        const popup = document.createElement('div');
        popup.className = 'popup';
        popup.innerHTML = `<div class="popup-content"><p class="popup-message">${message}</p></div>`;
        document.body.appendChild(popup);
        setTimeout(() => {
            popup.remove();
        }, 2000); // Remove popup after 2 seconds
    }

    // Initialize the tabs
    switchStep(step);
});
