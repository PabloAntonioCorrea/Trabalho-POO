<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>

<html>
<body>
    <h1>Cadastro de usuários</h1>
    <form action="usuario" method="post">
        <label for="email">
            Email
        </label>
        <input type="text" placeholder="Email" name="email" required>
        <label for="Senha">
            Senha
        </label>
        <input type="password" placeholder="Senha" name="senha" required>
        <input type="submit" value="cadastrar" name="CADASTRAR">
    </form>

    <c:if test="${not empty msg}">
        <h2>${msg}</h2>
    </c:if>

<table>
    <th>Email</th>
    <th>Ativo</th>
    <th>Ações</th>
    <c:forEach var="usuario" items="${usuarios}">
        <tr>
          <!--  <td>${usuario.id}</td> -->
            <td>${usuario.email}</td>
            <td>${usuario.ativo ? 'Sim' : 'Não'}</td>
            <td>
                <a href="usuario?opcao=editar&&info=${usuario.id}">Editar</a>
            </td>
            <td>
                <a href="usuario?opcao=excluir&&info=${usuario.id}">Excluir</a>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
