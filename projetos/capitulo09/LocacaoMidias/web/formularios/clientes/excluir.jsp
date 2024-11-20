<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>

<html>
  <head>
    <title>Excluir Cliente</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet"
          href="${cp}/css/estilos.css"/>
  </head>

  <body>

    <h1>Excluir Cliente</h1>

    <form method="post" action="${cp}/processaClientes">

      <input name="acao" type="hidden" value="excluir"/>
      <input name="id" type="hidden" value="${requestScope.cliente.id}"/>

      <table>
        <tr>
          <td class="alinharDireita">Nome:</td>
          <td>${requestScope.cliente.nome}</td>
        </tr>
        <tr>
          <td class="alinharDireita">Sobrenome:</td>
          <td>${requestScope.cliente.sobrenome}</td>
        </tr>
        <tr>
          <td class="alinharDireita">Data de Nascimento:</td>
          <td>
            <fmt:formatDate 
                pattern="dd/MM/yyyy"
                value="${requestScope.cliente.dataNascimento}"/>
          </td>
        </tr>
        <tr>
          <td class="alinharDireita">CPF:</td>
          <td>${requestScope.cliente.cpf}</td>
        </tr>
        <tr>
          <td class="alinharDireita">E-mail:</td>
          <td>${requestScope.cliente.email}</td>
        </tr>
        <tr>
          <td class="alinharDireita">Logradouro:</td>
          <td>${requestScope.cliente.logradouro}</td>
        </tr>
        <tr>
          <td class="alinharDireita">Número:</td>
          <td>${requestScope.cliente.numero}</td>
        </tr>
        <tr>
          <td class="alinharDireita">Bairro:</td>
          <td>${requestScope.cliente.bairro}</td>
        </tr>
        <tr>
          <td class="alinharDireita">CEP:</td>
          <td>${requestScope.cliente.cep}</td>
        </tr>
        <tr>
          <td class="alinharDireita">Cidade:</td>
          <td>${requestScope.cliente.cidade.nome}</td>
        </tr>
        <tr>
          <td>
            <a href="${cp}/formularios/clientes/listagem.jsp">
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
