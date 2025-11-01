<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>

<html>
  <head>
    <title>Nova Classificação Interna</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet"
          href="${cp}/css/estilos.css"/>
  </head>

  <body>

    <h1>Nova Classificação Interna</h1>

    <form method="post" action="${cp}/processaClassificacoesInternas">

      <input name="acao" type="hidden" value="inserir"/>

      <table>
        <tr>
          <td class="alinhar-direita">Descrição:</td>
          <td>
            <input name="descricao"
                   type="text"
                   size="20"
                   maxlength="45"
                   required/>
          </td>
        </tr>
        <tr>
          <td class="alinhar-direita">Valor do Aluguel:</td>
          <td>
            <input name="valorAluguel"
                   type="number"
                   size="8"
                   placeholder="R$ 9,99"
                   step="0.01"
                   min="0"
                   required/>
          </td>
        </tr>
        <tr>
          <td>
            <a href="${cp}/formularios/classificacoesInternas/listagem.jsp">
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
