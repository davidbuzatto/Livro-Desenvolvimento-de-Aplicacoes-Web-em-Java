package dsoc7.vendaprodutosspring.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entidade Fornecedor.
 *
 * @author Prof. Dr. David Buzatto
 */
@Entity
@Data
@AllArgsConstructor 
@NoArgsConstructor
@EqualsAndHashCode( onlyExplicitlyIncluded = true )
public class Fornecedor {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @EqualsAndHashCode.Include
    private Long id;
    
    @NotNull( message = "Razão social é obrigatória" )
    @Size( min = 1, max = 100, 
           message = "Razão social deve ter no mínimo {min} e " +
                     "no máximo {max} caracteres" )
    private String razaoSocial;
    
    @NotNull( message = "CNPJ é obrigatório" )
    @Pattern( regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}\\-\\d{2}$",
              message = "Formato deve corresponder à 99.999.999/9999-99" )
    @Size( min = 18, max = 18, 
           message = "CNPJ deve ter {min} caracteres" )
    @Column( unique = true )
    private String cnpj;
    
    @NotNull( message = "E-mail é obrigatório" )
    @Email( message = "Formato de e-mail inválido" )
    @Size( min = 3, max = 60, 
           message = "E-mail deve ter no mínimo {min} e " +
                     "no máximo {max} caracteres" )
    private String email;
    
    @NotNull( message = "Logradouro é obrigatório" )
    @Size( min = 1, max = 50, 
           message = "Logradouro deve ter no mínimo {min} e " +
                     "no máximo {max} caracteres" )
    private String logradouro;
    
    @NotNull( message = "Número é obrigatório" )
    @Size( min = 1, max = 6, 
           message = "Numero deve ter no mínimo {min} e " +
                     "no máximo {max} caracteres" )
    private String numero;
    
    @NotNull( message = "Bairro é obrigatório" )
    @Size( min = 1, max = 30, 
           message = "Bairro deve ter no mínimo {min} e " +
                     "no máximo {max} caracteres" )
    private String bairro;
    
    @NotNull( message = "CEP é obrigatório" )
    @Pattern( regexp = "^\\d{5}\\-\\d{3}$",
              message = "Formato deve corresponder à 99999-999" )
    @Size( min = 9, max = 9, 
           message = "CEP deve ter {min} caracteres" )
    private String cep;
    
    @NotNull( message = "Cidade é obrigatória" )
    @ManyToOne
    private Cidade cidade;

}
