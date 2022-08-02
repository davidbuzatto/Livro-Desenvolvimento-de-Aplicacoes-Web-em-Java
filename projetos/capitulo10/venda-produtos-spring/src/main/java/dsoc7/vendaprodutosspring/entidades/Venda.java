package dsoc7.vendaprodutosspring.entidades;

import java.time.LocalDate;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entidade Venda.
 *
 * @author Prof. Dr. David Buzatto
 */
@Entity
@Data
@AllArgsConstructor 
@NoArgsConstructor
@EqualsAndHashCode( onlyExplicitlyIncluded = true )
public class Venda {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @EqualsAndHashCode.Include
    private Long id;
    
    @NotNull( message = "Estado da venda é obrigatório" )
    private Boolean cancelada = Boolean.FALSE;
    
    @NotNull( message = "Data é obrigatória" )
    private LocalDate data;
    
    @NotNull( message = "Cliente é obrigatório" )
    @ManyToOne
    private Cliente cliente;
    
    @OneToMany( mappedBy = "venda", 
                cascade = CascadeType.ALL, 
                orphanRemoval = true )
    private Set<ItemVenda> itensDaVenda;

}
