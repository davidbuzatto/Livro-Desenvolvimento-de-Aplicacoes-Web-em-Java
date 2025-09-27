/**
 * Módulo principal da Single Page Application (SPA).
 * 
 * @author Prof. Dr. David Buzatto
 */
import * as Estados from "./modulos/estados.js";
import * as Cidades from "./modulos/cidades.js";
import * as Modais from "./modulos/modais.js";

function iniciar( urlBase ) {
    Modais.iniciar();
    Estados.iniciar( urlBase );
    Cidades.iniciar( urlBase );
}

iniciar( "/VendaProdutosSPA/api" );