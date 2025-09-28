/**
 * Módulo de fornecedores.
 * 
 * @author Prof. Dr. David Buzatto
 */
import { ContainerUtizadoError } from "../erros/ContainerUtizadoError.js";
import * as Cidades from "./cidades.js";
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
let txtRazaoSocial;
let txtCNPJ;
let txtEmail;
let txtLogradouro;
let txtNumero;
let txtBairro;
let txtCEP;
let selCidade;

// validação
let divValRazaoSocial;
let divValCNPJ;
let divValEmail;
let divValLogradouro;
let divValNumero;
let divValBairro;
let divValCEP;
let divValCidade;
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
        
        Utils.carregarFragmento( "divFornecedores", "fragmentos/cruds/fornecedores.html" ).then( () => {
            
            objSelecionado = null;
            dados = null;

            tbody = document.getElementById( "bodyTblFornecedor" );
            form = document.getElementById( "formFornecedor" );

            txtRazaoSocial = document.getElementById( "txtRazaoSocialFornecedor" );
            txtCNPJ = document.getElementById( "txtCNPJFornecedor" );
            txtEmail = document.getElementById( "txtEmailFornecedor" );
            txtLogradouro = document.getElementById( "txtLogradouroFornecedor" );
            txtNumero = document.getElementById( "txtNumeroFornecedor" );
            txtBairro = document.getElementById( "txtBairroFornecedor" );
            txtCEP = document.getElementById( "txtCEPFornecedor" );
            selCidade = document.getElementById( "selCidadeFornecedor" );

            divValRazaoSocial = document.getElementById( "divValRazaoSocialFornecedor" );
            divValCNPJ = document.getElementById( "divValCNPJFornecedor" );
            divValEmail = document.getElementById( "divValEmailFornecedor" );
            divValLogradouro = document.getElementById( "divValLogradouroFornecedor" );
            divValNumero = document.getElementById( "divValNumeroFornecedor" );
            divValBairro = document.getElementById( "divValBairroFornecedor" );
            divValCEP = document.getElementById( "divValCEPFornecedor" );
            divValCidade = document.getElementById( "divValCidadeFornecedor" );
            camposValidacao = [
                { campo: txtRazaoSocial, div: divValRazaoSocial },
                { campo: txtCNPJ, div: divValCNPJ, cnpj: { mensagem: "CNPJ inválido." } },
                { campo: txtEmail, div: divValEmail },
                { campo: txtLogradouro, div: divValLogradouro },
                { campo: txtNumero, div: divValNumero },
                { campo: txtBairro, div: divValBairro },
                { campo: txtCEP, div: divValCEP },
                { campo: selCidade, div: divValCidade }
            ];

            btnNovo = document.getElementById( "btnNovoFornecedor" );
            btnSalvar = document.getElementById( "btnSalvarFornecedor" );
            btnExcluir = document.getElementById( "btnExcluirFornecedor" );

            Cidades.adicionarSelectExterno( selCidade );

            btnSalvar.addEventListener( "click", salvar );
            btnExcluir.addEventListener( "click", excluir );
            btnNovo.addEventListener( "click", resetarFormulario );

            carregar();
            Utils.carregarSelect( `${_urlBase}/cidades`, selCidade, { id: "id", label: "nome" } );
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
    
    const response = await Utils.customFetch( `${_urlBase}/fornecedores`, "GET" );
    dados = await response.json();

    if ( response.ok ) {
        
        tbody.innerHTML = "";
        resetarFormulario();
        
        selects.forEach( select => {
            select.innerHTML = "";
        });
        
        dados.forEach( ( fornecedor, index ) =>{
            
            let linha = document.createElement( "tr" );

            linha.dataset.indice = index;
            linha.append( Utils.criarTd( fornecedor.razaoSocial ) );
            linha.append( Utils.criarTd( fornecedor.email ) );
            linha.append( Utils.criarTd( fornecedor.cnpj ) );
            linha.append( Utils.criarTd( fornecedor.cidade.nome ) );

            linha.addEventListener( "click", ( event ) => {
                objSelecionado = dados[event.target.parentElement.dataset.indice];
                preencherFormulario();
                Utils.selecionaLinha( event.currentTarget, tbody );
            });

            tbody.append( linha );
            
            selects.forEach( select => {
                select.append( Utils.criarOption( fornecedor.id, fornecedor.razaoSocial ) );
            });

        });

    } else {
        Modais.modalMensagem.abrir( "ERRO", Utils.montarMensagemErro( dados ) );
    }

}

async function salvar() {

    let metodo;
    let obj = objSelecionado;
    let url = `${_urlBase}/fornecedores`;

    if ( Utils.validarFormulario( form, camposValidacao ) ) {

        if ( obj === null ) {
            metodo = "POST";
            obj = {
                razaoSocial: txtRazaoSocial.value,
                cnpj: txtCNPJ.value,
                email: txtEmail.value,
                logradouro: txtLogradouro.value,
                numero: txtNumero.value,
                bairro: txtBairro.value,
                cep: txtCEP.value,
                cidade: {
                    id: selCidade.value
                }
            };
        } else {
            metodo = "PUT";
            obj.razaoSocial = txtRazaoSocial.value;
            obj.cnpj = txtCNPJ.value;
            obj.email = txtEmail.value;
            obj.logradouro = txtLogradouro.value;
            obj.numero = txtNumero.value;
            obj.bairro = txtBairro.value;
            obj.cep = txtCEP.value;
            obj.cidade.id = selCidade.value;
            url += `/${obj.id}`;
        }
        
        const response = await Utils.customFetch( url, metodo, obj );
        const dados = await response.json();

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
            "Deseja mesmo excluir o Fornecedor selecionado?",
            async () => {
                
                Modais.modalAguarde.abrir();
            
                const response = await Utils.customFetch( 
                    `${_urlBase}/fornecedores/${objSelecionado.id}`, 
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
        Modais.modalMensagem.abrir( "ERRO", "Selecione um Fornecedor!" );
    }

}

function preencherFormulario() {
    Utils.limparFormulario( form, camposValidacao );
    txtRazaoSocial.value = objSelecionado.razaoSocial;
    txtCNPJ.value = objSelecionado.cnpj;
    txtEmail.value = objSelecionado.email;
    txtLogradouro.value = objSelecionado.logradouro;
    txtNumero.value = objSelecionado.numero;
    txtBairro.value = objSelecionado.bairro;
    txtCEP.value = objSelecionado.cep;
    selCidade.value = objSelecionado.cidade.id;
}

function resetarFormulario() {
    Utils.limparFormulario( form, camposValidacao );
    objSelecionado = null;
    Utils.desselecionarLinhas( tbody );
}

export function adicionarSelectExterno( select ) {
    selects.push( select );
}
