<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<c:set var="prefixo" value="processaFornecedores?acao=preparar"/>
<!DOCTYPE html>

<html>
  <head>
    <title>Fornecedores Cadastrados</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet"
          href="${cp}/css/estilos.css"/>
  </head>

  <body>

    <h1>Fornecedores Cadastrados</h1>

    <p>
      <a href="${cp}/formularios/fornecedores/novo.jsp">
        Novo Fornecedor
      </a>
    </p>

    <table class="tabelaListagem">
      <thead>
        <tr>
          <th>Id</th>
          <th>Razão Social</th>
          <th>E-mail</th>
          <th>CNPJ</th>
          <th>Cidade</th>
          <th>Alterar</th>
          <th>Excluir</th>
        </tr>
      </thead>
      <tbody>

        <jsp:useBean 
            id="servicos"
            scope="page"
            class="vendaprodutos.servicos.FornecedorServices"/>

        <c:forEach items="${servicos.todos}" var="fornecedor">
          <tr>
            <td>${fornecedor.id}</td>
            <td>${fornecedor.razaoSocial}</td>
            <td>${fornecedor.email}</td>
            <td>${fornecedor.cnpj}</td>
            <td>${fornecedor.cidade.nome}</td>
            <td>
              <a href="${cp}/${prefixo}Alteracao&id=${fornecedor.id}">
                Alterar
              </a>
            </td>
            <td>
              <a href="${cp}/${prefixo}Exclusao&id=${fornecedor.id}">
                Excluir
              </a>
            </td>
          </tr>
        </c:forEach>
      </tbody>

    </table>

    <p>
      <a href="${cp}/formularios/fornecedores/novo.jsp">
        Novo Fornecedor
      </a>
    </p>

    <p><a href="${cp}/index.jsp">Tela Principal</a></p>

  </body>

</html>
