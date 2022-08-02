<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>

<html>
  <head>
    <title>Novo Produto</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet"
          href="${cp}/css/estilos.css"/>
  </head>

  <body>

    <h1>Novo Produto</h1>

    <form method="post" action="${cp}/processaProdutos">

      <input name="acao" type="hidden" value="inserir"/>

      <table>
        <tr>
          <td class="alinharDireita">Descrição:</td>
          <td>
            <input name="descricao"
                   type="text"
                   size="20"
                   maxlength="60"
                   required/>
          </td>
        </tr>
        <tr>
          <td class="alinharDireita">Código de Barras:</td>
          <td>
            <input name="codigoBarras"
                   type="text"
                   size="20"
                   pattern="\d{13}"
                   placeholder="9999999999999"
                   required/>
          </td>
        </tr>
        <tr>
          <td class="alinharDireita">Valor de Venda:</td>
          <td>
            <input name="valorVenda"
                   type="number"
                   size="8"
                   placeholder="R$ 9,99"
                   step="0.01"
                   min="0"
                   required/>
          </td>
        </tr>
        <tr>
          <td class="alinharDireita">Estoque:</td>
          <td>
            <input name="estoque"
                   type="number"
                   size="8"
                   placeholder="9,99"
                   step="0.01"
                   required/>
          </td>
        </tr>
        <tr>
          <td class="alinharDireita">Fornecedor:</td>
          <td>

            <jsp:useBean 
                id="servicosF" 
                scope="page" 
                class="vendaprodutos.servicos.FornecedorServices"/>

            <select name="idFornecedor" required>
              <c:forEach items="${servicosF.todos}" var="fornecedor">
                <option value="${fornecedor.id}">
                  ${fornecedor.razaoSocial}
                </option>
              </c:forEach>
            </select>

          </td>
        </tr>
        <tr>
          <td class="alinharDireita">Unidade de Medida:</td>
          <td>

            <jsp:useBean 
                id="servicosU" 
                scope="page" 
                class="vendaprodutos.servicos.UnidadeMedidaServices"/>

            <select name="idUnidadeMedida" required>
              <c:forEach items="${servicosU.todos}" var="un">
                <option value="${un.id}">
                  ${un.sigla}
                </option>
              </c:forEach>
            </select>

          </td>
        </tr>
        <tr>
          <td>
            <a href="${cp}/formularios/produtos/listagem.jsp">
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
