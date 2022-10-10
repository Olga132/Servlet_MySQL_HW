<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<head>
    <title>Учетная книга денежных операций</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>

<body>

    <header>
        <nav class="navbar navbar-light bg-light justify-content-between">
            <a href="<%=request.getContextPath()%>/list" class="navbar-brand">Возврат в книгу</a>
        </nav>
    </header>
    <br>
    <div class="container col-md-5">
        <div class="card">
            <div class="card-body">
                <c:if test="${entry != null}">
                <form action="update" method="post">
                    </c:if>
                    <c:if test="${entry == null}">
                    <form action="insert" method="post">
                        </c:if>

                        <caption>
                            <h2>
                                <c:if test="${entry != null}">
                                    Изменить запись
                                </c:if>
                                <c:if test="${entry == null}">
                                    Новая запись
                                </c:if>
                            </h2>
                        </caption>

                        <c:if test="${entry != null}">
                            <input type="hidden" name="id" value="<c:out value='${entry.id}' />" />
                        </c:if>

                        <fieldset class="form-group">
                            <label>Дата</label> <input type="date" value="<c:out value='${entry.date}' />" class="form-control" name="date" required="required">
                        </fieldset>

                        <fieldset class="form-group">
                            <label>Описание</label> <input type="text" value="<c:out value='${entry.specification}' />" class="form-control" name="specification" required="required">
                        </fieldset>

                        <fieldset class="form-group">
                            <label>Статья движения</label>
                            <select class="form-control" name="movementTypeString">
                                <option value="coming">Приход</option>
                                <option value="consumption">Расход</option>
                            </select>
                        </fieldset>

                        <fieldset class="form-group">
                            <label>Сумма</label> <input type="number" step="0.01" value="<c:out value='${entry.sum}' />" class="form-control" name="sum" required="required">
                        </fieldset>
                        <button type="submit" class="btn btn-success">Сохранить</button>
                    </form>
            </div>
        </div>
    </div>
</body>
</html>

