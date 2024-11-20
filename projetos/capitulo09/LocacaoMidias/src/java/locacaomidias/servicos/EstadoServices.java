package locacaomidias.servicos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import locacaomidias.dao.EstadoDAO;
import locacaomidias.entidades.Estado;

/**
 * Classe de serviços para a entidade Estado.
 *
 * @author Prof. Dr. David Buzatto
 */
public class EstadoServices {

    /**
     * Usa o EstadoDAO para obter todos os estados.
     *
     * @return Lista de Estados.
     */
    public List<Estado> getTodos() {

        List<Estado> lista = new ArrayList<>();

        try ( EstadoDAO dao = new EstadoDAO() ) {
            lista = dao.listarTodos();
        } catch ( SQLException exc ) {
            exc.printStackTrace();
        }

        return lista;

    }

}
