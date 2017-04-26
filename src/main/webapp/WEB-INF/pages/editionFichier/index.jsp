<%@ page pageEncoding="UTF-8" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <jsp:include page="../basecss.jsp"></jsp:include>
</head>

<body>
<jsp:include page="../menu/menuBack.jsp"></jsp:include>

<div class="container">
  <div class="row">
    <div class="col-md-6 col-offset-md-3">
      <div class="col-md-12">
        <h2>Créer un projet</h2>
        <hr>
      </div>
      <div class="col-md-12">
        <input type="text" class="valueToSent"/>
        <input type="button" name="send" value="Send" class="btnSend"/>
      </div>
    </div>
  </div>
  <div class="row messages">

  </div>
</div>

<jsp:include page="../basejs.jsp"></jsp:include>
<script src="/js/socket-io.js"></script>
<script src="/js/jquery.json.js"></script>
<script src="/js/gestionEditionFichier.js"></script>
</body>
</html>