package vendaprodutos.controladores;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vendaprodutos.dao.FornecedorDAO;
import vendaprodutos.dao.ProdutoDAO;
import vendaprodutos.dao.UnidadeMedidaDAO;
import vendaprodutos.entidades.Fornecedor;
import vendaprodutos.entidades.Produto;
import vendaprodutos.entidades.UnidadeMedida;
import vendaprodutos.utils.Utils;

/**
 * Servlet para tratar Produtos.
 *
 * @author Prof. Dr. David Buzatto
 */
@WebServlet( name = "ProdutosServlet", 
             urlPatterns = { "/processaProdutos" } )
public class ProdutosServlet extends HttpServlet {

    protected void processRequest( 
            HttpServletRequest request, 
            HttpServletResponse response )
            throws ServletException, IOException {
        
        String acao = request.getParameter( "acao" );
        RequestDispatcher disp = null;
        
        try ( ProdutoDAO daoProduto = new ProdutoDAO();
              FornecedorDAO daoFornecedor = new FornecedorDAO();
              UnidadeMedidaDAO daoUnidadeMedida = new UnidadeMedidaDAO() ) {

            if ( acao.equals( "inserir" ) ) {

                String descricao = request.getParameter( "descricao" );
                String codigoBarras = request.getParameter( "codigoBarras" );
                BigDecimal valorVenda = Utils.getBigDecimal( 
                        request, "valorVenda" );
                BigDecimal estoque = Utils.getBigDecimal( 
                        request, "estoque" );
                Long idFornecedor = Utils.getLong( 
                        request, "idFornecedor" );
                Long idUnidadeMedida = Utils.getLong( 
                        request, "idUnidadeMedida" );

                Fornecedor f = daoFornecedor.obterPorId( idFornecedor );
                UnidadeMedida u = daoUnidadeMedida.obterPorId( idUnidadeMedida );

                Produto p = new Produto();
                p.setDescricao( descricao );
                p.setCodigoBarras( codigoBarras );
                p.setValorVenda( valorVenda );
                p.setEstoque( estoque );
                p.setFornecedor( f );
                p.setUnidadeMedida( u );

                Utils.validar( p, "id" );
                daoProduto.salvar( p );
                disp = request.getRequestDispatcher(
                        "/formularios/produtos/listagem.jsp" );

            } else if ( acao.equals( "alterar" ) ) {

                Long id = Utils.getLong( request, "id" );
                String descricao = request.getParameter( "descricao" );
                String codigoBarras = request.getParameter( "codigoBarras" );
                BigDecimal valorVenda = Utils.getBigDecimal( 
                        request, "valorVenda" );
                BigDecimal estoque = Utils.getBigDecimal( 
                        request, "estoque" );
                Long idFornecedor = Utils.getLong( 
                        request, "idFornecedor" );
                Long idUnidadeMedida = Utils.getLong( 
                        request, "idUnidadeMedida" );

                Fornecedor f = daoFornecedor.obterPorId( idFornecedor );
                UnidadeMedida u = daoUnidadeMedida.obterPorId( idUnidadeMedida );

                Produto p = daoProduto.obterPorId( id );
                p.setDescricao( descricao );
                p.setCodigoBarras( codigoBarras );
                p.setValorVenda( valorVenda );
                p.setEstoque( estoque );
                p.setFornecedor( f );
                p.setUnidadeMedida( u );

                Utils.validar( p );
                daoProduto.atualizar( p );
                disp = request.getRequestDispatcher(
                        "/formularios/produtos/listagem.jsp" );

            } else if ( acao.equals( "excluir" ) ) {

                Long id = Utils.getLong( request, "id" );
                Produto p = daoProduto.obterPorId( id );

                daoProduto.excluir( p );
                disp = request.getRequestDispatcher(
                        "/formularios/produtos/listagem.jsp" );

            } else {
                
                Long id = Utils.getLong( request, "id" );
                Produto p = daoProduto.obterPorId( id );
                request.setAttribute( "produto", p );
                
                if ( acao.equals( "prepararAlteracao" ) ) {
                    disp = request.getRequestDispatcher( 
                            "/formularios/produtos/alterar.jsp" );
                } else if ( acao.equals( "prepararExclusao" ) ) {
                    disp = request.getRequestDispatcher( 
                            "/formularios/produtos/excluir.jsp" );
                }
                
            }

        } catch ( SQLException exc ) {
            disp = Utils.prepararDespachoErro( request, exc.getMessage() );
        }

        if ( disp != null ) {
            disp.forward( request, response );
        }
        
    }

    @Override
    protected void doGet( 
            HttpServletRequest request, 
            HttpServletResponse response )
            throws ServletException, IOException {
        processRequest( request, response );
    }

    @Override
    protected void doPost( 
            HttpServletRequest request, 
            HttpServletResponse response )
            throws ServletException, IOException {
        processRequest( request, response );
    }

    @Override
    public String getServletInfo() {
        return "ProdutosServlet";
    }

}
