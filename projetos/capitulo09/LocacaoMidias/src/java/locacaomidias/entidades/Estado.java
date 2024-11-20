package locacaomidias.entidades;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Entidade Estado.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Estado {

    @NotNull
    private Long id;
    
    @NotNull
    @Size( min = 1, max = 30 )
    private String nome;
    
    @NotNull
    @Size( min = 1, max = 2 )
    private String sigla;

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

    public String getSigla() {
        return sigla;
    }

    public void setSigla( String sigla ) {
        this.sigla = sigla;
    }

}
