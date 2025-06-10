<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Cadastro de Veículo - Lava Fácil</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2>Cadastro de Veículo</h2>

        <c:if test="${not empty erro}">
            <div class="alert alert-danger" role="alert">
                ${erro}
            </div>
        </c:if>

        <form action="/veiculo" method="post" class="mt-4">
            <div class="mb-3">
                <label for="placa" class="form-label">Placa:</label>
                <input type="text" class="form-control" id="placa" name="placa" 
                       value="${veiculo.placa}" required 
                       pattern="[A-Z]{3}[0-9]{4}" 
                       title="Placa deve estar no formato ABC1234">
            </div>

            <div class="mb-3">
                <label for="tipo" class="form-label">Tipo:</label>
                <select class="form-select" id="tipo" name="tipo" required>
                    <option value="">Selecione o tipo</option>
                    <option value="CARRO" ${veiculo.tipo == 'CARRO' ? 'selected' : ''}>Carro</option>
                    <option value="MOTO" ${veiculo.tipo == 'MOTO' ? 'selected' : ''}>Moto</option>
                    <option value="CAMINHONETE" ${veiculo.tipo == 'CAMINHONETE' ? 'selected' : ''}>Caminhonete</option>
                    <option value="VAN" ${veiculo.tipo == 'VAN' ? 'selected' : ''}>Van</option>
                </select>
            </div>

            <div class="mb-3">
                <label for="nome" class="form-label">Nome/Modelo:</label>
                <input type="text" class="form-control" id="nome" name="nome" 
                       value="${veiculo.nome}" required>
            </div>

            <button type="submit" class="btn btn-primary">Cadastrar</button>
            <a href="/veiculo?acao=listar" class="btn btn-secondary">Cancelar</a>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Converte a placa para maiúsculas
        document.getElementById('placa').addEventListener('input', function(e) {
            e.target.value = e.target.value.toUpperCase();
        });
    </script>
</body>
</html> 