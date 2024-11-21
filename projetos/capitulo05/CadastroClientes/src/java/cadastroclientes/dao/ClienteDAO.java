package cadastroclientes.dao;

import cadastroclientes.entidades.Cidade;
import cadastroclientes.entidades.Cliente;
import cadastroclientes.entidades.Estado;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para a entidade Cliente.
 *
 * @author Prof. Dr. David Buzatto
 */
public class ClienteDAO extends DAO<Cliente> {

    public ClienteDAO() throws SQLException {
    }

    @Override
    public void salvar( Cliente obj ) throws SQLException {

        PreparedStatement stmt = getConnection().prepareStatement(
                """
                INSERT INTO  
                cliente( 
                    nome,  
                    sobrenome,  
                    data_nascimento,  
                    cpf,  
                    email,  
                    logradouro,  
                    numero,  
                    bairro,  
                    cep,  
                    cidade_id )  
                VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? );
                """ );

        stmt.setString( 1, obj.getNome() );
        stmt.setString( 2, obj.getSobrenome() );
        stmt.setDate( 3, obj.getDataNascimento() );
        stmt.setString( 4, obj.getCpf() );
        stmt.setString( 5, obj.getEmail() );
        stmt.setString( 6, obj.getLogradouro() );
        stmt.setString( 7, obj.getNumero() );
        stmt.setString( 8, obj.getBairro() );
        stmt.setString( 9, obj.getCep() );
        stmt.setInt( 10, obj.getCidade().getId() );

        stmt.executeUpdate();
        stmt.close();

    }

    @Override
    public void atualizar( Cliente obj ) throws SQLException {

        PreparedStatement stmt = getConnection().prepareStatement(
                """
                UPDATE cliente  
                SET 
                    nome = ?,  
                    sobrenome = ?, 
                    data_nascimento = ?,  
                    cpf = ?,  
                    email = ?,  
                    logradouro = ?,  
                    numero = ?,  
                    bairro = ?,  
                    cep = ?,  
                    cidade_id = ?  
                WHERE 
                    id = ?;
                """ );

        stmt.setString( 1, obj.getNome() );
        stmt.setString( 2, obj.getSobrenome() );
        stmt.setDate( 3, obj.getDataNascimento() );
        stmt.setString( 4, obj.getCpf() );
        stmt.setString( 5, obj.getEmail() );
        stmt.setString( 6, obj.getLogradouro() );
        stmt.setString( 7, obj.getNumero() );
        stmt.setString( 8, obj.getBairro() );
        stmt.setString( 9, obj.getCep() );
        stmt.setInt( 10, obj.getCidade().getId() );
        stmt.setInt( 11, obj.getId() );

        stmt.executeUpdate();
        stmt.close();

    }

    @Override
    public void excluir( Cliente obj ) throws SQLException {

        PreparedStatement stmt = getConnection().prepareStatement(
                """
                DELETE FROM cliente  
                WHERE 
                    id = ?;
                """ );

        stmt.setInt( 1, obj.getId() );

        stmt.executeUpdate();
        stmt.close();

    }

