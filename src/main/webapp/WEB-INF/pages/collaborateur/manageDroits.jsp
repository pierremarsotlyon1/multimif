<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
    String idProjet = request.getParameter("idProjet");
    String idUser = request.getParameter("idUser");
    boolean find = false;
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
            <div class="col-sm-12">
                <h2>Manager les droits</h2>
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
                        <a href="/projet/${idProjet}/collaborateur">
                            Les collaborateurs
                        </a>
                    </li>
                    <li class="active">
                        <strong>Manager les droits</strong>
                    </li>
                </ol>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="wrapper wrapper-content animated fadeInUp">
                    <div class="ibox">
                        <div class="ibox-title">
                            <h5>Manager les droits</h5>
                        </div>
                        <div class="ibox-content">
                            <form:form id="frmFoo" action="/projet/${idProjet}/collaborateur/${idUser}/manageDroits"
                                       method="POST" commandName="viewModelDroit">

                                <div class="col-md-4 col-md-offset-5 blockCheckboxDroits">
                                    <c:forEach items="${viewModelDroit.droits}" var="droit" varStatus="statut">
                                        <div>
                                            <label>

                                            </label>
                                            <c:choose>
                                                <c:when test="${droit.checked == true}">
                                                    <input type="checkbox" name="droits[${statut.index}].id"
                                                           checked="checked" value="${droit.id}"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <input type="checkbox" name="droits[${statut.index}].id"
                                                           value="${droit.id}"/>
                                                </c:otherwise>
                                            </c:choose>
                                                ${droit.name}
                                        </div>
                                    </c:forEach>
                                </div>
                                <div class="clearfix"></div>
                                <div class="form-group text-center">
                                    <button type="submit" class="btn btn-primary">Sauvegarder les droits de
                                        l'utilisateur
                                    </button>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../basejs.jsp"></jsp:include>
<script src="/js/collaborateur/collaborateur.js"></script>
</body>
</html>