<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<c:set var="prefixo" value="processaGeneros?acao=preparar"/>
<!DOCTYPE html>

<html>
  <head>
    <title>Gêneros Cadastrados</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet"
          href="${cp}/css/estilos.css"/>
  </head>

  <body>

    <h1>Gêneros Cadastrados</h1>

    <p>
      <a href="${cp}/formularios/generos/novo.jsp">
        Novo Gênero
      </a>
    </p>

    <table class="tabelaListagem">
      <thead>
        <tr>
          <th>Id</th>
          <th>Nome</th>
          <th>Alterar</th>
          <th>Excluir</th>
        </tr>
      </thead>
      <tbody>

        <jsp:useBean 
            id="servicos"
            scope="page"
            class="locacaomidias.servicos.GeneroServices"/>

        <c:forEach items="${servicos.todos}" var="genero">
          <tr>
            <td>${genero.id}</td>
            <td>${genero.descricao}</td>
            <td>
              <a href="${cp}/${prefixo}Alteracao&id=${genero.id}">
                Alterar
              </a>
            </td>
            <td>
              <a href="${cp}/${prefixo}Exclusao&id=${genero.id}">
                Excluir
              </a>
            </td>
          </tr>
        </c:forEach>
      </tbody>
      
    </table>

    <p>
      <a href="${cp}/formularios/generos/novo.jsp">
        Novo Gênero
      </a>
    </p>

    <p><a href="${cp}/index.jsp">Tela Principal</a></p>

  </body>

</html>
