package vendaprodutos.servicos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vendaprodutos.dao.VendaDAO;
import vendaprodutos.entidades.Venda;

/**
 * Classe de serviços para a entidade Venda.
 *
 * @author Prof. Dr. David Buzatto
 */
public class VendaServices {

    /**
     * Usa o VendaDAO para obter todas as vendas.
     *
     * @return Lista de Vendas.
     */
    public List<Venda> getTodos() {

        List<Venda> lista = new ArrayList<>();

        try ( VendaDAO dao = new VendaDAO() ) {
            lista = dao.listarTodos();
        } catch ( SQLException exc ) {
            exc.printStackTrace();
        }

        return lista;

    }

}
