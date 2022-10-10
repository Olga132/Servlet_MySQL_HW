
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Учетная книга денежных операций</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body>
    <header>
        <nav class="navbar navbar-light bg-light justify-content-between">
            <a  href="<%=request.getContextPath()%>/list" class="navbar-brand">Главная/Обновить</a>
            <form action="search" method="post" class="form-inline">
                <input name="searchValue" class="form-control mr-sm-2" type="search" placeholder="Найти" aria-label="Найти">
                <button class="btn btn-success" type="submit">Поиск</button>
            </form>
        </nav>
    </header>
    <br>

<div class="row">

    <div class="container">
        <h3 class="text-center">Учетная книга денежных операций</h3>
        <hr>
        <div class="container">
            <a href="<%=request.getContextPath()%>/new" class="btn btn-success">Добавить запись</a>
        </div>
        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th>Дата</th>
                <th>Описание</th>
                <th>Сумма</th>
                <th>Статья движения</th>
                <th>Действия</th>
            </tr>
            </thead>
            <tbody>

            <c:forEach var="entry" items="${listEntry}">

                <tr>
                    <td>
                        <c:out value="${entry.id}" />
                    </td>
                    <td>
                        <c:out value="${entry.date}" />
                    </td>
                    <td>
                        <c:out value="${entry.specification}" />
                    </td>
                    <td>
                        <c:out value="${entry.sum}" />
                    </td>
                    <td>
                        <c:out value="${entry.movementType}" />
                    </td>
                    <td><a href="edit?id=<c:out value='${entry.id}' />">Изменить</a> &nbsp;&nbsp;&nbsp;&nbsp; <a href="delete?id=<c:out value='${entry.id}' />">Удалить</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="container text-left">
        <h4>Остаток средств : <fmt:formatNumber value="${listSum}" maxFractionDigits="2" /></h4>
        </div>
    </div>
</div>

</body>
</html>
