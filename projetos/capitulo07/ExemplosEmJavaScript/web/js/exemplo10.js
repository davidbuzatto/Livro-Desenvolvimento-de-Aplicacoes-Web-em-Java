function executarExemplo10jQuery( event ) {
    
    let n = prompt( "Calcular a tabuada de:" );
    
    // prepara uma requisição assíncrona usando jQuery
    // para a URL "calcularTabuada", enviando o parâmetro
    // numero na requisição como atributo do objeto data
    // configurado no objeto de opções da função
    $.ajax( "calcularTabuada", {
        
        // objeto data
        data: {
            numero: n
        },
        
        // atributo dataType do objeto de opções que 
        // indica qual o tipo de dado que é esperado
        // quando a requisição for bem suncedida.
        // nesse caso, um retorno de texto puro
        dataType: "text"
        
        // done associa uma função de callback para
        // tratar os dados da requisição caso seja
        // bem sucedida
    }).done( ( data, textStatus ) =>{
        $( "#divExemplo10" ).html( data );
        
        // fail é análoga a done, com a diferença
        // que lida com problemas na requisição
    }).fail( ( jqXHR, textStatus, errorThrown ) => {
        alert( "Erro: " + errorThrown + "\n" +
               "Status: " + textStatus );
    });
    
}

function executarExemplo10Fetch( event ) {
    
    let n = prompt( "Calcular a tabuada de:" );
    
    // cria um objeto do tipo URLSearchParams
    // que encapsula os parâmetros enviados
    // pela requisição assíncrona da função
    // fetch
    let parametros = new URLSearchParams();
    parametros.append( "numero", n );
    
    // envia uma requisição à URL "calcularTabuada" e passa
    // um init object com os atributos method e body
    fetch( "calcularTabuada", {
        method: "POST",
        body: parametros
        
        // se bem sucedido, retorna o texto da resposta
    }).then( response => {
        return response.text();
        
        // encadeia com o then anterior, tratando o texto
        // retornado
    }).then( text => {
        $( "#divExemplo10" ).html( text );
        
        // se algum problema ocorrer...
    }).catch( error => {
        alert( "Erro: " + error );
    });
    
}