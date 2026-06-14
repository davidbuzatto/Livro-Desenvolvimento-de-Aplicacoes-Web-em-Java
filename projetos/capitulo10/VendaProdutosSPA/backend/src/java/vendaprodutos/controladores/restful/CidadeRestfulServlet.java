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
import vendaprodutos.dao.EstadoDAO;
import vendaprodutos.entidades.Cidade;
import vendaprodutos.utils.Utils;

/**
 * Servlet de serviços RESTful para Cidades.
 * 
 * @author Prof. Dr. David Buzatto
 */
@WebServlet( name = "CidadeRestfulServlet", urlPatterns = { "/api/cidades/*" } )
public class CidadeRestfulServlet extends HttpServlet {
    
    private Jsonb jsonb = JsonbBuilder.create();
    
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
        throws ServletException, IOException {
        
        response.setContentType( "application/json" );
        
        String jsonResposta;
        int status;

        try ( CidadeDAO dao = new CidadeDAO() ) {
            
            String pathInfo = request.getPathInfo();
            
            if ( pathInfo != null && pathInfo.length() > 1 ) {
                
                // GET /api/cidades/1 - busca específica
                Long id = Long.valueOf( pathInfo.substring( 1 ) );
                Cidade cidade = dao.obterPorId( id );
                
                if ( cidade != null ) {
                    status = HttpServletResponse.SC_OK;
                    jsonResposta = jsonb.toJson( cidade );
                } else {
                    status = HttpServletResponse.SC_NOT_FOUND;
                    jsonResposta = jsonb.toJson( new Resposta( "Cidade não encontrada.", "" ) );
                }
                
            } else {
                // GET /api/cidades/ - listar todos
                status = HttpServletResponse.SC_OK;
                jsonResposta = jsonb.toJson( dao.listarTodos() );
            }
            
        } catch ( SQLException exc ) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            jsonResposta = jsonb.toJson( new Resposta( "Erro ao obter cidade(s).", exc.getMessage() ) );
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
        
        try ( CidadeDAO dao = new CidadeDAO() ){
            
            Cidade cidade = jsonb.fromJson( 
                Utils.obterDados( request ),
                Cidade.class
            );
            
            Utils.validar( cidade, "id" );
            dao.salvar( cidade );
            
            status = HttpServletResponse.SC_CREATED;
            response.setHeader( "Location", "/api/cidades/" + cidade.getId() );
            jsonResposta = jsonb.toJson( new Resposta( "Cidade inserida com successo!", cidade ) );

        } catch ( SQLException exc ) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            jsonResposta = jsonb.toJson( new Resposta( "Erro ao inserir a cidade.", exc.getMessage() ) );
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
        
        try ( CidadeDAO daoCidade = new CidadeDAO();
              EstadoDAO daoEstado = new EstadoDAO() ){
            
            Cidade cidadeRecebida = jsonb.fromJson( 
                Utils.obterDados( request ),
                Cidade.class
            );

            String pathInfo = request.getPathInfo();
            Long id = Long.valueOf( pathInfo.substring( 1 ) );
            
            Cidade cidade = daoCidade.obterPorId( id );
            cidade.setNome( cidadeRecebida.getNome() );
            
            if ( !cidade.getEstado().equals( cidadeRecebida.getEstado() ) ) {
                cidade.setEstado( daoEstado.obterPorId( cidadeRecebida.getEstado().getId() ) );
            }
        
            Utils.validar( cidade, "id" );
            daoCidade.atualizar( cidade );
            
            status = HttpServletResponse.SC_OK;
            jsonResposta = jsonb.toJson( new Resposta( "Cidade atualizada com successo!", cidade ) );

        } catch ( SQLException exc ) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            jsonResposta = jsonb.toJson( new Resposta( "Erro ao atualizar a cidade.", exc.getMessage() ) );
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
        
        try ( CidadeDAO dao = new CidadeDAO() ){

            String pathInfo = request.getPathInfo();
            Long id = Long.valueOf( pathInfo.substring( 1 ) );
            
            Cidade cidade = dao.obterPorId( id );
            dao.excluir( cidade );
            
            status = HttpServletResponse.SC_OK;
            jsonResposta = jsonb.toJson( new Resposta( "Cidade excluída com successo!", cidade ) );

        } catch ( SQLException exc ) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            jsonResposta = jsonb.toJson( new Resposta( "Erro ao excluir a cidade.", exc.getMessage() ) );
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
        return "Servlet de Serviços RESTful para Cidades";
    }

}
