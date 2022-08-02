package dsoc7.vendaprodutosspring.repositorios;

import dsoc7.vendaprodutosspring.entidades.Cidade;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositório para objetos do tipo Cidade.
 * 
 * @author Prof. Dr. David Buzatto
 */
public interface CidadeRepository extends CrudRepository<Cidade, Long> {
    
}
