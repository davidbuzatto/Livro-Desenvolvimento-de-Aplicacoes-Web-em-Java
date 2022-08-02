package vendaprodutos.servicos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vendaprodutos.dao.FornecedorDAO;
import vendaprodutos.entidades.Fornecedor;

/**
 * Classe de serviços para a entidade Fornecedore.
 *
 * @author Prof. Dr. David Buzatto
 */
public class FornecedorServices {

    /**
     * Usa o FornecedorDAO para obter todos os Fornecedores.
     *
     * @return Lista de Fornecedores.
     */
    public List<Fornecedor> getTodos() {

        List<Fornecedor> lista = new ArrayList<>();

        try ( FornecedorDAO dao = new FornecedorDAO() ) {
            lista = dao.listarTodos();
        } catch ( SQLException exc ) {
            exc.printStackTrace();
        }

        return lista;

    }

}
