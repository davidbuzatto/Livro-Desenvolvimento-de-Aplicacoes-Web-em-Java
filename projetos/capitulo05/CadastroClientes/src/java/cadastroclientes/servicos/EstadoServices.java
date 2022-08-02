package cadastroclientes.servicos;

import cadastroclientes.dao.EstadoDAO;
import cadastroclientes.entidades.Estado;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de serviços para a entidade Estado.
 *
 * @author Prof. Dr. David Buzatto
 */
public class EstadoServices {

    /**
     * Usa o EstadoDAO para obter todos os estados.
     *
     * @return Lista de estados.
     */
    public List<Estado> getTodos() {

        List<Estado> lista = new ArrayList<>();
        EstadoDAO dao = null;

        try {
            dao = new EstadoDAO();
            lista = dao.listarTodos();
        } catch ( SQLException exc ) {
            exc.printStackTrace();
        } finally {
            if ( dao != null ) {
                try {
                    dao.fecharConexao();
                } catch ( SQLException exc ) {
                    exc.printStackTrace();
                }
            }
        }

        return lista;

    }

}
