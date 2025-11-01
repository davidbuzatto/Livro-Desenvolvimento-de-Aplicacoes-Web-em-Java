// variáveis globais do módulo

const GRAVIDADE = 20;     // gravidade da simulação

let ctx;                  // contexto gráfico
let ultimoTempo = 0;      // tempo antes do próximo frame
let delta = 0;            // variação do tempo

let bolinhas = [];        // array de bolinhas criadas
let bolinhaSelecionada;   // a bolinha que está selecionada no momento
let largura;              // largura do canvas
let altura;               // altura do canvas

let isMouseDown = false;  // se o mouse ainda está pressionado
let mouseX;               // posição x do mouse
let mouseY;               // posição y do mouse

// classe da bolinha
class Bolinha {

    constructor( x, y, raio, vx, vy, cor ) {

        this.x = x;
        this.y = y;
        this.raio = raio;

        this.vx = vx;
        this.vy = vy;

        this.cor = cor;

        this.atrito = 0.99;
        this.elasticidade = 0.9;

        this.emArraste = false;
        
        // diferença em x e y para ajustar o "segurar" a bolinha
        this.diffX = 0;
        this.diffY = 0;
        
        // x e y anteriors para recálculo da velocidade
        this.prevX;
        this.prevY;

    }

    // atualiza o estado da bolinha. delta é a variação do tempo em segundos
    // entre um quadro e outro
    atualizar( delta ) {

        // se não está sendo arrastada
        if ( !this.emArraste ) {
            
            // atualiza a posição baseado na velocidade
            this.x += this.vx * delta;
            this.y += this.vy * delta;

            // verifica colisão com as laterais (horizontal)
            if ( this.x - this.raio < 0 ) {
                this.x = this.raio;
                this.vx = -this.vx * this.elasticidade;
            } else if ( this.x + this.raio > largura ) {
                this.x = largura - this.raio;
                this.vx = -this.vx * this.elasticidade;
            }

            // verifica colisão com as laterais (vertical)
            if ( this.y - this.raio < 0 ) {
                this.y = this.raio;
                this.vy = -this.vy * this.elasticidade;
            } else if ( this.y + this.raio > altura ) {
                this.y = altura - this.raio;
                this.vy = -this.vy * this.elasticidade;
            }

            // recalcula a velocidade
            this.vx = this.vx * this.atrito;
            this.vy = this.vy * this.atrito + GRAVIDADE;

        } else {
            
            // calcula a nova velocidade da bolinha enquanto a arrasta
            this.x = mouseX - this.diffX;
            this.y = mouseY - this.diffY;
            
            this.vx = ( this.x - this.prevX ) / delta;
            this.vy = ( this.y - this.prevY ) / delta;
            
            this.prevX = this.x;
            this.prevY = this.y;
            
        }

    }

    // desenha a bolinha, passando o contexto gráfico
    desenhar( ctx ) {
        ctx.save();  // salva o estado do contexto gráfico
        ctx.fillStyle = this.cor;
        ctx.beginPath();
        ctx.arc( this.x, this.y, this.raio, 0, 2 * Math.PI ); // desenho do círculo
        ctx.fill();
        ctx.restore();  // recupera o estado salvo do contexto gráfico
    }

    // retorna se a bolinha foi interceptada pela coordenada x e y
    intercepta( x, y ) {
        let c1 = this.x - x;
        let c2 = this.y - y;
        return c1 * c1 + c2 * c2 <= this.raio * this.raio;
    }

}

