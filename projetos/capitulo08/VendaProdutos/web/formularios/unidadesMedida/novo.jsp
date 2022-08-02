<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>

<html>
  <head>
    <title>Nova Unidade de Medida</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet"
          href="${cp}/css/estilos.css"/>
  </head>

  <body>

    <h1>Nova Unidade de Medida</h1>

    <form method="post" action="${cp}/processaUnidadesMedida">

      <input name="acao" type="hidden" value="inserir"/>

      <table>
        <tr>
          <td class="alinharDireita">Descrição:</td>
          <td>
            <input name="descricao"
                   type="text"
                   size="20"
                   maxlength="45"
                   required/>
          </td>
        </tr>
        <tr>
          <td class="alinharDireita">Sigla:</td>
          <td>
            <input name="sigla"
                   type="text"
                   size="4"
                   maxlength="4"
                   required/>
          </td>
        </tr>
        <tr>
          <td>
            <a href="${cp}/formularios/unidadesMedida/listagem.jsp">
              Voltar
            </a>
          </td>
          <td class="alinharDireita">
            <input type="submit" value="Salvar"/>
          </td>
        </tr>
      </table>

    </form>

  </body>

</html>
