<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>

<c:if test="${!empty success}">
    <span class="message-success hidden">${success}</span>
</c:if>
<c:if test="${!empty error}">
    <span class="message-error hidden">${error}</span>
</c:if>

<nav class="navbar-default navbar-static-side" role="navigation">
    <div class="sidebar-collapse">
        <ul class="nav metismenu" id="side-menu">
            <c:choose>
                <c:when test="${sessionScope.USER_SESSION != null}">
                    <li>
                        <a href="/projets">
                            <i class="fa fa-list-ul" aria-hidden="true"></i> <span class="nav-label">Mes projets</span>
                        </a>
                    </li>
                    <jsp:include page="../menuProjet.jsp"></jsp:include>
                    <li>
                        <a href="/deconnexion">
                            <i class="fa fa-sign-out" aria-hidden="true"></i> <span class="nav-label">Déconnexion</span>
                        </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li>
                        <a href="/connexion">
                            <span class="nav-label">Connexion</span>
                        </a>
                    </li>
                    <li>
                        <a href="/register">
                            <span class="nav-label">Inscription</span>
                        </a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>

    </div>
</nav>