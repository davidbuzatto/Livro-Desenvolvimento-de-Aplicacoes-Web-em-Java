package locacaomidias.entidades;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Entidade Tipo.
 *
 * @author Prof. Dr. David Buzatto
 */
public class ClassificacaoInterna {

    @NotNull
    private Long id;
    
    @NotNull
    @Size( min = 1, max = 45 )
    private String descricao;
    
    @NotNull
    @PositiveOrZero
    private BigDecimal valorAluguel;

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

    public BigDecimal getValorAluguel() {
        return valorAluguel;
    }

    public void setValorAluguel( BigDecimal valorAluguel ) {
        this.valorAluguel = valorAluguel;
    }

}
