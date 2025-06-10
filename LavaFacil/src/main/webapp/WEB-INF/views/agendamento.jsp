<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Novo Agendamento - Lava Fácil</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
  </head>
  <body>
    <div class="container mt-5">
      <h2>Novo Agendamento</h2>

      <form action="/agendamento" method="post" class="mt-4">
        <div class="mb-3">
          <label for="veiculo" class="form-label">Selecione seu veículo:</label>
          <select name="veiculo" id="veiculo" class="form-select" required>
            <c:forEach items="${veiculos}" var="veiculo">
              <option value="${veiculo.id}">
                ${veiculo.nome} - ${veiculo.placa}
              </option>
            </c:forEach>
          </select>
        </div>

        <div class="mb-3">
          <label for="tipoLavagem" class="form-label">Tipo de Lavagem:</label>
          <select
            name="tipoLavagem"
            id="tipoLavagem"
            class="form-select"
            required
          >
            <option value="SIMPLES">Lavagem Simples - R$ 30,00</option>
            <option value="COMPLETA">Lavagem Completa - R$ 50,00</option>
          </select>
        </div>

        <div class="mb-3">
          <label for="dataHora" class="form-label">Data e Hora:</label>
          <select name="dataHora" id="dataHora" class="form-select" required>
            <c:forEach items="${horariosDisponiveis}" var="horario">
              <option value="${horario}">
                <fmt:formatDate value="${horario}" pattern="dd/MM/yyyy HH:mm" />
              </option>
            </c:forEach>
          </select>
        </div>

        <button type="submit" class="btn btn-primary">Agendar</button>
        <a href="/agendamento?acao=listar" class="btn btn-secondary"
          >Cancelar</a
        >
      </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
