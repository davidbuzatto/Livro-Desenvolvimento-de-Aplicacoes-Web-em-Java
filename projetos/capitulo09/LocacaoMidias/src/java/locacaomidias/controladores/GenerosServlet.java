package locacaomidias.controladores;

import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import locacaomidias.dao.GeneroDAO;
import locacaomidias.entidades.Genero;
import locacaomidias.utils.Utils;

/**
 * Servlet para tratar Gêneros.
 *
 * @author Prof. Dr. David Buzatto
 */
@WebServlet( name = "GenerosServlet", 
             urlPatterns = { "/processaGeneros" } )
public class GenerosServlet extends HttpServlet {

    protected void processRequest( 
            HttpServletRequest request, 
            HttpServletResponse response )
            throws ServletException, IOException {
        
        String acao = request.getParameter( "acao" );
        RequestDispatcher disp = null;

        try ( GeneroDAO dao = new GeneroDAO() ){

            if ( acao.equals( "inserir" ) ) {

                String descricao = request.getParameter( "descricao" );

                Genero e = new Genero();
                e.setDescricao( descricao );

                Utils.validar( e, "id" );
                dao.salvar( e );
                disp = request.getRequestDispatcher(
                        "/formularios/generos/listagem.jsp" );

            } else if ( acao.equals( "alterar" ) ) {

                Long id = Utils.getLong( request, "id" );
                String descricao = request.getParameter( "descricao" );

                Genero e = dao.obterPorId( id );
                e.setDescricao( descricao );

                Utils.validar( e );
                dao.atualizar( e );
                disp = request.getRequestDispatcher(
                        "/formularios/generos/listagem.jsp" );

            } else if ( acao.equals( "excluir" ) ) {

                Long id = Utils.getLong( request, "id" );
                Genero e = dao.obterPorId( id );

                dao.excluir( e );
                disp = request.getRequestDispatcher(
                        "/formularios/generos/listagem.jsp" );

            } else {
                
                Long id = Utils.getLong( request, "id" );
                Genero e = dao.obterPorId( id );
                request.setAttribute( "genero", e );
                
                if ( acao.equals( "prepararAlteracao" ) ) {
                    disp = request.getRequestDispatcher( 
                            "/formularios/generos/alterar.jsp" );
                } else if ( acao.equals( "prepararExclusao" ) ) {
                    disp = request.getRequestDispatcher( 
                            "/formularios/generos/excluir.jsp" );
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
        return "GenerosServlet";
    }

}
