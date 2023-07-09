package vendaprodutos.entidades;

import java.sql.Date;
import jakarta.validation.constraints.NotNull;

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

}
