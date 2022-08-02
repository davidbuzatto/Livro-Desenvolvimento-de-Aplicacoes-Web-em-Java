/**
 * Implementação das funções do formulário de venda.
 */

// Document ready (quando o documento estiver pronto)
$( () => {
    
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
    
    // ao clicar no botão inserir
    $( "#btnInserir" ).on( "click", event => {
        
        let $selectProduto = $( "#selectProduto" );
        let $txtQuantidade = $( "#txtQuantidade" );
        
        let idProduto = $selectProduto.val();
        let valorVenda = $selectProduto.find( ":selected" ).data( "valor" ).toString();
        let descricao = $selectProduto.find( ":selected" ).data( "descricao" );
        let quantidade = null;
        
        // se o valor da venda tem vírgula, troca por ponto
        if ( valorVenda.includes( "," ) ) {
            valorVenda = valorVenda.replace( ",", "." );
        }
        
        try {
            quantidade = new Decimal( $txtQuantidade.val() );
        } catch ( e ) {
        }
        
        if ( quantidade !== null && quantidade.greaterThan( 0 ) ) {
            
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
                itemIgual.quantidade = itemIgual
                        .quantidade
                        .plus( quantidade );
                
                // caso contrário, cria um novo item
            } else {
                itensVenda.push({
                    idProduto: idProduto,
                    valorVenda: valorVenda,
                    descricao: descricao,
                    quantidade: quantidade
                });
            }
            
            atualizarGUI();
            $txtQuantidade.val( "" );
            
        } else {
            alert( "Forneça uma quantidade maior que zero!" );
        }
        
    });
    
    // ao clicar no botão remover
    $( "#btnRemover" ).on( "click", event => {
        
        // retorna um array com os values de todos os itens
        // (option) selecionados
        let selecao = $( "#selectItensVenda" ).val();
        
        // se não selecionou nenhum
        if ( selecao.length === 0 ) {
            alert( "Selecione um item da venda para remover!" );
            
            //se há seleção
        } else if ( confirm( "Deseja remover o(s) item(ns) da venda selecionado(s)?" ) ) {
            
            // itera pela seleção
            for ( let i = 0; i < selecao.length; i++ ) {
                
                // busca sequencial nos itens de venda
                for ( let j = 0; j < itensVenda.length; j++ ) {
                    
                    let item = itensVenda[j];
                    
                    // encontrou?
                    if ( selecao[i] === item.idProduto ) {
                        
                        // remove da posição j
                        itensVenda.splice( j, 1 );
                        break;
                        
                    }
                    
                }
                
            }
            
            // remonta a lista
            atualizarGUI();
            
        }
        
    });
    
    // ao clicar no botão limpar
    $( "#btnLimpar" ).on( "click", event => {
        if ( confirm( "Deseja remover todos os itens da venda?" ) ) {
            itensVenda = [];
            atualizarGUI();
        }
    });
    
    // submissão da venda
    $( "#formNovaVenda" ).on( "submit", event => {
        
        if ( $( "#selectItensVenda > option" ).length === 0 ) {
            alert( "Uma venda precisa conter pelo menos um item!" );
            return false;
        }
        
        return true;
        
    });
    
    // evita que ao teclar enter dentro do campo
    // de texto o formulário seja submetido
    $( "#txtQuantidade" ).on( "keydown", event => {
        if ( event.keyCode === 13 ) {
            event.preventDefault();
        }
    });
    
    // constrói as opções do <select> (lista) de itens de venda;
    // atualiza o valor total da venda;
    // e prepara os dados para envio
    let atualizarGUI = () => {
        
        let $select = $( "#selectItensVenda" );
        let total = new Decimal( 0 );
        
        $select.html( "" );
        
        itensVenda.forEach( item => {
            
            let valorItem = new Decimal( item.valorVenda )
                                .times( item.quantidade );
            
            $opt = $( "<option></option>" ).
                    html( `${item.descricao} - ` + 
                    `${fmtMoeda.format( item.valorVenda )} x ` + 
                    `${fmtNumero.format( item.quantidade )} = ` + 
                    `${fmtMoeda.format( valorItem )}` ).
                    val( `${item.idProduto}` );
            
            $select.append( $opt );
            total = total.plus( valorItem );
            
        });
        
        $( "#divTotal" ).html( "Total: " + fmtMoeda.format( total ) );
        $( "#hiddenItensVenda" ).val( JSON.stringify( itensVenda ) );
        
    };
    
});
