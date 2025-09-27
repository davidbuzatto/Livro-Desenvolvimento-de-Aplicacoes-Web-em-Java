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
import vendaprodutos.dao.UnidadeMedidaDAO;
import vendaprodutos.entidades.UnidadeMedida;
import vendaprodutos.utils.Utils;

/**
 * Servlet de serviços RESTful para Unidades de Medida.
 * 
 * @author Prof. Dr. David Buzatto
 */
@WebServlet( name = "UnidadeMedidaRestfulServlet", urlPatterns = { "/api/unidadesMedida/*" } )
public class UnidadeMedidaRestfulServlet extends HttpServlet {
    
    private Jsonb jsonb = JsonbBuilder.create();
    
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
        throws ServletException, IOException {
        
        response.setContentType( "application/json" );
        response.setCharacterEncoding( "UTF-8" );
        
        String jsonResposta;
        int status;

        try ( UnidadeMedidaDAO dao = new UnidadeMedidaDAO() ) {
            
            String pathInfo = request.getPathInfo();
            
            if ( pathInfo != null && pathInfo.length() > 1 ) {
                
                // GET /api/unidadesMedida/1 - busca específica
                Long id = Long.valueOf( pathInfo.substring( 1 ) );
                UnidadeMedida unidadeMedida = dao.obterPorId( id );
                
                if ( unidadeMedida != null ) {
                    status = HttpServletResponse.SC_OK;
                    jsonResposta = jsonb.toJson( unidadeMedida );
                } else {
                    status = HttpServletResponse.SC_NOT_FOUND;
                    jsonResposta = jsonb.toJson( new Resposta( "Unidade de medida não encontrada.", "" ) );
                }
                
            } else {
                // GET /api/unidadesMedida/ - listar todos
                status = HttpServletResponse.SC_OK;
                jsonResposta = jsonb.toJson( dao.listarTodos() );
            }
            
        } catch ( SQLException exc ) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            jsonResposta = jsonb.toJson( new Resposta( "Erro ao obter unidade(s) de medida.", exc.getMessage() ) );
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
        
        try ( UnidadeMedidaDAO dao = new UnidadeMedidaDAO() ){
            
            UnidadeMedida unidadeMedida = jsonb.fromJson( 
                Utils.obterDados( request ),
                UnidadeMedida.class
            );
            
            Utils.validar( unidadeMedida, "id" );
            dao.salvar( unidadeMedida );
            
            status = HttpServletResponse.SC_CREATED;
            response.setHeader( "Location", "/api/unidadesMedida/" + unidadeMedida.getId() );
            jsonResposta = jsonb.toJson( new Resposta( "Unidade de medida inserida com successo!", unidadeMedida ) );

        } catch ( SQLException exc ) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            jsonResposta = jsonb.toJson( new Resposta( "Erro ao inserir a unidade de medida.", exc.getMessage() ) );
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
        
        try ( UnidadeMedidaDAO dao = new UnidadeMedidaDAO() ){
            
            UnidadeMedida unidadeMedidaRecebido = jsonb.fromJson( 
                Utils.obterDados( request ),
                UnidadeMedida.class
            );

            String pathInfo = request.getPathInfo();
            Long id = Long.valueOf( pathInfo.substring( 1 ) );
            
            UnidadeMedida unidadeMedida = dao.obterPorId( id );
            unidadeMedida.setDescricao( unidadeMedidaRecebido.getDescricao() );
            unidadeMedida.setSigla( unidadeMedidaRecebido.getSigla() );
        
            Utils.validar( unidadeMedida, "id" );
            dao.atualizar( unidadeMedida );
            
            status = HttpServletResponse.SC_OK;
            jsonResposta = jsonb.toJson( new Resposta( "Unidade de medida atualizada com successo!", unidadeMedida ) );

        } catch ( SQLException exc ) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            jsonResposta = jsonb.toJson( new Resposta( "Erro ao atualizar a unidade de medida.", exc.getMessage() ) );
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
        
        try ( UnidadeMedidaDAO dao = new UnidadeMedidaDAO() ){

            String pathInfo = request.getPathInfo();
            Long id = Long.valueOf( pathInfo.substring( 1 ) );
            
            UnidadeMedida unidadeMedida = dao.obterPorId( id );
            dao.excluir( unidadeMedida );
            
            status = HttpServletResponse.SC_OK;
            jsonResposta = jsonb.toJson( new Resposta( "UnidadeMedida excluída com successo!", unidadeMedida ) );

        } catch ( SQLException exc ) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            jsonResposta = jsonb.toJson( new Resposta( "Erro ao excluir a unidade de medida.", exc.getMessage() ) );
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
        return "Servlet de Serviços RESTful para Unidades de Medida";
    }

}
