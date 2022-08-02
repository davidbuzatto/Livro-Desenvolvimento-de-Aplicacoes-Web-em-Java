package dsoc7.vendaprodutosspring.repositorios;

import dsoc7.vendaprodutosspring.entidades.Produto;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositório para objetos do tipo Produto.
 * 
 * @author Prof. Dr. David Buzatto
 */
public interface ProdutoRepository extends CrudRepository<Produto, Long> {
    
}
