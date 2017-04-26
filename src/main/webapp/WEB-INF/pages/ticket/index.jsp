<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String idProjet = request.getParameter("idProjet");
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="../basecss.jsp"></jsp:include>
    <link href="/css/style.css" rel="stylesheet"/>
    <link href="/css/template/inspinia/style.css" rel="stylesheet"/>
</head>

<body class="pace-done">

<div id="wrapper">
    <jsp:include page="../menu/menuBack.jsp"></jsp:include>
    <div id="page-wrapper" class="gray-bg">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-sm-4">
                <h2>Les tickets</h2>
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
                        <strong>Les tickets</strong>
                    </li>
                </ol>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="wrapper wrapper-content animated fadeInUp">
                    <div class="ibox">
                        <div class="ibox-title">
                            <h5>Les tickets</h5>
                            <div class="ibox-tools">
                                <a href="/projet/${idProjet}/ticket/add" class="btn btn-primary btn-xs">
                                    Ajouter un ticket
                                </a>
                            </div>
                        </div>
                        <div class="ibox-content">
                            <c:choose>
                                <c:when test="${empty tickets}">
                                    <div class="alert alert-info">
                                        Aucun ticket dans ce projet
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <table class="table table-hover">
                                        <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Titre</th>
                                            <th>Assigné à</th>
                                            <th></th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${tickets}" var="ticket">
                                            <tr>
                                                <td>${ticket.id}</td>
                                                <td>${ticket.sujet}</td>
                                                <td>${ticket.user.email}</td>
                                                <c:if test="${idUserConnecte == ticket.user.id}">
                                                    <td>
                                                        <a href="/projet/${idProjet}/ticket/edit/${ticket.id}" role="button" class="btn btn-warning">
                                                            Editer
                                                        </a>
                                                    </td>
                                                    <td>
                                                        <a href="/projet/${idProjet}/ticket/delete/${ticket.id}" role="button" class="btn btn-danger">
                                                            Supprimer
                                                        </a>
                                                    </td>
                                                </c:if>
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

<jsp:include page="../basejs.jsp"></jsp:include>
</body>
</html>