package dsoc7.vendaprodutosspring.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entidade Estado.
 *
 * @author Prof. Dr. David Buzatto
 */
@Entity
@Data
@AllArgsConstructor 
@NoArgsConstructor
@EqualsAndHashCode( onlyExplicitlyIncluded = true )
public class Estado {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @EqualsAndHashCode.Include
    private Long id;
    
    @NotNull( message = "Nome é obrigatório" )
    @Size( min = 1, max = 30, 
           message = "Nome deve ter no mínimo {min} e " +
                     "no máximo {max} caracteres" )
    private String nome;
    
    @NotNull( message = "Sigla é obrigatória" )
    @Size( min = 1, max = 2, 
           message = "Sigla deve ter no mínimo {min} e " +
                     "no máximo {max} caracteres" )
    @Column( unique = true )
    private String sigla;

}
