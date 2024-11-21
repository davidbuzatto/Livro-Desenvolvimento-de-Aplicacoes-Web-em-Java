function executarExemplo03( event ) {
    
    let v0 = 0;         // número
    let v1 = 2;         // número
    let v2 = "2";       // string
    let v3 = true;      // boolean
    let v4 = null;      // nulo
    let v5 = undefined; // indefinido
    let v6 = NaN;       // Not a Number
    
    // 0, false (óbvio), null e undefined
    // são avaliados como falso
    
    if ( v0 ) {
        console.log( "não devia chegar aqui" )  // ";" não obrigatório,
                                                // mas é padrão usar
    }
    
    if ( v1 ) {
        console.log( "aqui sim :)" );
    }
    
    // operador de igualdade
    // converte os tipos e testa igualdade de valor!
    if ( v1 == v2 ) {
        console.log( "como assim???" );
    }
    
    // operador de identidade
    // verifica se os operandos têm mesmo tipo e mesmo valor!
    if ( v1 === v2 ) {
        console.log( "aqui não!" );
    }
    
    if ( v3 ) {
        console.log( "óbvio!" );
    }
    
    if ( v4 ) {
        console.log( "não!" );
    }
    
    if ( v5 ) {
        console.log( "não tbm!" );
    }
    
    if ( v6 ) {
        console.log( "não tbm!" );
    }
    
    // NaN é um valor especial
    if ( v6 == NaN ) {
        console.log( "pq não?" );
    }
    
    if ( v6 === NaN ) {
        console.log( "uai!?" );
    }
    
    if ( isNaN( v6 ) ) {
        console.log( "pra NaN, só assim..." );
    }
    
    // operadores de igualdade:
    //      igualdade: ==  (mesmo valor com conversão implícita)
    //      diferente: !=  (valor diferente com conversão implícita)
    
    // operadores de identidade:
    //     identidade: === (mesmo valor e mesmo tipo)
    // não identidade: !== (valor diferente e tipo diferente)
    
    // operadores relacionais:
    //          menor: <
    // menor ou igual: <=
    //          maior: >
    // maior ou igual: >=
    
    // operadores lógicos
    //   e lógico: &&
    //  ou lógico: ||
    // não lógico: !
    
    // veja a documentação referenciada no livro para mais detalhes
    
    switch ( v1 ) {
        case 1:
            console.log( "v1 vale 1" );
            break;
        case 2:
            console.log( "v1 vale 2" );
            break;
        default:
            console.log( "v1 vale alguma coisa..." );
            break;
    }
    
    // sem conversão automática!
    switch ( v1 ) {
        case "1":
            console.log( "v1 vale 1" );
            break;
        case "2":
            console.log( "v1 vale 2" );
            break;
        default:
            console.log( "v1 vale alguma coisa..." );
            break;
    }
    
    switch ( v2 ) {
        case "1":
            console.log( "v2 vale \"1\"" );
            break;
        case "2":
            console.log( "v2 vale \"2\"" );
            break;
        default:
            console.log( "v2 vale alguma coisa..." );
            break;
    }
    
}