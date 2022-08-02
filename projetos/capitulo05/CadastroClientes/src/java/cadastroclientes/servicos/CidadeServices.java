package cadastroclientes.servicos;

import cadastroclientes.dao.CidadeDAO;
import cadastroclientes.entidades.Cidade;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de serviços para a entidade Cidade.
 *
 * @author Prof. Dr. David Buzatto
 */
public class CidadeServices {

    /**
     * Usa o CidadeDAO para obter todos os Cidades.
     *
     * @return Lista de Cidades.
     */
    public List<Cidade> getTodos() {

        List<Cidade> lista = new ArrayList<>();
        CidadeDAO dao = null;

        try {
            dao = new CidadeDAO();
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
