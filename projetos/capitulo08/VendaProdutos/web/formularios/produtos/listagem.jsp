<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<c:set var="prefixo" value="processaProdutos?acao=preparar"/>
<!DOCTYPE html>

<html>
  <head>
    <title>Produtos Cadastrados</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet"
          href="${cp}/css/estilos.css"/>
  </head>

  <body>

    <h1>Produtos Cadastrados</h1>

    <p>
      <a href="${cp}/formularios/produtos/novo.jsp">
        Novo Produto
      </a>
    </p>

    <table class="tabelaListagem">
      <thead>
        <tr>
          <th>Id</th>
          <th>Descrição</th>
          <th>Valor de Venda</th>
          <th>Estoque</th>
          <th>Fornecedor</th>
          <th>Unidade de Medida</th>
          <th>Alterar</th>
          <th>Excluir</th>
        </tr>
      </thead>
      <tbody>

        <fmt:setLocale value="pt_BR" />
          
        <jsp:useBean 
            id="servicos"
            scope="page"
            class="vendaprodutos.servicos.ProdutoServices"/>

        <c:forEach items="${servicos.todos}" var="produto">
          <tr>
            <td>${produto.id}</td>
            <td>${produto.descricao}</td>
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
            <td>
              <fmt:formatNumber
                  pattern="#.##"
                  minIntegerDigits="1"
                  minFractionDigits="2"
                  maxFractionDigits="2">
                ${produto.estoque}
              </fmt:formatNumber>
            </td>
            <td>${produto.fornecedor.razaoSocial}</td>
            <td>${produto.unidadeMedida.sigla}</td>
            <td>
              <a href="${cp}/${prefixo}Alteracao&id=${produto.id}">
                Alterar
              </a>
            </td>
            <td>
              <a href="${cp}/${prefixo}Exclusao&id=${produto.id}">
                Excluir
              </a>
            </td>
          </tr>
        </c:forEach>
      </tbody>

    </table>

    <p>
      <a href="${cp}/formularios/produtos/novo.jsp">
        Novo Produto
      </a>
    </p>

    <p><a href="${cp}/index.jsp">Tela Principal</a></p>

  </body>

</html>
