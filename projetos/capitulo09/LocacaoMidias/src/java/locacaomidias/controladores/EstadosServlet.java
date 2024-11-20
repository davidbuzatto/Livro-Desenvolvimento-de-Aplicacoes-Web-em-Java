package locacaomidias.controladores;

import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import locacaomidias.dao.EstadoDAO;
import locacaomidias.entidades.Estado;
import locacaomidias.utils.Utils;

/**
 * Servlet para tratar Estados.
 *
 * @author Prof. Dr. David Buzatto
 */
@WebServlet( name = "EstadosServlet", 
             urlPatterns = { "/processaEstados" } )
public class EstadosServlet extends HttpServlet {

    protected void processRequest( 
            HttpServletRequest request, 
            HttpServletResponse response )
            throws ServletException, IOException {
        
        String acao = request.getParameter( "acao" );
        RequestDispatcher disp = null;

        try ( EstadoDAO dao = new EstadoDAO() ){

            if ( acao.equals( "inserir" ) ) {

                String nome = request.getParameter( "nome" );
                String sigla = request.getParameter( "sigla" );

                Estado e = new Estado();
                e.setNome( nome );
                e.setSigla( sigla );

                Utils.validar( e, "id" );
                dao.salvar( e );
                disp = request.getRequestDispatcher(
                        "/formularios/estados/listagem.jsp" );

            } else if ( acao.equals( "alterar" ) ) {

                Long id = Utils.getLong( request, "id" );
                String nome = request.getParameter( "nome" );
                String sigla = request.getParameter( "sigla" );

                Estado e = dao.obterPorId( id );
                e.setNome( nome );
                e.setSigla( sigla );

                Utils.validar( e );
                dao.atualizar( e );
                disp = request.getRequestDispatcher(
                        "/formularios/estados/listagem.jsp" );

            } else if ( acao.equals( "excluir" ) ) {

                Long id = Utils.getLong( request, "id" );
                Estado e = dao.obterPorId( id );

                dao.excluir( e );
                disp = request.getRequestDispatcher(
                        "/formularios/estados/listagem.jsp" );

            } else {
                
                Long id = Utils.getLong( request, "id" );
                Estado e = dao.obterPorId( id );
                request.setAttribute( "estado", e );
                
                if ( acao.equals( "prepararAlteracao" ) ) {
                    disp = request.getRequestDispatcher( 
                            "/formularios/estados/alterar.jsp" );
                } else if ( acao.equals( "prepararExclusao" ) ) {
                    disp = request.getRequestDispatcher( 
                            "/formularios/estados/excluir.jsp" );
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
        return "EstadosServlet";
    }

}
