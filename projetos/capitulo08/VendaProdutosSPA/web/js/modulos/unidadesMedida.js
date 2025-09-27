/**
 * Módulo de unidades de medida.
 * 
 * @author Prof. Dr. David Buzatto
 */
import { ContainerUtizadoError } from "../erros/ContainerUtizadoError.js";
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
let txtDescricao;
let txtSigla;

// validação
let divValDescricao;
let divValSigla;
let camposValidacao;

// botões
let btnNovo;
let btnSalvar;
let btnExcluir;

// referências para outros formulários
let selects = [];

export function iniciar( urlBase ) {
    
    if ( !inicializado ) {

        _urlBase = urlBase;
        
        Utils.carregarFragmento( "divUnidadesMedida", "fragmentos/cruds/unidadesMedida.html" ).then( () => {
            
            objSelecionado = null;
            dados = null;

            tbody = document.getElementById( "bodyTblUnidadeMedida" );
            form = document.getElementById( "formUnidadeMedida" );

            txtDescricao = document.getElementById( "txtDescricaoUnidadeMedida" );
            txtSigla = document.getElementById( "txtSiglaUnidadeMedida" );

            divValDescricao = document.getElementById( "divValDescricaoUnidadeMedida" );
            divValSigla = document.getElementById( "divValSiglaUnidadeMedida" );
            camposValidacao = [
                { campo: txtDescricao, div: divValDescricao },
                { campo: txtSigla, div: divValSigla }
            ];

            btnNovo = document.getElementById( "btnNovoUnidadeMedida" );
            btnSalvar = document.getElementById( "btnSalvarUnidadeMedida" );
            btnExcluir = document.getElementById( "btnExcluirUnidadeMedida" );

            btnSalvar.addEventListener( "click", salvar );
            btnExcluir.addEventListener( "click", excluir );
            btnNovo.addEventListener( "click", resetarFormulario );

            carregar();
            inicializado = true;
        
        }).catch( error => {
            if ( !( error instanceof ContainerUtizadoError ) ) {
                console.log( error );
            }   
        });

    } else {
        carregar( null );
    }

}

async function carregar() {
    
    const response = await Utils.customFetch( `${_urlBase}/unidadesMedida`, "GET" );
    dados = await response.json();

    if ( response.ok ) {
        
        tbody.innerHTML = "";
        resetarFormulario();

        selects.forEach( select => {
            select.innerHTML = "";
        });
        
        Modais.modalAguarde.abrir();
        
        dados.forEach( ( unidadeMedida, index ) =>{
            
            let linha = document.createElement( "tr" );

            linha.dataset.indice = index;
            linha.append( Utils.criarTd( unidadeMedida.descricao ) );
            linha.append( Utils.criarTd( unidadeMedida.sigla ) );

            linha.addEventListener( "click", ( event ) => {
                objSelecionado = dados[event.target.parentElement.dataset.indice];
                preencherFormulario();
                Utils.selecionaLinha( event.currentTarget, tbody );
            });

            tbody.append( linha );
            
            selects.forEach( select => {
                select.append( Utils.criarOption( unidadeMedida.id, unidadeMedida.descricao ) );
            });

        });
        
        Modais.modalAguarde.fechar();

    } else {
        Modais.modalMensagem.abrir( "ERRO", Utils.montarMensagemErro( dados ) );
    }

}

async function salvar() {

    let metodo;
    let obj = objSelecionado;
    let url = `${_urlBase}/unidadesMedida`;

    if ( Utils.validarFormulario( form, camposValidacao ) ) {

        if ( obj === null ) {
            metodo = "POST";
            obj = {
                descricao: txtDescricao.value,
                sigla: txtSigla.value
            };
        } else {
            metodo = "PUT";
            obj.descricao = txtDescricao.value;
            obj.sigla = txtSigla.value;
            url += `/${obj.id}`;
        }
        
        Modais.modalAguarde.abrir();
        
        const response = await Utils.customFetch( url, metodo, obj );
        const dados = await response.json();

        Modais.modalAguarde.fechar();

        if ( response.ok ) {
            resetarFormulario();
            carregar();
        } else {
            Modais.modalMensagem.abrir( "ERRO", Utils.montarMensagemErro( dados ) );
        }

    }

}

async function excluir() {

    if ( objSelecionado !== null ) {

        Modais.modalConfirmacao.abrir( 
            "Confirmação",
            "Deseja mesmo excluir a Unidade de Medida selecionada?",
            async () => {
                
                Modais.modalAguarde.abrir();
            
                const response = await Utils.customFetch( 
                    `${_urlBase}/unidadesMedida/${objSelecionado.id}`, 
                    "DELETE"
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

    } else {
        Modais.modalMensagem.abrir( "ERRO", "Selecione uma Unidade de Medida!" );
    }

}

function preencherFormulario() {
    Utils.limparFormulario( form, camposValidacao );
    txtDescricao.value = objSelecionado.descricao;
    txtSigla.value = objSelecionado.sigla;
}

function resetarFormulario() {
    Utils.limparFormulario( form, camposValidacao );
    objSelecionado = null;
    Utils.desselecionarLinhas( tbody );
}

export function adicionarSelectExterno( select ) {
    selects.push( select );
}
