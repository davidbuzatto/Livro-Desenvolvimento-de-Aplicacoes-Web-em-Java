<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>

<html>
  <head>
    <title>Novo Fornecedor</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet"
          href="${cp}/css/estilos.css"/>
  </head>

  <body>

    <h1>Novo Fornecedor</h1>

    <form method="post" action="${cp}/processaFornecedores">

      <input name="acao" type="hidden" value="inserir"/>

      <table>
        <tr>
          <td class="alinhar-direita">Razão Social:</td>
          <td>
            <input name="razaoSocial"
                   type="text"
                   size="20"
                   maxlength="100"
                   required/>
          </td>
        </tr>
        <tr>
          <td class="alinhar-direita">CNPJ:</td>
          <td>
            <input name="cnpj"
                   type="text"
                   size="18"
                   pattern="\d{2}.\d{3}.\d{3}/\d{4}-\d{2}"
                   placeholder="99.999.999/9999-99"
                   required/>
          </td>
        </tr>
        <tr>
          <td class="alinhar-direita">E-mail:</td>
          <td>
            <input name="email"
                   type="email"
                   size="20"
                   minlength="3"
                   maxlength="60"
                   required/>
          </td>
        </tr>
        <tr>
          <td class="alinhar-direita">Logradouro:</td>
          <td>
            <input name="logradouro"
                   type="text"
                   size="25"
                   maxlength="50"
                   required/>
          </td>
        </tr>
        <tr>
          <td class="alinhar-direita">Número:</td>
          <td>
            <input name="numero"
                   type="text"
                   size="6"
                   maxlength="6"
                   required/>
          </td>
        </tr>
        <tr>
          <td class="alinhar-direita">Bairro:</td>
          <td>
            <input name="bairro"
                   type="text"
                   size="15"
                   maxlength="30"
                   required/>
          </td>
        </tr>
        <tr>
          <td class="alinhar-direita">CEP:</td>
          <td>
            <input name="cep"
                   type="text"
                   size="7"
                   pattern="\d{5}-\d{3}"
                   placeholder="99999-999"
                   required/>
          </td>
        </tr>
        <tr>
          <td class="alinhar-direita">Cidade:</td>
          <td>

            <jsp:useBean 
                id="servicos" 
                scope="page" 
                class="vendaprodutos.servicos.CidadeServices"/>

            <select name="idCidade" required>
              <c:forEach items="${servicos.todos}" var="cidade">
                <option value="${cidade.id}">
                  ${cidade.nome}
                </option>
              </c:forEach>
            </select>

          </td>
        </tr>
        <tr>
          <td>
            <a href="${cp}/formularios/fornecedores/listagem.jsp">
              Voltar
            </a>
          </td>
          <td class="alinhar-direita">
            <input type="submit" value="Salvar"/>
          </td>
        </tr>
      </table>

    </form>

  </body>

</html>
