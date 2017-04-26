<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
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
                <h2>Mes projets</h2>
            </div>
        </div>
        <div class="row">
            <!--<div class="col-md-4">
                <div class="image_profil">
                    <img src="${imageProfil}" alt="Image profil"/>
                </div>
            </div>-->
            <div class="col-lg-12">
                <div class="wrapper wrapper-content animated fadeInUp">

                    <div class="ibox">
                        <div class="ibox-title">
                            <h5>Tous mes projets</h5>
                            <div class="ibox-tools">
                                <a href="/projet/add" class="btn btn-primary btn-xs">Ajouter un projet</a>
                                <a href="/projet/import" class="btn btn-primary btn-xs">Importer un projet</a>
                            </div>
                        </div>
                        <div class="ibox-content">
                            <c:choose>
                                <c:when test="${empty projets}">
                                    <div class="alert alert-info">
                                        Vous n'avez pas de projet
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="project-list">
                                        <table class="table table-hover">
                                            <tbody>
                                            <c:forEach items="${projets}" var="projet">
                                                <c:set var="admin" value="false"/>
                                                <tr>
                                                    <td class="project-status">
                                                        <c:choose>
                                                            <c:when test="${projet.key.langage.id == 1}">
                                                                <span class="label label-danger">Java</span>
                                                            </c:when>
                                                            <c:when test="${projet.key.langage.id == 2}">
                                                                <span class="label label-warning">Python</span>
                                                            </c:when>
                                                            <c:when test="${projet.key.langage.id == 3}">
                                                                <span class="label label-primary">C++</span>
                                                            </c:when>
                                                        </c:choose>
                                                    </td>
                                                    <td class="project-title">
                                                        <a href="/projet/${projet.key.id}">
                                                                ${projet.key.name}
                                                            <br>
                                                            <small>${projet.key.description}</small>
                                                        </a>
                                                    </td>
                                                    <td class="project-actions">
                                                        <c:forEach items="${projet.value}" var="droit">
                                                            <c:if test="${droit.id == 6}">
                                                                <button type="button" class="btn btn-danger btn-sm"
                                                                        data-toggle="modal"
                                                                        data-target="#delModal${projet.key.id}">
                                                                    <i class="fa fa-trash-o" aria-hidden="true"></i>
                                                                </button>

                                                                <div class="modal inmodal" id="delModal${projet.key.id}"
                                                                     tabindex="-1"
                                                                     role="dialog"
                                                                     aria-labelledby="delModalLabel${projet.key.id}">
                                                                    <div class="modal-dialog" role="document">
                                                                        <div class="modal-content animated bounceInRight">
                                                                            <div class="modal-header">
                                                                                <button type="button" class="close"
                                                                                        data-dismiss="modal"><span
                                                                                        aria-hidden="true">×</span><span
                                                                                        class="sr-only">Close</span>
                                                                                </button>
                                                                                <i class="fa fa-laptop modal-icon"></i>
                                                                                <h4 class="modal-title">
                                                                                    Suppression du
                                                                                    projet ${projet.key.name}
                                                                                </h4>
                                                                            </div>
                                                                            <div class="modal-body text-center">
                                                                                Voulez-vous vraiment supprimer
                                                                                définitivement ce projet pour
                                                                                tous ses utilisateurs ?
                                                                            </div>
                                                                            <div class="modal-footer">
                                                                                <a href="/projet/delete/${projet.key.id}/1"
                                                                                   type="button"
                                                                                   class="btn btn-primary"
                                                                                   role="button">Supprimer
                                                                                </a>

                                                                                <button type="button"
                                                                                        class="btn btn-default"
                                                                                        data-dismiss="modal">Fermer
                                                                                </button>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>


                                                                <c:set var="admin" value="true"/>
                                                            </c:if>
                                                        </c:forEach>

                                                        <c:if test="${not admin}">
                                                            <button type="button" class="btn btn-danger btn-sm"
                                                                    data-toggle="modal"
                                                                    data-target="#delModalnAdm${projet.key.id}">
                                                                <i class="fa fa-trash-o" aria-hidden="true"></i>
                                                            </button>

                                                            <!-- Modal -->
                                                            <div class="modal inmodal" id="delModalnAdm${projet.key.id}"
                                                                 tabindex="-1"
                                                                 role="dialog"
                                                                 aria-labelledby="delModalNAdmLabel${projet.key.id}">
                                                                <div class="modal-dialog">
                                                                    <div class="modal-content animated bounceInRight">
                                                                        <div class="modal-header">
                                                                            <button type="button" class="close"
                                                                                    data-dismiss="modal"><span
                                                                                    aria-hidden="true">×</span><span
                                                                                    class="sr-only">Close</span>
                                                                            </button>
                                                                            <i class="fa fa-laptop modal-icon"></i>
                                                                            <h4 class="modal-title">
                                                                                Suppression du projet ${projet.key.name}
                                                                            </h4>
                                                                        </div>
                                                                        <div class="modal-body text-center">
                                                                            Voulez-vous vraiment supprimer
                                                                            définitivement ce
                                                                            projet de votre
                                                                            liste ?
                                                                        </div>
                                                                        <div class="modal-footer">
                                                                            <a href="/projet/delete/${projet.key.id}/0"
                                                                               type="button"
                                                                               class="btn btn-primary" role="button">Supprimer
                                                                            </a>

                                                                            <button type="button"
                                                                                    class="btn btn-default"
                                                                                    data-dismiss="modal">
                                                                                Fermer
                                                                            </button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <c:set var="admin" value="true"/>
                                                        </c:if>
                                                        <c:forEach items="${projet.value}" var="droit">
                                                            <c:if test="${droit.id == 7}">
                                                                <a href="/projet/${projet.key.id}/export"
                                                                   class="btn btn-primary btn-sm"
                                                                   role="button">
                                                                    <i class="fa fa-download" aria-hidden="true"></i>
                                                                </a>
                                                            </c:if>

                                                        </c:forEach>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
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