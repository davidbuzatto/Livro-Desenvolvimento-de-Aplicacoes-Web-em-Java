package dsoc7.vendaprodutosspring.entidades;

import java.time.LocalDate;
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
 * Entidade Cliente.
 *
 * @author Prof. Dr. David Buzatto
 */
@Entity
@Data
@AllArgsConstructor 
@NoArgsConstructor
@EqualsAndHashCode( onlyExplicitlyIncluded = true )
public class Cliente {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @EqualsAndHashCode.Include
    private Long id;
    
    @NotNull( message = "Nome é obrigatório" )
    @Size( min = 1, max = 45, 
           message = "Nome deve ter no mínimo {min} e " +
                     "no máximo {max} caracteres" )
    private String nome;
    
    @NotNull( message = "Sobrenome é obrigatório" )
    @Size( min = 1, max = 45, 
           message = "Sobrenome deve ter no mínimo {min} e " +
                     "no máximo {max} caracteres" )
    private String sobrenome;
    
    @NotNull( message = "Data de nascimento é obrigatória" )
    private LocalDate dataNascimento;
    
    @NotNull( message = "CPF é obrigatório" )
    @Pattern( regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$",
              message = "Formato deve corresponder à 999.999.999-99" )
    @Size( min = 14, max = 14, 
           message = "CPF deve ter {min} caracteres" )
    @Column( unique = true )
    private String cpf;
    
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
           message = "Número deve ter no mínimo {min} e " +
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
