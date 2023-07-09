package vendaprodutos.entidades;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Entidade Fornecedor.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Fornecedor {

    @NotNull
    private Long id;
    
    @NotNull
    @Size( min = 1, max = 100 )
    private String razaoSocial;
    
    @NotNull
    @Pattern( regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}\\-\\d{2}$",
              message = "deve corresponder à 99.999.999/9999-99" )
    private String cnpj;
    
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

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial( String razaoSocial ) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj( String cnpj ) {
        this.cnpj = cnpj;
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

}
