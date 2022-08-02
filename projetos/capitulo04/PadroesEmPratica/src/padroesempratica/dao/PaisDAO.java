package padroesempratica.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

        String sql = "UPDATE pais " +
                     "SET" +
                     "    nome = ?, " +
                     "    sigla = ? " +
                     "WHERE" +
                     "    id = ?;";

        PreparedStatement stmt = getConnection().prepareStatement( sql );
        stmt.setString( 1, obj.getNome() );
        stmt.setString( 2, obj.getSigla() );
        stmt.setInt( 3, obj.getId() );

        stmt.executeUpdate();
        stmt.close();

    }

    @Override
    public void excluir( Pais obj ) throws SQLException {

        String sql = "DELETE FROM pais WHERE id = ?;";

        PreparedStatement stmt = getConnection().prepareStatement( sql );
        stmt.setInt( 1, obj.getId() );

        stmt.executeUpdate();
        stmt.close();

    }

    @Override
    public List<Pais> listarTodos() throws SQLException {

        List<Pais> lista = new ArrayList<>();
        String sql = "SELECT * FROM pais;";

        PreparedStatement stmt = getConnection().prepareStatement( sql );
        ResultSet rs = stmt.executeQuery();

        while ( rs.next() ) {

            Pais pais = new Pais();
            pais.setId( rs.getInt( "id" ) );
            pais.setNome( rs.getString( "nome" ) );
            pais.setSigla( rs.getString( "sigla" ) );

            lista.add( pais );

        }

        rs.close();
        stmt.close();

        return lista;

    }

    @Override
    public Pais obterPorId( int id ) throws SQLException {

        Pais pais = null;
        String sql = "SELECT * FROM pais WHERE id = ?;";

        PreparedStatement stmt = getConnection().prepareStatement( sql );
        stmt.setInt( 1, id );

        ResultSet rs = stmt.executeQuery();

        if ( rs.next() ) {

            pais = new Pais();
            pais.setId( rs.getInt( "id" ) );
            pais.setNome( rs.getString( "nome" ) );
            pais.setSigla( rs.getString( "sigla" ) );

        }

        rs.close();
        stmt.close();

        return pais;

    }

}
