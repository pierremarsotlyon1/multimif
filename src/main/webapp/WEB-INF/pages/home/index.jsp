<%@ page pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <jsp:include page="../basecss.jsp"></jsp:include>
    <link href="/css/codemirror.css" rel="stylesheet" />
    <link href="/css/dracula.css" rel="stylesheet" />
    <link href="/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="/css/templateSedna/normalize.min.css">
    <link rel="stylesheet" type="text/css" href="/css/templateSedna/jquery.fancybox.css">
    <link rel="stylesheet" type="text/css" href="/css/templateSedna/flexslider.css">
    <link rel="stylesheet" type="text/css" href="/css/templateSedna/styles.css">
    <link rel="stylesheet" type="text/css" href="/css/templateSedna/etline-font.css">
    <link rel="stylesheet" type="text/css" href="/css/templateSedna/animate.min.css">
    <link rel="stylesheet" type="text/css" href="/css/font-awesome.min.css">
    <link href="/css/codemirror.css" rel="stylesheet"/>
    <link href="/css/dracula.css" rel="stylesheet"/>
</head>

<body id="top">
<section class="hero">

    <jsp:include page="../menu/menuFront.jsp"></jsp:include>

    <div class="container">

        <div class="row">
            <div class="col-md-10 col-md-offset-1">
                <div class="hero-content text-center">
                    <h1>${message}</h1>
                    <h1>Bienvenue</h1>
                    <p class="intro">Plateforme de développement collaboratif en ligne</p>
                    <a href="/connexion" class="btn btn-fill btn-large btn-margin-right">Connexion</a>
                    <a href="/register" class="btn btn-accent btn-large">Inscription</a>
                </div>
            </div>
        </div>

        <jsp:include page="../basejs.jsp"></jsp:include>

    </div>
    <div class="down-arrow floating-arrow">
        <i class="fa fa-angle-down"></i>
    </div>
</section>

<section class="intro section-padding">
    <div class="container">
        <div class="row">
            <div class="col-md-4 intro-feature">
                <div class="intro-icon"><span data-icon="" class="icon"></span></div>
                <div class="intro-content">
                    <h5>Gérez vos projets en ligne</h5>
                    <p>
                        Créez, modifiez, supprimez vos projets. Utilisez l'éditeur de code intégré pour coder tous vos
                        fichiers Java, Python et C/C++.
                        Utilisez le gestionnaire de versions pour revenir à une version précédente de votre projet.
                    </p>
                </div>
            </div>
            <div class="col-md-4 intro-feature">
                <div class="intro-icon"><span data-icon="" class="icon"></span></div>
                <div class="intro-content">
                    <h5>Partagez vos idées</h5>
                    <p>
                        Ajoutez des collaborateurs à votre projet et discutez avec eux de vos avancées grâce au chat.
                        Gérez les tâches grâce au système de tickets.
                        Créez facilement un wiki.
                    </p>
                </div>
            </div>
            <div class="col-md-4 intro-feature">
                <div class="intro-icon"><span data-icon="" class="icon"></span></div>
                <div class="intro-content last">
                    <h5>Compilez et exécutez vos projets Java, Python et C/C++</h5>
                    <p>
                        Testez votre projet en ligne, que ce soit un projet d'architecture simple ou utilisant maven.
                    </p>
                </div>
            </div>
        </div>
    </div>
</section>

<script src="/js/templateSedna/jquery.fancybox.pack.js"></script>
<script src="/js/templateSedna/scripts.js"></script>
<script src="/js/templateSedna/jquery.flexslider-min.js"></script>
<script src="/js/templateSedna/bower_components/classie/classie.js"></script>
<script src="/js/templateSedna/bower_components/jquery-waypoints/jquery.waypoints.min.js"></script>

</body>
</html>