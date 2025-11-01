var contadorExemplo07 = 1;

function executarExemplo07( event ) {
    
    // seleciona a div com id divExemplo07
    // a jQuery usa a sintaxe os seletores do CSS
    let div = $( "#divExemplo07" );
    
    // cria um novo elemento do tipo p (tag <p>)
    // e configura os atributos encadeando a chamada de métodos
    let p = $( "<p></p>" )
            .html( `jQuery - Contador: ${contadorExemplo07++}` )
            .addClass( "p-dom" );
    
    // adiciona na div
    div.append( p );
    
}