// inicia o módulo
function iniciar() {
    
    // obtém o canvas e o contexto gráfico 2D
    const canvas = document.getElementById( "canvasExemplo12" );
    ctx = canvas.getContext( "2d" );

    // obtém a largura e a altura do canvas
    largura = canvas.width;
    altura = canvas.height;
    
    // cria a primeira bolinha e insere no array
    bolinhas.push( new Bolinha(
        largura / 2,
        altura / 2,
        25,
        200,
        200,
        "#000000"
    ));

    // interação com o usuário
    
    // cancela o comportamento padrão do menu de contexto
    canvas.addEventListener( "contextmenu", event => {
        event.preventDefault();
    });

    // quando o mouse foi pressionado no canvas
    canvas.addEventListener( "mousedown", event => {
        
        event.preventDefault();

        // botão da esquerda
        if ( event.button === 0 ) {
            for ( let i = bolinhas.length - 1; i >= 0; i-- ) {
                const bolinha = bolinhas[i];
                if ( bolinha.intercepta( event.offsetX, event.offsetY ) ) {
                    bolinha.emArraste = true;
                    bolinha.diffX = event.offsetX - bolinha.x;
                    bolinha.diffY = event.offsetY - bolinha.y;
                    bolinhaSelecionada = bolinha;
                    break;
                }
            }
        } else if ( event.button === 1 ) {   // botão do meio
            for ( let i = 0; i < bolinhas.length; i++ ) {
                const bolinha = bolinhas[i];
                bolinha.vx = gerarVelocidadeAleatoria( -1000, 1000 );
                bolinha.vy = gerarVelocidadeAleatoria( -1000, 1000 );
            }
        } else if ( event.button === 2 ) {   // botão direito
            criarBolinha( event.offsetX, event.offsetY );
            isMouseDown = true;
        }

    });
    
    // quando o mouse foi solto no canvas
    canvas.addEventListener( "mouseup", event => {
        if ( event.button === 0 ) {
            if ( bolinhaSelecionada ) {
                bolinhaSelecionada.emArraste = false;
            }
        } else if ( event.button === 2 ) {
            isMouseDown = false;
        }
    });
    
    // quando o mouse se moveu dentro do canvas
    canvas.addEventListener( "mousemove", event => {
        mouseX = event.offsetX;
        mouseY = event.offsetY;
        if ( isMouseDown ) {
            criarBolinha( event.offsetX, event.offsetY );
        }
    });

    // quando o mouse saiu do canvas
    canvas.addEventListener( "mouseout", event => {
        if ( bolinhaSelecionada ) {
            bolinhaSelecionada.emArraste = false;
            bolinhaSelecionada = null;
        }
    });

    // inicia a animação
    requestAnimationFrame( executar );

}


function executar( tempoAtual ) {

    // os tempos estão em milisegundos
    // convertendo para segundos para usar na função
    delta = ( tempoAtual - ultimoTempo ) / 1000.0;
    ultimoTempo = tempoAtual;

    atualizar( delta );
    desenhar( ctx );

    // executa o próximo quadro
    requestAnimationFrame( executar );
    
}

// atualiza a simulação
function atualizar( delta ) {
    for ( let i = 0; i < bolinhas.length; i++ ) {
        bolinhas[i].atualizar( delta );
    }
}

// desenha a simulação
function desenhar( ctx ) {

    ctx.clearRect( 0, 0, largura, altura );
    ctx.fillStyle = "#000000";
    ctx.font = "12px monospace";
    ctx.fillText( `FPS: ${Math.round( 1.0 / delta )}`, 10, 22 );
    ctx.fillText( `Bolinhas: ${bolinhas.length}`, 10, 36 );

    for ( let i = 0; i < bolinhas.length; i++ ) {
        bolinhas[i].desenhar( ctx );
    }

}

// cria uma nova bolinha com cor aleatória
function criarBolinha( x, y ) {

    const r = Math.random() * 256;
    const g = Math.random() * 256;
    const b = Math.random() * 256;

    bolinhas.push( 
        new Bolinha(
            x,
            y,
            6 + Math.random() * 15,
            gerarVelocidadeAleatoria( -500, 500 ),
            gerarVelocidadeAleatoria( -500, 500 ),
            `rgb(${r},${g},${b})`
        )
    );
}


// gera uma velocidade aleatória
function gerarVelocidadeAleatoria( minima, maxima ) {
    return minima + Math.random() * Math.abs( maxima - minima );
}

// inicia o módulo
iniciar();
