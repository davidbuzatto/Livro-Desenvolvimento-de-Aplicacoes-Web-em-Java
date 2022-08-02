package cadastroclientes.servicos;

import cadastroclientes.dao.ClienteDAO;
import cadastroclientes.entidades.Cliente;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de serviços para a entidade Cliente.
 *
 * @author Prof. Dr. David Buzatto
 */
public class ClienteServices {

    /**
     * Usa o ClienteDAO para obter todos os Clientes.
     *
     * @return Lista de Clientes.
     */
    public List<Cliente> getTodos() {

        List<Cliente> lista = new ArrayList<>();
        ClienteDAO dao = null;

        try {
            dao = new ClienteDAO();
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
