<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>

<html>
  <head>
    <title>Novo Estado</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet"
          href="${cp}/css/estilos.css"/>
  </head>

  <body>

    <h1>Novo Estado</h1>

    <form method="post" action="${cp}/processaEstados">

      <input name="acao" type="hidden" value="inserir"/>

      <table>
        <tr>
          <td class="alinhar-direita">Nome:</td>
          <td>
            <input name="nome"
                   type="text"
                   size="20"
                   maxlength="30"
                   required/>
          </td>
        </tr>
        <tr>
          <td class="alinhar-direita">Sigla:</td>
          <td>
            <input name="sigla"
                   type="text"
                   size="3"
                   maxlength="2"
                   required/>
          </td>
        </tr>
        <tr>
          <td>
            <a href="${cp}/formularios/estados/listagem.jsp">
              Voltar
            </a>
          </td>
          <td class="alinhar-direita">
            <input type="submit" value="Salvar"/>
          </td>
        </tr>
      </table>

    </form>

  </body>

</html>
