function executarExemplo11jQuery( event ) {
    
    let q = prompt( "Quantidade de pessoas:" );
    
    $.ajax( "listarPessoas", {
        data: {
            quantidade: q
        },
        // esperando JSON no retorno
        dataType: "json"
    }).done( ( data, textStatus ) =>{
        
        // data já contém o objeto resultado do parse
        // do json retornado. isso é automático.
        let $div = $( "#divExemplo11" );
        $div.html( "" );
        
        data.forEach( pessoa => {
            $div.append( 
                `<div class="dadosPessoa">Pessoa:<p>Nome: ${pessoa.nome}</p>`+
                `<p>Data de Nascimento: ${pessoa.dataNasc}</p>` +
                `<p>Salário: R$ ${pessoa.salario}</p></div>` );
        });
        
    }).fail( ( jqXHR, textStatus, errorThrown ) => {
        alert( "Erro: " + errorThrown + "\n" +
               "Status: " + textStatus );
    });
    
}

function executarExemplo11Fetch( event ) {
    
    let n = prompt( "Calcular a tabuada de:" );
    
    let parametros = new URLSearchParams();
    parametros.append( "quantidade", n );
    
    fetch( "listarPessoas", {
        method: "POST",
        body: parametros
    }).then( response => {
        // faz o parse do json em objeto e retorna
        return response.json();
    }).then( data => {
        
        let $div = $( "#divExemplo11" );
        $div.html( "" );
        
        data.forEach( pessoa => {
            $div.append( 
                `<div class="dadosPessoa">Pessoa:<p>Nome: ${pessoa.nome}</p>`+
                `<p>Data de Nascimento: ${pessoa.dataNasc}</p>` +
                `<p>Salário: R$ ${pessoa.salario}</p></div>` );
        });
        
    }).catch( error => {
        alert( "Erro: " + error );
    });
    
}