<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.Droit" %>
<%@ page import="java.util.ArrayList" %>
<%@ page pageEncoding="UTF-8" %>
<%
    String idProjet = request.getParameter("idProjet");
%>
<c:if test="${!empty idProjet}">
    <%
        ArrayList<Droit> droits = (ArrayList<Droit>) request.getSession().getAttribute("DROIT_SESSION");
        if (droits.contains(new Droit(Droit.Lecture))) {%>
    <li>
        <a href="/projet/${idProjet}">
            <i class="fa fa-file-code-o" aria-hidden="true"></i> <span class="nav-label">Code</span>
        </a>
    </li>
    <%}%>
    <li>
        <a href="/projet/${idProjet}/wiki">
            <i class="fa fa-wikipedia-w" aria-hidden="true"></i> <span class="nav-label">Wiki</span>
        </a>
    </li>
    <li>
        <a href="/projet/${idProjet}/ticket">
            <i class="fa fa-ticket" aria-hidden="true"></i> <span class="nav-label">Tickets</span>
        </a>
    </li>
    <%
        if (droits.contains(new Droit(Droit.Manager)) || droits.contains(new Droit(Droit.Administrateur))) {%>
    <li>
        <a href="/projet/${idProjet}/collaborateur">
            <i class="fa fa-users" aria-hidden="true"></i> <span class="nav-label">Collaborateurs</span>
        </a>
    </li>
    <%}%>

    <li>
        <a href="/projet/${idProjet}/chat">
            <i class="fa fa-comments" aria-hidden="true"></i> <span class="nav-label">Chat</span>
        </a>
    </li>
    <li>
        <a href="/projet/${idProjet}/commits">
            <i class="fa fa-github" aria-hidden="true"></i> <span class="nav-label">Commits</span>
        </a>
    </li>
    <li>
        <a href="/projet/${idProjet}/docs">
            <i class="fa fa-book" aria-hidden="true"></i> <span class="nav-label">Docs</span>
        </a>
    </li>
</c:if>