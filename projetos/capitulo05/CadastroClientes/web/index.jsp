<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>

<html>
  <head>
    <title>Sistema para Cadastro de Clientes</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet"
          href="${cp}/css/estilos.css"/>
  </head>

  <body>

    <h1>Sistema para Cadastro de Clientes</h1>

    <p>
      <a href="${cp}/formularios/clientes/listagem.jsp">Clientes</a>
    </p>
    <p>
      <a href="${cp}/formularios/cidades/listagem.jsp">Cidades</a>
    </p>
    <p>
      <a href="${cp}/formularios/estados/listagem.jsp">Estados</a>
    </p>

  </body>

</html>
