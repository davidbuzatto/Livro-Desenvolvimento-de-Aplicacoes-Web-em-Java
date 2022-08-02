package vendaprodutos.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vendaprodutos.entidades.Cidade;
import vendaprodutos.entidades.Estado;
import vendaprodutos.entidades.Fornecedor;
import vendaprodutos.entidades.Produto;
import vendaprodutos.entidades.UnidadeMedida;
import vendaprodutos.utils.Utils;

/**
 * DAO para a entidade Produto.
 *
 * @author Prof. Dr. David Buzatto
 */
public class ProdutoDAO extends DAO<Produto> {

    public ProdutoDAO() throws SQLException {
    }

    @Override
    public void salvar( Produto obj ) throws SQLException {

        PreparedStatement stmt = getConnection().prepareStatement(
                "INSERT INTO " + 
                "produto(" + 
                "    descricao, " + 
                "    codigoBarras, " + 
                "    valorVenda, " + 
                "    estoque, " + 
                "    fornecedor_id, " + 
                "    unidade_medida_id ) " + 
                "VALUES( ?, ?, ?, ?, ?, ? );",
                new String[]{ "id" } ); // para retorno da chave
                                        // primária gerada

        stmt.setString( 1, obj.getDescricao() );
        stmt.setString( 2, obj.getCodigoBarras() );
        stmt.setBigDecimal( 3, obj.getValorVenda() );
        stmt.setBigDecimal( 4, obj.getEstoque() );
        stmt.setLong( 5, obj.getFornecedor().getId() );
        stmt.setLong( 6, obj.getUnidadeMedida().getId() );

        stmt.executeUpdate();
        obj.setId( Utils.getChavePrimariaAposInsercao( stmt, "id" ) );
        stmt.close();

    }

    @Override
    public void atualizar( Produto obj ) throws SQLException {

        PreparedStatement stmt = getConnection().prepareStatement(
                "UPDATE produto " + 
                "SET" + 
                "    descricao = ?, " + 
                "    codigoBarras = ?," + 
                "    valorVenda = ?, " + 
                "    estoque = ?, " + 
                "    fornecedor_id = ?, " + 
                "    unidade_medida_id = ? " + 
                "WHERE" + 
                "    id = ?;" );

        stmt.setString( 1, obj.getDescricao() );
        stmt.setString( 2, obj.getCodigoBarras() );
        stmt.setBigDecimal( 3, obj.getValorVenda() );
        stmt.setBigDecimal( 4, obj.getEstoque() );
        stmt.setLong( 5, obj.getFornecedor().getId() );
        stmt.setLong( 6, obj.getUnidadeMedida().getId() );
        stmt.setLong( 7, obj.getId() );

        stmt.executeUpdate();
        stmt.close();

    }

    @Override
    public void excluir( Produto obj ) throws SQLException {

        PreparedStatement stmt = getConnection().prepareStatement(
                "DELETE FROM produto " + 
                "WHERE" + 
                "    id = ?;" );

        stmt.setLong( 1, obj.getId() );

        stmt.executeUpdate();
        stmt.close();

    }

    @Override
    public List<Produto> listarTodos() throws SQLException {

        List<Produto> lista = new ArrayList<>();

        PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT" + 
                "    p.id idProduto, " + 
                "    p.descricao descricaoProduto, " + 
                "    p.codigoBarras codigoBarrasProduto, " + 
                "    p.valorVenda valorVendaProduto, " + 
                "    p.estoque estoqueProduto, " +
                "    u.id idUnidadeMedida, " +
                "    u.descricao descricaoUnidadeMedida, " +
                "    u.sigla siglaUnidadeMedida, " +
                "    f.id idFornecedor, " +
                "    f.razaoSocial razaoSocialFornecedor, " +
                "    f.cnpj cnpjFornecedor, " + 
                "    f.email emailFornecedor, " + 
                "    f.logradouro logradouroFornecedor, " + 
                "    f.numero numeroFornecedor, " + 
                "    f.bairro bairroFornecedor, " + 
                "    f.cep cepFornecedor, " +
                "    ci.id idCidade, " + 
                "    ci.nome nomeCidade, " + 
                "    e.id idEstado, " + 
                "    e.nome nomeEstado, " + 
                "    e.sigla siglaEstado " + 
                "FROM" + 
                "    produto p, " + 
                "    unidade_medida u, " +
                "    fornecedor f, " +
                "    cidade ci, " + 
                "    estado e " + 
                "WHERE" + 
                "    p.unidade_medida_id = u.id AND " + 
                "    p.fornecedor_id = f.id AND " + 
                "    f.cidade_id = ci.id AND " + 
                "    ci.estado_id = e.id " +
                "ORDER BY p.descricao;" );

