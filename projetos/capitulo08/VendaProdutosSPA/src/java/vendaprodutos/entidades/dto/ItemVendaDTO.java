package vendaprodutos.entidades.dto;

import java.math.BigDecimal;

/**
 * Representação de um item da venda que vem do frontend.
 * 
 * @author Prof. Dr. David Buzatto
 */
public record ItemVendaDTO( Long idProduto, BigDecimal quantidade ) {
}
