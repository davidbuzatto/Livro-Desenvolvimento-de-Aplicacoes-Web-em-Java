package dsoc7.vendaprodutosspring.repositorios;

import dsoc7.vendaprodutosspring.entidades.ItemVenda;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositório para objetos do tipo ItemVenda.
 * 
 * @author Prof. Dr. David Buzatto
 */
public interface ItemVendaRepository extends CrudRepository<ItemVenda, Long> {
    
}
