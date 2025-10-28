var contadorExemplo06 = 1;

function executarExemplo06( event ) {
    
    // obtém o elemento pelo identificador
    let div = document.getElementById( "divExemplo06" );
    
    // cria um novo elemento do tipo p (tag <p>)
    let p = document.createElement( "p" );
    
    // configura os atributos
    p.textContent = `JS Puro - Contador: ${contadorExemplo06++}`;
    p.className = "pDOM";
    
    // adiciona na div
    div.append( p );
    
}