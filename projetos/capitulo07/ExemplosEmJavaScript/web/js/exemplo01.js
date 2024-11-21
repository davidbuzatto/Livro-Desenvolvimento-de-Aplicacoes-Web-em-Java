function executarExemplo01( event ) {
    
    // let indica a declaração de uma variável local
    // a função prompt retorna uma string
    let n1 = prompt( "Entre com o primeiro número" );
    
    // a função Number converte a string retornada
    // por prompt em um número
    let n2 = Number( prompt( "Entre com o segundo número" ) );
    
    // perceba que n1 é uma string!
    let adicao = n1 + n2;    // concatenação
    let subtracao = n1 - n2; // aqui subtração!
    let multiplicacao = n1 * n2;
    let divisao = n1 / n2;
    let resto = n1 % n2;
    
    // interpolação usando "`"
    let saida = `${n1} + ${n2} = ${adicao}\n` +
                // aspas simples
                n1 + ' - ' + n2 + ' = ' + subtracao + '\n' +
                // ou duplas
                n1 + " * " + n2 + " = " + multiplicacao + "\n" +
                `${n1} / ${n2} = ${divisao}\n` +
                `${n1} % ${n2} = ${resto}`;
    
    // um alerta. cuidado! alert é bloqueante,
    // assim como prompt e confirm.
    alert( saida );
    
    if ( confirm( "Mostrar a saída no console?" ) ) {
        // saída no console
        console.log( saida );
    }
    
}