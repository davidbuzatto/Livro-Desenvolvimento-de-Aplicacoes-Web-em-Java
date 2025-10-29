/**
 * Módulo para as funções do formulário de venda.
 */

// array para armazenar os itens da venda
let itensVenda = [];

// formatadores
let fmtMoeda = new Intl.NumberFormat( 
    "pt-BR", {
        style: "currency",
        currency: "BRL"
    }
);

let fmtNumero = new Intl.NumberFormat( 
    "pt-BR", {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    }
);

// componentes do formulário.
let formNovaVenda;
let hiddenItensVenda;
let txtQuantidade;
let selectProduto;
let btnInserir;
let btnRemover;
let btnLimpar;
let selectItensVenda;
let divTotal;
    
function iniciar() {
    
    formNovaVenda = document.getElementById( "formNovaVenda" );
    hiddenItensVenda = document.getElementById( "hiddenItensVenda" );
    selectProduto = document.getElementById( "selectProduto" );
    txtQuantidade = document.getElementById( "txtQuantidade" );
    btnInserir = document.getElementById( "btnInserir" );
    btnRemover = document.getElementById( "btnRemover" );
    btnLimpar = document.getElementById( "btnLimpar" );
    selectItensVenda = document.getElementById( "selectItensVenda" );
    divTotal = document.getElementById( "divTotal" );
    
    // ao clicar no botão inserir
    btnInserir.addEventListener( "click", event => {
        
        let idProduto = selectProduto.value;
        let option = selectProduto.selectedOptions[0];
        let valorVenda = +option.dataset.valor;
        let descricao = option.dataset.descricao;
        let quantidade = +txtQuantidade.value;
        
        if ( quantidade > 0 ) {
            
            // há um item da venda igual?
            let itemIgual = null;
            itensVenda.some( item => {
                if ( item.idProduto === idProduto ) {
                    itemIgual = item;
                    return true; // para a iteração
                }
            });
            
            // se há item igual, atualiza
            if ( itemIgual !== null ) {
                
                // soma a quantidade
                itemIgual.quantidade += quantidade;
                
                // caso contrário, cria um novo item
            } else {
                itensVenda.push({
                    idProduto: idProduto,
                    valorVenda: valorVenda,
                    descricao: descricao,
                    quantidade: quantidade
                });
            }
            
            txtQuantidade.value = "";
            atualizar();
            
        } else {
            alert( "Forneça uma quantidade maior que zero!" );
        }
        
    });
    
    // ao clicar no botão remover
    btnRemover.addEventListener( "click", event => {
        
        // retorna um array com os values de todos os itens
        // (option) selecionados
        let options = selectItensVenda.selectedOptions;
        
        // se não selecionou nenhum
        if ( options.length === 0 ) {
            alert( "Selecione um item da venda para remover!" );
            
            //se há seleção
        } else if ( confirm( "Deseja remover o(s) item(ns) da venda selecionado(s)?" ) ) {
            
            // itera pela seleção
            for ( let i = 0; i < options.length; i++ ) {
                
                // busca sequencial nos itens de venda
                for ( let j = 0; j < itensVenda.length; j++ ) {
                    
                    let item = itensVenda[j];
                    
                    // encontrou?
                    if ( options[i].value === item.idProduto ) {
                        
                        // remove da posição j
                        itensVenda.splice( j, 1 );
                        break;
                        
                    }
                    
                }
                
            }
            
            // remonta a lista
            atualizar();
            
        }
        
    });
    
    // ao clicar no botão limpar
    btnLimpar.addEventListener( "click", event => {
        if ( confirm( "Deseja remover todos os itens da venda?" ) ) {
            itensVenda = [];
            atualizar();
        }
    });
    
    // submissão da venda
    formNovaVenda.addEventListener( "submit", event => {
        if ( selectItensVenda.options.length === 0 ) {
            alert( "Uma venda precisa conter pelo menos um item!" );
            event.preventDefault();
        }
    });
    
    // evita que ao teclar enter dentro do campo
    // de texto o formulário seja submetido
    txtQuantidade.addEventListener( "keydown", event => {
        if ( event.key === "Enter" ) {
            event.preventDefault();
        }
    });
    
};

// constrói as opções do <select> (lista) de itens de venda;
// atualiza o valor total da venda;
// e prepara os dados para envio
function atualizar() {

    selectItensVenda.innerHTML = "";
    let total = 0;

    itensVenda.forEach( item => {

        let valorItem = +item.valorVenda * +item.quantidade;
        let opt = document.createElement( "option" );
        
        opt.textContent = `${item.descricao} - ` + 
                `${fmtMoeda.format( item.valorVenda )} x ` + 
                `${fmtNumero.format( item.quantidade )} = ` + 
                `${fmtMoeda.format( valorItem )}`;
                
        opt.value = item.idProduto;

        selectItensVenda.append( opt );
        total += valorItem;

    });
    
    divTotal.textContent = "Total: " + fmtMoeda.format( total );
    hiddenItensVenda.value = 
            // cria um novo array com os dados necessários
            // e converte para json
            JSON.stringify( itensVenda.map( item => {
                return { 
                    idProduto: item.idProduto, 
                    quantidade: item.quantidade
                };
            }));

};

iniciar();
