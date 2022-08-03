package vendaprodutos.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vendaprodutos.entidades.UnidadeMedida;
import vendaprodutos.utils.Utils;

/**
 * DAO para a entidade UnidadeMedida.
 *
 * @author Prof. Dr. David Buzatto
 */
public class UnidadeMedidaDAO extends DAO<UnidadeMedida> {

    public UnidadeMedidaDAO() throws SQLException {
    }

    @Override
    public void salvar( UnidadeMedida obj ) throws SQLException {

        PreparedStatement stmt = getConnection().prepareStatement(
                "INSERT INTO " + 
                "unidade_medida( descricao, sigla ) " + 
                "VALUES( ?, ? );",
                new String[]{ "insert_id" } );

        stmt.setString( 1, obj.getDescricao() );
        stmt.setString( 2, obj.getSigla() );

        stmt.executeUpdate();
        obj.setId( Utils.getChavePrimariaAposInsercao( stmt, "insert_id" ) );
        stmt.close();

    }

    @Override
    public void atualizar( UnidadeMedida obj ) throws SQLException {

        PreparedStatement stmt = getConnection().prepareStatement(
                "UPDATE unidade_medida " + 
                "SET" + 
                "    descricao = ?," + 
                "    sigla = ? " + 
                "WHERE" + 
                "    id = ?;" );

        stmt.setString( 1, obj.getDescricao() );
        stmt.setString( 2, obj.getSigla() );
        stmt.setLong( 3, obj.getId() );

        stmt.executeUpdate();
        stmt.close();

    }

    @Override
    public void excluir( UnidadeMedida obj ) throws SQLException {

        PreparedStatement stmt = getConnection().prepareStatement(
                "DELETE FROM unidade_medida " + 
                "WHERE" + 
                "    id = ?;" );

        stmt.setLong( 1, obj.getId() );

        stmt.executeUpdate();
        stmt.close();

    }

    @Override
    public List<UnidadeMedida> listarTodos() throws SQLException {

        List<UnidadeMedida> lista = new ArrayList<>();

        PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT * FROM unidade_medida " + 
                "ORDER BY descricao, sigla;" );

        ResultSet rs = stmt.executeQuery();

        while ( rs.next() ) {

            UnidadeMedida e = new UnidadeMedida();

            e.setId( rs.getLong( "id" ) );
            e.setDescricao( rs.getString( "descricao" ) );
            e.setSigla( rs.getString( "sigla" ) );

            lista.add( e );

        }

        rs.close();
        stmt.close();

        return lista;

    }

    @Override
    public UnidadeMedida obterPorId( Long id ) throws SQLException {

        UnidadeMedida unidadeMedida = null;

        PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT * FROM unidade_medida " + 
                "WHERE id = ?;" );

        stmt.setLong( 1, id );

        ResultSet rs = stmt.executeQuery();

        if ( rs.next() ) {

            unidadeMedida = new UnidadeMedida();

            unidadeMedida.setId( rs.getLong( "id" ) );
            unidadeMedida.setDescricao( rs.getString( "descricao" ) );
            unidadeMedida.setSigla( rs.getString( "sigla" ) );

        }

        rs.close();
        stmt.close();

        return unidadeMedida;

    }

}
