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
import vendaprodutos.dao.CidadeDAO;
import vendaprodutos.dao.FornecedorDAO;
import vendaprodutos.entidades.Fornecedor;
import vendaprodutos.utils.Utils;

/**
 * Servlet de serviços RESTful para Fornecedores.
 * 
 * @author Prof. Dr. David Buzatto
 */
@WebServlet( name = "FornecedorRestfulServlet", urlPatterns = { "/api/fornecedores/*" } )
public class FornecedorRestfulServlet extends HttpServlet {
    
    private Jsonb jsonb = JsonbBuilder.create();
    
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
        throws ServletException, IOException {
        
        response.setContentType( "application/json" );
        
        String jsonResposta;
        int status;

        try ( FornecedorDAO dao = new FornecedorDAO() ) {
            
            String pathInfo = request.getPathInfo();
            
            if ( pathInfo != null && pathInfo.length() > 1 ) {
                
                // GET /api/fornecedores/1 - busca específica
                Long id = Long.valueOf( pathInfo.substring( 1 ) );
                Fornecedor fornecedor = dao.obterPorId( id );
                
                if ( fornecedor != null ) {
                    status = HttpServletResponse.SC_OK;
                    jsonResposta = jsonb.toJson( fornecedor );
                } else {
                    status = HttpServletResponse.SC_NOT_FOUND;
                    jsonResposta = jsonb.toJson( new Resposta( "Fornecedor não encontrado.", "" ) );
                }
                
            } else {
                // GET /api/fornecedores/ - listar todos
                status = HttpServletResponse.SC_OK;
                jsonResposta = jsonb.toJson( dao.listarTodos() );
            }
            
        } catch ( SQLException exc ) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            jsonResposta = jsonb.toJson( new Resposta( "Erro ao obter fornecedor(s).", exc.getMessage() ) );
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
        
        String jsonResposta;
        int status;
        
        try ( FornecedorDAO dao = new FornecedorDAO() ){
            
            Fornecedor fornecedor = jsonb.fromJson( 
                Utils.obterDados( request ),
                Fornecedor.class
            );
            
            Utils.validar( fornecedor, "id" );
            dao.salvar( fornecedor );
            
            status = HttpServletResponse.SC_CREATED;
            response.setHeader( "Location", "/api/fornecedores/" + fornecedor.getId() );
            jsonResposta = jsonb.toJson( new Resposta( "Fornecedor inserido com successo!", fornecedor ) );

        } catch ( SQLException exc ) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            jsonResposta = jsonb.toJson( new Resposta( "Erro ao inserir o fornecedor.", exc.getMessage() ) );
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
        
        String jsonResposta;
        int status;
        
        try ( FornecedorDAO daoFornecedor = new FornecedorDAO();
              CidadeDAO daoCidade = new CidadeDAO() ){
            
            Fornecedor fornecedorRecebido = jsonb.fromJson( 
                Utils.obterDados( request ),
                Fornecedor.class
            );

            String pathInfo = request.getPathInfo();
            Long id = Long.valueOf( pathInfo.substring( 1 ) );
            
            Fornecedor fornecedor = daoFornecedor.obterPorId( id );
            fornecedor.setRazaoSocial( fornecedorRecebido.getRazaoSocial() );
            fornecedor.setCnpj( fornecedorRecebido.getCnpj() );
            fornecedor.setEmail( fornecedorRecebido.getEmail() );
            fornecedor.setLogradouro( fornecedorRecebido.getLogradouro() );
            fornecedor.setNumero( fornecedorRecebido.getNumero() );
            fornecedor.setBairro( fornecedorRecebido.getBairro() );
            fornecedor.setCep( fornecedorRecebido.getCep() );
            
            if ( !fornecedor.getCidade().equals( fornecedorRecebido.getCidade() ) ) {
                fornecedor.setCidade( daoCidade.obterPorId( fornecedorRecebido.getCidade().getId() ) );
            }
        
            Utils.validar( fornecedor, "id" );
            daoFornecedor.atualizar( fornecedor );
            
            status = HttpServletResponse.SC_OK;
            jsonResposta = jsonb.toJson( new Resposta( "Fornecedor atualizado com successo!", fornecedor ) );

        } catch ( SQLException exc ) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            jsonResposta = jsonb.toJson( new Resposta( "Erro ao atualizar o fornecedor.", exc.getMessage() ) );
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
        
        String jsonResposta;
        int status;
        
        try ( FornecedorDAO dao = new FornecedorDAO() ){

            String pathInfo = request.getPathInfo();
            Long id = Long.valueOf( pathInfo.substring( 1 ) );
            
            Fornecedor fornecedor = dao.obterPorId( id );
            dao.excluir( fornecedor );
            
            status = HttpServletResponse.SC_OK;
            jsonResposta = jsonb.toJson( new Resposta( "Fornecedor excluído com successo!", fornecedor ) );

        } catch ( SQLException exc ) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            jsonResposta = jsonb.toJson( new Resposta( "Erro ao excluir o fornecedor.", exc.getMessage() ) );
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
        return "Servlet de Serviços RESTful para Fornecedores";
    }

}
