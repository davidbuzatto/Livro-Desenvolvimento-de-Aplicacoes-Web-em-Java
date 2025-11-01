<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>

<html>
  <head>
    <title>Alterar Produto</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet"
          href="${cp}/css/estilos.css"/>
  </head>

  <body>

    <h1>Alterar Produto</h1>

    <form method="post" action="${cp}/processaProdutos">

      <input name="acao" type="hidden" value="alterar"/>
      <input name="id" type="hidden" value="${requestScope.produto.id}"/>

      <table>
        <tr>
          <td class="alinhar-direita">Descrição:</td>
          <td>
            <input name="descricao"
                   type="text"
                   size="20"
                   maxlength="60"
                   required
                   value="${requestScope.produto.descricao}"/>
          </td>
        </tr>
        <tr>
          <td class="alinhar-direita">Código de Barras:</td>
          <td>
            <input name="codigoBarras"
                   type="text"
                   size="20"
                   pattern="\d{13}"
                   placeholder="9999999999999"
                   required
                   value="${requestScope.produto.codigoBarras}"/>
          </td>
        </tr>
        <tr>
          <td class="alinhar-direita">Valor de Venda:</td>
          <td>
            <input name="valorVenda"
                   type="number"
                   size="8"
                   placeholder="R$ 9,99"
                   step="0.01"
                   min="0"
                   required
                   value="${requestScope.produto.valorVenda}"/>
          </td>
        </tr>
        <tr>
          <td class="alinhar-direita">Estoque:</td>
          <td>
            <input name="estoque"
                   type="number"
                   size="8"
                   placeholder="9,99"
                   step="0.01"
                   required
                   value="${requestScope.produto.estoque}"/>
          </td>
        </tr>
        <tr>
          <td class="alinhar-direita">Fornecedor:</td>
          <td>
            <jsp:useBean 
                id="servicosF" 
                scope="page" 
                class="vendaprodutos.servicos.FornecedorServices"/>

            <select name="idFornecedor" required>
              <c:forEach items="${servicosF.todos}" var="fornecedor">
                <c:choose>
                  <c:when test="${requestScope.produto.fornecedor.id eq fornecedor.id}">
                    <option value="${fornecedor.id}" selected>
                      ${fornecedor.razaoSocial}
                    </option>
                  </c:when>
                  <c:otherwise>
                    <option value="${fornecedor.id}">
                      ${fornecedor.razaoSocial}
                    </option>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </select>
          </td>
        </tr>
        <tr>
          <td class="alinhar-direita">Unidade de Medida:</td>
          <td>
            <jsp:useBean 
                id="servicosU" 
                scope="page" 
                class="vendaprodutos.servicos.UnidadeMedidaServices"/>

            <select name="idUnidadeMedida" required>
              <c:forEach items="${servicosU.todos}" var="un">
                <c:choose>
                  <c:when test="${requestScope.produto.unidadeMedida.id eq un.id}">
                    <option value="${un.id}" selected>
                      ${un.sigla}
                    </option>
                  </c:when>
                  <c:otherwise>
                    <option value="${un.id}">
                      ${un.sigla}
                    </option>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </select>
          </td>
        </tr>
        <tr>
          <td>
            <a href="${cp}/formularios/produtos/listagem.jsp">Voltar</a>
          </td>
          <td class="alinhar-direita">
            <input type="submit" value="Alterar"/>
          </td>
        </tr>
      </table>

    </form>

  </body>

</html>
