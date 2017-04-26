<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String idProjet = request.getParameter("idProjet");
%>
<%@ page pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <jsp:include page="../basecss.jsp"></jsp:include>
    <link href="/css/summernote.css" rel="stylesheet"/>
    <link href="/css/style.css" rel="stylesheet"/>
    <link href="/css/template/inspinia/style.css" rel="stylesheet"/>

</head>

<body class="pace-done">

<div id="wrapper">
    <jsp:include page="../menu/menuBack.jsp"></jsp:include>
    <div id="page-wrapper" class="gray-bg">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-sm-4">
                <h2>Ajouter une page</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="/projets">Mes projets</a>
                    </li>
                    <li>
                        <a href="/projet/${idProjet}">
                            Mon projet
                        </a>
                    </li>
                    <li>
                        <a href="/projet/${idProjet}/wiki">
                            Le wiki
                        </a>
                    </li>
                    <li class="active">
                        <strong>Ajouter une page</strong>
                    </li>
                </ol>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="wrapper wrapper-content animated fadeInUp">
                    <div class="ibox">
                        <div class="ibox-title">
                            <h5>Ajouter une page</h5>
                        </div>
                        <div class="ibox-content">
                            <form action="/projet/${idProjet}/wiki/new" method="POST" commandName="page" class="formSendPage">

                                <input name="description" type="text" id="description" hidden="hidden">

                                <div class="form-group">
                                    <label for="titre">Titre</label>
                                    <input name="title" type="text" class="form-control" id="titre" placeholder="Titre"
                                           value="${page.title}">
                                </div>

                                <div class="form-group">
                                    <label for="summernote">Description</label>
                                    <div type="text" class="form-control" id="summernote"
                                         placeholder="Une description de la page">
                                    </div>
                                </div>

                                <div class="form-group text-center">
                                    <button type="submit" class="btn btn-primary btnSubmitPage">Créer la page</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../basejs.jsp"></jsp:include>
<script src="/js/summernote.js"></script>
<script src="/js/pagewiki/pagewiki.js"></script>
</body>
</html>