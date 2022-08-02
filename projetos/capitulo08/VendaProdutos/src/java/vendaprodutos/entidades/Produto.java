package vendaprodutos.entidades;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

/**
 * Entidade Produto.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Produto {

    @NotNull
    private Long id;
    
    @NotNull
    @Size( min = 1, max = 60 )
    private String descricao;
    
    @NotNull
    @Pattern( regexp = "^\\d{13}$",
              message = "deve corresponder à 9999999999999" )
    private String codigoBarras;
    
    @NotNull
    @PositiveOrZero
    private BigDecimal valorVenda;
    
    @NotNull
    private BigDecimal estoque;
    
    @NotNull
    private Fornecedor fornecedor;
    
    @NotNull
    private UnidadeMedida unidadeMedida;

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao( String descricao ) {
        this.descricao = descricao;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras( String codigoBarras ) {
        this.codigoBarras = codigoBarras;
    }

    public BigDecimal getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda( BigDecimal valorVenda ) {
        this.valorVenda = valorVenda;
    }

    public BigDecimal getEstoque() {
        return estoque;
    }

    public void setEstoque( BigDecimal estoque ) {
        this.estoque = estoque;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor( Fornecedor fornecedor ) {
        this.fornecedor = fornecedor;
    }

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida( UnidadeMedida unidadeMedida ) {
        this.unidadeMedida = unidadeMedida;
    }

}
