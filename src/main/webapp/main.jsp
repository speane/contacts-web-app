<%@ page import="java.util.logging.LogManager" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Список контактов</title>
    </head>
    <body>
        <form class="center all-contacts" method="post">
            <h1 class="center">Список контактов</h1>
            <div class="clearfix">
                <a class="nav-button left" href="#">Search</a>
                <a class="nav-button left" href="#">Send e-mail</a>
                <button class="action-button right" formaction="#">ADD</button>
                <button class="action-button right" formaction="#">RMV</button>
                <button class="action-button right" formaction="#">EDT</button>
            </div>
            <div class="row">
                <div class="cell-1">Check</div>
                <div class="cell-3">Name</div>
                <div class="cell-2">Date of birth</div>
                <div class="cell-4">Address</div>
                <div class="cell-2">Company</div>
            </div>
            <div class="row">
                <div class="cell-1"><input type="checkbox" name="row-1"></div>
                <div class="cell-3">Evgeny Shilov</div>
                <div class="cell-2">25.06.1997</div>
                <div class="cell-4">Krichev</div>
                <div class="cell-2">:iTechArt</div>
            </div>
            <div class="row">
                <div class="cell-1"><input type="checkbox" name="row-1" checked></div>
                <div class="cell-3">Evgeny Shilov</div>
                <div class="cell-2">25.06.1997</div>
                <div class="cell-4">Krichev</div>
                <div class="cell-2">:iTechArt</div>
            </div>
        </form>
    </body>

    <link rel="stylesheet" href="css/grid-system.css">
    <link rel="stylesheet" href="css/style.css">
</html>
