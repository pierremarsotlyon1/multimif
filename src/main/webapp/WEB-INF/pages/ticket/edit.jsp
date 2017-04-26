<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
    String idProjet = request.getParameter("idProjet");
    String idTicket = request.getParameter("idTicket");
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
            <div class="col-sm-12">
                <h2>Modifier un ticket</h2>
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
                        <a href="/projet/${idProjet}/ticket">
                            Les tickets
                        </a>
                    </li>
                    <li class="active">
                        <strong>Modifier le ticket</strong>
                    </li>
                </ol>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="wrapper wrapper-content animated fadeInUp">
                    <div class="ibox">
                        <div class="ibox-title">
                            <h5>Modifier le ticket</h5>
                        </div>
                        <div class="ibox-content">
                            <form action="/projet/${idProjet}/ticket/edit/${idTicket}" method="POST" commandName="ticket"
                                  class="formTicket">

                                <input name="description" type="text" id="description" hidden="hidden">

                                <div class="form-group">
                                    <label for="idTracker">Tracker</label>
                                    <c:choose>
                                        <c:when test="${canUpdate == true}">
                                            <select name="idTracker" id="idTracker">
                                                <c:forEach items="${trackers}" var="tracker">
                                                    <c:choose>
                                                        <c:when test="${tracker.id == ticket.tracker.id}">
                                                            <option value="${tracker.id}" selected="selected">${tracker.nom}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="${tracker.id}">${tracker.nom}</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </select>
                                        </c:when>
                                        <c:otherwise>
                                            <select name="idTracker" id="idTracker" disabled="disabled">
                                                <c:forEach items="${trackers}" var="tracker">
                                                    <c:choose>
                                                        <c:when test="${tracker.id == ticket.tracker.id}">
                                                            <option value="${tracker.id}" selected="selected">${tracker.nom}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="${tracker.id}">${tracker.nom}</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </select>
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <div class="form-group">
                                    <label for="sujet">Sujet</label>
                                    <c:choose>
                                        <c:when test="${canUpdate == true}">
                                            <input name="sujet" type="text" class="form-control" id="sujet" placeholder="Sujet"
                                                   value="${ticket.sujet}" required="required">
                                        </c:when>
                                        <c:otherwise>
                                            <input name="sujet" type="text" class="form-control" id="sujet" placeholder="Sujet"
                                                   value="${ticket.sujet}" disabled="disabled" required="required">
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <div class="form-group">
                                    <label for="summernote">Description</label>

                                    <c:choose>
                                        <c:when test="${canUpdate == true}">
                                            <div type="text" class="form-control" id="summernote"
                                                 placeholder="Une description du ticket">
                                                    ${ticket.description}
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div type="text" class="form-control" id="summernote"
                                                 placeholder="Une description du ticket" aria-disabled="true">
                                                    ${ticket.description}
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <div class="form-group">
                                    <label for="idStatutTicket">Statut</label>
                                    <c:choose>
                                        <c:when test="${canUpdate == true}">
                                            <select name="idStatutTicket" id="idStatutTicket">
                                                <c:forEach items="${statutTickets}" var="statutTicket">
                                                    <c:choose>
                                                        <c:when test="${statutTicket.id == ticket.statutTicket.id}">
                                                            <option value="${statutTicket.id}" selected="selected">${statutTicket.name}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="${statutTicket.id}">${statutTicket.name}</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </select>
                                        </c:when>
                                        <c:otherwise>
                                            <select name="idStatutTicket" id="idStatutTicket" disabled="disabled">
                                                <c:forEach items="${statutTickets}" var="statutTicket">
                                                    <c:choose>
                                                        <c:when test="${statutTicket.id == ticket.statutTicket.id}">
                                                            <option value="${statutTicket.id}" selected="selected">${statutTicket.name}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="${statutTicket.id}">${statutTicket.name}</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </select>
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <div class="form-group">
                                    <label for="idPrioriteTicket">Priorité</label>
                                    <c:choose>
                                        <c:when test="${canUpdate == true}">
                                            <select name="idPrioriteTicket" id="idPrioriteTicket">
                                                <c:forEach items="${prioriteTickets}" var="prioriteTicket">
                                                    <c:choose>
                                                        <c:when test="${prioriteTicket.id == ticket.prioriteTicket.id}">

                                                            <option value="${prioriteTicket.id}"
                                                                    selected="selected">${prioriteTicket.name}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="${prioriteTicket.id}">${prioriteTicket.name}</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </select>
                                        </c:when>
                                        <c:otherwise>
                                            <select name="idPrioriteTicket" id="idPrioriteTicket" disabled="disabled">
                                                <c:forEach items="${prioriteTickets}" var="prioriteTicket">
                                                    <c:choose>
                                                        <c:when test="${prioriteTicket.id == ticket.prioriteTicket.id}">

                                                            <option value="${prioriteTicket.id}"
                                                                    selected="selected">${prioriteTicket.name}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="${prioriteTicket.id}">${prioriteTicket.name}</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </select>
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <div class="form-group">
                                    <label for="idUserAssigne">Assigner à</label>
                                    <c:choose>
                                        <c:when test="${canUpdate == true}">
                                            <select name="idUserAssigne" id="idUserAssigne">
                                                <c:forEach items="${users}" var="user">
                                                    <c:choose>
                                                        <c:when test="${user.id == ticket.user.id}">
                                                            <option value="${user.id}" selected="selected">${user.email}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="${user.id}">${user.email}</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </select>
                                        </c:when>
                                        <c:otherwise>
                                            <select name="idUserAssigne" id="idUserAssigne" disabled="disabled">
                                                <c:forEach items="${users}" var="user">
                                                    <c:choose>
                                                        <c:when test="${user.id == ticket.user.id}">
                                                            <option value="${user.id}" selected="selected">${user.email}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="${user.id}">${user.email}</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </select>
                                        </c:otherwise>
                                    </c:choose>

                                </div>

                                <div class="form-group">
                                    <label for="sujet">Temps estimé (Heures)</label>
                                    <c:choose>
                                        <c:when test="${canUpdate == true}">
                                            <input name="tempsEstime" type="text" class="form-control" id="tempsEstime"
                                                   placeholder="Temps estimé" value="${ticket.tempsEstime}">
                                        </c:when>
                                        <c:otherwise>
                                            <input name="tempsEstime" type="text" class="form-control" id="tempsEstime"
                                                   placeholder="Temps estimé" value="${ticket.tempsEstime}" disabled="disabled">
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <div class="form-group">
                                    <label for="sujet">Réalisation (%)</label>
                                    <c:choose>
                                        <c:when test="${canUpdate == true}">
                                            <input name="realisation" type="text" class="form-control" id="realisation"
                                                   placeholder="Réalisation" value="${ticket.realisation}">
                                        </c:when>
                                        <c:otherwise>
                                            <input name="realisation" type="text" class="form-control" id="realisation"
                                                   placeholder="Réalisation" value="${ticket.realisation}" disabled="disabled">
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <c:choose>
                                    <c:when test="${canUpdate == true}">
                                        <div class="form-group text-center">
                                            <button type="submit" class="btn btn-primary btnSubmitTicket">Modifier le ticket</button>
                                        </div>
                                    </c:when>
                                </c:choose>
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
<script src="/js/ticket/gestion.js"></script>
</body>
</html>