/**
 * Módulo principal da Single Page Application (SPA).
 * 
 * @author Prof. Dr. David Buzatto
 */
import * as Estados from "./modulos/estados.js";
import * as Cidades from "./modulos/cidades.js";
import * as Modais from "./modulos/modais.js";

let _urlBase;

function iniciar( urlBase ) {
    _urlBase = urlBase;
    Modais.iniciar();
    prepararMenu();
    document.getElementById( "itemMenuHome" ).click();
}

function prepararMenu() {
    
    document.getElementById( "itemMenuHome" ).addEventListener( "click", event => {
        esconderTodosContaineres( "divHome" );
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
    });
    
    document.getElementById( "itemMenuCidades" ).addEventListener( "click", event => {
        esconderTodosContaineres( "divCidades" );
        Cidades.iniciar( _urlBase );
    });
    
    document.getElementById( "itemMenuEstados" ).addEventListener( "click", event => {
        esconderTodosContaineres( "divEstados" );
        Estados.iniciar( _urlBase );
    });
    
    document.getElementById( "itemMenuUnidadesDeMedida" ).addEventListener( "click", event => {
        esconderTodosContaineres( "divUnidadesDeMedida" );
    });

}

function esconderTodosContaineres( idMostrar ) {
    document.querySelectorAll( ".containerCadastro" ).forEach( container => {
        container.classList.add( "escondido" );
    });
    document.getElementById( idMostrar ).classList.remove( "escondido" );
}

iniciar( "/VendaProdutosSPA/api" );