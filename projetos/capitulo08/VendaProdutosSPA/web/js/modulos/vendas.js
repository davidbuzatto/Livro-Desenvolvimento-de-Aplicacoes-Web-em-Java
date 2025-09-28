/**
 * Módulo de vendas.
 * 
 * @author Prof. Dr. David Buzatto
 */
import { ContainerUtizadoError } from "../erros/ContainerUtizadoError.js";
import * as Cliente from "./clientes.js";
import * as Produto from "./produtos.js";
import * as Modais from "./modais.js";
import * as Utils from "./utils.js";

// estado interno do módulo
let _urlBase;
let inicializado = false;

// dados
let objSelecionado;
let dados;

// tabela
let tbody;

// formulário
let form;
let txtNome;
let selEstado;

// validação
let divValNome;
let divValEstado;
let camposValidacao;

// botões
let btnNovo;
let btnSalvar;

export function iniciar( urlBase ) {
    
    if ( !inicializado ) {

        _urlBase = urlBase;
        
        Utils.carregarFragmento( "divVendas", "fragmentos/cruds/vendas.html" ).then( () => {
            
            objSelecionado = null;
            dados = null;
            
            tbody = document.getElementById( "bodyTblVenda" );
            form = document.getElementById( "formVenda" );

            btnNovo = document.getElementById( "btnNovoVenda" );
            btnSalvar = document.getElementById( "btnSalvarVenda" );

            btnSalvar.addEventListener( "click", salvar );
            btnNovo.addEventListener( "click", resetarFormulario );

            carregar();
        
        }).catch( error => {
            if ( !( error instanceof ContainerUtizadoError ) ) {
                console.log( error );
            }   
        });

    } else {
        carregar();
    }

}

async function carregar() {
    
    const response = await Utils.customFetch( `${_urlBase}/vendas`, "GET" );
    dados = await response.json();

    if ( response.ok ) {
        
        tbody.innerHTML = "";
        
        dados.forEach( ( venda, index ) =>{
            
            let linha = document.createElement( "tr" );

            linha.dataset.indice = index;
            linha.append( Utils.criarTd( Utils.formatarDataBrasil( venda.data ) ) );
            linha.append( Utils.criarTd( venda.cliente.nome ) );
            
            if ( venda.cancelada ) {
                let td = Utils.criarTd( "" );
                let span = document.createElement( "span" );
                span.classList.add( "badge" );
                span.style.backgroundColor = "#aaaaaa";
                span.innerHTML = "cancelada";
                td.append( span );
                linha.append( td );
            } else {
                linha.append( Utils.criarTdBoolean( 
                    venda,
                    "cancelada",
                    event => {
                        event.stopPropagation();
                        cancelarVenda( venda.id );
                    },
                    "cancelada",
                    "cancelar"
                ));
            }

            /*linha.addEventListener( "click", ( event ) => {
                objSelecionado = dados[event.target.parentElement.dataset.indice];
                preencherFormulario();
                Utils.selecionaLinha( event.currentTarget, tbody );
            });*/

            tbody.append( linha );

        });

    } else {
        Modais.modalMensagem.abrir( "ERRO", Utils.montarMensagemErro( dados ) );
    }

}

async function salvar() {

    let metodo;
    let obj = objSelecionado;
    let url = `${_urlBase}/vendas`;
    
}

function resetarFormulario() {
    Utils.limparFormulario( form, camposValidacao );
    objSelecionado = null;
    Utils.desselecionarLinhas( tbody );
}

async function cancelarVenda( idVenda ) {
    
    Modais.modalConfirmacao.abrir( 
        "Confirmação",
        "Deseja cancelar a Venda selecionada?",
        async () => {

            Modais.modalAguarde.abrir();
            
            const response = await Utils.customFetch( 
                `${_urlBase}/vendas/cancelar/${idVenda}`, 
                "PUT"
            );
            const dados = await response.json();

            Modais.modalAguarde.fechar();

            if ( response.ok ) {
                resetarFormulario();
                carregar();
            } else {
                Modais.modalMensagem.abrir( "ERRO", Utils.montarMensagemErro( dados ) );
            }

        }
    );
    
}
