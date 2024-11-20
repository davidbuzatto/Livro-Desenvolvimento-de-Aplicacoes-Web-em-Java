package locacaomidias.servicos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import locacaomidias.dao.ClassificacaoInternaDAO;
import locacaomidias.entidades.ClassificacaoInterna;

/**
 * Classe de serviços para a entidade ClassificacaoInterna.
 *
 * @author Prof. Dr. David Buzatto
 */
public class ClassificacaoInternaServices {

    /**
     * Usa o ClassificacaoInternaDAO para obter todos as classificações internas.
     *
     * @return Lista de ClassificacaoInterna.
     */
    public List<ClassificacaoInterna> getTodos() {

        List<ClassificacaoInterna> lista = new ArrayList<>();

        try ( ClassificacaoInternaDAO dao = new ClassificacaoInternaDAO() ) {
            lista = dao.listarTodos();
        } catch ( SQLException exc ) {
            exc.printStackTrace();
        }

        return lista;

    }

}
