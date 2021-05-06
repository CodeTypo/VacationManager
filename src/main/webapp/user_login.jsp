<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    if(request.getParameter("adminCheck") == null && request.getParameter("userLogin") != null) {
        request.getSession().setAttribute("login", request.getParameter("userLogin"));
        request.getSession().setAttribute("password", request.getParameter("userPassword"));
        response.sendRedirect("UserServlet");
    } else if (request.getParameter("userLogin") != null){
        request.getSession().setAttribute("login", request.getParameter("userLogin"));
        request.getSession().setAttribute("password", request.getParameter("userPassword"));
        response.sendRedirect("AdminServlet");
    }
%>



<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Split Screen Bootstrap 4 Sign In Page Example with Floating Form Labels</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta name="robots" content="noindex, nofollow">
    <meta name="googlebot" content="noindex, nofollow">
    <meta name="viewport" content="width=device-width, initial-scale=1">


    <script
            type="text/javascript"
            src="/js/lib/dummy.js"

    ></script>

    <link rel="stylesheet" type="text/css" href="/css/result-light.css">

    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.slim.js"></script>
    <link rel="stylesheet" type="text/css" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>

    <style id="compiled-css" type="text/css">
        :root {
            --input-padding-x: 1.5rem;
            --input-padding-y: 0.75rem;
        }

        .login,
        .image {
            min-height: 100vh;
        }

        .bg-image {
            background-image: url('https://source.unsplash.com/WEQbe2jBg40/600x1200');
            background-size: cover;
            background-position: center;
        }

        .login-heading {
            font-weight: 300;
        }

        .btn-login {
            font-size: 0.9rem;
            letter-spacing: 0.05rem;
            padding: 0.75rem 1rem;
            border-radius: 2rem;
        }

        .form-label-group {
            position: relative;
            margin-bottom: 1rem;
        }

        .form-label-group>input,
        .form-label-group>label {
            padding: var(--input-padding-y) var(--input-padding-x);
            height: auto;
            border-radius: 2rem;
        }

        .form-label-group>label {
            position: absolute;
            top: 0;
            left: 0;
            display: block;
            width: 100%;
            margin-bottom: 0;
            /* Override default `<label>` margin */
            line-height: 1.5;
            color: #495057;
            cursor: text;
            /* Match the input under the label */
            border: 1px solid transparent;
            border-radius: .25rem;
            transition: all .1s ease-in-out;
        }

        .form-label-group input::-webkit-input-placeholder {
            color: transparent;
        }

        .form-label-group input:-ms-input-placeholder {
            color: transparent;
        }

        .form-label-group input::-ms-input-placeholder {
            color: transparent;
        }

        .form-label-group input::-moz-placeholder {
            color: transparent;
        }

        .form-label-group input::placeholder {
            color: transparent;
        }

        .form-label-group input:not(:placeholder-shown) {
            padding-top: calc(var(--input-padding-y) + var(--input-padding-y) * (2 / 3));
            padding-bottom: calc(var(--input-padding-y) / 3);
        }

        .form-label-group input:not(:placeholder-shown)~label {
            padding-top: calc(var(--input-padding-y) / 3);
            padding-bottom: calc(var(--input-padding-y) / 3);
            font-size: 12px;
            color: #777;
        }

        /* Fallback for Edge
        -------------------------------------------------- */

        @supports (-ms-ime-align: auto) {
            .form-label-group>label {
                display: none;
            }
            .form-label-group input::-ms-input-placeholder {
                color: #777;
            }
        }

        /* Fallback for IE
        -------------------------------------------------- */

        @media all and (-ms-high-contrast: none),
        (-ms-high-contrast: active) {
            .form-label-group>label {
                display: none;
            }
            .form-label-group input:-ms-input-placeholder {
                color: #777;
            }
        }

        /* EOS */
    </style>

    <script id="insert"></script>


    <script src="/js/stringify.js?3abc336100dcdb62d9b5a5c28f17e42b913f4ffd" charset="utf-8"></script>
    <script>
        const customConsole = (w) => {
            const pushToConsole = (payload, type) => {
                w.parent.postMessage({
                    console: {
                        payload: stringify(payload),
                        type:    type
                    }
                }, "*")
            }

            w.onerror = (message, url, line, column) => {
                // the line needs to correspond with the editor panel
                // unfortunately this number needs to be altered every time this view is changed
                line = line - 70
                if (line < 0){
                    pushToConsole(message, "error")
                } else {
                    pushToConsole(`[${line}:${column}] ${message}`, "error")
                }
            }

            let console = (function(systemConsole){
                return {
                    log: function(){
                        let args = Array.from(arguments)
                        pushToConsole(args, "log")
                        systemConsole.log.apply(this, args)
                    },
                    info: function(){
                        let args = Array.from(arguments)
                        pushToConsole(args, "info")
                        systemConsole.info.apply(this, args)
                    },
                    warn: function(){
                        let args = Array.from(arguments)
                        pushToConsole(args, "warn")
                        systemConsole.warn.apply(this, args)
                    },
                    error: function(){
                        let args = Array.from(arguments)
                        pushToConsole(args, "error")
                        systemConsole.error.apply(this, args)
                    },
                    system: function(arg){
                        pushToConsole(arg, "system")
                    },
                    clear: function(){
                        systemConsole.clear.apply(this, {})
                    },
                    time: function(){
                        let args = Array.from(arguments)
                        systemConsole.time.apply(this, args)
                    },
                    assert: function(assertion, label){
                        if (!assertion){
                            pushToConsole(label, "log")
                        }

                        let args = Array.from(arguments)
                        systemConsole.assert.apply(this, args)
                    }
                }
            }(window.console))

            window.console = { ...window.console, ...console }

            console.system("Running fiddle")
        }

        if (window.parent){
            customConsole(window)
        }
    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row no-gutter">
        <div class="d-none d-md-flex col-md-4 col-lg-6 bg-image"></div>
        <div class="col-md-8 col-lg-6">
            <div class="login d-flex align-items-center py-5">
                <div class="container">
                    <div class="row">
                        <div class="col-md-9 col-lg-8 mx-auto">
                            <h3 class="login-heading mb-4">Welcome back!</h3>
                            <form action="user_login.jsp" method="get">
                                <div class="form-label-group">
                                    <input name="userLogin" type="text" id="inputEmail" class="form-control" placeholder="Your login:" required autofocus>
                                    <label for="inputEmail">Login</label>
                                </div>

                                <div class="form-label-group">
                                    <input name="userPassword" type="password" id="inputPassword" class="form-control" placeholder="Password" required>
                                    <label for="inputPassword">Password</label>
                                </div>

                                <div class="custom-control custom-checkbox mb-3">
                                    <input name="adminCheck" type="checkbox" class="custom-control-input" id="customCheck1" value="adminCheck">
                                    <label class="custom-control-label" for="customCheck1">I am an Admin</label>
                                </div>

                                <button class="btn btn-lg btn-primary btn-block btn-login text-uppercase font-weight-bold mb-2" type="submit">Sign in</button>
                                <div class="text-center">
                                    <a class="small" href="user_register.jsp">Register now!</a></div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">//<![CDATA[





//]]></script>

<script>
    // tell the embed parent frame the height of the content
    if (window.parent && window.parent.parent){
        window.parent.parent.postMessage(["resultsFrame", {
            height: document.body.getBoundingClientRect().height,
            slug: "efvg9j7a"
        }], "*")
    }

    // always overwrite window.name, in case users try to set it manually
    window.name = "result"
</script>

<script>
    let allLines = []

    window.addEventListener("message", (message) => {
        if (message.data.console){
            let insert = document.querySelector("#insert")
            allLines.push(message.data.console.payload)
            insert.innerHTML = allLines.join(";\r")

            let result = eval.call(null, message.data.console.payload)
            if (result !== undefined){
                console.log(result)
            }
        }
    })
</script>

</body>
</html>
