<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>

<html>
  <head>
    <title>Excluir Estado</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet"
          href="${cp}/css/estilos.css"/>
  </head>

  <body>

    <h1>Excluir Estado</h1>

    <form method="post" action="${cp}/processaEstados">

      <input name="acao" type="hidden" value="excluir"/>
      <input name="id" type="hidden" value="${requestScope.estado.id}"/>

      <table>
        <tr>
          <td class="alinhar-direita">Nome:</td>
          <td>${requestScope.estado.nome}</td>
        </tr>
        <tr>
          <td class="alinhar-direita">Sigla:</td>
          <td>${requestScope.estado.sigla}</td>
        </tr>
        <tr>
          <td>
            <a href="${cp}/formularios/estados/listagem.jsp">
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
