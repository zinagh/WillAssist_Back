<#import "theme.properties" as theme>
<#assign styles = theme.styles![]>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="robots" content="noindex, nofollow">
    <title>Update Account password</title>
    <link rel="icon" type="image/png" href="${url.resourcesPath}/teamwill.png">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #95aa4c;
        }

        .container {
            max-width: 50%;
            background-color: #fff;
            padding: 5%;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }

        input {
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 3px;
        }

        input[type="submit"] {
            background-color: #95aa4c;
            color: white;
            padding: 10px 20px;
            border: none;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #95aa4c;
        }

        .checkbox {
            margin-top: 15px;
        }

        .change-password-phrase {
            margin-bottom: 20px;
            padding: 15px;
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
            color: #95aa4c;
            border-radius: 5px;
        }

        .input-error-custom {
            color: red;
            font-size: 0.9rem;
            margin-top: -5px;
            margin-bottom: 10px;
            display: block;
        }
    </style>
</head>
<body>
<div class="container">
    <form id="kc-passwd-update-form" class="form" action="${url.loginAction}" method="post">
        <input type="text" id="username" name="username" value="${username}" autocomplete="username"
               readonly="readonly" style="display:none;"/>
        <input type="password" id="password" name="password" autocomplete="current-password" style="display:none;"/>

        <div class="change-password-phrase">
            <p>Enhance your account security by updating your password. Ensure your new password includes a combination of letters, numbers, and symbols for added strength.</p>
        </div>

        <div>
            <label for="password-new">${msg("passwordNew")}</label>
            <input type="password" id="password-new" name="password-new" autocomplete="new-password"/>
        </div>

        <div>
            <label for="password-confirm">${msg("passwordConfirm")}</label>
            <input type="password" id="password-confirm" name="password-confirm" autocomplete="new-password"/>
        </div>

        <#if isAppInitiatedAction??>
            <div class="checkbox">
                <label><input type="checkbox" id="logout-sessions" name="logout-sessions" value="on" checked> ${msg("logoutOtherSessions")}</label>
            </div>
        </#if>

        <div style="margin-top: 20px;">
            <#if isAppInitiatedAction??>
                <input type="submit" value="${msg("doSubmit")}" />
                <button type="submit" name="cancel-aia" value="true">${msg("doCancel")}</button>
            <#else>
                <input type="submit" value="${msg("doSubmit")}" />
            </#if>
        </div>
    </form>
</div>

<script>
document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('kc-passwd-update-form');
    const passwordInput = document.getElementById('password-new');
    const confirmPasswordInput = document.getElementById('password-confirm');

    function showError(input, message) {
        let errorSpan = input.parentNode.querySelector('.input-error-custom');
        if (!errorSpan) {
            errorSpan = document.createElement('span');
            errorSpan.className = 'input-error-custom';
            input.parentNode.appendChild(errorSpan);
        }
        errorSpan.textContent = message;
    }

    function clearErrors() {
        document.querySelectorAll('.input-error-custom').forEach(e => e.remove());
    }

    function validatePasswordRules(password) {
        const hasUpperCase = /[A-Z]/.test(password);
        const hasNumber = /[0-9]/.test(password);
        const hasSpecialChar = /[!@#$&*]/.test(password);
        const hasMinLength = password.length >= 6;
        return hasUpperCase && hasNumber && hasSpecialChar && hasMinLength;
    }

    form.addEventListener('submit', function (event) {
        clearErrors();
        const password = passwordInput.value;
        const confirmPassword = confirmPasswordInput.value;
        let isValid = true;

        if (!validatePasswordRules(password)) {
            showError(passwordInput, "Le mot de passe doit contenir au moins 6 caractères, une majuscule, un chiffre et un symbole.");
            isValid = false;
        }

        if (password !== confirmPassword) {
            showError(confirmPasswordInput, "Les mots de passe ne correspondent pas.");
            isValid = false;
        }

        if (!isValid) {
            event.preventDefault(); // Bloque la soumission du formulaire
        }
    });
});
</script>

</body>
</html>
