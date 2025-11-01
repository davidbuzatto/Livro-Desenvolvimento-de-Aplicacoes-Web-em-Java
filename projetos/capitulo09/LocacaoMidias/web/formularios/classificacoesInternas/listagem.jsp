<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<c:set var="prefixo" value="processaClassificacoesInternas?acao=preparar"/>
<!DOCTYPE html>

<html>
  <head>
    <title>Classificações Internas Cadastradas</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet"
          href="${cp}/css/estilos.css"/>
  </head>

  <body>

    <h1>Classificações Internas Cadastradas</h1>

    <p>
      <a href="${cp}/formularios/classificacoesInternas/novo.jsp">
        Nova Classificação Interna
      </a>
    </p>

    <table class="tabela-listagem">
      <thead>
        <tr>
          <th>Id</th>
          <th>Nome</th>
          <th>Valor do Aluguel</th>
          <th>Alterar</th>
          <th>Excluir</th>
        </tr>
      </thead>
      <tbody>

        <jsp:useBean 
            id="servicos"
            scope="page"
            class="locacaomidias.servicos.ClassificacaoInternaServices"/>

        <c:forEach items="${servicos.todos}" var="classificacaoInterna">
          <tr>
            <td>${classificacaoInterna.id}</td>
            <td>${classificacaoInterna.descricao}</td>
            <td>
              R$
              <fmt:formatNumber
                  pattern="#.##"
                  minIntegerDigits="1"
                  minFractionDigits="2"
                  maxFractionDigits="2">
                ${classificacaoInterna.valorAluguel}
              </fmt:formatNumber>
            </td>
            <td>
              <a href="${cp}/${prefixo}Alteracao&id=${classificacaoInterna.id}">
                Alterar
              </a>
            </td>
            <td>
              <a href="${cp}/${prefixo}Exclusao&id=${classificacaoInterna.id}">
                Excluir
              </a>
            </td>
          </tr>
        </c:forEach>
      </tbody>
      
    </table>

    <p>
      <a href="${cp}/formularios/classificacoesInternas/novo.jsp">
        Nova Classificação Interna
      </a>
    </p>

    <p><a href="${cp}/index.jsp">Tela Principal</a></p>

  </body>

</html>
