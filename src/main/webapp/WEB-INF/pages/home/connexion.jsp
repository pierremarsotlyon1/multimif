<%@ page pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="google-signin-client_id"
          content="832657537413-5mo8nead7hbn9g7egmcge5anmqehk8to.apps.googleusercontent.com">
    <jsp:include page="../basecss.jsp"></jsp:include>
    <link href="css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="/css/templateSedna/normalize.min.css">
    <link rel="stylesheet" type="text/css" href="/css/templateSedna/jquery.fancybox.css">
    <link rel="stylesheet" type="text/css" href="/css/templateSedna/flexslider.css">
    <link rel="stylesheet" type="text/css" href="/css/templateSedna/styles.css">
    <link rel="stylesheet" type="text/css" href="/css/templateSedna/etline-font.css">
    <link rel="stylesheet" type="text/css" href="/css/templateSedna/animate.min.css">
    <link rel="stylesheet" type="text/css" href="/css/font-awesome.min.css">
</head>

<body>
<jsp:include page="../menu/menuFront.jsp"></jsp:include>

<section class="sign-up section-padding text-center" id="download">
<div class="container">
    <div class="row">
        <div class="col-md-12">
                <h2>Connexion avec les réseaux sociaux</h2>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6 col-md-offset-3 text-center">
            <button class="g-signin "
                    data-scope="https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/userinfo.email"
                    data-requestvisibleactions="http://schemas.google.com/AddActivity"
                    data-clientId="832657537413-5mo8nead7hbn9g7egmcge5anmqehk8to.apps.googleusercontent.com"
                    data-accesstype="offline"
                    data-callback="googleLogIn"
                    data-theme="dark"
                    data-cookiepolicy="single_host_origin">
            </button>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
                <h2>Connexion avec votre propre compte</h2>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <form class="signup-form" action="/connexion" method="POST" commandName="user">

                <div class="form-input-group"><i class="fa fa-envelope"></i>
                    <input name="email" type="email" class="form-control" id="email" placeholder="Email">
                </div>

                <div class="form-input-group"><i class="fa fa-lock"></i>
                    <input name="password" type="password" class="form-control" id="password"
                           placeholder="Mot de passe">
                </div>
                <button type="submit" class="btn-fill sign-up-btn">Connexion</button>

            </form>
        </div>
    </div>
</div>
</section>

<div class="hidden">
    <form action="/connexion" class="formLogInGoogle" method="POST" commandName="user">

        <div class="form-group">
            <label for="email">Email</label>
            <input name="email" type="email" class="form-control emailLogInGoogle" placeholder="Email">
        </div>

        <div class="form-group">
            <label for="password">Mot de passe</label>
            <input name="password" type="password" class="form-control passwordLogInGoogle"
                   placeholder="Mot de passe">
        </div>
    </form>
</div>

<jsp:include page="../basejs.jsp"></jsp:include>
<script src="https://apis.google.com/js/platform.js"></script>
<script src="https://apis.google.com/js/client.js"></script>
<script src="/js/reseaux_sociaux/google.js"></script>
<script src="/js/templateSedna/jquery.fancybox.pack.js"></script>
<script src="/js/templateSedna/scripts.js"></script>
<script src="/js/templateSedna/jquery.flexslider-min.js"></script>
<script src="/js/templateSedna/bower_components/classie/classie.js"></script>
<script src="/js/templateSedna/bower_components/jquery-waypoints/jquery.waypoints.min.js"></script>
<script src="/js/gestionConnexion.js"></script>
</body>
</html>