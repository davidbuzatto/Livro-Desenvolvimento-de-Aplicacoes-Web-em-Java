package vendaprodutos.entidades.dto;

/**
 * Representação de uma venda que vem do frontend.
 * 
 * @author Prof. Dr. David Buzatto
 */
public record VendaDTO( Long idCliente, ItemVendaDTO[] itens ) {
}
