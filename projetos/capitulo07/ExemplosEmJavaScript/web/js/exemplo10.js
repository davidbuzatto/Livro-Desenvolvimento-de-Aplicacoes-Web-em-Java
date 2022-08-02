function prepararCanvasExemplo10() {
    
    class Bolinha {
        
        // x e y são os centros da bolinha
        constructor( x, y, velocidadeX, velocidadeY, raio, cor ) {
            this.x = x;
            this.y = y;
            this.raio = raio;
            this.cor = cor;
            this.velocidadeX = velocidadeX;
            this.velocidadeY = velocidadeY;
            this.elasticidade = 0.9; // elasticidade do material
            this.atrito = 0.99;      // atrito do material com o meio
            this.emArraste = false;  // está sendo arrastada?
        }
        
        desenhar( ctx ) {
            
            // cor usada pelo contexto gráfico
            // para realizar o desenho
            ctx.fillStyle = this.cor;
            
            // inciando um caminho
            ctx.beginPath();
            
            // realizando um arco de uma volta (círculo)
            // no caminho iniciado
            ctx.arc( this.x, this.y, this.raio, 0, 2 * Math.PI );
            
            // fechando e preenchendo o caminho iniciado
            // com a cor definida acima
            ctx.fill();
            
        }
        
        // move a bolinha em cada passo da simulação
        mover() {
            
            // se a bolinha não estiver sendo arrastada pelo mouse
            if ( !this.emArraste ) {
                
                // recalcula a posição baseando-se
                // nas velocidades
                this.x += this.velocidadeX;
                this.y += this.velocidadeY;

                // se a bolinha passou da "parede" direita do <canvas>
                if ( this.x + this.raio > larguraCanvas ) {
                    
                    // reposiciona a bolinha sem passar da fronteira
                    this.x = larguraCanvas - this.raio;
                    
                    // recalcula a velocidade em x, trocando a direção
                    // e aplicando a elasticidade
                    this.velocidadeX = -this.velocidadeX * this.elasticidade;
                    
                }

                // se a bolinha passou da "parede" esquerda do <canvas>
                if ( this.x - this.raio < 0 ) {
                    
                    // reposiciona a bolinha sem passar da fronteira
                    this.x = this.raio;
                    
                    // recalcula a velocidade em x, trocando a direção
                    // e aplicando a elasticidade
                    this.velocidadeX = -this.velocidadeX * this.elasticidade;
                    
                }

                // se a bolinha passou do "chão" do <canvas>
                if ( this.y + this.raio > alturaCanvas ) {
                    
                    // reposiciona a bolinha sem passar da fronteira
                    this.y = alturaCanvas - this.raio;
                    
                    // recalcula a velocidade em y, trocando a direção
                    // e aplicando a elasticidade
                    this.velocidadeY = -this.velocidadeY * this.elasticidade;
                    
                }

                // se a bolinha passou do "teto" do <canvas>
                if ( this.y - this.raio < 0 ) {
                    
                    // reposiciona a bolinha sem passar da fronteira
                    this.y = this.raio;
                    
                    // recalcula a velocidade em y, trocando a direção
                    // e aplicando a elasticidade
                    this.velocidadeY = -this.velocidadeY * this.elasticidade;
                    
                }

                // aplica o atrito
                this.velocidadeX *= this.atrito;
                this.velocidadeY *= this.atrito;
                
                // aplica a gravidade
                this.velocidadeY += gravidade;
            
            }
            
        }
        
        // a coordenada x, y está dentro da bolinha
        intercepta( x, y ) {
            
            // pitágoras ;)
            return Math.hypot( this.x - x, this.y - y ) <= this.raio;
            
        }
        
        // cria uma nova velocidade aleatória para a bolinha
        gerarNovaVelocidade() {
            this.velocidadeX = gerarValorAleatorio( -30, 30 );
            this.velocidadeY = gerarValorAleatorio( -30, 30 );
        }
        
    }
    
    // obtém o canvas que será usado
    let canvas = document.getElementById( "canvasExemplo10" );
    
    // a partir do canvas obtido, requisita um contexto
    // gráfico 2D
    let context = canvas.getContext( "2d" );
    
    // variáveis para largura e altura do <canvas>
    // para facilitar o entendimento
    let larguraCanvas = canvas.width;
    let alturaCanvas = canvas.height;
    
    // diferenças em x e y no clique para ajuste
    // de posição durante o arraste
    let dx;
    let dy;
    
    // posições antigas em x e y para recálculo das
    // velocidades durante o arraste
    let xAntigo;
    let yAntigo;
    
    // gravidade
    let gravidade = 1;
    
    // cria uma bolinha
    let bolinha = new Bolinha( 
            larguraCanvas / 2,
            alturaCanvas / 2,
            2.0,
            2.0,
            10, 
            "rgb(0,0,0)" );
    
    // qual bolinha está sendo arrastada?
    let bolinhaEmArraste = null;
    
    // cria um array de bolinhas
    let bolinhas = [ bolinha ];
    
    // "engine/motor" da simulação
    // a função passada será executada a cada
    // 10 milisegundos, ou seja cada segundo terá 
    // aproximadamente 100 quadros de animação
    setInterval( () => {
        
        // limpa os desenhos anteriores
        context.clearRect( 0, 0, larguraCanvas, alturaCanvas );
        
        // realiza a movimentação e o desenho de cada
        // bolinha
        bolinhas.forEach( bolinha => {
            bolinha.mover();
            bolinha.desenhar( context );
        });
        
    }, 10 );
    
    
    /*******************************
     *   interação com o usuário   *
     *******************************/
    
    // quando algum botão do mouse for pressionado no <canvas>
    canvas.onmousedown = event => {
        
        // se for o botão esquerdo
        if ( event.button === 0 ) {
        
            // verifica se a posição que foi clicada
            // está em cima de alguma bolinha
            // percorre-se o array ao contrário para dar
            // prioridade às bolinhas que estão em cima
            // das outras
            for ( let i = bolinhas.length - 1; i >= 0; i-- ) {
                
                // uma das bolinhas
                let bolinha = bolinhas[i];
                
                // interceptou?
                if ( bolinha.intercepta( event.offsetX, event.offsetY ) ) {
                    
                    // a bolinha atual está em arraste então
                    bolinha.emArraste = true;
                    
                    // obtém a diferença da posição do clique
                    // com o centro da bolinha
                    // isso é importante para que se dê a ideia
                    // que a bolinha ficou "colada" no cursor
                    // na posição correta
                    dx = event.offsetX - bolinha.x;
                    dy = event.offsetY - bolinha.y;
                    
                    // sabemos agora qual bolinha está em arraste
                    bolinhaEmArraste = bolinha;
                    
                    break;
                    
                }
                
            }

            // não há bolinha sendo arrasta
            if ( bolinhaEmArraste === null ) {

                // criamos uma nova bolinha
                let novaBolinha = new Bolinha( 
                        event.offsetX,
                        event.offsetY,
                        gerarValorAleatorio( -3, 3 ),
                        2.0,
                        5 + Math.random() * 10, 
                        gerarCorAleatoria() );

                // inserimos no array
                bolinhas.push( novaBolinha );

            }
            
            // outro botão que não o esquerdo
        } else {
            
            // percorre o array e gera novas velocidades
            // para todas as bolinhas, dando a impressão
            // "chacoalhar" o <canvas>
            bolinhas.forEach( bolinha => {
                bolinha.gerarNovaVelocidade();
            });
            
        }
        
    };
    
    // o botão clicado no <canvas> foi solto
    canvas.onmouseup = event => {
        
        // há bolinha em arraste?
        if ( bolinhaEmArraste !== null ) {
            
            // não está mais sendo arrastada!
            bolinhaEmArraste.emArraste = false;
            bolinhaEmArraste = null;
            
        }
        
    };
    
    // o cursor do mouse saiu do <canvas>
    canvas.onmouseout = event => {
        
        // se houver bolinha em arraste, solte-a
        if ( bolinhaEmArraste !== null ) {
            bolinhaEmArraste.emArraste = false;
            bolinhaEmArraste = null;
        }
        
    };
    
    // o cursor do mouse moveu dentro do canvas
    canvas.onmousemove = event => {
        
        // há bolinha em arraste?
        if ( bolinhaEmArraste !== null ) {
            
            // obtém as coordenadas anteriores
            xAntigo = bolinhaEmArraste.x;
            yAntigo = bolinhaEmArraste.y;
            
            // reposiciona a bolinha de acordo com
            // a difença calculada no clique/seleção
            bolinhaEmArraste.x = event.offsetX - dx;
            bolinhaEmArraste.y = event.offsetY - dy;
            
            // recalcula as velocidades para
            // quando essa bolinha for solta
            // dando a impressão de arremesso
            bolinhaEmArraste.velocidadeX = ( bolinhaEmArraste.x - xAntigo ) / 2;
            bolinhaEmArraste.velocidadeY = ( bolinhaEmArraste.y - yAntigo ) / 2;
            
        }
        
    };
    
    // esconde o menu de contexto no clique
    // com o botão direito
    canvas.oncontextmenu = event => {
        // não realiza a ação padrão
        // nesse caso, não exibir o menu de contexto
        event.preventDefault();
    };
    
}

// cria uma cor aleatória 
function gerarCorAleatoria() {
    
    // um valor inteiro no intervalo [0, 255]
    // para cada componente da tríade luminosa 
    // vermelho (r), verde (g) a azul (b)
    let r = Math.trunc( Math.random() * 256 );
    let g = Math.trunc( Math.random() * 256 );
    let b = Math.trunc( Math.random() * 256 );
    
    // retorna uma string que representa tal cor
    return `rgb(${r},${g},${b})`;
    
}

// gera um valor aleatório no intervalo [minimo, maximo]
function gerarValorAleatorio( minimo, maximo ) {
    return minimo + Math.random() * ( maximo - minimo );
}