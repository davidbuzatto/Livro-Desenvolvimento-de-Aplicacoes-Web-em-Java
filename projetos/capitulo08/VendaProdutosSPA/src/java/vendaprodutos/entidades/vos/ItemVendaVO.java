package vendaprodutos.entidades.vos;

import java.math.BigDecimal;

/**
 * Representação de um item da venda que vem do frontend.
 * 
 * @author Prof. Dr. David Buzatto
 */
public record ItemVendaVO( Long idProduto, BigDecimal quantidade ) {
}
