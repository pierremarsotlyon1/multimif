<%@ page import="model.Fichier" %>
<%@ page import="java.util.List" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String idProjet = request.getParameter("idProjet");
    List<Fichier> h = (List<Fichier>) request.getAttribute("fichiers");

    List<Fichier> i = h;
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <jsp:include page="../basecss.jsp"></jsp:include>
    <link href="/css/template/inspinia/style.css" rel="stylesheet"/>
    <link href="/css/style.css" rel="stylesheet"/>
</head>

<body class="pace-done">

<span class="idProjet hidden">${idProjet}</span>
<div id="wrapper">
    <jsp:include page="../menu/menuBack.jsp"></jsp:include>
    <div id="page-wrapper" class="gray-bg">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-sm-4">
                <h2>La documentation</h2>
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
                        <strong>La documentation</strong>
                    </li>
                </ol>
            </div>
        </div>
        <div class="wrapper wrapper-content animated fadeInUp">
            <c:choose>
            <c:when test="${empty fichiers}">
                <div class="row alertErrorJs">
                    <div class="col-md-12">
                        <div class="alert alert-info">
                            Aucune documentation
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </c:when>
            <c:otherwise>
            <div class="row">
                <div id="blockDocumentation" class="col-sm-4 col-lg-4">
                    <div class="ibox">
                        <div class="ibox-content">
                            <nav id="sidebar" class="" role="navigation">
                                <p class="menu-section">
                                    Getting Started
                                </p>
                                <ul class="nav nav-pills nav-stacked">
                                    <c:forEach items="${fichiers}" var="fichier">
                                        <li class="fichier" data-id="${fichier.id}">
                                            <a href="#">
                                                    ${fichier.nomComplet}
                                            </a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>

                <div id="contentDocs" class="col-sm-8 col-lg-8 hidden">
                    <div class="ibox">
                        <div class="ibox-content">
                            <c:forEach items="${fichiers}" var="fichier">
                                <div class="docs${fichier.id} hidden">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <h1>${fichier.javaDocFichier.classeName}</h1>
                                            <c:if test="${!empty fichier.javaDocFichier.packageName}">
                                                <p>Package : ${fichier.javaDocFichier.packageName}</p>
                                            </c:if>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <c:if test="${!empty fichier.javaDocFichier.javaDocImportList}">
                                            <div class="col-md-12">
                                                <h2>Liste des imports</h2>
                                                <ul>
                                                    <c:forEach items="${fichier.javaDocFichier.javaDocImportList}"
                                                               var="importObj">
                                                        <li>${importObj.name}</li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </c:if>
                                    </div>
                                    <div class="row">
                                        <c:if test="${!empty fichier.javaDocFichier.javaDocClasses}">
                                            <div class="col-md-12">
                                                <h2>Liste des classes exends/implements :</h2>
                                                <ul>
                                                    <c:forEach items="${fichier.javaDocFichier.javaDocClasses}"
                                                               var="classes">
                                                        <li>${classes.name}</li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </c:if>
                                    </div>
                                    <div class="row">
                                        <c:if test="${!empty fichier.javaDocFichier.javaDocMethodes}">
                                            <div class="col-md-12">
                                                <h2>Liste des méthodes</h2>
                                                <c:forEach items="${fichier.javaDocFichier.javaDocMethodes}"
                                                           var="methode">
                                                    <div class="panel panel-default">
                                                        <div class="panel-heading">
                                                            <h3 class="panel-title">${methode.name}</h3>
                                                        </div>
                                                        <div class="panel-body">
                                                            <p>${methode.declaration}</p>
                                                            <p>${methode.contentJavaDoc}</p>
                                                            <c:if test="${!empty methode.javaDocAttribut}">
                                                                <h3>Les attributs</h3>
                                                                <ul>
                                                                    <c:forEach items="${methode.javaDocAttribut}"
                                                                               var="attribut">
                                                                        <li>
                                                                                ${attribut.name} : ${attribut.type}
                                                                        </li>
                                                                    </c:forEach>
                                                                </ul>
                                                            </c:if>
                                                        </div>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </c:otherwise>
        </c:choose>
    </div>
</div>

<jsp:include page="../basejs.jsp"></jsp:include>
<script src="/js/docs/gestionDocs.js"></script>
</body>
</html>