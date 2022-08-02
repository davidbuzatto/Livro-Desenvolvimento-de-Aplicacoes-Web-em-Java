<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>

<html>
  <head>
    <title>Sistema para Venda de Produtos</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet"
          href="${cp}/css/estilos.css"/>
  </head>

  <body>

    <h1>Sistema para Venda de Produtos</h1>

    <p>
      <a href="${cp}/formularios/vendas/listagem.jsp">
        Vendas
      </a>
    </p>
    <p>
      <a href="${cp}/formularios/clientes/listagem.jsp">
        Clientes
      </a>
    </p>
    <p>
      <a href="${cp}/formularios/fornecedores/listagem.jsp">
        Fornecedores
      </a>
    </p>
    <p>
      <a href="${cp}/formularios/produtos/listagem.jsp">
        Produtos
      </a>
    </p>
    <p>
      <a href="${cp}/formularios/cidades/listagem.jsp">
        Cidades
      </a>
    </p>
    <p>
      <a href="${cp}/formularios/estados/listagem.jsp">
        Estados
      </a>
    </p>
    <p>
      <a href="${cp}/formularios/unidadesMedida/listagem.jsp">
        Unidades de Medida
      </a>
    </p>

  </body>

</html>
