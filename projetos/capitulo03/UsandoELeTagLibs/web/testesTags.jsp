<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <title>Testes Tags JSP</title>
        <meta charset="UTF-8">
        <meta name="viewport" 
              content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        
        <%-- 
             Criando um objeto do tipo produto
             usando a tag <jsp:useBean> 
        --%>
        <jsp:useBean id="meuProduto"
                     class="entidades.Produto"
                     scope="page"/>
        <jsp:setProperty name="meuProduto"
                         property="codigo"
                         value="4"/>
        <jsp:setProperty name="meuProduto"
                         property="descricao"
                         value="Arroz"/>
        <jsp:setProperty name="meuProduto"
                         property="unidadeMedida"
                         value="kg"/>
        <jsp:setProperty name="meuProduto"
                         property="quantidade"
                         value="100"/>

        <h1>Produto Criado:</h1>
        ${pageScope.meuProduto.codigo},
        ${pageScope.meuProduto.descricao},
        ${pageScope.meuProduto.unidadeMedida}, 
        ${pageScope.meuProduto.quantidade}
        
    </body>
</html>
