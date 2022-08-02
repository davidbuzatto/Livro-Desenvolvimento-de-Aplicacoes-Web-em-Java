package vendaprodutos.servicos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vendaprodutos.dao.UnidadeMedidaDAO;
import vendaprodutos.entidades.UnidadeMedida;

/**
 * Classe de serviços para a entidade UnidadeMedida.
 *
 * @author Prof. Dr. David Buzatto
 */
public class UnidadeMedidaServices {

    /**
     * Usa o UnidadeMedidaDAO para obter todas as unidades
     * de medida.
     *
     * @return Lista de Unidades de Medida.
     */
    public List<UnidadeMedida> getTodos() {

        List<UnidadeMedida> lista = new ArrayList<>();

        try ( UnidadeMedidaDAO dao = new UnidadeMedidaDAO() ) {
            lista = dao.listarTodos();
        } catch ( SQLException exc ) {
            exc.printStackTrace();
        }

        return lista;

    }

}
