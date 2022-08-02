package vendaprodutos.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vendaprodutos.entidades.Estado;
import vendaprodutos.utils.Utils;

/**
 * DAO para a entidade Estado.
 *
 * @author Prof. Dr. David Buzatto
 */
public class EstadoDAO extends DAO<Estado> {

    public EstadoDAO() throws SQLException {
    }

    @Override
    public void salvar( Estado obj ) throws SQLException {

        PreparedStatement stmt = getConnection().prepareStatement(
                "INSERT INTO " + 
                "estado( nome, sigla ) " + 
                "VALUES( ?, ? );",
                new String[]{ "id" } );

        stmt.setString( 1, obj.getNome() );
        stmt.setString( 2, obj.getSigla() );

        stmt.executeUpdate();
        obj.setId( Utils.getChavePrimariaAposInsercao( stmt, "id" ) );
        stmt.close();

    }

    @Override
    public void atualizar( Estado obj ) throws SQLException {

        PreparedStatement stmt = getConnection().prepareStatement(
                "UPDATE estado " + 
                "SET" + 
                "    nome = ?," + 
                "    sigla = ? " + 
                "WHERE" + 
                "    id = ?;" );

        stmt.setString( 1, obj.getNome() );
        stmt.setString( 2, obj.getSigla() );
        stmt.setLong( 3, obj.getId() );

        stmt.executeUpdate();
        stmt.close();

    }

    @Override
    public void excluir( Estado obj ) throws SQLException {

        PreparedStatement stmt = getConnection().prepareStatement(
                "DELETE FROM estado " + 
                "WHERE" + 
                "    id = ?;" );

        stmt.setLong( 1, obj.getId() );

        stmt.executeUpdate();
        stmt.close();

    }

    @Override
    public List<Estado> listarTodos() throws SQLException {

        List<Estado> lista = new ArrayList<>();

        PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT * FROM estado " + 
                "ORDER BY nome, sigla;" );

        ResultSet rs = stmt.executeQuery();

        while ( rs.next() ) {

            Estado e = new Estado();

            e.setId( rs.getLong( "id" ) );
            e.setNome( rs.getString( "nome" ) );
            e.setSigla( rs.getString( "sigla" ) );

            lista.add( e );

        }

        rs.close();
        stmt.close();

        return lista;

    }

    @Override
    public Estado obterPorId( Long id ) throws SQLException {

        Estado estado = null;

        PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT * FROM estado " + 
                "WHERE id = ?;" );

        stmt.setLong( 1, id );

        ResultSet rs = stmt.executeQuery();

        if ( rs.next() ) {

            estado = new Estado();

            estado.setId( rs.getLong( "id" ) );
            estado.setNome( rs.getString( "nome" ) );
            estado.setSigla( rs.getString( "sigla" ) );

        }

        rs.close();
        stmt.close();

        return estado;

    }

}
