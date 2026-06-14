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
import vendaprodutos.dao.EstadoDAO;
import vendaprodutos.entidades.Estado;
import vendaprodutos.utils.Utils;

/**
 * Servlet de serviços RESTful para Estados.
 * 
 * @author Prof. Dr. David Buzatto
 */
@WebServlet( name = "EstadoRestfulServlet", urlPatterns = { "/api/estados/*" } )
public class EstadoRestfulServlet extends HttpServlet {
    
    private Jsonb jsonb = JsonbBuilder.create();
    
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
        throws ServletException, IOException {
        
        response.setContentType( "application/json" );
        
        String jsonResposta;
        int status;

        try ( EstadoDAO dao = new EstadoDAO() ) {
            
            String pathInfo = request.getPathInfo();
            
            if ( pathInfo != null && pathInfo.length() > 1 ) {
                
                // GET /api/estados/1 - busca específica
                Long id = Long.valueOf( pathInfo.substring( 1 ) );
                Estado estado = dao.obterPorId( id );
                
                if ( estado != null ) {
                    status = HttpServletResponse.SC_OK;
                    jsonResposta = jsonb.toJson( estado );
                } else {
                    status = HttpServletResponse.SC_NOT_FOUND;
                    jsonResposta = jsonb.toJson( new Resposta( "Estado não encontrado.", "" ) );
                }
                
            } else {
                // GET /api/estados/ - listar todos
                status = HttpServletResponse.SC_OK;
                jsonResposta = jsonb.toJson( dao.listarTodos() );
            }
            
        } catch ( SQLException exc ) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            jsonResposta = jsonb.toJson( new Resposta( "Erro ao obter estado(s).", exc.getMessage() ) );
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
        
        try ( EstadoDAO dao = new EstadoDAO() ){
            
            Estado estado = jsonb.fromJson( 
                Utils.obterDados( request ),
                Estado.class
            );
            
            Utils.validar( estado, "id" );
            dao.salvar( estado );
            
            status = HttpServletResponse.SC_CREATED;
            response.setHeader( "Location", "/api/estados/" + estado.getId() );
            jsonResposta = jsonb.toJson( new Resposta( "Estado inserido com successo!", estado ) );

        } catch ( SQLException exc ) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            jsonResposta = jsonb.toJson( new Resposta( "Erro ao inserir o estado.", exc.getMessage() ) );
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
        
        try ( EstadoDAO dao = new EstadoDAO() ){
            
            Estado estadoRecebido = jsonb.fromJson( 
                Utils.obterDados( request ),
                Estado.class
            );

            String pathInfo = request.getPathInfo();
            Long id = Long.valueOf( pathInfo.substring( 1 ) );
            
            Estado estado = dao.obterPorId( id );
            estado.setNome( estadoRecebido.getNome() );
            estado.setSigla( estadoRecebido.getSigla() );
        
            Utils.validar( estado, "id" );
            dao.atualizar( estado );
            
            status = HttpServletResponse.SC_OK;
            jsonResposta = jsonb.toJson( new Resposta( "Estado atualizado com successo!", estado ) );

        } catch ( SQLException exc ) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            jsonResposta = jsonb.toJson( new Resposta( "Erro ao atualizar o estado.", exc.getMessage() ) );
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
        
        try ( EstadoDAO dao = new EstadoDAO() ){

            String pathInfo = request.getPathInfo();
            Long id = Long.valueOf( pathInfo.substring( 1 ) );
            
            Estado estado = dao.obterPorId( id );
            dao.excluir( estado );
            
            status = HttpServletResponse.SC_OK;
            jsonResposta = jsonb.toJson( new Resposta( "Estado excluído com successo!", estado ) );

        } catch ( SQLException exc ) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            jsonResposta = jsonb.toJson( new Resposta( "Erro ao excluir o estado.", exc.getMessage() ) );
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
        return "Servlet de Serviços RESTful para Estados";
    }

}
