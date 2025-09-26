/**
 * Módulo principal da Single Page Application (SPA).
 * 
 * @author Prof. Dr. David Buzatto
 */
import * as Estados from "./modulos/estados.js";
import * as Cidades from "./modulos/cidades.js";

function iniciar( urlBase ) {
    Estados.iniciar( urlBase );
    Cidades.iniciar( urlBase );
}

iniciar( "/VendaProdutos/api" );