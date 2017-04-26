<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>
<%
    String idProjet = request.getParameter("idProjet");
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <jsp:include page="../basecss.jsp"></jsp:include>
    <link href="/css/template/inspinia/style.css" rel="stylesheet"/>
</head>

<body class="pace-done">

<div id="wrapper">
    <jsp:include page="../menu/menuBack.jsp"></jsp:include>
    <div id="page-wrapper" class="gray-bg">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-sm-4">
                <h2>Le wiki</h2>
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
                        <strong>Le wiki</strong>
                    </li>
                </ol>
            </div>
        </div>
        <c:choose>
            <c:when test="${not empty page}">
                <div class="row">
                    <div class="col-md-8">
                        <div class="wrapper wrapper-content animated fadeInUp">
                            <div class="ibox">
                                <div class="ibox-title">
                                    <h5>${page.title}</h5>
                                    <div class="ibox-tools">
                                        <a role="button" href="/projet/${idProjet}/wiki/edit/${page.id}"
                                           class="btn btn-w-m btn-warning btn-xs">
                                            Modifier
                                        </a>
                                    </div>
                                </div>
                                <div class="ibox-content">
                                        ${page.description}
                                </div>
                            </div>

                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="wrapper wrapper-content animated fadeInUp">
                            <div class="ibox">
                                <div class="ibox-title">
                                    <h5>Liste des pages</h5>
                                    <div class="ibox-tools">
                                        <a role="button" href="/projet/${idProjet}/wiki/new"
                                           class="btn btn-w-m btn-primary btn-xs">
                                            Ajouter une page
                                        </a>
                                    </div>
                                </div>
                                <div class="ibox-content">
                                    <ul>
                                        <c:forEach items="${pages}" var="pageWiki">
                                            <li>
                                                <a href="/projet/${idProjet}/wiki/${pageWiki.id}">
                                                        ${pageWiki.title}
                                                </a>
                                                <a href="/projet/${idProjet}/wiki/delete/${pageWiki.id}"
                                                   class="text-danger">
                                                    <i class="fa fa-trash" aria-hidden="true"></i>
                                                </a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="row">
                    <div class="col-md-12">
                        <div class="wrapper wrapper-content animated fadeInUp">
                            <div class="ibox-content text-center">
                                <a role="button" href="/projet/${idProjet}/wiki/new" class="btn btn-primary">
                                    Créer votre première page
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<jsp:include page="../basejs.jsp"></jsp:include>
</body>
</html>