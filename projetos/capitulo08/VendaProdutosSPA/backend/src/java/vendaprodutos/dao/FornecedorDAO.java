package vendaprodutos.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vendaprodutos.entidades.Cidade;
import vendaprodutos.entidades.Estado;
import vendaprodutos.entidades.Fornecedor;
import vendaprodutos.utils.Utils;

/**
 * DAO para a entidade Fornecedor.
 *
 * @author Prof. Dr. David Buzatto
 */
public class FornecedorDAO extends DAO<Fornecedor> {

    public FornecedorDAO() throws SQLException {
    }

    @Override
    public void salvar( Fornecedor obj ) throws SQLException {

        PreparedStatement stmt = getConnection().prepareStatement(
                """
                INSERT INTO  
                fornecedor( 
                    razao_social,  
                    cnpj,  
                    email,  
                    logradouro,  
                    numero,  
                    bairro,  
                    cep,  
                    cidade_id )  
                VALUES( ?, ?, ?, ?, ?, ?, ?, ? );
                """,
                new String[]{ "insert_id" } );

        stmt.setString( 1, obj.getRazaoSocial() );
        stmt.setString( 2, obj.getCnpj() );
        stmt.setString( 3, obj.getEmail() );
        stmt.setString( 4, obj.getLogradouro() );
        stmt.setString( 5, obj.getNumero() );
        stmt.setString( 6, obj.getBairro() );
        stmt.setString( 7, obj.getCep() );
        stmt.setLong( 8, obj.getCidade().getId() );

        stmt.executeUpdate();
        obj.setId( Utils.getChavePrimariaAposInsercao( stmt, "insert_id" ) );
        stmt.close();

    }

    @Override
    public void atualizar( Fornecedor obj ) throws SQLException {

        PreparedStatement stmt = getConnection().prepareStatement(
                """
                UPDATE fornecedor  
                SET 
                    razao_social = ?,  
                    cnpj = ?,  
                    email = ?,  
                    logradouro = ?,  
                    numero = ?,  
                    bairro = ?,  
                    cep = ?,  
                    cidade_id = ?  
                WHERE 
                    id = ?;
                """ );

        stmt.setString( 1, obj.getRazaoSocial() );
        stmt.setString( 2, obj.getCnpj() );
        stmt.setString( 3, obj.getEmail() );
        stmt.setString( 4, obj.getLogradouro() );
        stmt.setString( 5, obj.getNumero() );
        stmt.setString( 6, obj.getBairro() );
        stmt.setString( 7, obj.getCep() );
        stmt.setLong( 8, obj.getCidade().getId() );
        stmt.setLong( 9, obj.getId() );

        stmt.executeUpdate();
        stmt.close();

    }

    @Override
    public void excluir( Fornecedor obj ) throws SQLException {

        PreparedStatement stmt = getConnection().prepareStatement(
                """
                DELETE FROM fornecedor  
                WHERE 
                    id = ?;
                """ );

        stmt.setLong( 1, obj.getId() );

        stmt.executeUpdate();
        stmt.close();

    }

    @Override
    public List<Fornecedor> listarTodos() throws SQLException {

        List<Fornecedor> lista = new ArrayList<>();

        PreparedStatement stmt = getConnection().prepareStatement(
                """
                SELECT 
                    f.id idFornecedor,  
                    f.razao_social razaoSocialFornecedor,  
                    f.cnpj cnpjFornecedor,  
                    f.email emailFornecedor,  
                    f.logradouro logradouroFornecedor,  
                    f.numero numeroFornecedor,  
                    f.bairro bairroFornecedor,  
                    f.cep cepFornecedor,  
                    ci.id idCidade,  
                    ci.nome nomeCidade,  
                    e.id idEstado,  
                    e.nome nomeEstado,  
                    e.sigla siglaEstado  
                FROM 
                    fornecedor f,  
                    cidade ci,  
                    estado e  
                WHERE 
                    f.cidade_id = ci.id AND  
                    ci.estado_id = e.id 
                ORDER BY f.razao_social, ci.nome;
                """ );

        ResultSet rs = stmt.executeQuery();

        while ( rs.next() ) {

            Fornecedor f = new Fornecedor();
            Cidade ci = new Cidade();
            Estado e = new Estado();

            f.setId( rs.getLong( "idFornecedor" ) );
            f.setRazaoSocial( rs.getString( "razaoSocialFornecedor" ) );
            f.setCnpj( rs.getString( "cnpjFornecedor" ) );
            f.setEmail( rs.getString( "emailFornecedor" ) );
            f.setLogradouro( rs.getString( "logradouroFornecedor" ) );
            f.setNumero( rs.getString( "numeroFornecedor" ) );
            f.setBairro( rs.getString( "bairroFornecedor" ) );
            f.setCep( rs.getString( "cepFornecedor" ) );
            f.setCidade( ci );

            ci.setId( rs.getLong( "idCidade" ) );
            ci.setNome( rs.getString( "nomeCidade" ) );
            ci.setEstado( e );

            e.setId( rs.getLong( "idEstado" ) );
            e.setNome( rs.getString( "nomeEstado" ) );
            e.setSigla( rs.getString( "siglaEstado" ) );

            lista.add( f );

        }

        rs.close();
        stmt.close();

        return lista;

    }

    @Override
    public Fornecedor obterPorId( Long id ) throws SQLException {

        Fornecedor fornecedor = null;

        PreparedStatement stmt = getConnection().prepareStatement(
                """
                SELECT 
                    f.id idFornecedor,  
                    f.razao_social razaoSocialFornecedor,  
                    f.cnpj cnpjFornecedor,  
                    f.email emailFornecedor,  
                    f.logradouro logradouroFornecedor,  
                    f.numero numeroFornecedor,  
                    f.bairro bairroFornecedor,  
                    f.cep cepFornecedor,  
                    ci.id idCidade,  
                    ci.nome nomeCidade,  
                    e.id idEstado,  
                    e.nome nomeEstado,  
                    e.sigla siglaEstado  
                FROM 
                    fornecedor f,  
                    cidade ci,  
                    estado e  
                WHERE 
                    f.id = ? AND 
                    f.cidade_id = ci.id AND  
                    ci.estado_id = e.id;
                """ );

        stmt.setLong( 1, id );

        ResultSet rs = stmt.executeQuery();

        if ( rs.next() ) {

            fornecedor = new Fornecedor();
            Cidade ci = new Cidade();
            Estado e = new Estado();

            fornecedor.setId( rs.getLong( "idFornecedor" ) );
            fornecedor.setRazaoSocial( rs.getString( "razaoSocialFornecedor" ) );
            fornecedor.setCnpj( rs.getString( "cnpjFornecedor" ) );
            fornecedor.setEmail( rs.getString( "emailFornecedor" ) );
            fornecedor.setLogradouro( rs.getString( "logradouroFornecedor" ) );
            fornecedor.setNumero( rs.getString( "numeroFornecedor" ) );
            fornecedor.setBairro( rs.getString( "bairroFornecedor" ) );
            fornecedor.setCep( rs.getString( "cepFornecedor" ) );
            fornecedor.setCidade( ci );

            ci.setId( rs.getLong( "idCidade" ) );
            ci.setNome( rs.getString( "nomeCidade" ) );
            ci.setEstado( e );

            e.setId( rs.getLong( "idEstado" ) );
            e.setNome( rs.getString( "nomeEstado" ) );
            e.setSigla( rs.getString( "siglaEstado" ) );

        }

        rs.close();
        stmt.close();

        return fornecedor;

    }

}
