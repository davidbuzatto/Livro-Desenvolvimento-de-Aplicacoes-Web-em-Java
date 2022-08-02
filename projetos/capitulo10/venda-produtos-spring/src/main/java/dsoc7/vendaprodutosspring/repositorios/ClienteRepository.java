package dsoc7.vendaprodutosspring.repositorios;

import dsoc7.vendaprodutosspring.entidades.Cliente;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositório para objetos do tipo Cliente.
 * 
 * @author Prof. Dr. David Buzatto
 */
public interface ClienteRepository extends CrudRepository<Cliente, Long> {
    
}
