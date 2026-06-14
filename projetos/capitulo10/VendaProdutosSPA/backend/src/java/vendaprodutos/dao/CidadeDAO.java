package vendaprodutos.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vendaprodutos.entidades.Cidade;
import vendaprodutos.entidades.Estado;
import vendaprodutos.utils.Utils;

/**
 * DAO para a entidade Cidade.
 *
 * @author Prof. Dr. David Buzatto
 */
public class CidadeDAO extends DAO<Cidade> {

    public CidadeDAO() throws SQLException {
    }

    @Override
    public void salvar( Cidade obj ) throws SQLException {

        PreparedStatement stmt = getConnection().prepareStatement(
                """
                INSERT INTO  
                cidade( nome, estado_id )  
                VALUES( ?, ? );
                """,
                new String[]{ "insert_id" } );

        stmt.setString( 1, obj.getNome() );
        stmt.setLong( 2, obj.getEstado().getId() );

        stmt.executeUpdate();
        obj.setId( Utils.getChavePrimariaAposInsercao( stmt, "insert_id" ) );
        stmt.close();

    }

    @Override
    public void atualizar( Cidade obj ) throws SQLException {

        PreparedStatement stmt = getConnection().prepareStatement(
                """
                UPDATE cidade  
                SET 
                    nome = ?, 
                    estado_id = ?  
                WHERE 
                    id = ?;
                """ );

        stmt.setString( 1, obj.getNome() );
        stmt.setLong( 2, obj.getEstado().getId() );
        stmt.setLong( 3, obj.getId() );

        stmt.executeUpdate();
        stmt.close();

    }

    @Override
    public void excluir( Cidade obj ) throws SQLException {

        PreparedStatement stmt = getConnection().prepareStatement(
                """
                DELETE FROM cidade  
                WHERE 
                    id = ?;
                """ );

        stmt.setLong( 1, obj.getId() );

        stmt.executeUpdate();
        stmt.close();

    }

    @Override
    public List<Cidade> listarTodos() throws SQLException {

        List<Cidade> lista = new ArrayList<>();

        PreparedStatement stmt = getConnection().prepareStatement(
                """
                SELECT 
                    c.id idCidade,  
                    c.nome nomeCidade,  
                    e.id idEstado,  
                    e.nome nomeEstado,  
                    e.sigla siglaEstado  
                FROM 
                    cidade c,  
                    estado e  
                WHERE 
                    c.estado_id = e.id 
                ORDER BY c.nome, e.nome, e.sigla;
                """ );

        ResultSet rs = stmt.executeQuery();

        while ( rs.next() ) {

            Cidade c = new Cidade();
            Estado e = new Estado();

            c.setId( rs.getLong( "idCidade" ) );
            c.setNome( rs.getString( "nomeCidade" ) );
            c.setEstado( e );

            e.setId( rs.getLong( "idEstado" ) );
            e.setNome( rs.getString( "nomeEstado" ) );
            e.setSigla( rs.getString( "siglaEstado" ) );

            lista.add( c );

        }

        rs.close();
        stmt.close();

        return lista;

    }

    @Override
    public Cidade obterPorId( Long id ) throws SQLException {

        Cidade cidade = null;

        PreparedStatement stmt = getConnection().prepareStatement(
                """
                SELECT 
                    c.id idCidade,  
                    c.nome nomeCidade,  
                    e.id idEstado,  
                    e.nome nomeEstado,  
                    e.sigla siglaEstado  
                FROM 
                    cidade c,  
                    estado e  
                WHERE 
                    c.id = ? AND  
                    c.estado_id = e.id;
                """ );

        stmt.setLong( 1, id );

        ResultSet rs = stmt.executeQuery();

        if ( rs.next() ) {

            cidade = new Cidade();
            Estado e = new Estado();

            cidade.setId( rs.getLong( "idCidade" ) );
            cidade.setNome( rs.getString( "nomeCidade" ) );
            cidade.setEstado( e );

            e.setId( rs.getLong( "idEstado" ) );
            e.setNome( rs.getString( "nomeEstado" ) );
            e.setSigla( rs.getString( "siglaEstado" ) );

        }

        rs.close();
        stmt.close();

        return cidade;

    }


}
