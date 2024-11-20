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

    <h1>Sistema para Locação de Mídias</h1>
    
    <p>
      <a href="${cp}/formularios/locacoes/listagem.jsp" class="alerta">
        Locações (Implementar)
      </a>
    </p>
    
    <hr/>
    
    <p>
      <a href="${cp}/formularios/midias/listagem.jsp" class="alerta">
        Mídias (Implementar)
      </a>
    </p>
    <p>
      <a href="${cp}/formularios/exemplares/listagem.jsp" class="alerta">
        Exemplares (Implementar)
      </a>
    </p>
    
    <hr/>
    
    <p>
      <a href="${cp}/formularios/atorAtriz/listagem.jsp" class="alerta">
        Ator/Atriz (Implementar)
      </a>
    </p>
    <p>
      <a href="${cp}/formularios/tipos/listagem.jsp">
        Tipos
      </a>
    </p>
    <p>
      <a href="${cp}/formularios/generos/listagem.jsp">
        Gêneros
      </a>
    </p>
    <p>
      <a href="${cp}/formularios/classificacoesEtarias/listagem.jsp">
        Classificações Etárias
      </a>
    </p>
    <p>
      <a href="${cp}/formularios/classificacoesInternas/listagem.jsp">
        Classificações Internas
      </a>
    </p>
    
    <hr/>
    
    <p>
      <a href="${cp}/formularios/clientes/listagem.jsp">
        Clientes
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

  </body>

</html>
