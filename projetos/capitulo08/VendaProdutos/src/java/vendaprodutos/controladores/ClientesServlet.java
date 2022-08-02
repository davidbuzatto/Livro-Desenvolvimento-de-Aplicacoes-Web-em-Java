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
import vendaprodutos.dao.ClienteDAO;
import vendaprodutos.entidades.Cidade;
import vendaprodutos.entidades.Cliente;
import vendaprodutos.utils.Utils;

/**
 * Servlet para tratar Clientes.
 *
 * @author Prof. Dr. David Buzatto
 */
@WebServlet( name = "ClientesServlet", 
             urlPatterns = { "/processaClientes" } )
public class ClientesServlet extends HttpServlet {

    protected void processRequest( 
            HttpServletRequest request, 
            HttpServletResponse response )
            throws ServletException, IOException {
        
        String acao = request.getParameter( "acao" );
        RequestDispatcher disp = null;
        
        try ( ClienteDAO daoCliente = new ClienteDAO();
              CidadeDAO daoCidade = new CidadeDAO() ) {

            if ( acao.equals( "inserir" ) ) {

                String nome = request.getParameter( "nome" );
                String sobrenome = request.getParameter( "sobrenome" );
                String dataNascimento = request.getParameter( "dataNascimento" );
                String cpf = request.getParameter( "cpf" );
                String email = request.getParameter( "email" );
                String logradouro = request.getParameter( "logradouro" );
                String numero = request.getParameter( "numero" );
                String bairro = request.getParameter( "bairro" );
                String cep = request.getParameter( "cep" );
                Long idCidade = Utils.getLong( request, "idCidade" );

                Cidade ci = daoCidade.obterPorId( idCidade );

                Cliente c = new Cliente();
                c.setNome( nome );
                c.setSobrenome( sobrenome );
                c.setDataNascimento( Utils.getDate( dataNascimento ) );
                c.setCpf( cpf );
                c.setEmail( email );
                c.setLogradouro( logradouro );
                c.setNumero( numero );
                c.setBairro( bairro );
                c.setCep( cep );
                c.setCidade( ci );

                Utils.validar( c, "id" );
                daoCliente.salvar( c );
                disp = request.getRequestDispatcher(
                        "/formularios/clientes/listagem.jsp" );

            } else if ( acao.equals( "alterar" ) ) {

                Long id = Utils.getLong( request, "id" );
                String nome = request.getParameter( "nome" );
                String sobrenome = request.getParameter( "sobrenome" );
                String dataNascimento = request.getParameter( "dataNascimento" );
                String cpf = request.getParameter( "cpf" );
                String email = request.getParameter( "email" );
                String logradouro = request.getParameter( "logradouro" );
                String numero = request.getParameter( "numero" );
                String bairro = request.getParameter( "bairro" );
                String cep = request.getParameter( "cep" );
                Long idCidade = Utils.getLong( request, "idCidade" );

                Cidade ci = daoCidade.obterPorId( idCidade );

                Cliente c = daoCliente.obterPorId( id );
                c.setNome( nome );
                c.setSobrenome( sobrenome );
                c.setDataNascimento( Utils.getDate( dataNascimento ) );
                c.setCpf( cpf );
                c.setEmail( email );
                c.setLogradouro( logradouro );
                c.setNumero( numero );
                c.setBairro( bairro );
                c.setCep( cep );
                c.setCidade( ci );

                Utils.validar( c );
                daoCliente.atualizar( c );
                disp = request.getRequestDispatcher(
                        "/formularios/clientes/listagem.jsp" );

            } else if ( acao.equals( "excluir" ) ) {

                Long id = Utils.getLong( request, "id" );
                Cliente c = daoCliente.obterPorId( id );

                daoCliente.excluir( c );
                disp = request.getRequestDispatcher(
                        "/formularios/clientes/listagem.jsp" );

            } else {
                
                Long id = Utils.getLong( request, "id" );
                Cliente c = daoCliente.obterPorId( id );
                request.setAttribute( "cliente", c );
                
                if ( acao.equals( "prepararAlteracao" ) ) {
                    disp = request.getRequestDispatcher( 
                            "/formularios/clientes/alterar.jsp" );
                } else if ( acao.equals( "prepararExclusao" ) ) {
                    disp = request.getRequestDispatcher( 
                            "/formularios/clientes/excluir.jsp" );
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
        return "ClientesServlet";
    }

}
