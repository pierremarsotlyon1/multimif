<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
    String idProjet = request.getParameter("idProjet");
%>
<%@ page pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <jsp:include page="../basecss.jsp"></jsp:include>
    <link href="/css/template/inspinia/style.css" rel="stylesheet"/>
    <link href="/css/bootTree.css" rel="stylesheet" type="text/css"/>
    <link href="/css/codemirror.css" rel="stylesheet"/>
    <link href="/css/dracula.css" rel="stylesheet"/>
    <link href="/css/chat.css" rel="stylesheet"/>
    <link href="/css/style.css" rel="stylesheet"/>
</head>

<body class="pace-done">

<span class="idProjet hidden">${idProjet}</span>
<span class="emailUser hidden">${emailUser}</span>
<div id="wrapper">
    <jsp:include page="../menu/menuBack.jsp"></jsp:include>
    <div id="page-wrapper" class="gray-bg">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-sm-12">
                <h2>Le chat</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="/projets">Mes projets</a>
                    </li>
                    <li>
                        <a href="/projet/${idProjet}">
                            Mon projet
                        </a>
                    </li>
                    <li class="active">
                        <strong>Le chat</strong>
                    </li>
                </ol>
            </div>
        </div>
        <div class="row blockAlertSocketConnexion">
            <div class="wrapper wrapper-content animated fadeInUp">
                <div class="col-lg-12">
                    <div class="alert alert-info">
                        Socket en cours de connexion
                    </div>
                </div>
            </div>
        </div>
        <div class="row blockChat hidden">
            <div class="col-lg-12">
                <div class="wrapper wrapper-content animated fadeInUp">
                    <div class="ibox">
                        <div class="ibox-title">
                            <h5>Le chat</h5>
                        </div>
                        <div class="ibox-content">
                            <div class="col-md-12 panel-chat">
                                <div class="panel panel-primary">
                                    <div class="panel-body">
                                        <ul class="chat">

                                        </ul>
                                    </div>
                                    <div class="panel-footer">
                                        <div class="input-group">
                                            <input id="btn-input" type="text" class="form-control input-sm messageChat"
                                                   placeholder="Entrez votre message ici ..."/>
                                            <span class="input-group-btn">
                                                <button class="btn btn-warning btn-sm" id="btn-chat">
                                                    Envoyer
                                                </button>
                                             </span>
                                        </div>
                                    </div>

                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../basejs.jsp"></jsp:include>
<script src="/js/socket-io.js"></script>
<script src="/js/jquery.json.js"></script>
<script src="/js/chat/gestionChat.js"></script>
</body>
</html>