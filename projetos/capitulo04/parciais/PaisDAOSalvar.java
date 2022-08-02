package padroesempratica.dao;

import java.sql.PreparedStatement;
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

        String sql = "INSERT INTO pais( nome, sigla ) " +
                     "VALUES( ?, ? );";

        PreparedStatement stmt = getConnection().prepareStatement( sql );
        stmt.setString( 1, obj.getNome() );
        stmt.setString( 2, obj.getSigla() );

        stmt.executeUpdate();
        stmt.close();

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
