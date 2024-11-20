package locacaomidias.controladores;

import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import locacaomidias.dao.ClassificacaoInternaDAO;
import locacaomidias.entidades.ClassificacaoInterna;
import locacaomidias.utils.Utils;

/**
 * Servlet para tratar Classificações Etárias.
 *
 * @author Prof. Dr. David Buzatto
 */
@WebServlet( name = "ClassificacaoInternasServlet", 
             urlPatterns = { "/processaClassificacoesInternas" } )
public class ClassificacaoInternaServlet extends HttpServlet {

    protected void processRequest( 
            HttpServletRequest request, 
            HttpServletResponse response )
            throws ServletException, IOException {
        
        String acao = request.getParameter( "acao" );
        RequestDispatcher disp = null;

        try ( ClassificacaoInternaDAO dao = new ClassificacaoInternaDAO() ){

            if ( acao.equals( "inserir" ) ) {

                String descricao = request.getParameter( "descricao" );
                BigDecimal valorAluguel = Utils.getBigDecimal( 
                        request, "valorAluguel" );

                ClassificacaoInterna e = new ClassificacaoInterna();
                e.setDescricao( descricao );
                e.setValorAluguel( valorAluguel );

                Utils.validar( e, "id" );
                dao.salvar( e );
                disp = request.getRequestDispatcher(
                        "/formularios/classificacoesInternas/listagem.jsp" );

            } else if ( acao.equals( "alterar" ) ) {

                Long id = Utils.getLong( request, "id" );
                String descricao = request.getParameter( "descricao" );
                BigDecimal valorAluguel = Utils.getBigDecimal( 
                        request, "valorAluguel" );

                ClassificacaoInterna e = dao.obterPorId( id );
                e.setDescricao( descricao );
                e.setValorAluguel( valorAluguel );

                Utils.validar( e );
                dao.atualizar( e );
                disp = request.getRequestDispatcher(
                        "/formularios/classificacoesInternas/listagem.jsp" );

            } else if ( acao.equals( "excluir" ) ) {

                Long id = Utils.getLong( request, "id" );
                ClassificacaoInterna e = dao.obterPorId( id );

                dao.excluir( e );
                disp = request.getRequestDispatcher(
                        "/formularios/classificacoesInternas/listagem.jsp" );

            } else {
                
                Long id = Utils.getLong( request, "id" );
                ClassificacaoInterna e = dao.obterPorId( id );
                request.setAttribute( "classificacaoInterna", e );
                
                if ( acao.equals( "prepararAlteracao" ) ) {
                    disp = request.getRequestDispatcher( 
                            "/formularios/classificacoesInternas/alterar.jsp" );
                } else if ( acao.equals( "prepararExclusao" ) ) {
                    disp = request.getRequestDispatcher( 
                            "/formularios/classificacoesInternas/excluir.jsp" );
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
        return "ClassificacaoInternasServlet";
    }

}
