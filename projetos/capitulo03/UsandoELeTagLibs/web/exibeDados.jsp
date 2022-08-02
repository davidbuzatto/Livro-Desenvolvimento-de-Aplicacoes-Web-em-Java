<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <title>Produto Obtido</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <style>
            .alinharDireita {
                text-align: right;
            }
        </style>
        
    </head>
    <body>
        <div>
            
            <h1>Produto Obtido</h1>

            <table>
                <tr>
                    <td class="alinharDireita">Código:</td>
                    <td>${requestScope.produtoObtido.codigo}</td>
                </tr>
                <tr>
                    <td class="alinharDireita">Descrição:</td>
                    <td>${requestScope.produtoObtido.descricao}</td>
                </tr>
                <tr>
                    <td class="alinharDireita">Unidade de Medida:</td>
                    <td>${requestScope.produtoObtido.unidadeMedida}</td>
                </tr>
                <tr>
                    <td class="alinharDireita">Quant. em Estoque:</td>
                    <td>${requestScope.produtoObtido.quantidade}</td>
                </tr>
                <tr>
                    <td colspan="2">
                        <a href="index.html">Voltar</a>
                    </td>
                </tr>
            </table>
                
        </div>
    </body>
</html>
