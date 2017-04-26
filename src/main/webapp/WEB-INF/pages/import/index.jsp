<%@ page pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Importer un projet</title>
    <jsp:include page="../basecss.jsp"></jsp:include>
    <link href="/css/template/inspinia/style.css" rel="stylesheet"/>
</head>

<body>

<div id="wrapper">
    <jsp:include page="../menu/menuBack.jsp"></jsp:include>
    <div id="page-wrapper" class="gray-bg">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-sm-4">
                <h2>Importer un projet</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="/projets">Mes projets</a>
                    </li>
                    <li class="active">
                        <strong>Importer un projet</strong>
                    </li>
                </ol>
            </div>
        </div>
        <div class="wrapper wrapper-content animated fadeInRight">
            <div class="row">
                <div class="col-md-6 col-md-offset-3">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>Importer un projet</h5>
                        </div>
                        <div class="ibox-content">
                            <div class="row">
                                <div class="col-md-12">
                                    <form action="/projet/import" method="POST" commandName="projet" enctype="multipart/form-data">

                                        <jsp:include page="../includes/formProjet.jsp"/>

                                        <div class="form-group">
                                            <label>Zip de votre projet</label>
                                            <input type="file" name="file" class="form-control" accept="application/zip"/>
                                        </div>

                                        <div class="form-group text-center">
                                            <button type="submit" class="btn btn-primary">Importer mon projet</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
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