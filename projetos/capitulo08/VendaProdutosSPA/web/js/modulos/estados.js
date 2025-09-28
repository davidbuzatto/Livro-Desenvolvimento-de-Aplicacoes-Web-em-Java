/**
 * Módulo de estados.
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
let txtNome;
let txtSigla;

// validação
let divValNome;
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
        
        Utils.carregarFragmento( "divEstados", "fragmentos/cruds/estados.html" ).then( () => {
            
            objSelecionado = null;
            dados = null;

            tbody = document.getElementById( "bodyTblEstado" );
            form = document.getElementById( "formEstado" );

            txtNome = document.getElementById( "txtNomeEstado" );
            txtSigla = document.getElementById( "txtSiglaEstado" );

            divValNome = document.getElementById( "divValNomeEstado" );
            divValSigla = document.getElementById( "divValSiglaEstado" );
            camposValidacao = [
                { campo: txtNome, div: divValNome },
                { campo: txtSigla, div: divValSigla }
            ];

            btnNovo = document.getElementById( "btnNovoEstado" );
            btnSalvar = document.getElementById( "btnSalvarEstado" );
            btnExcluir = document.getElementById( "btnExcluirEstado" );

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
        carregar();
    }

}

async function carregar() {
    
    const response = await Utils.customFetch( `${_urlBase}/estados`, "GET" );
    dados = await response.json();

    if ( response.ok ) {
        
        tbody.innerHTML = "";
        resetarFormulario();

        selects.forEach( select => {
            select.innerHTML = "";
        });
        
        Modais.modalAguarde.abrir();
        
        dados.forEach( ( estado, index ) =>{
            
            let linha = document.createElement( "tr" );

            linha.dataset.indice = index;
            linha.append( Utils.criarTd( estado.nome ) );
            linha.append( Utils.criarTd( estado.sigla ) );

            linha.addEventListener( "click", ( event ) => {
                objSelecionado = dados[event.target.parentElement.dataset.indice];
                preencherFormulario();
                Utils.selecionaLinha( event.currentTarget, tbody );
            });

            tbody.append( linha );
            
            selects.forEach( select => {
                select.append( Utils.criarOption( estado.id, estado.nome ) );
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
    let url = `${_urlBase}/estados`;

    if ( Utils.validarFormulario( form, camposValidacao ) ) {

        if ( obj === null ) {
            metodo = "POST";
            obj = {
                nome: txtNome.value,
                sigla: txtSigla.value
            };
        } else {
            metodo = "PUT";
            obj.nome = txtNome.value;
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
            "Deseja mesmo excluir o Estado selecionado?",
            async () => {
                
                Modais.modalAguarde.abrir();
            
                const response = await Utils.customFetch( 
                    `${_urlBase}/estados/${objSelecionado.id}`, 
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
        Modais.modalMensagem.abrir( "ERRO", "Selecione um Estado!" );
    }

}

function preencherFormulario() {
    Utils.limparFormulario( form, camposValidacao );
    txtNome.value = objSelecionado.nome;
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
