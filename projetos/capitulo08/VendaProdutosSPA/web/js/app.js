/**
 * Módulo principal da Single Page Application (SPA).
 * 
 * @author Prof. Dr. David Buzatto
 */
import * as Cidades from "./modulos/cidades.js";
import * as Estados from "./modulos/estados.js";
import * as Modais from "./modulos/modais.js";
import * as Produtos from "./modulos/produtos.js";
import * as UnidadesMedida from "./modulos/unidadesMedida.js";

let _urlBase;

// controle da animação
let animationId = null;
let ultimoTempo = 0;

function iniciar( urlBase ) {
    _urlBase = urlBase;
    Modais.iniciar();
    prepararMenu();
    document.getElementById( "itemMenuProdutos" ).click();
}

function prepararMenu() {
    
    document.getElementById( "itemMenuHome" ).addEventListener( "click", event => {
        esconderTodosContaineres( "divHome" );
        prepararAnimacao();
    });
    
    document.getElementById( "itemMenuVendas" ).addEventListener( "click", event => {
        esconderTodosContaineres( "divVendas" );
    });
    
    document.getElementById( "itemMenuClientes" ).addEventListener( "click", event => {
        esconderTodosContaineres( "divClientes" );
    });
    
    document.getElementById( "itemMenuFornecedores" ).addEventListener( "click", event => {
        esconderTodosContaineres( "divFornecedores" );
    });
    
    document.getElementById( "itemMenuProdutos" ).addEventListener( "click", event => {
        esconderTodosContaineres( "divProdutos" );
        Produtos.iniciar( _urlBase );
    });
    
    document.getElementById( "itemMenuCidades" ).addEventListener( "click", event => {
        esconderTodosContaineres( "divCidades" );
        Cidades.iniciar( _urlBase );
    });
    
    document.getElementById( "itemMenuEstados" ).addEventListener( "click", event => {
        esconderTodosContaineres( "divEstados" );
        Estados.iniciar( _urlBase );
    });
    
    document.getElementById( "itemMenuUnidadesMedida" ).addEventListener( "click", event => {
        esconderTodosContaineres( "divUnidadesMedida" );
        UnidadesMedida.iniciar( _urlBase );
    });

}

function esconderTodosContaineres( idMostrar ) {
    document.querySelectorAll( ".containerCadastro" ).forEach( container => {
        container.classList.add( "escondido" );
    });
    document.getElementById( idMostrar ).classList.remove( "escondido" );
}

function prepararAnimacao() {
    
    const div = document.getElementById( "divAnimada" );
    const logoRect = document.getElementById( "logoHome" ).getBoundingClientRect();
    
    let xCentro = logoRect.x + logoRect.width / 2 + 20 + window.pageXOffset;
    let yCentro = logoRect.y + logoRect.height / 4 + 30 + window.pageYOffset;
    let raio = logoRect.width / 2;
    let tempo = 0;
    let velocidade = 0.001;   // 0.05 grau por milisegundo
    let largura = div.offsetWidth;
    let altura = div.offsetHeight;
    
    if ( animationId ) {
        cancelAnimationFrame( animationId );
    }
    
    function animar( tempoAtual ) {
        
        if ( ultimoTempo === 0 ) {
            ultimoTempo = tempoAtual;
        }
        
        const delta = tempoAtual - ultimoTempo;
        ultimoTempo = tempoAtual;
        tempo += velocidade * delta;
        
        const angulo = Math.sin( tempo ) * 90 - 90;
        
        const aRad = angulo * Math.PI / 180;
        const x = xCentro + Math.cos( aRad ) * raio;
        const y = yCentro + Math.sin( aRad ) * raio;
        
        div.style.left = `${x - largura / 2}px`;
        div.style.top = `${y - altura / 2}px`;
        
        animationId = requestAnimationFrame( animar );
        
    };
    
    animationId = requestAnimationFrame( animar );
    
}

iniciar( "/VendaProdutosSPA/api" );