    @Override
    public List<Cliente> listarTodos() throws SQLException {

        List<Cliente> lista = new ArrayList<>();

        PreparedStatement stmt = getConnection().prepareStatement(
                """
                SELECT 
                    c.id idCliente,  
                    c.nome nomeCliente,  
                    c.sobreNome sobrenomeCliente,  
                    c.data_nascimento dataNascimentoCliente,  
                    c.cpf cpfCliente,  
                    c.email emailCliente,  
                    c.logradouro logradouroCliente,  
                    c.numero numeroCliente,  
                    c.bairro bairroCliente,  
                    c.cep cepCliente,  
                    ci.id idCidade,  
                    ci.nome nomeCidade,  
                    e.id idEstado,  
                    e.nome nomeEstado,  
                    e.sigla siglaEstado  
                FROM 
                    cliente c,  
                    cidade ci,  
                    estado e  
                WHERE 
                    c.cidade_id = ci.id AND  
                    ci.estado_id = e.id 
                ORDER BY c.nome, c.sobreNome, ci.nome;
                """ );

        ResultSet rs = stmt.executeQuery();

        while ( rs.next() ) {

            Cliente c = new Cliente();
            Cidade ci = new Cidade();
            Estado e = new Estado();

            c.setId( rs.getInt( "idCliente" ) );
            c.setNome( rs.getString( "nomeCliente" ) );
            c.setSobrenome( rs.getString( "sobrenomeCliente" ) );
            c.setDataNascimento( rs.getDate( "dataNascimentoCliente" ) );
            c.setCpf( rs.getString( "cpfCliente" ) );
            c.setEmail( rs.getString( "emailCliente" ) );
            c.setLogradouro( rs.getString( "logradouroCliente" ) );
            c.setNumero( rs.getString( "numeroCliente" ) );
            c.setBairro( rs.getString( "bairroCliente" ) );
            c.setCep( rs.getString( "cepCliente" ) );
            c.setCidade( ci );

            ci.setId( rs.getInt( "idCidade" ) );
            ci.setNome( rs.getString( "nomeCidade" ) );
            ci.setEstado( e );

            e.setId( rs.getInt( "idEstado" ) );
            e.setNome( rs.getString( "nomeEstado" ) );
            e.setSigla( rs.getString( "siglaEstado" ) );

            lista.add( c );

        }

        rs.close();
        stmt.close();

        return lista;

    }

    @Override
    public Cliente obterPorId( int id ) throws SQLException {

        Cliente cliente = null;

        PreparedStatement stmt = getConnection().prepareStatement(
                """
                SELECT
                    c.id idCliente, 
                    c.nome nomeCliente, 
                    c.sobreNome sobrenomeCliente, 
                    c.data_nascimento dataNascimentoCliente, 
                    c.cpf cpfCliente, 
                    c.email emailCliente, 
                    c.logradouro logradouroCliente, 
                    c.numero numeroCliente, 
                    c.bairro bairroCliente, 
                    c.cep cepCliente, 
                    ci.id idCidade, 
                    ci.nome nomeCidade, 
                    e.id idEstado, 
                    e.nome nomeEstado, 
                    e.sigla siglaEstado 
                FROM
                    cliente c, 
                    cidade ci, 
                    estado e 
                WHERE
                    c.id = ? AND 
                    c.cidade_id = ci.id AND 
                    ci.estado_id = e.id;
                """ );

        stmt.setInt( 1, id );

        ResultSet rs = stmt.executeQuery();

        if ( rs.next() ) {

            cliente = new Cliente();
            Cidade ci = new Cidade();
            Estado e = new Estado();

            cliente.setId( rs.getInt( "idCliente" ) );
            cliente.setNome( rs.getString( "nomeCliente" ) );
            cliente.setSobrenome( rs.getString( "sobrenomeCliente" ) );
            cliente.setDataNascimento( rs.getDate( "dataNascimentoCliente" ) );
            cliente.setCpf( rs.getString( "cpfCliente" ) );
            cliente.setEmail( rs.getString( "emailCliente" ) );
            cliente.setLogradouro( rs.getString( "logradouroCliente" ) );
            cliente.setNumero( rs.getString( "numeroCliente" ) );
            cliente.setBairro( rs.getString( "bairroCliente" ) );
            cliente.setCep( rs.getString( "cepCliente" ) );
            cliente.setCidade( ci );

            ci.setId( rs.getInt( "idCidade" ) );
            ci.setNome( rs.getString( "nomeCidade" ) );
            ci.setEstado( e );

            e.setId( rs.getInt( "idEstado" ) );
            e.setNome( rs.getString( "nomeEstado" ) );
            e.setSigla( rs.getString( "siglaEstado" ) );

        }

        rs.close();
        stmt.close();

        return cliente;

    }

}
