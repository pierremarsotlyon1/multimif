<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

<div class="idProjet hidden">${idProjet}</div>
<div id="wrapper">
    <jsp:include page="../menu/menuBack.jsp"></jsp:include>
    <div id="page-wrapper" class="gray-bg">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-sm-4">
                <h2>Les collaborateurs</h2>
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
                        <strong>Les collaborateurs</strong>
                    </li>
                </ol>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="wrapper wrapper-content animated fadeInUp">
                    <div class="ibox">
                        <div class="ibox-title">
                            <h5>Les collaborateurs</h5>
                            <div class="ibox-tools">
                                <button type="button" class="btn btn-primary btn-xs" data-toggle="modal" data-target="#searchCollaborateurModal">
                                    Ajouter un collaborateur
                                </button>
                            </div>
                        </div>
                        <div class="ibox-content">
                            <c:choose>
                                <c:when test="${empty users}">
                                    <div class="alert alert-info">
                                        Aucun collaborateur dans le projet
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <table class="table table-hover">
                                        <thead>
                                        <tr>
                                            <th>Email du collaborateur</th>
                                            <th></th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${users}" var="user">
                                            <tr>
                                                <td>${user.email}</td>
                                                <td>
                                                    <a href="/projet/${idProjet}/collaborateur/${user.id}/manageDroits" role="button" class="btn btn-info">
                                                        Manager ses droits
                                                    </a>
                                                </td>
                                                <td>
                                                    <a href="/projet/${idProjet}/collaborateur/remove/${user.id}" role="button" class="btn btn-danger">
                                                        Supprimer le collaborateur
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal inmodal" id="searchCollaborateurModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated bounceInRight">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <i class="fa fa-laptop modal-icon"></i>
                <h4 class="modal-title">Ajout d'un collaborateur</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <label>Recherche un collaborateur via son email :</label>
                        <input type="text" class="form-control search-collaborateur"
                               placeholder="Email du collaborateur"/>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <table class="table-collaborateur hidden table table-hover">
                            <thead>
                            <tr>
                                <th>Email du collaborateur</th>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">Fermer</button>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../basejs.jsp"></jsp:include>
<script src="/js/collaborateur/collaborateur.js"></script>
</body>
</html>