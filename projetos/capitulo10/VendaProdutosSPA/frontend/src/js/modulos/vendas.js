/**
 * Módulo de vendas.
 * 
 * @author Prof. Dr. David Buzatto
 */
import { ContainerUtizadoError } from "../erros/ContainerUtizadoError.js";
import * as Clientes from "./clientes.js";
import * as Produtos from "./produtos.js";
import * as Modais from "./modais.js";
import * as Utils from "./utils.js";

// estado interno do módulo
let _urlBase;
let inicializado = false;

// dados
let dados;

// tabela
let tbody;

// formulário
let form;
let selCliente;
let selProduto;
let btnAtualizarProdutos;
let txtQuantidade;
let btnAdicionar;
let btnRemover;
let btnLimpar;
let selItens;
let spanTotal;

// botões
let btnNova;
let btnSalvar;
let btnCancelar;

// controle da GUI
let divListagem;
let divNova;

// os itens de venda da venda atual
let itensVenda = [];

export function iniciar( urlBase ) {
    
    if ( !inicializado ) {

        _urlBase = urlBase;
        
        Utils.carregarFragmento( "divVendas", "/public/fragmentos/cruds/vendas.html" ).then( () => {
            
            dados = null;
            
            tbody = document.getElementById( "bodyTblVenda" );
            form = document.getElementById( "formVenda" );

            divNova = document.getElementById( "divNovaVenda" );
            divListagem = document.getElementById( "divListagemVendas" );
            
            selCliente = document.getElementById( "selClienteVenda" );
            selProduto = document.getElementById( "selProdutoVenda" );
            btnAtualizarProdutos = document.getElementById( "btnAtualizarProdutosVenda" );
            txtQuantidade = document.getElementById( "txtQuantidadeVenda" );
            btnAdicionar = document.getElementById( "btnAdicionarItemVenda" );
            btnRemover = document.getElementById( "btnRemoverItemVenda" );
            btnLimpar = document.getElementById( "btnLimparItensVenda" );
            selItens = document.getElementById( "selItensVenda" );
            spanTotal = document.getElementById( "spanTotalVenda" );

            btnNova = document.getElementById( "btnNovaVenda" );
            btnSalvar = document.getElementById( "btnSalvarVenda" );
            btnCancelar = document.getElementById( "btnCancelarVenda" );

            btnAtualizarProdutos.addEventListener( "click", carregarProdutos );
            btnSalvar.addEventListener( "click", salvar );
            btnNova.addEventListener( "click", prepararNovaVenda );
            btnCancelar.addEventListener( "click", cancelarVenda );
            
            btnAdicionar.addEventListener( "click", adicionarItem );
            btnRemover.addEventListener( "click", removerItens );
            btnLimpar.addEventListener( "click", limparItens );

            Clientes.adicionarSelectExterno( selCliente );

            resetarFormulario();
            atualizarListaItens();
            
            Utils.carregarSelect( `${_urlBase}/clientes`, selCliente, { id: "id" }, cliente => {
                return `${cliente.nome} ${cliente.sobrenome}`;
            });
            
            carregarProdutos();
            
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
    
    const response = await Utils.customFetch( `${_urlBase}/vendas`, "GET" );
    dados = await response.json();

    if ( response.ok ) {
        
        tbody.innerHTML = "";
        
        dados.forEach( ( venda, index ) =>{
            
            let linha = document.createElement( "tr" );

            linha.dataset.indice = index;
            linha.append( Utils.criarTd( Utils.formatarDataBrasil( venda.data ) ) );
            linha.append( Utils.criarTd( `${venda.cliente.nome} ${venda.cliente.sobrenome}` ) );
            
            if ( venda.cancelada ) {
                let td = Utils.criarTd( "" );
                let span = document.createElement( "span" );
                span.classList.add( "badge" );
                span.style.backgroundColor = "#aaaaaa";
                span.textContent = "cancelada";
                td.append( span );
                linha.append( td );
            } else {
                linha.append( Utils.criarTdBoolean( 
                    venda,
                    "cancelada",
                    event => {
                        event.stopPropagation();
                        cancelarVendaRealizada( venda.id );
                    },
                    "cancelada",
                    "cancelar"
                ));
            }

            tbody.append( linha );

        });

    } else {
        Modais.modalMensagem.abrir( "ERRO", Utils.montarMensagemErro( dados ) );
    }

}

async function salvar() {

    if ( itensVenda.length === 0 ) {
        Modais.modalMensagem.abrir( "ERRO", "Uma Venda precisa conter pelo menos um Item da Venda!" );
    } else {

        let itens = [];
        itensVenda.forEach( item => {
            itens.push({
                idProduto: item.idProduto,
                quantidade: item.quantidade
            });
        });

        const obj = {
            idCliente: selCliente.value,
            itens: itens
        };
        
        const response = await Utils.customFetch( `${_urlBase}/vendas`, "POST", obj );
        const dados = await response.json();

        if ( response.ok ) {
            resetarFormulario();
            Modais.modalMensagem.abrir( "Aviso", "Venda realizada com sucesso!" );
        } else {
            Modais.modalMensagem.abrir( "ERRO", Utils.montarMensagemErro( dados ) );
        }

    }
    
}

function prepararNovaVenda() {
    Utils.esconder( divListagem );
    Utils.mostrar( divNova );
    resetarFormulario();
    atualizarListaItens();
}

function cancelarVenda() {
    
    if ( itensVenda.length === 0 ) {
        Utils.mostrar( divListagem );
        Utils.esconder( divNova );
        resetarFormulario();
        atualizarListaItens();
        carregar();
    } else {
        Modais.modalConfirmacao.abrir( 
            "Confirmação", 
            "Deseja mesmo cancelar a Venda atual?",
            () => {
                Utils.mostrar( divListagem );
                Utils.esconder( divNova );
                resetarFormulario();
                atualizarListaItens();
                carregar();
            }
        );
    }
    
}

function carregarProdutos() {
    
    Modais.modalAguarde.abrir();
    
    Utils.carregarSelect( `${_urlBase}/produtos`, selProduto, { id: "id" }, produto => {
        return `${produto.descricao} (${Utils.formatarDinheiro( produto.valorVenda )} por ${produto.unidadeMedida.sigla})`;
    }, [ "descricao", "valorVenda" ]);
    
    Modais.modalAguarde.fechar();
    
}

async function cancelarVendaRealizada( idVenda ) {
    
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
                carregar();
            } else {
                Modais.modalMensagem.abrir( "ERRO", Utils.montarMensagemErro( dados ) );
            }

        }
    );
    
}

