function executarExemplo04( event ) {
    
    // um array de uma dimensão
    let a1 = [ 1, 2, 3, 4 ];
    
    // um array de arrays
    let a2 = [ [ 1, 2 ], [ 3, 4 ] ];
    
    // um array vazio
    let a3 = [];
    a3["a"] = 2; // simulação de array associativo!
    a3["b"] = 4; // análogo à uma tabela de símbolos
    a3["c"] = 6; // mas as propriedades são inseridas 
    a3["d"] = 8; // no objeto!
    
    // arrays em JavaScript podem crescer e diminuir
    // livremente usando os métodos:
    //      push (insere no fim)
    //       pop (remove do fim)
    //   unshift (insere no início)
    //     shift (remove do início)
    //    splice (remove de uma posição fornecida)
    
    for ( let i = 0; i < a1.length; i++ ) {
        console.log( `a1[${i}] = ${a1[i]}` );
    }
    
    // iterando usando o método forEach do
    // objeto Array
    a1.forEach( function( valor, indice ) {
        console.log( `a1[${indice}] = ${valor}` );
    });
    
    for ( let i = 0; i < a2.length; i++ ) {
        console.log( `a2[${i}] = ${a2[i]}` );
    }
    
    // notação de closure
    a2.forEach( ( valor, indice ) => {
        console.log( `a2[${indice}] = ${valor}` );
    });
    
    // a3 não tem elementos, pois o usamos como um
    // "array associativo" 
    for ( let i = 0; i < a3.length; i++ ) {
        // não entra aqui...
        console.log( `a3[${i}] = ${a3[i]}` );
    }
    
    // e agora?
    a3.forEach( valor => {
        // não entra aqui também
        console.log( valor );
    });
    
    // assim funciona, pois iteramos pelas
    // propriedades do objeto
    for ( let chave in a3 ) {
        console.log( `a3[${chave}] = ${a3[chave]}` );
    }
    
    // ou
    Object.keys( a3 ).forEach( chave => {
        console.log( `a3[${chave}] = ${a3[chave]}` );
    });
    
    
    // métodos de iteração every e some.
    
    // o método forEach não foi projetado para
    // parar no meio da execução. para isso, existem
    // outros dois métodos análogos que podem ser
    // usados para esse propósito.
    
    // some => algum/alguns. a ideia é processar
    // alguns elementos do array até que uma condição seja
    // alcançada. o retorno true da função de callback faz
    // a iteração parar e falso continuar para o próximo
    // elemento.
    let algum;
    console.log( "há algum valor maior que 10?" );
    algum = a1.some( maiorQue10 );
    console.log( algum ? "sim" : "não" );
    
    console.log( "há algum valor menor que 10?" );
    algum = a1.some( menorQue10 );
    console.log( algum ? "sim" : "não" );
    
    // every => todos. a ideia é processar
    // todos elementos do array verificando se todos
    // passam por uma condição especificada na função
    // de callback. o retorno true faz a função continuar
    // para o próximo elemento, enquanto false a faz parar.
    let todos;
    console.log( "todos são maiores que 10?" );
    todos = a1.every( maiorQue10 );
    console.log( todos ? "sim" : "não" );
    
    console.log( "todos são menores que 10?" );
    todos = a1.every( menorQue10 );
    console.log( todos ? "sim" : "não" );
    
    // while e do while são análogos a C, C++, Java etc.
    
}

// callbacks para testes das funções some e every
function maiorQue10( valor ) {
    console.log( valor );
    return valor > 10;
}

function menorQue10( valor ) {
    console.log( valor );
    return valor < 10;
}