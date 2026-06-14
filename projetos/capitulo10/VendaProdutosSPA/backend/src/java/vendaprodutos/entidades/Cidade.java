package vendaprodutos.entidades;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode( this.id );
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
        final Cidade other = (Cidade) obj;
        return Objects.equals( this.id, other.id );
    }

}
