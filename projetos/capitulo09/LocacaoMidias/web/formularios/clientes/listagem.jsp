<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<c:set var="prefixo" value="processaClientes?acao=preparar"/>
<!DOCTYPE html>

<html>
  <head>
    <title>Clientes Cadastrados</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet"
          href="${cp}/css/estilos.css"/>
  </head>

  <body>

    <h1>Clientes Cadastrados</h1>

    <p>
      <a href="${cp}/formularios/clientes/novo.jsp">
        Novo Cliente
      </a>
    </p>

    <table class="tabela-listagem">
      <thead>
        <tr>
          <th>Id</th>
          <th>Nome</th>
          <th>Sobrenome</th>
          <th>E-mail</th>
          <th>CPF</th>
          <th>Cidade</th>
          <th>Alterar</th>
          <th>Excluir</th>
        </tr>
      </thead>
      <tbody>

        <jsp:useBean 
            id="servicos"
            scope="page"
            class="locacaomidias.servicos.ClienteServices"/>

        <c:forEach items="${servicos.todos}" var="cliente">
          <tr>
            <td>${cliente.id}</td>
            <td>${cliente.nome}</td>
            <td>${cliente.sobrenome}</td>
            <td>${cliente.email}</td>
            <td>${cliente.cpf}</td>
            <td>${cliente.cidade.nome}</td>
            <td>
              <a href="${cp}/${prefixo}Alteracao&id=${cliente.id}">
                Alterar
              </a>
            </td>
            <td>
              <a href="${cp}/${prefixo}Exclusao&id=${cliente.id}">
                Excluir
              </a>
            </td>
          </tr>
        </c:forEach>
      </tbody>

    </table>

    <p>
      <a href="${cp}/formularios/clientes/novo.jsp">
        Novo Cliente
      </a>
    </p>

    <p><a href="${cp}/index.jsp">Tela Principal</a></p>

  </body>

</html>
