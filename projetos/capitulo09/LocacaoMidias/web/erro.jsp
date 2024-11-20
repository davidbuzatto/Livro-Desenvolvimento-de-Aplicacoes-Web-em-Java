<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>

<html>
  <head>
    <title>Erro(s)!</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet"
          href="${cp}/css/estilos.css"/>
  </head>

  <body>

    <h1>Erro(s)!</h1>
    
    <div id="divErros">
      <ul>
        ${requestScope.mensagemErro}
      </ul>
    </div>
    
    <a href="${requestScope.voltarPara}">Voltar</a>

  </body>

</html>