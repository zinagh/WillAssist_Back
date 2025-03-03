<#import "theme.properties" as theme>
<#assign styles = theme.styles![]>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="robots" content="noindex, nofollow">
    <title>Reset Password</title>
    <link rel="icon" type="image/png" href="${url.resourcesPath}/teamwill.png">
    <link rel="stylesheet" type="text/css" href="${url.resourcesPath}/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${url.resourcesPath}/css/style.css">
    <style>
        /* Custom styles matching the login page with light green */
        html, body {
            height: 100%;
            margin: 0;
            padding: 0;
            background-color: #f8f9fa; /* Light background matching login */
        }

        .centered-container {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh; /* Full viewport height */
        }

        .form-container {
            background: white;
            padding: 50px; /* Increased padding for more internal space */
            border-radius: 15px; /* Larger border radius */
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.2); /* Slightly larger shadow */
            width: 100%;
            max-width: 600px; /* Increased from 400px to 600px for a wider form */
            text-align: center; /* Center text inside form */
        }

        .logo-container {
            position: absolute;
            top: 10px; /* Higher position matching login */
            left: 20px; /* Distance from left unchanged */
        }

        .logo {
            max-width: 200px; /* Bigger logo matching login */
        }

        h3 {
            font-size: 2.5rem; /* Larger heading matching login */
            margin-bottom: 30px; /* More space below heading */
        }

        .form-group label {
            font-size: 1.5rem; /* Larger labels matching login */
            text-align: left; /* Left-align labels for consistency */
            display: block;
        }

        .form-control {
            font-size: 1.25rem; /* Larger input text matching login */
            padding: 15px; /* Increased padding for larger input fields */
            height: auto; /* Ensure height adjusts to padding */
            width: 100%;
            border: 1px solid #ccc; /* Consistent with previous style */
            border-radius: 3px;
        }

        .btn-primary {
            font-size: 1.5rem; /* Larger button text matching login */
            padding: 15px; /* Larger button padding */
            width: 100%; /* Full-width button */
            background-color: #95aa4c; /* Light green (Bootstrap success) */
            border: none;
            border-radius: 3px;
            color: white;
        }

        .btn-primary:hover {
            background-color: #95aa4c; /* Slightly darker green on hover */
        }

        .back-to-login {
            font-size: 1.25rem; /* Larger link text matching login’s "Forgot Password" */
            color: #95aa4c; /* Light green matching button */
            text-decoration: none;
            display: block;
            margin-top: 20px;
        }

        .back-to-login:hover {
            color: #95aa4c; /* Darker green on hover */
            text-decoration: underline; /* Underline on hover */
        }

        .reset-instructions {
            font-size: 1.25rem; /* Larger text matching login */
            margin-bottom: 30px; /* More spacing */
            color: #333; /* Dark gray text */
            text-align: left; /* Left-align for readability */
        }

        .input-error {
            color: #721c24; /* Red for errors */
            font-size: 1rem;
            margin-top: 5px;
            display: block;
            text-align: left;
        }
    </style>
</head>
<body>
    <!-- Logo in top-left corner -->
    <div class="logo-container">
        <img src="${url.resourcesPath}/teamwillbg.png" alt="PhoenixStock Keeper Logo" class="logo">
    </div>

    <!-- Centered content -->
    <div class="centered-container">
        <div class="form-container">
            <h3>Reset Password</h3>
            <form id="kc-reset-password-form" action="${url.loginAction}" method="post">
                <!-- Instructions -->
                <div class="reset-instructions">
                    <p>We will send you instructions to reset your password via email. Please make sure to check your email for further steps.</p>
                </div>

                <div class="form-group">
                    <label for="username">Email or Username</label>
                    <input type="text" id="username" name="username" class="form-control" autofocus
                        value="${(auth.attemptedUsername!'')}"
                        aria-invalid="<#if messagesPerField.existsError('username')>true</#if>"/>
                    <#if messagesPerField.existsError('username')>
                        <span id="input-error-username" class="input-error" aria-live="polite">
                            ${kcSanitize(messagesPerField.get('username'))?no_esc}
                        </span>
                    </#if>
                </div>

                <div class="form-group">
                    <input class="btn-primary" type="submit" value="${msg("doSubmit")}" />
                    <a href="${url.loginUrl}" class="back-to-login">${kcSanitize(msg("backToLogin"))?no_esc}</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html> 