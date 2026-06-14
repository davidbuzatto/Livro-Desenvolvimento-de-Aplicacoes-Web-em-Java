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
import vendaprodutos.dao.ClienteDAO;
import vendaprodutos.entidades.Cliente;
import vendaprodutos.utils.Utils;

/**
 * Servlet de serviços RESTful para Clientes.
 * 
 * @author Prof. Dr. David Buzatto
 */
@WebServlet( name = "ClienteRestfulServlet", urlPatterns = { "/api/clientes/*" } )
public class ClienteRestfulServlet extends HttpServlet {
    
    private Jsonb jsonb = JsonbBuilder.create();
    
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
        throws ServletException, IOException {
        
        response.setContentType( "application/json" );
        
        String jsonResposta;
        int status;

        try ( ClienteDAO dao = new ClienteDAO() ) {
            
            String pathInfo = request.getPathInfo();
            
            if ( pathInfo != null && pathInfo.length() > 1 ) {
                
                // GET /api/clientes/1 - busca específica
                Long id = Long.valueOf( pathInfo.substring( 1 ) );
                Cliente cliente = dao.obterPorId( id );
                
                if ( cliente != null ) {
                    status = HttpServletResponse.SC_OK;
                    jsonResposta = jsonb.toJson( cliente );
                } else {
                    status = HttpServletResponse.SC_NOT_FOUND;
                    jsonResposta = jsonb.toJson( new Resposta( "Cliente não encontrado.", "" ) );
                }
                
            } else {
                // GET /api/clientes/ - listar todos
                status = HttpServletResponse.SC_OK;
                jsonResposta = jsonb.toJson( dao.listarTodos() );
            }
            
        } catch ( SQLException exc ) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            jsonResposta = jsonb.toJson( new Resposta( "Erro ao obter cliente(s).", exc.getMessage() ) );
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
        
        try ( ClienteDAO dao = new ClienteDAO() ){
            
            Cliente cliente = jsonb.fromJson( 
                Utils.obterDados( request ),
                Cliente.class
            );
            
            Utils.validar( cliente, "id" );
            dao.salvar( cliente );
            
            status = HttpServletResponse.SC_CREATED;
            response.setHeader( "Location", "/api/clientes/" + cliente.getId() );
            jsonResposta = jsonb.toJson( new Resposta( "Cliente inserido com successo!", cliente ) );

        } catch ( SQLException exc ) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            jsonResposta = jsonb.toJson( new Resposta( "Erro ao inserir o cliente.", exc.getMessage() ) );
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
        
        try ( ClienteDAO daoCliente = new ClienteDAO();
              CidadeDAO daoCidade = new CidadeDAO() ){
            
            Cliente clienteRecebido = jsonb.fromJson( 
                Utils.obterDados( request ),
                Cliente.class
            );
            
            System.out.println( clienteRecebido.getDataNascimento() );

            String pathInfo = request.getPathInfo();
            Long id = Long.valueOf( pathInfo.substring( 1 ) );
            
            Cliente cliente = daoCliente.obterPorId( id );
            cliente.setNome( clienteRecebido.getNome() );
            cliente.setSobrenome( clienteRecebido.getSobrenome() );
            cliente.setDataNascimento( clienteRecebido.getDataNascimento() );
            cliente.setCpf( clienteRecebido.getCpf() );
            cliente.setEmail( clienteRecebido.getEmail() );
            cliente.setLogradouro( clienteRecebido.getLogradouro() );
            cliente.setNumero( clienteRecebido.getNumero() );
            cliente.setBairro( clienteRecebido.getBairro() );
            cliente.setCep( clienteRecebido.getCep() );
            
            if ( !cliente.getCidade().equals( clienteRecebido.getCidade() ) ) {
                cliente.setCidade( daoCidade.obterPorId( clienteRecebido.getCidade().getId() ) );
            }
        
            Utils.validar( cliente, "id" );
            daoCliente.atualizar( cliente );
            
            status = HttpServletResponse.SC_OK;
            jsonResposta = jsonb.toJson( new Resposta( "Cliente atualizado com successo!", cliente ) );

        } catch ( SQLException exc ) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            jsonResposta = jsonb.toJson( new Resposta( "Erro ao atualizar o cliente.", exc.getMessage() ) );
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
        
        try ( ClienteDAO dao = new ClienteDAO() ){

            String pathInfo = request.getPathInfo();
            Long id = Long.valueOf( pathInfo.substring( 1 ) );
            
            Cliente cliente = dao.obterPorId( id );
            dao.excluir( cliente );
            
            status = HttpServletResponse.SC_OK;
            jsonResposta = jsonb.toJson( new Resposta( "Cliente excluído com successo!", cliente ) );

        } catch ( SQLException exc ) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            jsonResposta = jsonb.toJson( new Resposta( "Erro ao excluir o cliente.", exc.getMessage() ) );
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
        return "Servlet de Serviços RESTful para Clientes";
    }

}
