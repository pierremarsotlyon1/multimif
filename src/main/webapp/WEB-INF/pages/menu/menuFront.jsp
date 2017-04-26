<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>

<c:if test="${!empty success}">
    <span class="message-success hidden">${success}</span>
</c:if>
<c:if test="${!empty error}">
    <span class="message-error hidden">${error}</span>
</c:if>

<section class="navigation">
    <header style="border-bottom: 1px solid rgba(255,255,255,0.2); padding: 50px 0px;">
        <div class="header-content">

            <div class="logo">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <c:choose>
                    <c:when test="${sessionScope.userSession == null}">
                        <a class="navbar-brand" href="/">Multimif - Groupe 6</a>
                    </c:when>
                    <c:otherwise>
                        <a class="navbar-brand" href="/projets">Multimif - Groupe 6</a>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="header-nav">
                <nav>
                    <c:if test="${sessionScope.userSession != null}">
                        <ul class="primary-nav">
                            <li>
                                <a href="/projets">
                                    Mes projets
                                </a>
                            </li>
                        </ul>
                    </c:if>
                    <ul class="member-actions">
                        <c:choose>
                            <c:when test="${sessionScope.userSession != null}">
                                <li>
                                    <a href="/deconnexion">
                                        Déconnexion
                                    </a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li>
                                    <a href="/connexion">
                                        Connexion
                                    </a>
                                </li>
                                <li>
                                    <a class="btn-white btn-small" href="/register">
                                        Inscription
                                    </a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </nav>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
        </div>
    </header>
</section>