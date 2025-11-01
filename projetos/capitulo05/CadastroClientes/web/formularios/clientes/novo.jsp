<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>

<html>
  <head>
    <title>Novo Cliente</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet"
          href="${cp}/css/estilos.css"/>
  </head>

  <body>

    <h1>Novo Cliente</h1>

    <form method="post" action="${cp}/processaClientes">

      <input name="acao" type="hidden" value="inserir"/>

      <table>
        <tr>
          <td class="alinhar-direita">Nome:</td>
          <td>
            <input name="nome"
                   type="text"
                   size="20"
                   maxlength="45"
                   required/>
          </td>
        </tr>
        <tr>
          <td class="alinhar-direita">Sobrenome:</td>
          <td>
            <input name="sobrenome"
                   type="text"
                   size="20"
                   maxlength="45"
                   required/>
          </td>
        </tr>
        <tr>
          <td class="alinhar-direita">Data de Nascimento:</td>
          <td>
            <input name="dataNascimento"
                   type="date"
                   size="8"
                   placeholder="dd/mm/yyyy"
                   required/>
          </td>
        </tr>
        <tr>
          <td class="alinhar-direita">CPF:</td>
          <td>
            <input name="cpf"
                   type="text"
                   size="13"
                   pattern="\d{3}.\d{3}.\d{3}-\d{2}"
                   placeholder="999.999.999-99"
                   required/>
          </td>
        </tr>
        <tr>
          <td class="alinhar-direita">E-mail:</td>
          <td>
            <input name="email"
                   type="email"
                   size="20"
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
                class="cadastroclientes.servicos.CidadeServices"/>

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
            <a href="${cp}/formularios/clientes/listagem.jsp">
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
