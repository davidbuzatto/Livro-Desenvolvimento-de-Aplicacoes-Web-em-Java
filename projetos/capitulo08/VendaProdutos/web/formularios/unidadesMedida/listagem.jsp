<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<c:set var="prefixo" value="processaUnidadesMedida?acao=preparar"/>
<!DOCTYPE html>

<html>
  <head>
    <title>Unidades de Medida Cadastradas</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet"
          href="${cp}/css/estilos.css"/>
  </head>

  <body>

    <h1>Unidades de Medida Cadastradas</h1>

    <p>
      <a href="${cp}/formularios/unidadesMedida/novo.jsp">
        Nova Unidade de Medida
      </a>
    </p>

    <table class="tabela-listagem">
      <thead>
        <tr>
          <th>Id</th>
          <th>Descrição</th>
          <th>Sigla</th>
          <th>Alterar</th>
          <th>Excluir</th>
        </tr>
      </thead>
      <tbody>

        <jsp:useBean 
            id="servicos"
            scope="page"
            class="vendaprodutos.servicos.UnidadeMedidaServices"/>

        <c:forEach items="${servicos.todos}" var="un">
          <tr>
            <td>${un.id}</td>
            <td>${un.descricao}</td>
            <td>${un.sigla}</td>
            <td>
              <a href="${cp}/${prefixo}Alteracao&id=${un.id}">
                Alterar
              </a>
            </td>
            <td>
              <a href="${cp}/${prefixo}Exclusao&id=${un.id}">
                Excluir
              </a>
            </td>
          </tr>
        </c:forEach>
      </tbody>
      
    </table>

    <p>
      <a href="${cp}/formularios/unidadesMedida/novo.jsp">
        Nova Unidade de Medida
      </a>
    </p>

    <p><a href="${cp}/index.jsp">Tela Principal</a></p>

  </body>

</html>
