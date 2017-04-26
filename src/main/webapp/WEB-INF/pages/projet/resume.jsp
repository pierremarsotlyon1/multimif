<%@ page import="model.Droit" %>
<%@ page import="java.util.ArrayList" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String idProjet = request.getParameter("idProjet");
    ArrayList<Droit> droits = (ArrayList<Droit>) request.getSession().getAttribute("DROIT_SESSION");
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <jsp:include page="../basecss.jsp"></jsp:include>
    <link href="/css/style.css" rel="stylesheet"/>
    <link href="/css/template/inspinia/style.css" rel="stylesheet"/>
    <link href="/css/bootTree.css" rel="stylesheet" type="text/css"/>
    <link href="/css/codemirror.css" rel="stylesheet"/>
    <link href="/css/dracula.css" rel="stylesheet"/>
</head>

<body class="pace-done">
<span class="spanIdProjet hidden">${idProjet}</span>
<div id="wrapper">
    <jsp:include page="../menu/menuBack.jsp"></jsp:include>
    <div id="page-wrapper" class="gray-bg">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-sm-4">
                <h2>Mon projet</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="/projets">Mes projets</a>
                    </li>
                    <li class="active">
                        <strong>Mon projet</strong>
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
        <div class="row hidden blockResume">
            <div class="wrapper wrapper-content animated fadeInUp">
                <div class="row">
                    <div class="col-md-4">
                        <div class="ibox ">
                            <div class="ibox-title">
                                <h5>Arborescence</h5>
                            </div>
                            <div class="ibox-content fh-600">
                                <div class="newtreeview"></div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-8">
                        <div class="ibox ">
                            <div class="ibox-title">
                                <div class="ibox-tools">
                                    <a role="button" class="btn btn-primary btn-xs hidden btn saveicon">
                                        <i class="fa fa-floppy-o" aria-hidden="true"></i> Sauvegarder le fichier
                                    </a>

                                    <div class="btn-group pull-left">
                                        <button data-toggle="dropdown" class="btn btn-default dropdown-toggle"
                                                aria-expanded="true">Action <span class="caret"></span></button>
                                        <ul class="dropdown-menu">
                                            <li>
                                                <a data-toggle="modal" data-target="#execModal">
                                                    Lancer le projet
                                                </a>
                                            </li>
                                            <li class="divider"></li>
                                            <li>
                                                <a data-toggle="modal" data-target="#mavenModal">
                                                    Lancer une commande Maven
                                                </a>
                                            </li>
                                            <li class="divider"></li>
                                            <li>
                                                <%if (droits.contains(new Droit(Droit.Compilation))) {%>
                                                <a role="button" type="submit" class="btn-compil">Compiler le projet</a>
                                                <%}%>
                                            </li>
                                        </ul>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                            <div class="ibox-content fh-200">
                                <div class="row blockUserEditionFichier hidden">
                                    <div class="col-md-12">
                                        <small class="label label-primary "><i class="fa fa-clock-o"></i> <span
                                                class="nbUserEditionFichier"></span></small>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="clearfix"></div>
                                <div class="row">
                                    <div class="col-md-12 hidden blockCodeMirror">
                                    <textarea <%if (droits.contains(new Droit(Droit.Ecriture))) {%>
                                            id="textarea-codemirror"
                                            <%} else {%>
                                            id="textarea-codemirror-read-only"
                                            <%}%>
                                            name="textarea-codemirror" class="textarea-codemirror"></textarea>
                                        <div class="clearfix"></div>
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
</div>

<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Ajouter au projet</h4>
            </div>
            <form method="POST" action="/projet/${idProjet}/addElement" commandName="createElementProjet"
                  class="formAddElementProjet">
                <div class="modal-body">
                    <input type="text" class="hidden" name="idDossier"/>
                    <input type="text" class="hidden" name="dossierRoot"/>
                    <div class="form-group">
                        <label for="name">Nom de l'élément</label>
                        <input name="name" type="text" class="form-control nameElementProjet" id="name"
                               placeholder="Nom de l'élément">
                    </div>

                    <div id="toto" class="form-group selectTypeElement">
                        <label>Type de l'élément</label>
                        <select name="type">
                            <option value="1">Fichier</option>
                            <option value="2">Dossier</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <div class="checkbox checkboxline">
                            <label>
                                <input type="checkbox" id="execcheckbox" name="main"/>
                                Executable ?
                            </label>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-default btnSubmitAddElementProjet" disabled="disabled">Créer
                    </button>
                </div>
            </form>
        </div>

    </div>
</div>
<div class="modal fade" id="execModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Choisir la classe à lancer</h4>
            </div>
            <div class="modal-body">
                <div class="col-md-12 emptyMainFile hidden">
                    <div class="alert alert-info">
                        Aucun point d'entrée dans votre projet
                    </div>
                </div>
                <div class="col-md-12 formCompilation hidden">
                    <div class="execSelect"></div>
                    <div class="form-group text-center">
                        <button type="submit" class="btn-launch btn btn-primary" data-dismiss="modal">Lancer le projet
                        </button>
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
            <div class="modal-footer">

            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="mavenModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="mavenModalLabel">Entrer la commande Maven a lancer</h4>
            </div>
            <div class="modal-body">

                <div class="col-md-12 formMaven">
                    <input type="text" id="mavenCommands"/>
                    <div class="form-group text-center">
                        <button type="submit" class="btn-maven btn btn-primary" data-dismiss="modal">Lancer la
                            commande
                        </button>
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
            <div class="modal-footer">
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="resultModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="resultModalLabel">Résultat</h4>
            </div>
            <div class="modal-body">
                <div class="col-md-12">
                    <div class="form-group">
                        <textarea class="form-control" readonly id="execArea" rows="10">
                        </textarea>
                    </div>
                </div>

                <div class="clearfix"></div>
            </div>
            <div class="modal-footer">
            </div>
        </div>
    </div>
</div>


<jsp:include page="../basejs.jsp"></jsp:include>
<script src="/js/socket-io.js"></script>
<script src="/js/jquery.json.js"></script>
<script src="/js/codemirror/lib/codemirror.js"></script>
<script src="/js/codemirror/mode/clike/clike.js"></script>
<script src="/js/codemirror/mode/python/python.js"></script>
<script src="/js/codemirror/mode/xml/xml.js"></script>
<script src="/js/codemirror/mode/javascript/javascript.js"></script>
<script src="/js/codemirror/addon/dialog/dialog.js"></script>
<script src="/js/codemirror/addon/hint/show-hint.js"></script>
<script src="/js/codemirror/addon/hint/anyword-hint.js"></script>
<script src="/js/codemirror/addon/search/search.js"></script>
<script src="/js/codemirror/addon/display/fullscreen.js"></script>
<script src="/js/codemirror/addon/comment/comment.js"></script>
<script src="/js/codemirror/addon/search/searchcursor.js"></script>
<script src="/js/bootTree.js"></script>
<!-- Create a CodeMirror instance -->
<%--<script src="/js/codemirror/customCodeMirror.js"></script>--%>
<!-- Gestion du sockets pour le codage simultané -->
<script src="/js/gestionEditionFichier.js"></script>
<script src="/js/gestionResume.js"></script>
<!-- Gestion de l'arborescence des fichiers -->


</body>
</html>