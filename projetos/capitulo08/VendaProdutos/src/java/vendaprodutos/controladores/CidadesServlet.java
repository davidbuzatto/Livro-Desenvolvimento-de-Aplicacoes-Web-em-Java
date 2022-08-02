package vendaprodutos.controladores;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import vendaprodutos.dao.CidadeDAO;
import vendaprodutos.dao.EstadoDAO;
import vendaprodutos.entidades.Cidade;
import vendaprodutos.entidades.Estado;
import vendaprodutos.utils.Utils;

/**
 * Servlet para tratar Cidades.
 *
 * @author Prof. Dr. David Buzatto
 */
@WebServlet( name = "CidadesServlet", 
             urlPatterns = { "/processaCidades" } )
public class CidadesServlet extends HttpServlet {

    protected void processRequest( 
            HttpServletRequest request, 
            HttpServletResponse response )
            throws ServletException, IOException {
        
        String acao = request.getParameter( "acao" );
        RequestDispatcher disp = null;

        try ( CidadeDAO daoCidade = new CidadeDAO();
              EstadoDAO daoEstado = new EstadoDAO() ){

            if ( acao.equals( "inserir" ) ) {

                String nome = request.getParameter( "nome" );
                Long idEstado = Utils.getLong( request, "idEstado" );

                Estado e = daoEstado.obterPorId( idEstado );

                Cidade c = new Cidade();
                c.setNome( nome );
                c.setEstado( e );

                Utils.validar( c, "id" );
                daoCidade.salvar( c );
                disp = request.getRequestDispatcher(
                        "/formularios/cidades/listagem.jsp" );

            } else if ( acao.equals( "alterar" ) ) {

                Long id = Utils.getLong( request, "id" );
                String nome = request.getParameter( "nome" );
                Long idEstado = Utils.getLong( request, "idEstado" );

                Estado e = daoEstado.obterPorId( idEstado );

                Cidade c = daoCidade.obterPorId( id );
                c.setNome( nome );
                c.setEstado( e );

                Utils.validar( c );
                daoCidade.atualizar( c );
                disp = request.getRequestDispatcher(
                        "/formularios/cidades/listagem.jsp" );

            } else if ( acao.equals( "excluir" ) ) {

                Long id = Utils.getLong( request, "id" );
                Cidade c = daoCidade.obterPorId( id );

                daoCidade.excluir( c );
                disp = request.getRequestDispatcher(
                        "/formularios/cidades/listagem.jsp" );

            } else {
                
                Long id = Utils.getLong( request, "id" );
                Cidade c = daoCidade.obterPorId( id );
                request.setAttribute( "cidade", c );
                
                if ( acao.equals( "prepararAlteracao" ) ) {
                    disp = request.getRequestDispatcher( 
                            "/formularios/cidades/alterar.jsp" );
                } else if ( acao.equals( "prepararExclusao" ) ) {
                    disp = request.getRequestDispatcher( 
                            "/formularios/cidades/excluir.jsp" );
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
        return "CidadesServlet";
    }

}
