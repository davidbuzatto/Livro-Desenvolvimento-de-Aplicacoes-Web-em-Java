package dsoc7.vendaprodutosspring.repositorios;

import dsoc7.vendaprodutosspring.entidades.Estado;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositório para objetos do tipo Estado.
 * 
 * @author Prof. Dr. David Buzatto
 */
public interface EstadoRepository extends CrudRepository<Estado, Long> {
    
}
