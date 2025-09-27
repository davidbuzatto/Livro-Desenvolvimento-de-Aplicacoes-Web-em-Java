package vendaprodutos.controladores.restful;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import vendaprodutos.dao.FornecedorDAO;
import vendaprodutos.dao.ProdutoDAO;
import vendaprodutos.dao.UnidadeMedidaDAO;
import vendaprodutos.entidades.Produto;
import vendaprodutos.utils.Utils;

/**
 * Servlet de serviços RESTful para Produtos.
 * 
 * @author Prof. Dr. David Buzatto
 */
@WebServlet( name = "ProdutoRestfulServlet", urlPatterns = { "/api/produtos/*" } )
public class ProdutoRestfulServlet extends HttpServlet {
    
    private Jsonb jsonb = JsonbBuilder.create();
    
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
        throws ServletException, IOException {
        
        response.setContentType( "application/json" );
        response.setCharacterEncoding( "UTF-8" );
        
        String jsonResposta;
        int status;

        try ( ProdutoDAO dao = new ProdutoDAO() ) {
            
            String pathInfo = request.getPathInfo();
            
            if ( pathInfo != null && pathInfo.length() > 1 ) {
                
                // GET /api/produtos/1 - busca específica
                Long id = Long.valueOf( pathInfo.substring( 1 ) );
                Produto produto = dao.obterPorId( id );
                
                if ( produto != null ) {
                    status = HttpServletResponse.SC_OK;
                    jsonResposta = jsonb.toJson( produto );
                } else {
                    status = HttpServletResponse.SC_NOT_FOUND;
                    jsonResposta = jsonb.toJson( new Resposta( "Produto não encontrado.", "" ) );
                }
                
            } else {
                // GET /api/produtos/ - listar todos
                status = HttpServletResponse.SC_OK;
                jsonResposta = jsonb.toJson( dao.listarTodos() );
            }
            
        } catch ( SQLException exc ) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            jsonResposta = jsonb.toJson( new Resposta( "Erro ao obter produto(s).", exc.getMessage() ) );
        } catch ( NumberFormatException exc ) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            jsonResposta = jsonb.toJson( new Resposta( "ID inválido.", exc.getMessage() ) );
        }
        
        try ( PrintWriter out = response.getWriter() ) {
            response.setStatus( status );
            out.write( jsonResposta );
        }
        
    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
        throws ServletException, IOException {
        
        response.setContentType( "application/json" );
        response.setCharacterEncoding( "UTF-8" );
        
        String jsonResposta;
        int status;
        
        try ( ProdutoDAO dao = new ProdutoDAO() ){
            
            Produto produto = jsonb.fromJson( 
                Utils.obterDados( request ),
                Produto.class
            );
            
            Utils.validar( produto, "id" );
            dao.salvar( produto );
            
            status = HttpServletResponse.SC_CREATED;
            response.setHeader( "Location", "/api/produtos/" + produto.getId() );
            jsonResposta = jsonb.toJson( new Resposta( "Produto inserido com successo!", produto ) );

        } catch ( SQLException exc ) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            jsonResposta = jsonb.toJson( new Resposta( "Erro ao inserir o produto.", exc.getMessage() ) );
        }
        
        try ( PrintWriter out = response.getWriter() ) {
            response.setStatus( status );
            out.write( jsonResposta );
        }
        
    }
    
    @Override
    protected void doPut( HttpServletRequest request, HttpServletResponse response )
        throws ServletException, IOException {
        
        response.setContentType( "application/json" );
        response.setCharacterEncoding( "UTF-8" );
        
        String jsonResposta;
        int status;
        
        try ( ProdutoDAO daoProduto = new ProdutoDAO();
              FornecedorDAO daoFornecedor = new FornecedorDAO();
              UnidadeMedidaDAO daoUnidadeMedida = new UnidadeMedidaDAO() ){
            
            Produto produtoRecebido = jsonb.fromJson( 
                Utils.obterDados( request ),
                Produto.class
            );

            String pathInfo = request.getPathInfo();
            Long id = Long.valueOf( pathInfo.substring( 1 ) );
            
            Produto produto = daoProduto.obterPorId( id );
            produto.setDescricao( produtoRecebido.getDescricao() );
            produto.setCodigoBarras( produtoRecebido.getCodigoBarras() );
            produto.setValorVenda( produtoRecebido.getValorVenda() );
            produto.setEstoque( produtoRecebido.getEstoque() );
            
            if ( !produto.getFornecedor().equals( produtoRecebido.getFornecedor() ) ) {
                produto.setFornecedor( daoFornecedor.obterPorId( produtoRecebido.getFornecedor().getId() ) );
            }
            
            if ( !produto.getUnidadeMedida().equals( produtoRecebido.getUnidadeMedida() ) ) {
                produto.setUnidadeMedida( daoUnidadeMedida.obterPorId( produtoRecebido.getUnidadeMedida().getId() ) );
            }
        
            Utils.validar( produto, "id" );
            daoProduto.atualizar( produto );
            
            status = HttpServletResponse.SC_OK;
            jsonResposta = jsonb.toJson( new Resposta( "Produto atualizado com successo!", produto ) );

        } catch ( SQLException exc ) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            jsonResposta = jsonb.toJson( new Resposta( "Erro ao atualizar o produto.", exc.getMessage() ) );
        } catch ( NumberFormatException exc ) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            jsonResposta = jsonb.toJson( new Resposta( "ID inválido.", exc.getMessage() ) );
        }
        
        try ( PrintWriter out = response.getWriter() ) {
            response.setStatus( status );
            out.write( jsonResposta );
        }
        
    }
    
    @Override
    protected void doDelete( HttpServletRequest request, HttpServletResponse response )
        throws ServletException, IOException {
        
        response.setContentType( "application/json" );
        response.setCharacterEncoding( "UTF-8" );
        
        String jsonResposta;
        int status;
        
        try ( ProdutoDAO dao = new ProdutoDAO() ){

            String pathInfo = request.getPathInfo();
            Long id = Long.valueOf( pathInfo.substring( 1 ) );
            
            Produto produto = dao.obterPorId( id );
            dao.excluir( produto );
            
            status = HttpServletResponse.SC_OK;
            jsonResposta = jsonb.toJson( new Resposta( "Produto excluído com successo!", produto ) );

        } catch ( SQLException exc ) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            jsonResposta = jsonb.toJson( new Resposta( "Erro ao excluir o produto.", exc.getMessage() ) );
        } catch ( NumberFormatException exc ) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            jsonResposta = jsonb.toJson( new Resposta( "ID inválido.", exc.getMessage() ) );
        }
        
        try ( PrintWriter out = response.getWriter() ) {
            response.setStatus( status );
            out.write( jsonResposta );
        }
        
    }

    @Override
    public String getServletInfo() {
        return "Servlet de Serviços RESTful para Produtos";
    }

}
