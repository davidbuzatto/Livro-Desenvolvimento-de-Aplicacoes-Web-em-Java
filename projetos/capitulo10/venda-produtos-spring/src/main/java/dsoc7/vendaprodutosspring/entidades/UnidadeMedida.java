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
 * Entidade UnidadeMedida.
 *
 * @author Prof. Dr. David Buzatto
 */
@Entity
@Data
@AllArgsConstructor 
@NoArgsConstructor
@EqualsAndHashCode( onlyExplicitlyIncluded = true )
public class UnidadeMedida {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @EqualsAndHashCode.Include
    private Long id;
    
    @NotNull( message = "Descrição é obrigatória" )
    @Size( min = 1, max = 45, 
           message = "Descrição deve ter no mínimo {min} e " +
                     "no máximo {max} caracteres" )
    private String descricao;
    
    @NotNull( message = "Sigla é obrigatória" )
    @Size( min = 1, max = 4, 
           message = "Sigla deve ter no mínimo {min} e no " +
                     "máximo {max} caracteres" )
    @Column( unique = true )
    private String sigla;

}
