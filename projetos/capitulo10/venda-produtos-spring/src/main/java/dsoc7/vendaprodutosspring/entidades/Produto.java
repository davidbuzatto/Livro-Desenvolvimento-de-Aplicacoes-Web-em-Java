package dsoc7.vendaprodutosspring.entidades;

import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entidade Produto.
 *
 * @author Prof. Dr. David Buzatto
 */
@Entity
@Data
@AllArgsConstructor 
@NoArgsConstructor
@EqualsAndHashCode( onlyExplicitlyIncluded = true )
public class Produto {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @EqualsAndHashCode.Include
    private Long id;
    
    @NotNull( message = "Descrição é obrigatória" )
    @Size( min = 1, max = 60, 
           message = "Descrição deve ter no mínimo {min} e " +
                     "no máximo {max} caracteres" )
    private String descricao;
    
    @NotNull( message = "Código de barras obrigatório" )
    @Pattern( regexp = "^\\d{13}$",
              message = "deve corresponder à 9999999999999" )
    @Size( min = 13, max = 13, 
           message = "Código de barras deve ter {min} caracteres" )
    @Column( unique = true )
    private String codigoBarras;
    
    @NotNull( message = "Valor de venda é obrigatório" )
    @PositiveOrZero( message = "Valor da venda deve ser maior ou igual a zero" )
    @Column( precision = 6, scale = 2 )
    private BigDecimal valorVenda;
    
    @NotNull( message = "Estoque é obrigatório" )
    @Column( precision = 6, scale = 2 )
    private BigDecimal estoque;
    
    @NotNull( message = "Fornecedor é obrigatório" )
    @ManyToOne
    private Fornecedor fornecedor;
    
    @NotNull( message = "Unidade de Medida é obrigatória" )
    @ManyToOne
    private UnidadeMedida unidadeMedida;
    
    @OneToMany( mappedBy = "produto", 
                cascade = CascadeType.ALL, 
                orphanRemoval = true )
    private Set<ItemVenda> itensDaVenda;

}
