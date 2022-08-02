<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>

<html>
  <head>
    <title>Excluir Produto</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet"
          href="${cp}/css/estilos.css"/>
  </head>

  <body>

    <h1>Excluir Produto</h1>

    <form method="post" action="${cp}/processaProdutos">

      <input name="acao" type="hidden" value="excluir"/>
      <input name="id" type="hidden" value="${requestScope.produto.id}"/>
      <fmt:setLocale value="pt_BR" />
      
      <table>
        <tr>
          <td class="alinharDireita">Descrição:</td>
          <td>${requestScope.produto.descricao}</td>
        </tr>
        <tr>
          <td class="alinharDireita">Código de Barras:</td>
          <td>${requestScope.produto.codigoBarras}</td>
        </tr>
        <tr>
          <td class="alinharDireita">Valor de Venda:</td>
          <td>
            R$
            <fmt:formatNumber
                pattern="#.##"
                minIntegerDigits="1"
                minFractionDigits="2"
                maxFractionDigits="2">
              ${produto.valorVenda}
            </fmt:formatNumber>
          </td>
        </tr>
        <tr>
          <td class="alinharDireita">Estoque:</td>
          <td>
            <fmt:formatNumber
                pattern="#.##"
                minIntegerDigits="1"
                minFractionDigits="2"
                maxFractionDigits="2">
              ${produto.estoque}
            </fmt:formatNumber>
          </td>
        </tr>
        <tr>
          <td class="alinharDireita">Fornecedor:</td>
          <td>${requestScope.produto.fornecedor.razaoSocial}</td>
        </tr>
        <tr>
          <td class="alinharDireita">Unidade de Medida:</td>
          <td>${requestScope.produto.unidadeMedida.sigla}</td>
        </tr>
        <tr>
          <td>
            <a href="${cp}/formularios/produtos/listagem.jsp">
              Voltar
            </a>
          </td>
          <td class="alinharDireita">
            <input type="submit" value="Excluir"/>
          </td>
        </tr>
      </table>

    </form>

  </body>

</html>
