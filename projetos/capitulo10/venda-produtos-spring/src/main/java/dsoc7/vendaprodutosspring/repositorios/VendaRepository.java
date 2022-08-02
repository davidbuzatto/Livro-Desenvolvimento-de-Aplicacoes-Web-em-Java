package dsoc7.vendaprodutosspring.repositorios;

import dsoc7.vendaprodutosspring.entidades.Venda;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositório para objetos do tipo Venda.
 * 
 * @author Prof. Dr. David Buzatto
 */
public interface VendaRepository extends CrudRepository<Venda, Long> {
    
}
