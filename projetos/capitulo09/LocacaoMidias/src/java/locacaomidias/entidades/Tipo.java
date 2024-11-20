package locacaomidias.entidades;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Entidade Tipo.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Tipo {

    @NotNull
    private Long id;
    
    @NotNull
    @Size( min = 1, max = 45 )
    private String descricao;

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

}
