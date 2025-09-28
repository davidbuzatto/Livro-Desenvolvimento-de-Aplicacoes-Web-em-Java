/**
 * Módulo de produtos.
 * 
 * @author Prof. Dr. David Buzatto
 */
import { ContainerUtizadoError } from "../erros/ContainerUtizadoError.js";
import * as Fornecedores from "./fornecedores.js";
import * as Modais from "./modais.js";
import * as UnidadesMedida from "./unidadesMedida.js";
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
let txtCodigoBarras;
let txtValorVenda;
let txtEstoque;
let selFornecedor;
let selUnidadeMedida;

// validação
let divValDescricao;
let divValCodigoBarras;
let divValValorVenda;
let divValEstoque;
let divValFornecedor;
let divValUnidadeMedida;
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
        
        Utils.carregarFragmento( "divProdutos", "fragmentos/cruds/produtos.html" ).then( () => {
            
            objSelecionado = null;
            dados = null;

            tbody = document.getElementById( "bodyTblProduto" );
            form = document.getElementById( "formProduto" );

            txtDescricao = document.getElementById( "txtDescricaoProduto" );
            txtCodigoBarras = document.getElementById( "txtCodigoBarrasProduto" );
            txtValorVenda = document.getElementById( "txtValorVendaProduto" );
            txtEstoque = document.getElementById( "txtEstoqueProduto" );
            selFornecedor = document.getElementById( "selFornecedorProduto" );
            selUnidadeMedida = document.getElementById( "selUnidadeMedidaProduto" );

            divValDescricao = document.getElementById( "divValDescricaoProduto" );
            divValCodigoBarras = document.getElementById( "divValCodigoBarrasProduto" );
            divValValorVenda = document.getElementById( "divValValorVendaProduto" );
            divValEstoque = document.getElementById( "divValEstoqueProduto" );
            divValFornecedor = document.getElementById( "divValFornecedorProduto" );
            divValUnidadeMedida = document.getElementById( "divValUnidadeMedidaProduto" );
            camposValidacao = [
                { campo: txtDescricao, div: divValDescricao },
                { campo: txtCodigoBarras, div: divValCodigoBarras },
                { campo: txtValorVenda, div: divValValorVenda },
                { campo: txtEstoque, div: divValEstoque },
                { campo: selFornecedor, div: divValFornecedor },
                { campo: selUnidadeMedida, div: divValUnidadeMedida }
            ];

            btnNovo = document.getElementById( "btnNovoProduto" );
            btnSalvar = document.getElementById( "btnSalvarProduto" );
            btnExcluir = document.getElementById( "btnExcluirProduto" );

            Fornecedores.adicionarSelectExterno( selFornecedor );
            UnidadesMedida.adicionarSelectExterno( selUnidadeMedida );

            btnSalvar.addEventListener( "click", salvar );
            btnExcluir.addEventListener( "click", excluir );
            btnNovo.addEventListener( "click", resetarFormulario );

            carregar();
            Utils.carregarSelect( `${_urlBase}/fornecedores`, selFornecedor, { id: "id", label: "razaoSocial" } );
            Utils.carregarSelect( `${_urlBase}/unidadesMedida`, selUnidadeMedida, { id: "id", label: "sigla" } );
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
    
    const response = await Utils.customFetch( `${_urlBase}/produtos`, "GET" );
    dados = await response.json();

    if ( response.ok ) {
        
        tbody.innerHTML = "";
        resetarFormulario();

        selects.forEach( item => {
            item.select.innerHTML = "";
        });
        
        dados.forEach( ( produto, index ) =>{
            
            let linha = document.createElement( "tr" );

            linha.dataset.indice = index;
            linha.append( Utils.criarTd( produto.descricao ) );
            linha.append( Utils.criarTd( Utils.formatarDinheiro( produto.valorVenda ) ) );
            linha.append( Utils.criarTd( Utils.formatarNumeroBrasil( produto.estoque ) ) );
            linha.append( Utils.criarTd( produto.fornecedor.razaoSocial ) );
            linha.append( Utils.criarTd( produto.unidadeMedida.sigla ) );

            linha.addEventListener( "click", ( event ) => {
                objSelecionado = dados[event.target.parentElement.dataset.indice];
                preencherFormulario();
                Utils.selecionaLinha( event.currentTarget, tbody );
            });

            tbody.append( linha );
            
            selects.forEach( item => {
                let select = item.select;
                let formatador = item.formatador;
                let label = formatador ? formatador( produto ) : produto.descricao;
                select.append( Utils.criarOption( produto.id, label ) );
            });

        });

    } else {
        Modais.modalMensagem.abrir( "ERRO", Utils.montarMensagemErro( dados ) );
    }

}

async function salvar() {

    let metodo;
    let obj = objSelecionado;
    let url = `${_urlBase}/produtos`;

    if ( Utils.validarFormulario( form, camposValidacao ) ) {

        if ( obj === null ) {
            metodo = "POST";
            obj = {
                descricao: txtDescricao.value,
                codigoBarras: txtCodigoBarras.value,
                valorVenda: txtValorVenda.value,
                estoque: txtEstoque.value,
                fornecedor: {
                    id: selFornecedor.value
                },
                unidadeMedida: {
                    id: selUnidadeMedida.value
                }
            };
        } else {
            metodo = "PUT";
            obj.descricao = txtDescricao.value;
            obj.codigoBarras = txtCodigoBarras.value;
            obj.valorVenda = txtValorVenda.value;
            obj.estoque = txtEstoque.value;
            obj.fornecedor.id = selFornecedor.value;
            obj.unidadeMedida.id = selUnidadeMedida.value;
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
            "Deseja mesmo excluir o Produto selecionado?",
            async () => {
                
                Modais.modalAguarde.abrir();
            
                const response = await Utils.customFetch( 
                    `${_urlBase}/produtos/${objSelecionado.id}`, 
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
        Modais.modalMensagem.abrir( "ERRO", "Selecione um Produto!" );
    }

}

function preencherFormulario() {
    Utils.limparFormulario( form, camposValidacao );
    txtDescricao.value = objSelecionado.descricao;
    txtCodigoBarras.value = objSelecionado.codigoBarras;
    txtValorVenda.value = objSelecionado.valorVenda;
    txtEstoque.value = objSelecionado.estoque;
    selFornecedor.value = objSelecionado.fornecedor.id;
    selUnidadeMedida.value = objSelecionado.unidadeMedida.id;
}

function resetarFormulario() {
    Utils.limparFormulario( form, camposValidacao );
    objSelecionado = null;
    Utils.desselecionarLinhas( tbody );
}

export function adicionarSelectExterno( select, formatador = null ) {
    selects.push( { select: select, formatador: formatador } );
}

