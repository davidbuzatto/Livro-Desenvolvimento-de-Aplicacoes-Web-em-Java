/**
 * Módulo principal da Single Page Application (SPA).
 * 
 * @author Prof. Dr. David Buzatto
 */
import * as Cidades from "./modulos/cidades.js";
import * as Clientes from "./modulos/clientes.js";
import * as Estados from "./modulos/estados.js";
import * as Fornecedores from "./modulos/fornecedores.js";
import * as Modais from "./modulos/modais.js";
import * as Produtos from "./modulos/produtos.js";
import * as UnidadesMedida from "./modulos/unidadesMedida.js";
import * as Vendas from "./modulos/vendas.js";

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
        Vendas.iniciar( _urlBase );
    });
    
    document.getElementById( "itemMenuClientes" ).addEventListener( "click", event => {
        esconderTodosContaineres( "divClientes" );
        Clientes.iniciar( _urlBase );
    });
    
    document.getElementById( "itemMenuFornecedores" ).addEventListener( "click", event => {
        esconderTodosContaineres( "divFornecedores" );
        Fornecedores.iniciar( _urlBase );
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
    document.querySelectorAll( ".container-cadastro" ).forEach( container => {
        container.classList.add( "escondido" );
    });
    document.getElementById( idMostrar ).classList.remove( "escondido" );
}

iniciar( "http://localhost:8080/backend/api" );