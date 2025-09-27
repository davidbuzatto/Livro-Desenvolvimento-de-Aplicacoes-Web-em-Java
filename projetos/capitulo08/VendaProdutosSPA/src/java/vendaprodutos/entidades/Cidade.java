package vendaprodutos.entidades;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Entidade Cidade.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Cidade {

    @NotNull
    private Long id;
    
    @NotNull
    @Size( min = 1, max = 30 )
    private String nome;
    
    @NotNull
    private Estado estado;

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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado( Estado estado ) {
        this.estado = estado;
    }

}
