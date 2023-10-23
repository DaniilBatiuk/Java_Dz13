<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>

<h1>Додати нову країну</h1>
<form action="hello-servlet" method="post">
    <label for="countryName">Назва країни:</label>
    <input type="text" id="countryName" name="countryName" required><br>

    <label for="capitalCity">Назва столиці:</label>
    <input type="text" id="capitalCity" name="capitalCity" required><br>

    <label for="population">Кількість населення:</label>
    <input type="number" id="population" name="population" required><br>

    <input type="submit" value="Додати країну">
</form>

</body>
</html>