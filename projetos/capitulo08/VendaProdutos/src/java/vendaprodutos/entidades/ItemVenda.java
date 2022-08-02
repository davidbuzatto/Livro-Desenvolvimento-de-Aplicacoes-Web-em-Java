package vendaprodutos.entidades;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

/**
 * Entidade ItemVenda.
 *
 * @author Prof. Dr. David Buzatto
 */
public class ItemVenda {

    @NotNull
    private Venda venda;
    
    @NotNull
    private Produto produto;
    
    @NotNull
    @PositiveOrZero
    private BigDecimal valor;
    
    @NotNull
    @Positive
    private BigDecimal quantidade;

    public Venda getVenda() {
        return venda;
    }

    public void setVenda( Venda venda ) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto( Produto produto ) {
        this.produto = produto;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor( BigDecimal valor ) {
        this.valor = valor;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade( BigDecimal quantidade ) {
        this.quantidade = quantidade;
    }

}
