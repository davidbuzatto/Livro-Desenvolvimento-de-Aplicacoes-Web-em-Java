package dsoc7.vendaprodutosspring.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entidade ItemVenda.
 *
 * @author Prof. Dr. David Buzatto
 */
@Entity
@Data
@AllArgsConstructor 
@NoArgsConstructor
@EqualsAndHashCode( onlyExplicitlyIncluded = true )
public class ItemVenda {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    
    @NotNull( message = "Venda é obrigatória" )
    @ManyToOne
    @JoinColumn( name = "venda_id" )
    @JsonProperty( access = Access.WRITE_ONLY )
    @ToString.Exclude
    @EqualsAndHashCode.Include
    private Venda venda;
    
    @NotNull( message = "Produto é obrigatório" )
    @ManyToOne
    @JoinColumn( name = "produto_id" )
    @JsonProperty( access = Access.WRITE_ONLY )
    @ToString.Exclude
    @EqualsAndHashCode.Include
    private Produto produto;
    
    @NotNull( message = "Valor é obrigatório" )
    @PositiveOrZero( message = "Valor deve ser maior ou igual a zero" )
    @Column( precision = 6, scale = 2 )
    private BigDecimal valor;
    
    @NotNull( message = "Quantidade é obrigatória" )
    @Positive( message = "Quantidade deve ser maior que zero")
    @Column( precision = 6, scale = 2 )
    private BigDecimal quantidade;

}
