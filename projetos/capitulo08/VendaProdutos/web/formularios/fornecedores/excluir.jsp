<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>

<html>
  <head>
    <title>Excluir Fornecedor</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet"
          href="${cp}/css/estilos.css"/>
  </head>

  <body>

    <h1>Excluir Fornecedor</h1>

    <form method="post" action="${cp}/processaFornecedores">

      <input name="acao" type="hidden" value="excluir"/>
      <input name="id" type="hidden" value="${requestScope.fornecedor.id}"/>

      <table>
        <tr>
          <td class="alinhar-direita">Razão Social:</td>
          <td>${requestScope.fornecedor.razaoSocial}</td>
        </tr>
        <tr>
          <td class="alinhar-direita">CNPJ:</td>
          <td>${requestScope.fornecedor.cnpj}</td>
        </tr>
        <tr>
          <td class="alinhar-direita">E-mail:</td>
          <td>${requestScope.fornecedor.email}</td>
        </tr>
        <tr>
          <td class="alinhar-direita">Logradouro:</td>
          <td>${requestScope.fornecedor.logradouro}</td>
        </tr>
        <tr>
          <td class="alinhar-direita">Número:</td>
          <td>${requestScope.fornecedor.numero}</td>
        </tr>
        <tr>
          <td class="alinhar-direita">Bairro:</td>
          <td>${requestScope.fornecedor.bairro}</td>
        </tr>
        <tr>
          <td class="alinhar-direita">CEP:</td>
          <td>${requestScope.fornecedor.cep}</td>
        </tr>
        <tr>
          <td class="alinhar-direita">Cidade:</td>
          <td>${requestScope.fornecedor.cidade.nome}</td>
        </tr>
        <tr>
          <td>
            <a href="${cp}/formularios/fornecedores/listagem.jsp">
              Voltar
            </a>
          </td>
          <td class="alinhar-direita">
            <input type="submit" value="Excluir"/>
          </td>
        </tr>
      </table>

    </form>

  </body>

</html>
