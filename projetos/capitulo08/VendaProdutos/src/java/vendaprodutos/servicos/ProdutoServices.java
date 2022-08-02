package vendaprodutos.servicos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vendaprodutos.dao.ProdutoDAO;
import vendaprodutos.entidades.Produto;

/**
 * Classe de serviços para a entidade Produto.
 *
 * @author Prof. Dr. David Buzatto
 */
public class ProdutoServices {

    /**
     * Usa o ProdutoDAO para obter todos os Produtos.
     *
     * @return Lista de Produtos.
     */
    public List<Produto> getTodos() {

        List<Produto> lista = new ArrayList<>();

        try ( ProdutoDAO dao = new ProdutoDAO() ) {
            lista = dao.listarTodos();
        } catch ( SQLException exc ) {
            exc.printStackTrace();
        }

        return lista;

    }

}
