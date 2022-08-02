package vendaprodutos.controladores;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import vendaprodutos.dao.UnidadeMedidaDAO;
import vendaprodutos.entidades.UnidadeMedida;
import vendaprodutos.utils.Utils;

/**
 * Servlet para tratar Unidades de Medida.
 *
 * @author Prof. Dr. David Buzatto
 */
@WebServlet( name = "UnidadesMedidaServlet", 
             urlPatterns = { "/processaUnidadesMedida" } )
public class UnidadesMedidaServlet extends HttpServlet {

    protected void processRequest( 
            HttpServletRequest request, 
            HttpServletResponse response )
            throws ServletException, IOException {
        
        String acao = request.getParameter( "acao" );
        RequestDispatcher disp = null;

        try ( UnidadeMedidaDAO dao = new UnidadeMedidaDAO() ) {

            if ( acao.equals( "inserir" ) ) {

                String descricao = request.getParameter( "descricao" );
                String sigla = request.getParameter( "sigla" );

                UnidadeMedida u = new UnidadeMedida();
                u.setDescricao( descricao );
                u.setSigla( sigla );

                Utils.validar( u, "id" );
                dao.salvar( u );
                disp = request.getRequestDispatcher(
                        "/formularios/unidadesMedida/listagem.jsp" );

            } else if ( acao.equals( "alterar" ) ) {

                Long id = Utils.getLong( request, "id" );
                String descricao = request.getParameter( "descricao" );
                String sigla = request.getParameter( "sigla" );

                UnidadeMedida u = dao.obterPorId( id );
                u.setDescricao( descricao );
                u.setSigla( sigla );

                Utils.validar( u );
                dao.atualizar( u );
                disp = request.getRequestDispatcher(
                        "/formularios/unidadesMedida/listagem.jsp" );

            } else if ( acao.equals( "excluir" ) ) {

                Long id = Utils.getLong( request, "id" );
                UnidadeMedida u = dao.obterPorId( id );

                dao.excluir( u );
                disp = request.getRequestDispatcher(
                        "/formularios/unidadesMedida/listagem.jsp" );

            } else {
                
                Long id = Utils.getLong( request, "id" );
                UnidadeMedida u = dao.obterPorId( id );
                request.setAttribute( "un", u );
                
                if ( acao.equals( "prepararAlteracao" ) ) {
                    disp = request.getRequestDispatcher( 
                            "/formularios/unidadesMedida/alterar.jsp" );
                } else if ( acao.equals( "prepararExclusao" ) ) {
                    disp = request.getRequestDispatcher( 
                            "/formularios/unidadesMedida/excluir.jsp" );
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
        return "UnidadesMedidaServlet";
    }

}
