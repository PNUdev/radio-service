<html>
<head>
    <#include "./coreDependencies.ftl" >
    <title>Admin</title>
</head>
<body>
<nav class="navbar navbar-dark bg-info">
    <div>
        <a class="navbar-brand" href="/admin/schedule">Розклад</a>
        <a class="navbar-brand" href="/admin/programs">Програми</a>
        <a class="navbar-brand" href="/admin/videos">Відео</a>
        <a class="navbar-brand" href="/admin/banners">Банери</a>
    </div>
    <div class="d-inline">
        <form method="POST" action="/logout" class="m-0 p-0">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-light">Вийти</button>
        </form>
    </div>
</nav>