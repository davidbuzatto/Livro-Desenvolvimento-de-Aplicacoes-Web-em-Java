package locacaomidias.servicos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import locacaomidias.dao.ClassificacaoEtariaDAO;
import locacaomidias.entidades.ClassificacaoEtaria;

/**
 * Classe de serviços para a entidade ClassificacaoEtaria.
 *
 * @author Prof. Dr. David Buzatto
 */
public class ClassificacaoEtariaServices {

    /**
     * Usa o ClassificacaoEtariaDAO para obter todos as classificações etárias.
     *
     * @return Lista de ClassificacaoEtaria.
     */
    public List<ClassificacaoEtaria> getTodos() {

        List<ClassificacaoEtaria> lista = new ArrayList<>();

        try ( ClassificacaoEtariaDAO dao = new ClassificacaoEtariaDAO() ) {
            lista = dao.listarTodos();
        } catch ( SQLException exc ) {
            exc.printStackTrace();
        }

        return lista;

    }

}
