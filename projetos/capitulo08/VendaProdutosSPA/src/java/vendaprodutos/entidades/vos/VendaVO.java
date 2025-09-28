package vendaprodutos.entidades.vos;

/**
 * Representação de uma venda que vem do frontend.
 * 
 * @author Prof. Dr. David Buzatto
 */
public record VendaVO( Long idCliente, ItemVendaVO[] itens ) {
}
