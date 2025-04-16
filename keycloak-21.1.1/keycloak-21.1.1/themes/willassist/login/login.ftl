<#import "theme.properties" as theme>
<#assign styles = theme.styles![]>

<!doctype html>
<html lang="en">
<head>
    <title>WillAssist</title>
    <link rel="icon" type="image/png" href="${url.resourcesPath}/iin.png">
    <link rel="stylesheet" type="text/css" href="${url.resourcesPath}/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${url.resourcesPath}/css/style.css">
    <style>
        /* Custom styles */
        html, body {
            height: 100%;
            margin: 0;
            padding: 0;
            background-color: #f8f9fa; /* Optional: light background */
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
            top: 10px; /* Moved higher (was 20px) */
            left: 20px; /* Distance from left unchanged */
        }

        .logo {
            max-width: 200px; /* Increased from 150px to 200px for a bigger logo */
        }

        h3 {
            font-size: 2.5rem; /* Larger heading (was ~1.75rem by default) */
            margin-bottom: 30px; /* More space below heading */
        }

        .form-group label {
            font-size: 1.25rem; /* Smaller from 1.5rem */
            text-align: left; /* Left-align labels */
            display: block; /* Ensure block layout */
        }

        .form-control {
            font-size: 1rem; /* Smaller from 1.25rem */
            padding: 15px; /* Keep padding for larger input fields */
            height: auto; /* Ensure height adjusts to padding */
            width: 100%; /* Full width */
        }

        .d-flex {
            font-size: 1.25rem; /* Keep larger text for "Remember me" and "Forgot Password" */
        }

        .btn-primary {
            font-size: 1.5rem; /* Larger button text (unchanged) */
            padding: 15px; /* Larger button padding */
            width: 100%; /* Full-width button */
            background-color: #95aa4c; /* Light green from previous request */
        }

        .btn-primary:hover {
            background-color: #95aa4c; /* Darker green on hover */
        }

        .forgot-pass {
            font-size: 1.25rem; /* Larger "Forgot Password" link (unchanged) */
            color: #95aa4c; /* Light green */
        }

        .forgot-pass:hover {
            color: #95aa4c; /* Darker green on hover */
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
            <h3>Login to <strong>WillAssist</strong></h3>
            <form action="${url.loginAction}" method="post">
                <div class="form-group first">
                    <label for="username">Username</label>
                    <input type="text" class="form-control" placeholder="your-email@phoenixmas.com" id="username" name="username">
                </div>
                <div class="form-group last mb-3">
                    <label for="password">Password</label>
                    <input type="password" class="form-control" placeholder="Your Password" id="password" name="password">
                </div>

                <div class="d-flex mb-5 align-items-center justify-content-between">
                    <label class="control control--checkbox mb-0">
                        <span class="caption">Remember me</span>
                        <input type="checkbox" checked="checked" name="rememberMe"/>
                        <div class="control__indicator"></div>
                    </label>
                    <span><a href="${url.loginResetCredentialsUrl}" class="forgot-pass">Forgot Password</a></span>
                </div>

                <input type="submit" value="Log In" class="btn btn-block btn-primary">
            </form>
        </div>
    </div>
</body>
</html>