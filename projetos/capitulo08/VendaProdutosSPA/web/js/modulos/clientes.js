/**
 * Módulo de clientes.
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
let txtNome;
let txtSobrenome;
let txtDataNascimento;
let txtCPF;
let txtEmail;
let txtLogradouro;
let txtNumero;
let txtBairro;
let txtCEP;
let selCidade;

// validação
let divValNome;
let divValSobrenome;
let divValDataNascimento;
let divValCPF;
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
        
        Utils.carregarFragmento( "divClientes", "fragmentos/cruds/clientes.html" ).then( () => {
            
            objSelecionado = null;
            dados = null;

            tbody = document.getElementById( "bodyTblCliente" );
            form = document.getElementById( "formCliente" );

            txtNome = document.getElementById( "txtNomeCliente" );
            txtSobrenome = document.getElementById( "txtSobrenomeCliente" );
            txtDataNascimento = document.getElementById( "txtDataNascimentoCliente" );
            txtCPF = document.getElementById( "txtCPFCliente" );
            txtEmail = document.getElementById( "txtEmailCliente" );
            txtLogradouro = document.getElementById( "txtLogradouroCliente" );
            txtNumero = document.getElementById( "txtNumeroCliente" );
            txtBairro = document.getElementById( "txtBairroCliente" );
            txtCEP = document.getElementById( "txtCEPCliente" );
            selCidade = document.getElementById( "selCidadeCliente" );

            divValNome = document.getElementById( "divValNomeCliente" );
            divValSobrenome = document.getElementById( "divValSobrenomeCliente" );
            divValDataNascimento = document.getElementById( "divValDataNascimentoCliente" );
            divValCPF = document.getElementById( "divValCPFCliente" );
            divValEmail = document.getElementById( "divValEmailCliente" );
            divValLogradouro = document.getElementById( "divValLogradouroCliente" );
            divValNumero = document.getElementById( "divValNumeroCliente" );
            divValBairro = document.getElementById( "divValBairroCliente" );
            divValCEP = document.getElementById( "divValCEPCliente" );
            divValCidade = document.getElementById( "divValCidadeCliente" );
            camposValidacao = [
                { campo: txtNome, div: divValNome },
                { campo: txtSobrenome, div: divValSobrenome },
                { campo: txtDataNascimento, div: divValDataNascimento },
                { campo: txtCPF, div: divValCPF, cpf: { mensagem: "CPF inválido." } },
                { campo: txtEmail, div: divValEmail },
                { campo: txtLogradouro, div: divValLogradouro },
                { campo: txtNumero, div: divValNumero },
                { campo: txtBairro, div: divValBairro },
                { campo: txtCEP, div: divValCEP },
                { campo: selCidade, div: divValCidade }
            ];

            btnNovo = document.getElementById( "btnNovoCliente" );
            btnSalvar = document.getElementById( "btnSalvarCliente" );
            btnExcluir = document.getElementById( "btnExcluirCliente" );

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
    
    const response = await Utils.customFetch( `${_urlBase}/clientes`, "GET" );
    dados = await response.json();

    if ( response.ok ) {
        
        tbody.innerHTML = "";
        resetarFormulario();
        
        selects.forEach( select => {
            select.innerHTML = "";
        });
        
        dados.forEach( ( cliente, index ) =>{
            
            let linha = document.createElement( "tr" );

            linha.dataset.indice = index;
            linha.append( Utils.criarTd( cliente.nome ) );
            linha.append( Utils.criarTd( cliente.sobrenome ) );
            linha.append( Utils.criarTd( cliente.email ) );
            linha.append( Utils.criarTd( cliente.cpf ) );
            linha.append( Utils.criarTd( cliente.cidade.nome ) );

            linha.addEventListener( "click", ( event ) => {
                objSelecionado = dados[event.target.parentElement.dataset.indice];
                preencherFormulario();
                Utils.selecionaLinha( event.currentTarget, tbody );
            });

            tbody.append( linha );
            
            selects.forEach( select => {
                select.append( Utils.criarOption( cliente.id, `${cliente.nome} ${cliente.sobrenome}` ) );
            });

        });

    } else {
        Modais.modalMensagem.abrir( "ERRO", Utils.montarMensagemErro( dados ) );
    }

}

async function salvar() {

    let metodo;
    let obj = objSelecionado;
    let url = `${_urlBase}/clientes`;

    if ( Utils.validarFormulario( form, camposValidacao ) ) {

        if ( obj === null ) {
            metodo = "POST";
            obj = {
                nome: txtNome.value,
                sobrenome: txtSobrenome.value,
                dataNascimento: txtDataNascimento.value,
                cpf: txtCPF.value,
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
            obj.nome = txtNome.value;
            obj.sobrenome = txtSobrenome.value;
            obj.dataNascimento = txtDataNascimento.value;
            obj.cpf = txtCPF.value;
            obj.email = txtEmail.value;
            obj.logradouro = txtLogradouro.value;
            obj.numero = txtNumero.value;
            obj.bairro = txtBairro.value;
            obj.cep = txtCEP.value;
            obj.cidade.id = selCidade.value;
            url += `/${obj.id}`;
            console.log( obj.dataNascimento );
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
            "Deseja mesmo excluir o Cliente selecionado?",
            async () => {
                
                Modais.modalAguarde.abrir();
            
                const response = await Utils.customFetch( 
                    `${_urlBase}/clientes/${objSelecionado.id}`, 
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
        Modais.modalMensagem.abrir( "ERRO", "Selecione um Cliente!" );
    }

}

function preencherFormulario() {
    Utils.limparFormulario( form, camposValidacao );
    txtNome.value = objSelecionado.nome;
    txtSobrenome.value = objSelecionado.sobrenome;
    txtDataNascimento.value = objSelecionado.dataNascimento;
    txtCPF.value = objSelecionado.cpf;
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
