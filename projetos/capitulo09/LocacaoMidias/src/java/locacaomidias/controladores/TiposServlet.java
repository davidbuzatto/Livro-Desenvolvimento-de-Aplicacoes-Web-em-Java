package locacaomidias.controladores;

import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import locacaomidias.dao.TipoDAO;
import locacaomidias.entidades.Tipo;
import locacaomidias.utils.Utils;

/**
 * Servlet para tratar Tipos.
 *
 * @author Prof. Dr. David Buzatto
 */
@WebServlet( name = "TiposServlet", 
             urlPatterns = { "/processaTipos" } )
public class TiposServlet extends HttpServlet {

    protected void processRequest( 
            HttpServletRequest request, 
            HttpServletResponse response )
            throws ServletException, IOException {
        
        String acao = request.getParameter( "acao" );
        RequestDispatcher disp = null;

        try ( TipoDAO dao = new TipoDAO() ){

            if ( acao.equals( "inserir" ) ) {

                String descricao = request.getParameter( "descricao" );

                Tipo e = new Tipo();
                e.setDescricao( descricao );

                Utils.validar( e, "id" );
                dao.salvar( e );
                disp = request.getRequestDispatcher(
                        "/formularios/tipos/listagem.jsp" );

            } else if ( acao.equals( "alterar" ) ) {

                Long id = Utils.getLong( request, "id" );
                String descricao = request.getParameter( "descricao" );

                Tipo e = dao.obterPorId( id );
                e.setDescricao( descricao );

                Utils.validar( e );
                dao.atualizar( e );
                disp = request.getRequestDispatcher(
                        "/formularios/tipos/listagem.jsp" );

            } else if ( acao.equals( "excluir" ) ) {

                Long id = Utils.getLong( request, "id" );
                Tipo e = dao.obterPorId( id );

                dao.excluir( e );
                disp = request.getRequestDispatcher(
                        "/formularios/tipos/listagem.jsp" );

            } else {
                
                Long id = Utils.getLong( request, "id" );
                Tipo e = dao.obterPorId( id );
                request.setAttribute( "tipo", e );
                
                if ( acao.equals( "prepararAlteracao" ) ) {
                    disp = request.getRequestDispatcher( 
                            "/formularios/tipos/alterar.jsp" );
                } else if ( acao.equals( "prepararExclusao" ) ) {
                    disp = request.getRequestDispatcher( 
                            "/formularios/tipos/excluir.jsp" );
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
        return "TiposServlet";
    }

}