function adicionarItem() {
    
    let quantidade = +txtQuantidade.value;
    
    if ( quantidade ) {
        
        let option = selProduto.options[selProduto.selectedIndex];
        
        let itemVenda = {
            idProduto: selProduto.value,
            descricaoProduto: option.dataset.descricao,
            valorVendaProduto: option.dataset.valorVenda,
            quantidade: quantidade
        };
        
        let achou = itensVenda.some( item => {
            if ( itemVenda.idProduto === item.idProduto ) {
                item.quantidade += itemVenda.quantidade;
                return true;
            }
            return false;
        });
        
        if ( !achou ) {
            itensVenda.push( itemVenda );
        }
        
        txtQuantidade.value = "";
        
    } else {
        Modais.modalMensagem.abrir( "ERRO", "Forneça uma quantidade maior que zero!" );
    }
    
    atualizarListaItens();
    
}

function removerItens() {
    
    let indices = [];
    
    for ( let i = 0; i < selItens.options.length; i++ ) {
        if ( selItens.options[i].selected ) {
            indices.push( i );
        }
    }
    
    if ( indices.length === 0 ) {
        Modais.modalMensagem.abrir( 
            "ERRO", 
            "Selecione pelo menos um Item da Venda para remover!" );
    } else {
        Modais.modalConfirmacao.abrir( 
            "Confirmação",
            "Deseja remover o(s) Item(ns) da Venda selecionado(s)?",
            () => {
                for ( let i = indices.length - 1; i >= 0; i-- ) {
                    itensVenda.splice( indices[i], 1 );
                }
                atualizarListaItens();
            }
        );
    }
    
}

function limparItens() {
    
    Modais.modalConfirmacao.abrir( 
        "Confirmação",
        "Deseja remover todos os Itens da Venda?",
        () => {
            itensVenda.length = 0;
            atualizarListaItens();
        }
    );
    
}

function resetarFormulario() {
    txtQuantidade.value = "";
    selCliente.selectedIndex = 0;
    selProduto.selectedIndex = 0;
    itensVenda.length = 0;
    atualizarListaItens();
}

function atualizarListaItens() {
    
    let total = 0;
    selItens.innerHTML = "";
    
    itensVenda.forEach( item => {
        
        let option = document.createElement( "option" );
        let desc = item.descricaoProduto;
        let valor = item.valorVendaProduto;
        let quant = item.quantidade;
        
        total += valor * quant;
        
        option.textContent = 
            `${desc} - ${Utils.formatarDinheiro(valor)} x ` +
            `${Utils.formatarNumeroBrasil(quant)} = ` + 
            `${Utils.formatarDinheiro(valor * quant)}`;
            
        selItens.append( option );
        
    });
    
    spanTotal.textContent = `Total: ${Utils.formatarDinheiro( total )}`;
    
}
