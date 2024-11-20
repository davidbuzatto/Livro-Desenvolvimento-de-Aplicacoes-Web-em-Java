package locacaomidias.controladores;

import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import locacaomidias.dao.ClassificacaoEtariaDAO;
import locacaomidias.entidades.ClassificacaoEtaria;
import locacaomidias.utils.Utils;

/**
 * Servlet para tratar Classificações Etárias.
 *
 * @author Prof. Dr. David Buzatto
 */
@WebServlet( name = "ClassificacaoEtariasServlet", 
             urlPatterns = { "/processaClassificacoesEtarias" } )
public class ClassificacaoEtariaServlet extends HttpServlet {

    protected void processRequest( 
            HttpServletRequest request, 
            HttpServletResponse response )
            throws ServletException, IOException {
        
        String acao = request.getParameter( "acao" );
        RequestDispatcher disp = null;

        try ( ClassificacaoEtariaDAO dao = new ClassificacaoEtariaDAO() ){

            if ( acao.equals( "inserir" ) ) {

                String descricao = request.getParameter( "descricao" );

                ClassificacaoEtaria e = new ClassificacaoEtaria();
                e.setDescricao( descricao );

                Utils.validar( e, "id" );
                dao.salvar( e );
                disp = request.getRequestDispatcher(
                        "/formularios/classificacoesEtarias/listagem.jsp" );

            } else if ( acao.equals( "alterar" ) ) {

                Long id = Utils.getLong( request, "id" );
                String descricao = request.getParameter( "descricao" );

                ClassificacaoEtaria e = dao.obterPorId( id );
                e.setDescricao( descricao );

                Utils.validar( e );
                dao.atualizar( e );
                disp = request.getRequestDispatcher(
                        "/formularios/classificacoesEtarias/listagem.jsp" );

            } else if ( acao.equals( "excluir" ) ) {

                Long id = Utils.getLong( request, "id" );
                ClassificacaoEtaria e = dao.obterPorId( id );

                dao.excluir( e );
                disp = request.getRequestDispatcher(
                        "/formularios/classificacoesEtarias/listagem.jsp" );

            } else {
                
                Long id = Utils.getLong( request, "id" );
                ClassificacaoEtaria e = dao.obterPorId( id );
                request.setAttribute( "classificacaoEtaria", e );
                
                if ( acao.equals( "prepararAlteracao" ) ) {
                    disp = request.getRequestDispatcher( 
                            "/formularios/classificacoesEtarias/alterar.jsp" );
                } else if ( acao.equals( "prepararExclusao" ) ) {
                    disp = request.getRequestDispatcher( 
                            "/formularios/classificacoesEtarias/excluir.jsp" );
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
        return "ClassificacaoEtariasServlet";
    }

}
