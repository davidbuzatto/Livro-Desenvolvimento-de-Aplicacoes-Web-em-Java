let contadorOpSelect03 = 4;
let contadorOpSelect04 = 4;

function lerDadosFormulario( event ) {
    
    // obtém os dados do formulário usando
    // JavaScript puro
    
    // obtém por id (não deve haver mais de um!)
    // e pegar a propriedade value
    let campo01 = document.getElementById( "campo01" ).value;
    
    // obtém pelo atributo name (pode haver mais de um!)
    // e na seleção, usa o primeiro elemento do resultado
    let campo02 = document.getElementsByName( "campo02" )[0].value;
    
    // por id
    let select03 = document.getElementById( "select03" ).value;
    let select04 = document.getElementById( "select04" ).value;
    let area05 = document.getElementById( "area05" ).value;
    
    alert( 
           `Campo 01: ${campo01}\n` +
           `Campo 02: ${campo02}\n` +
           `Select 03: ${select03}\n` +
           `Select 04: ${select04}\n` +
           `Área 05: ${area05}` );
    
}

// funções podem ser atribuídas à variáveis!
var lerDadosFormularioJQuery = function( event ) {
    
    // com a jQuery, usa-se a sintaxe igual
    // aos seletores do CSS
    
    // a função val() retornará o valor
    // do componente de forma padronizada
    let campo01 = $( "#campo01" ).val();
    let campo02 = $( "#campo02" ).val();
    let select03 = $( "#select03" ).val();
    let select04 = $( "#select04" ).val();
    let area05 = $( "#area05" ).val();
    
    alert( 
           `Campo 01: ${campo01}\n` +
           `Campo 02: ${campo02}\n` +
           `Select 03: ${select03}\n` +
           `Select 04: ${select04}\n` +
           `Área 05: ${area05}` );
    
}; // termina com ponto e vírgula

// pode-se usar a sintaxe de closures
let inserirDadosFormulario = event => {
    
    // configurando os valores
    document.getElementById( "campo01" ).value = "campo 01 atualizado";
    document.getElementsByName( "campo02" )[0].value = "campo 02 também";
    document.getElementById( "select03" ).value = "o2";
    document.getElementById( "select04" ).value = "o3";
    document.getElementById( "area05" ).value = "outro valor";
    
}; // termina com ponto e vírgula

function inserirDadosFormularioJQuery( event ) {
    
    // configura os valores usando a função val()
    $( "#campo01" ).val( "novo valor campo 01" );
    $( "#campo02" ).val( "outro novo valor... ");
    $( "#select03" ).val( "o3");
    $( "#select04" ).val( "o1" );
    $( "#area05" ).val( "mudando o valor da área" );
    
}

function inserirNovaOpcao( event ) {
    
    // cria um elemento do tipo option (tag <option>)
    let op = document.createElement( "option" );
    
    // configura
    op.innerHTML = `Opção ${contadorOpSelect03}j`;
    op.value = `o${contadorOpSelect03}j`;
    
    // obtém o select e adiciona o op
    document.getElementById( "select03" ).add( op );
    
    contadorOpSelect03++;
    
}

function inserirNovaOpcaoJQuery( event ) {
    
    // com jQuery é um pouco mais limpo
    let op = $( "<option></option>" )
            .html( `Opção ${contadorOpSelect04}jq` )
            .val( `o${contadorOpSelect04}jq` );
    
    $( "#select04" ).append( op );
    
    contadorOpSelect04++;
    
}