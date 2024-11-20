package locacaomidias.servicos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import locacaomidias.dao.GeneroDAO;
import locacaomidias.entidades.Genero;

/**
 * Classe de serviços para a entidade Gênero.
 *
 * @author Prof. Dr. David Buzatto
 */
public class GeneroServices {

    /**
     * Usa o GeneroDAO para obter todos os gêneros.
     *
     * @return Lista de Gêneros.
     */
    public List<Genero> getTodos() {

        List<Genero> lista = new ArrayList<>();

        try ( GeneroDAO dao = new GeneroDAO() ) {
            lista = dao.listarTodos();
        } catch ( SQLException exc ) {
            exc.printStackTrace();
        }

        return lista;

    }

}
