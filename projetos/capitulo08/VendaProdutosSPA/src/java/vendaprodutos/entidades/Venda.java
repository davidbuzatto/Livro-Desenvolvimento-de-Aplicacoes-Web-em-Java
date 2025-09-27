package vendaprodutos.entidades;

import java.sql.Date;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Entidade Venda.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Venda {

    @NotNull
    private Long id;
    
    @NotNull
    private Boolean cancelada;
    
    @NotNull
    private Date data;
    
    @NotNull
    private Cliente cliente;

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public Boolean getCancelada() {
        return cancelada;
    }

    public void setCancelada( Boolean cancelada ) {
        this.cancelada = cancelada;
    }

    public Date getData() {
        return data;
    }

    public void setData( Date data ) {
        this.data = data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente( Cliente cliente ) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode( this.id );
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
        final Venda other = (Venda) obj;
        return Objects.equals( this.id, other.id );
    }

}
