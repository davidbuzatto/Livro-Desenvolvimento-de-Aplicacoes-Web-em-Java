function executarExemplo02( event ) {
    
    // a declaração de variáveis em JavaScript
    // merece uma certa atenção. além de não precisarem
    // de um tipo na declaração, elas tem alguns comportamentos
    // dependendo de como são declaradas.
    
    testarVariaveis(); // erro! v1, v2 e v3 não definidas
    //console.log( v1 );    // erro, não há valor atribuído 
    //console.log( v2 );    // ok, inicialização com undefined
    //console.log( v3 );    // erro, não definida na primeira execução
    console.log( "--------------------------" );
    
    let v1 = 10;   // escopo local, só existe dentro da função.
                   // análoga a uma varíavel de pilha.
                   // inicialização nesse ponto.
                   // const tem o mesmo comportamento.
    
    testarVariaveis();      // erro! v1, v2 e v3 não definidas
    //console.log( v1 );    // ok, imprime 10!
    //console.log( v2 );    // ok, inicialização com undefined
    //console.log( v3 );    // erro, não definida na primeira execução
    console.log( "--------------------------" );
    
    var v2 = 20;   // escopo local, só existe dentro da função.
                   // análoga a uma varíavel de pilha.
                   // inicialização com undefined
                   // e alteração do valor nesse ponto.
    
    testarVariaveis();      // erro! v1, v2 e v3 não definidas
    //console.log( v1 );    // ok, imprime 10!
    //console.log( v2 );    // ok, imprime 20!
    //console.log( v3 );    // erro, não definida
    console.log( "--------------------------" );
    
    v3 = 30;       // variável GLOBAL!!!
                   // não faça isso :P
    
    testarVariaveis();    // erro! v1, v2 não definidas e v3?
    console.log( v1 );    // ok, imprime 10!
    console.log( v2 );    // ok, imprime 20!
    console.log( v3 );    // aqui mora o perigo...
    
    alert( "Clique no botão novamente e inicie o caos!" );
    
}

function testarVariaveis() {
    
    try {
        // v1 não existe neste escopo nem
        // em um escopo externo
        console.log( v1 ); 
        v1++; // nunca chegará aqui
    } catch ( e ) {
        console.log( "v1 não declarada!" );
    }
    
    try {
        // v2 não existe neste escopo nem
        // em um escopo externo
        console.log( v2 );
        v2++; // nem aqui
    } catch ( e ) {
        console.log( "v2 não declarada!" );
    }
    
    try {
        // v3 passará a ser acessível quando
        // for encontrada pelo interpretador!
        console.log( v3 );
        v3++; // PERIGO!!!
    } catch ( e ) {
        console.log( "v3 não declarada!" );
    }
    
}