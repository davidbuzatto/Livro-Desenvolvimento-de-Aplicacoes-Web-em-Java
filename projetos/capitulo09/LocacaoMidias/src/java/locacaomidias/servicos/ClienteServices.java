package locacaomidias.servicos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import locacaomidias.dao.ClienteDAO;
import locacaomidias.entidades.Cliente;

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

        try ( ClienteDAO dao = new ClienteDAO() ) {
            lista = dao.listarTodos();
        } catch ( SQLException exc ) {
            exc.printStackTrace();
        }

        return lista;

    }

}