        ResultSet rs = stmt.executeQuery();

        while ( rs.next() ) {

            Produto p = new Produto();
            UnidadeMedida u = new UnidadeMedida();
            Fornecedor f = new Fornecedor();
            Cidade ci = new Cidade();
            Estado e = new Estado();

            p.setId( rs.getLong( "idProduto" ) );
            p.setDescricao( rs.getString( "descricaoProduto" ) );
            p.setCodigoBarras( rs.getString( "codigoBarrasProduto" ) );
            p.setValorVenda( rs.getBigDecimal( "valorVendaProduto" ) );
            p.setEstoque( rs.getBigDecimal( "estoqueProduto" ) );
            p.setUnidadeMedida( u );
            p.setFornecedor( f );
            
            u.setId( rs.getLong( "idUnidadeMedida" ) );
            u.setDescricao( rs.getString( "descricaoUnidadeMedida" ) );
            u.setSigla( rs.getString( "siglaUnidadeMedida" ) );

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

            lista.add( p );

        }

        rs.close();
        stmt.close();

        return lista;

    }

    @Override
    public Produto obterPorId( Long id ) throws SQLException {

        Produto produto = null;

        PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT" + 
                "    p.id idProduto, " + 
                "    p.descricao descricaoProduto, " + 
                "    p.codigoBarras codigoBarrasProduto, " + 
                "    p.valorVenda valorVendaProduto, " + 
                "    p.estoque estoqueProduto, " +
                "    u.id idUnidadeMedida, " +
                "    u.descricao descricaoUnidadeMedida, " +
                "    u.sigla siglaUnidadeMedida, " +
                "    f.id idFornecedor, " +
                "    f.razaoSocial razaoSocialFornecedor, " +
                "    f.cnpj cnpjFornecedor, " + 
                "    f.email emailFornecedor, " + 
                "    f.logradouro logradouroFornecedor, " + 
                "    f.numero numeroFornecedor, " + 
                "    f.bairro bairroFornecedor, " + 
                "    f.cep cepFornecedor, " +
                "    ci.id idCidade, " + 
                "    ci.nome nomeCidade, " + 
                "    e.id idEstado, " + 
                "    e.nome nomeEstado, " + 
                "    e.sigla siglaEstado " + 
                "FROM" + 
                "    produto p, " + 
                "    unidade_medida u, " +
                "    fornecedor f, " +
                "    cidade ci, " + 
                "    estado e " + 
                "WHERE" + 
                "    p.id = ? AND " +
                "    p.unidade_medida_id = u.id AND " + 
                "    p.fornecedor_id = f.id AND " + 
                "    f.cidade_id = ci.id AND " + 
                "    ci.estado_id = e.id;" );

        stmt.setLong( 1, id );

        ResultSet rs = stmt.executeQuery();

        if ( rs.next() ) {

            produto = new Produto();
            UnidadeMedida u = new UnidadeMedida();
            Fornecedor f = new Fornecedor();
            Cidade ci = new Cidade();
            Estado e = new Estado();

            produto.setId( rs.getLong( "idProduto" ) );
            produto.setDescricao( rs.getString( "descricaoProduto" ) );
            produto.setCodigoBarras( rs.getString( "codigoBarrasProduto" ) );
            produto.setValorVenda( rs.getBigDecimal( "valorVendaProduto" ) );
            produto.setEstoque( rs.getBigDecimal( "estoqueProduto" ) );
            produto.setUnidadeMedida( u );
            produto.setFornecedor( f );
            
            u.setId( rs.getLong( "idUnidadeMedida" ) );
            u.setDescricao( rs.getString( "descricaoUnidadeMedida" ) );
            u.setSigla( rs.getString( "siglaUnidadeMedida" ) );

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

        }

        rs.close();
        stmt.close();

        return produto;

    }
    
    /**
     * Atualização do estoque para o cancelamento de vendas.
     */
    public void atualizarEstoque( Produto obj ) throws SQLException {

        PreparedStatement stmt = getConnection().prepareStatement(
                "UPDATE produto " + 
                "SET" + 
                "    estoque = ? " +
                "WHERE" + 
                "    id = ?;" );

        stmt.setBigDecimal( 1, obj.getEstoque() );
        stmt.setLong( 2, obj.getId() );

        stmt.executeUpdate();
        stmt.close();

    }

}
