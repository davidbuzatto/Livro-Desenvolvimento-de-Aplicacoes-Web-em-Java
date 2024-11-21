package locacaomidias.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import locacaomidias.entidades.Tipo;
import locacaomidias.utils.Utils;

/**
 * DAO para a entidade Tipo.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TipoDAO extends DAO<Tipo> {

    public TipoDAO() throws SQLException {
    }

    @Override
    public void salvar( Tipo obj ) throws SQLException {

        PreparedStatement stmt = getConnection().prepareStatement(
                """
                INSERT INTO  
                tipo( descricao )  
                VALUES( ? );
                """,
                new String[]{ "insert_id" } );

        stmt.setString( 1, obj.getDescricao() );

        stmt.executeUpdate();
        obj.setId( Utils.getChavePrimariaAposInsercao( stmt, "insert_id" ) );
        stmt.close();

    }

    @Override
    public void atualizar( Tipo obj ) throws SQLException {

        PreparedStatement stmt = getConnection().prepareStatement(
                """
                UPDATE tipo  
                SET 
                    descricao = ?  
                WHERE 
                    id = ?;
                """ );

        stmt.setString( 1, obj.getDescricao() );
        stmt.setLong( 2, obj.getId() );

        stmt.executeUpdate();
        stmt.close();

    }

    @Override
    public void excluir( Tipo obj ) throws SQLException {

        PreparedStatement stmt = getConnection().prepareStatement(
                """
                DELETE FROM tipo  
                WHERE 
                    id = ?;
                """ );

        stmt.setLong( 1, obj.getId() );

        stmt.executeUpdate();
        stmt.close();

    }

    @Override
    public List<Tipo> listarTodos() throws SQLException {

        List<Tipo> lista = new ArrayList<>();

        PreparedStatement stmt = getConnection().prepareStatement(
                """
                SELECT * FROM tipo  
                ORDER BY descricao;
                """ );

        ResultSet rs = stmt.executeQuery();

        while ( rs.next() ) {

            Tipo e = new Tipo();

            e.setId( rs.getLong( "id" ) );
            e.setDescricao( rs.getString( "descricao" ) );

            lista.add( e );

        }

        rs.close();
        stmt.close();

        return lista;

    }

    @Override
    public Tipo obterPorId( Long id ) throws SQLException {

        Tipo tipo = null;

        PreparedStatement stmt = getConnection().prepareStatement(
                """
                SELECT * FROM tipo  
                WHERE id = ?;
                """ );

        stmt.setLong( 1, id );

        ResultSet rs = stmt.executeQuery();

        if ( rs.next() ) {

            tipo = new Tipo();

            tipo.setId( rs.getLong( "id" ) );
            tipo.setDescricao( rs.getString( "descricao" ) );

        }

        rs.close();
        stmt.close();

        return tipo;

    }

}
