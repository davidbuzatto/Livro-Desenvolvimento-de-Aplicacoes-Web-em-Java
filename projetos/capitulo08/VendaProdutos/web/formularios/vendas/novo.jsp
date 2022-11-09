<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>

<html>
  <head>
    
    <title>Nova Venda</title>
    
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
    
    <link rel="stylesheet"
          href="${cp}/css/estilos.css"/>
    
    <script src="${cp}/js/libs/jquery/jquery.min.js"></script>
    <script src="${cp}/js/libs/decimal.js/decimal.min.js"></script>
    <script src="${cp}/js/formularios/vendas/novo.js"></script>
    
  </head>

  <body>

    <h1>Nova Venda</h1>

    <form id="formNovaVenda" method="post" action="${cp}/processaVendas">

      <input name="acao" type="hidden" value="inserir"/>
      <input id="hiddenItensVenda" name="itensVenda" type="hidden"/>

      <div id="divCliente">
        <jsp:useBean 
            id="servicosC" 
            scope="page" 
            class="vendaprodutos.servicos.ClienteServices"/>

        Cliente:
        <br/>
        <select id="selectCliente" name="idCliente" required>
          <c:forEach items="${servicosC.todos}" var="cliente">
            <option value="${cliente.id}">
              ${cliente.nome} ${cliente.sobrenome}
            </option>
          </c:forEach>
        </select>
      </div>
      
      <div id="divItensVenda">
        <table>
          <tr>
            <td>

              <jsp:useBean 
                  id="servicosP" 
                  scope="page" 
                  class="vendaprodutos.servicos.ProdutoServices"/>

              <p>
                Produto:
                <br/>
                <select id="selectProduto">
                  <c:forEach items="${servicosP.todos}" var="produto">
                      
                    <fmt:formatNumber
                        pattern="#.##"
                        minIntegerDigits="1"
                        minFractionDigits="2"
                        maxFractionDigits="2"
                        var="valorVenda"
                        scope="page"
                        value="${produto.valorVenda}"/>
                    
                    <option value="${produto.id}"
                            data-valor="${valorVenda}"
                            data-descricao="${produto.descricao}">
                      ${produto.descricao}
                      (R$ ${valorVenda}
                      por
                      ${produto.unidadeMedida.sigla})
                    </option>
                  </c:forEach>
                </select>
              </p>

              <p>
              Quantidade:
              <br/>
              <input id="txtQuantidade"
                     type="number"
                     size="3"
                     placeholder="9,99"
                     step="0.01"
                     min="0"/>
              </p>

            </td>
            <td class="btnsItensVenda">
              <p><input id="btnInserir" type="button" value="&#x2795;"/></p>
              <p><input id="btnRemover" type="button" value="&#x2796;"/></p>
              <p><input id="btnLimpar" type="button" value="&#x274C;"/></p>
            </td>
            <td>
              Itens da Venda:
              <br/>
              <select id="selectItensVenda" size="10" multiple>
              </select>
              <br/>
              <div>
                <div id="divTotal">Total: R$ 0,00</div>
              </div>
            </td>
          </tr>
          <tr>
            <td>
            </td>
            <td></td>
            <td class="alinharDireita">
              <input id="btnSalvar" type="submit" value="Salvar"/>
            </td>
          </tr>
        </table>
      </div>
            
      <a href="${cp}/formularios/vendas/listagem.jsp">
        Voltar
      </a>

    </form>

  </body>

</html>
