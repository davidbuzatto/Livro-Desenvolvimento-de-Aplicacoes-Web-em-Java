/**
 * Módulo de cidades.
 * 
 * @author Prof. Dr. David Buzatto
 */

import * as Estados from "./estados.js";
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
let divErros;

// botões
let btnNovo;
let btnSalvar;
let btnExcluir;

export function iniciar( urlBase ) {
    
    if ( !inicializado ) {

        _urlBase = urlBase;
        objSelecionado = null;
        dados = null;

        tbody = document.getElementById( "bodyTblCidade" );
        form = document.getElementById( "formCidade" );

        txtNome = document.getElementById( "txtNomeCidade" );
        selEstado = document.getElementById( "selEstadoCidade" );

        divValNome = document.getElementById( "divValNomeCidade" );
        divValEstado = document.getElementById( "divValEstadoCidade" );
        camposValidacao = [
            { campo: txtNome, div: divValNome },
            { campo: selEstado, div: divValEstado }
        ];
        
        divErros = document.getElementById( "divErros" );

        btnNovo = document.getElementById( "btnNovoCidade" );
        btnSalvar = document.getElementById( "btnSalvarCidade" );
        btnExcluir = document.getElementById( "btnExcluirCidade" );
        
        Estados.adicionarSelectExterno( selEstado );

        btnSalvar.addEventListener( "click", salvar );
        btnExcluir.addEventListener( "click", excluir );
        btnNovo.addEventListener( "click", resetarFormulario );

        carregar();
        Utils.carregarSelect( `${_urlBase}/estados`, selEstado, { id: "id", label: "nome" } );
        inicializado = true;

    } else {
        carregar( null );
    }

}

async function carregar() {
    
    const response = await Utils.customFetch( `${_urlBase}/cidades`, "GET" );
    dados = await response.json();

    if ( response.ok ) {
        
        tbody.innerHTML = "";
        resetarFormulario();

        dados.forEach( ( cidade, index ) =>{
            
            let linha = document.createElement( "tr" );

            linha.dataset.indice = index;
            linha.append( Utils.criarTd( cidade.nome ) );
            linha.append( Utils.criarTd( cidade.estado.sigla ) );

            linha.addEventListener( "click", ( event ) => {
                objSelecionado = dados[event.target.parentElement.dataset.indice];
                preencherFormulario();
                Utils.selecionaLinha( event.currentTarget, tbody );
            });

            tbody.append( linha );

        });

    } else {
        Utils.configurarMensagemErro( divErros, Utils.montarMensagemErro( dados ) );
    }

}

async function salvar() {

    let metodo;
    let obj = objSelecionado;
    let url = `${_urlBase}/cidades`;

    if ( Utils.validarFormulario( form, camposValidacao ) ) {

        if ( obj === null ) {
            metodo = "POST";
            obj = {
                nome: txtNome.value,
                estado: {
                    id: selEstado.value
                }
            };
        } else {
            metodo = "PUT";
            obj.nome = txtNome.value;
            obj.estado = {
                id: selEstado.value
            };
            url += `/${obj.id}`;
        }
        
        const response = await Utils.customFetch( url, metodo, obj );
        const dados = await response.json();

        if ( response.ok ) {
            resetarFormulario();
            carregar();
        } else {
            Utils.configurarMensagemErro( divErros, Utils.montarMensagemErro( dados ) );
        }

    }

}

async function excluir() {

    if ( objSelecionado !== null ) {

        if ( confirm( "Deseja mesmo excluir a Cidade selecionada?" ) ) {
            
            const response = await Utils.customFetch( 
                `${_urlBase}/cidades/${objSelecionado.id}`, 
                "DELETE"
            );

            const dados = await response.json();

            if ( response.ok ) {
                resetarFormulario();
                carregar();
            } else {
                Utils.configurarMensagemErro( divErros, Utils.montarMensagemErro( dados ) );
            }
            
        }

    } else {
        Utils.configurarMensagemErro( divErros, "Selecione uma Cidade!" );
    }

}

function preencherFormulario() {
    Utils.limparFormulario( form, camposValidacao );
    txtNome.value = objSelecionado.nome;
    selEstado.value = objSelecionado.estado.id;
}

function resetarFormulario() {
    Utils.limparFormulario( form, camposValidacao );
    objSelecionado = null;
    Utils.desselecionarLinhas( tbody );
    Utils.limparMensagemErro( divErros );
}
