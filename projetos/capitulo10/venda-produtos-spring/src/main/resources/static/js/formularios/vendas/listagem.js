function cancelarVenda( event ) {
          
    if ( !confirm( "Deseja mesmo cancelar essa venda?" ) ) {
        event.preventDefault();
    }

}