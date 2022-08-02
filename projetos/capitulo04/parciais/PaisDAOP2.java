package padroesempratica.dao;

import java.sql.SQLException;
import java.util.List;
import padroesempratica.entidades.Pais;

/**
 * DAO para a classe Pais.
 *
 * @author Prof. Dr. David Buzatto
 */
public class PaisDAO extends DAO<Pais> {

    public PaisDAO() throws SQLException {
        super();
    }
    
    @Override
    public void salvar( Pais obj ) throws SQLException {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public void atualizar( Pais obj ) throws SQLException {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public void excluir( Pais obj ) throws SQLException {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public List<Pais> listarTodos() throws SQLException {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public Pais obterPorId( int id ) throws SQLException {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

}
