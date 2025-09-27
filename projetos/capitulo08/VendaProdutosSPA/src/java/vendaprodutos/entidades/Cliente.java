package vendaprodutos.entidades;

import java.sql.Date;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Objects;

/**
 * Entidade Cliente.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Cliente {

    @NotNull
    private Long id;
    
    @NotNull
    @Size( min = 1, max = 45 )
    private String nome;
    
    @NotNull
    @Size( min = 1, max = 45 )
    private String sobrenome;
    
    @NotNull
    private Date dataNascimento;
    
    @NotNull
    @Pattern( regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$",
              message = "deve corresponder à 999.999.999-99" )
    private String cpf;
    
    @NotNull
    @Email
    @Size( min = 3, max = 60 )
    private String email;
    
    @NotNull
    @Size( min = 1, max = 50 )
    private String logradouro;
    
    @NotNull
    @Size( min = 1, max = 6 )
    private String numero;
    
    @NotNull
    @Size( min = 1, max = 30 )
    private String bairro;
    
    @NotNull
    @Pattern( regexp = "^\\d{5}\\-\\d{3}$",
              message = "deve corresponder à 99999-999" )
    private String cep;
    
    @NotNull
    private Cidade cidade;

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome( String nome ) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome( String sobrenome ) {
        this.sobrenome = sobrenome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento( Date dataNascimento ) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf( String cpf ) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro( String logradouro ) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero( String numero ) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro( String bairro ) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep( String cep ) {
        this.cep = cep;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade( Cidade cidade ) {
        this.cidade = cidade;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode( this.id );
        return hash;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final Cliente other = (Cliente) obj;
        return Objects.equals( this.id, other.id );
    }

}
