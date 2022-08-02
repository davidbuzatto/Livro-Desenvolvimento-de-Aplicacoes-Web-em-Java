package dsoc7.vendaprodutosspring.repositorios;

import dsoc7.vendaprodutosspring.entidades.Fornecedor;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositório para objetos do tipo Fornecedor.
 * 
 * @author Prof. Dr. David Buzatto
 */
public interface FornecedorRepository extends CrudRepository<Fornecedor, Long> {
    
}
