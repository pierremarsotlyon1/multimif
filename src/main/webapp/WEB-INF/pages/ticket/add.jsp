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
  <link href="/css/summernote.css" rel="stylesheet" />
  <link href="/css/style.css" rel="stylesheet"/>
    <link href="/css/template/inspinia/style.css" rel="stylesheet"/>

</head>

<body class="pace-done">

<div id="wrapper">
    <jsp:include page="../menu/menuBack.jsp"></jsp:include>
    <div id="page-wrapper" class="gray-bg">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-sm-12">
                <h2>Ajouter un ticket</h2>
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
                        <strong>Ajouter un ticket</strong>
                    </li>
                </ol>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="wrapper wrapper-content animated fadeInUp">
                    <div class="ibox">
                        <div class="ibox-title">
                            <h5>Ajouter un ticket</h5>
                        </div>
                        <div class="ibox-content">
                            <form action="/projet/${idProjet}/ticket/add" method="POST" commandName="ticket" class="formTicket">

                                <input name="description" type="text" id="description" hidden="hidden">

                                <div class="form-group">
                                    <label for="idTracker">Tracker</label>
                                    <select name="idTracker" id="idTracker">
                                        <c:forEach items="${trackers}" var="tracker">
                                            <option value="${tracker.id}">${tracker.nom}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label for="sujet">Sujet</label>
                                    <input name="sujet" type="text" class="form-control" id="sujet" placeholder="Sujet" required="required">
                                </div>

                                <div class="form-group">
                                    <label for="summernote">Description</label>
                                    <input type="text" class="form-control" id="summernote"
                                           placeholder="Une description du ticket">
                                </div>

                                <div class="form-group">
                                    <label for="idStatutTicket">Statut</label>
                                    <select name="idStatutTicket" id="idStatutTicket">
                                        <c:forEach items="${statutTickets}" var="statutTicket">
                                            <option value="${statutTicket.id}">${statutTicket.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label for="idPrioriteTicket">Priorité</label>
                                    <select name="idPrioriteTicket" id="idPrioriteTicket">
                                        <c:forEach items="${prioriteTickets}" var="prioriteTicket">
                                            <option value="${prioriteTicket.id}">${prioriteTicket.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label for="idUserAssigne">Assigner à</label>
                                    <select name="idUserAssigne" id="idUserAssigne">
                                        <c:forEach items="${users}" var="user">
                                            <option value="${user.id}">${user.email}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label for="sujet">Temps estimé (Heures)</label>
                                    <input name="tempsEstime" type="text" class="form-control" id="tempsEstime" placeholder="Temps estimé">
                                </div>

                                <div class="form-group">
                                    <label for="sujet">Réalisation (%)</label>
                                    <input name="realisation" type="text" class="form-control" id="realisation" placeholder="Réalisation">
                                </div>

                                <div class="form-group text-center">
                                    <button type="submit" class="btn btn-primary btnSubmitTicket">Ajouter le ticket</button>
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
<script src="/js/ticket/gestion.js"></script>
</body>
</html>