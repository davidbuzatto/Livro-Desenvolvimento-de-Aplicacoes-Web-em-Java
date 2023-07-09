package vendaprodutos.controladores;

import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vendaprodutos.dao.CidadeDAO;
import vendaprodutos.dao.FornecedorDAO;
import vendaprodutos.entidades.Cidade;
import vendaprodutos.entidades.Fornecedor;
import vendaprodutos.utils.Utils;

/**
 * Servlet para tratar Fornecedores.
 *
 * @author Prof. Dr. David Buzatto
 */
@WebServlet( name = "FornecedoresServlet", 
             urlPatterns = { "/processaFornecedores" } )
public class FornecedoresServlet extends HttpServlet {

    protected void processRequest( 
            HttpServletRequest request, 
            HttpServletResponse response )
            throws ServletException, IOException {
        
        String acao = request.getParameter( "acao" );
        RequestDispatcher disp = null;
        
        try ( FornecedorDAO daoFornecedor = new FornecedorDAO();
              CidadeDAO daoCidade = new CidadeDAO() ) {

            if ( acao.equals( "inserir" ) ) {

                String razaoSocial = request.getParameter( "razaoSocial" );
                String cnpj = request.getParameter( "cnpj" );
                String email = request.getParameter( "email" );
                String logradouro = request.getParameter( "logradouro" );
                String numero = request.getParameter( "numero" );
                String bairro = request.getParameter( "bairro" );
                String cep = request.getParameter( "cep" );
                Long idCidade = Utils.getLong( request, "idCidade" );

                Cidade ci = daoCidade.obterPorId( idCidade );

                Fornecedor f = new Fornecedor();
                f.setRazaoSocial( razaoSocial );
                f.setCnpj( cnpj );
                f.setEmail( email );
                f.setLogradouro( logradouro );
                f.setNumero( numero );
                f.setBairro( bairro );
                f.setCep( cep );
                f.setCidade( ci );

                Utils.validar( f, "id" );
                daoFornecedor.salvar( f );
                disp = request.getRequestDispatcher(
                        "/formularios/fornecedores/listagem.jsp" );

            } else if ( acao.equals( "alterar" ) ) {

                Long id = Utils.getLong( request, "id" );
                String razaoSocial = request.getParameter( "razaoSocial" );
                String cnpj = request.getParameter( "cnpj" );
                String email = request.getParameter( "email" );
                String logradouro = request.getParameter( "logradouro" );
                String numero = request.getParameter( "numero" );
                String bairro = request.getParameter( "bairro" );
                String cep = request.getParameter( "cep" );
                Long idCidade = Utils.getLong( request, "idCidade" );

                Cidade ci = daoCidade.obterPorId( idCidade );

                Fornecedor f = daoFornecedor.obterPorId( id );
                f.setRazaoSocial( razaoSocial );
                f.setCnpj( cnpj );
                f.setEmail( email );
                f.setLogradouro( logradouro );
                f.setNumero( numero );
                f.setBairro( bairro );
                f.setCep( cep );
                f.setCidade( ci );

                Utils.validar( f );
                daoFornecedor.atualizar( f );
                disp = request.getRequestDispatcher(
                        "/formularios/fornecedores/listagem.jsp" );

            } else if ( acao.equals( "excluir" ) ) {

                Long id = Utils.getLong( request, "id" );
                Fornecedor f = daoFornecedor.obterPorId( id );

                daoFornecedor.excluir( f );
                disp = request.getRequestDispatcher(
                        "/formularios/fornecedores/listagem.jsp" );

            } else {
                
                Long id = Utils.getLong( request, "id" );
                Fornecedor f = daoFornecedor.obterPorId( id );
                request.setAttribute( "fornecedor", f );
                
                if ( acao.equals( "prepararAlteracao" ) ) {
                    disp = request.getRequestDispatcher( 
                            "/formularios/fornecedores/alterar.jsp" );
                } else if ( acao.equals( "prepararExclusao" ) ) {
                    disp = request.getRequestDispatcher( 
                            "/formularios/fornecedores/excluir.jsp" );
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
        return "FornecedoresServlet";
    }

}
