class Forma {
    
    // só pode haver um construtor
    constructor( xIni, yIni, xFim, yFim ) {
        // criação da propriedade
        // usando this
        this.xIni = xIni;
        this.yIni = yIni;
        this.xFim = xFim;
        this.yFim = yFim;
    }
    
    calcularArea() {
        return 0;
    }
    
}

// "herança"
class Retangulo extends Forma {
    
    // sobrescrevendo a função calcularArea
    calcularArea() {
        let largura = Math.abs( this.xFim - this.xIni );
        let altura = Math.abs( this.yFim - this.yIni );
        return largura * altura;
    }
    
}

class Circulo extends Forma {
    
    // novo construtor
    constructor( xCentro, yCentro, raio ) {
        super( xCentro, yCentro, 0, 0 );
        this.raio = raio;
    }
    
    calcularArea() {
        return Math.PI * this.raio * this.raio;
    }
    
}

function executarExemplo05( event ) {
    
    let r = new Retangulo( 0, 0, 10, 20 );
    let c = new Circulo( 5, 10, 10 );
    
    // um objeto criado usando o inicilizador de objetos
    let o = { 
        nome: "joão",
        sobrenome: "da silva",
        endereco: {
            logradouro: "rua um",
            numero: 100,
            cep: "12345-67",
            cidade: {
                nome: "Vargem Grande do Sul",
                estado: {
                    nome: "São Paulo",
                    sigla: "SP"
                }
            }
        }
    };
    
    // tipos dos objetos
    console.log( "Retângulo:", typeof r );
    console.log( "Círculo:", typeof c );
    console.log( "Genérico:", typeof o );
    
    // são instâncias de?
    console.log( "Retângulo é um Objeto?", r instanceof Object );
    console.log( "Retângulo é uma Forma?", r instanceof Forma );
    console.log( "Retângulo é um Retângulo?", r instanceof Retangulo );
    console.log( "Retângulo é um Círculo?", r instanceof Circulo );
    
    console.log( "Círculo é um Objeto?", c instanceof Object );
    console.log( "Círculo é uma Forma?", c instanceof Forma );
    console.log( "Círculo é um Retângulo?", c instanceof Retangulo );
    console.log( "Círculo é um Círculo?", c instanceof Circulo );
    
    console.log( "Genérico é um Objeto?", o instanceof Object );
    console.log( "Genérico é uma Forma?", o instanceof Forma );
    console.log( "Genérico é um Retângulo?", o instanceof Retangulo );
    console.log( "Genérico é um Círculo?", o instanceof Circulo );
    
    // uma string com um objeto codificado como
    // JavaScript Object Notation (JSON)
    let json = '{ "nome": "Maria", "peso": 52.5 }';
    
    // converte json para objeto
    let jsonO = JSON.parse( json );
    
    // converte objeto para json (string)
    let jsonD = JSON.stringify( o );
    
    // prettyprint
    let jsonDId = JSON.stringify( o, null, 4 );
    
    console.log( r );
    console.log( r.calcularArea() );
    
    r.xIni = 5;
    console.log( r );
    console.log( r.calcularArea() );
    
    console.log( c );
    console.log( c.calcularArea() );
    
    c.raio = 5;
    console.log( c );
    console.log( c.calcularArea() );
    
    console.log( o );
    console.log( o.nome );
    console.log( o[ "sobrenome" ] );
    
    // exibindo cada propriedade
    for ( var p in o ) {
        console.log( `o.${p} = ${o[p]}`  );
    }
    
    // usando a função recursiva
    processarObjeto( o, "o" );
    
    // mostrando as conversões json <-> objeto
    console.log( jsonO );
    processarObjeto( jsonO, "jsonO" );
    
    console.log( jsonD );
    console.log( jsonDId );
    
}

function processarObjeto( obj, nome ) {
    for ( var p in obj ) {
        if ( typeof obj[p] === 'object' && obj[p] !== null ) {
            processarObjeto( obj[p], p );
        } else {
            console.log( `${nome}.${p} = ${obj[p]}` );
        }
    }
}