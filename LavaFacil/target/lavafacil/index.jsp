
<%@page contentType="text/html; ISO-8859-1" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>


<html>
<body>

    <h2>LOGAR NO SISTEMA</h2>
    <form action="login" method="post">
        <label for="email">
            <b>Login</b>
        </label>
        <input type="email" placeholder="E-mail" name="email" required>
        <br /> <br />
        <label for="senha">
            <b>Senha</b>
        </label>
        <input type="password" placeholder="Senha" name="senha" required>
        <br />
        <input type="submit" value="LOGAR" name="login">
    </form>

    <c:if test="${not empty msg}">
        <h2>${msg}</h2>
    </c:if>

</body>
</html>
