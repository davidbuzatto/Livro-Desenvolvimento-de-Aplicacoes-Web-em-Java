package vendaprodutos.entidades;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Objects;

/**
 * Entidade UnidadeMedida.
 *
 * @author Prof. Dr. David Buzatto
 */
public class UnidadeMedida {

    @NotNull
    private Long id;
    
    @NotNull
    @Size( min = 1, max = 45 )
    private String descricao;
    
    @NotNull
    @Size( min = 1, max = 4 )
    private String sigla;

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao( String descricao ) {
        this.descricao = descricao;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla( String sigla ) {
        this.sigla = sigla;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final UnidadeMedida other = (UnidadeMedida) obj;
        return Objects.equals( this.id, other.id );
    }

}
