<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String idProjet = request.getParameter("idProjet");
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="../basecss.jsp"></jsp:include>
    <link href="/css/template/inspinia/style.css" rel="stylesheet"/>
    <link href="/css/style.css" rel="stylesheet"/>
</head>

<body class="pace-done">

<div id="wrapper">
    <jsp:include page="../menu/menuBack.jsp"></jsp:include>
    <div id="page-wrapper" class="gray-bg">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-sm-4">
                <h2>Les commits</h2>
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
                        <strong>Les commits</strong>
                    </li>
                </ol>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="wrapper wrapper-content animated fadeInUp">
                    <div class="ibox">
                        <div class="ibox-title">
                            <h5>Les commits</h5>
                        </div>
                        <div class="ibox-content">
                            <c:choose>
                                <c:when test="${empty commits}">
                                    <div class="alert alert-info">
                                        Aucun commit enregistré
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <table class="table table-hover">
                                        <thead>
                                        <tr>
                                            <th>Auteur</th>
                                            <th>Id</th>
                                            <th>Date</th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${commits}" var="commit">
                                            <tr>
                                                <td>${commit.author}</td>
                                                <td>${commit.id}</td>
                                                <td>
                                                    <fmt:formatDate type="both"
                                                                    dateStyle="medium" timeStyle="medium"
                                                                    value="${commit.date}" />
                                                </td>
                                                <td>
                                                    <a href="/projet/${idProjet}/commits/checkout/${commit.id}" role="button" class="btn btn-warning btn-xs">
                                                        Checkout à ce commit
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

<jsp:include page="../basejs.jsp"></jsp:include>
</body>
</html>