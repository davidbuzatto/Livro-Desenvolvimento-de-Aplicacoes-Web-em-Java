package primeiroformulario.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet para processamento dos dados do formulário.
 * 
 * @author Prof. Dr. David Buzatto
 */
@WebServlet( name = "ProcessaDadosClienteServlet",
             urlPatterns = { "/processaDadosCliente" } )
public class ProcessaDadosClienteServlet extends HttpServlet {

    protected void processRequest( 
            HttpServletRequest request, 
            HttpServletResponse response )
            throws ServletException, IOException {
        
        /* Precisamos manter de forma consistente
         * o mesmo encoding em todas as camadas da
         * aplicação, evitando assim problemas com
         * caracteres acentuados. Aqui informamos
         * que a requisição está chegando codificada
         * em UTF-8.
         */
        request.setCharacterEncoding( "UTF-8" );
        
        /* Obtém os dados do request.
         * O método getParameter de request obtém
         * um parâmetro enviado pelo formulário
         * que acessou o Servlet.
         *
         * O parâmetro tem SEMPRE o mesmo nome configurado
         * na propriedade "name" do componente do formulário.
         */
        String nome = request.getParameter( "nome" );
        String sobrenome = request.getParameter( "sobrenome" );
        String CPF = request.getParameter( "cpf" );
        String dataNascimento = request.getParameter( "dataNasc" );
        String sexo = request.getParameter( "sexo" );
        String observacoes = request.getParameter( "observacoes" );

        System.out.println( "Dados do Cliente:" );
        System.out.println( "Nome: " + nome );
        System.out.println( "Sobrenome: " + sobrenome );
        System.out.println( "CPF: " + CPF );
        System.out.println( "Data de Nascimento: " + dataNascimento );

        if ( sexo.equals( "M" ) ) {
            System.out.println( "Sexo: Masculino" );
        } else {
            System.out.println( "Sexo: Feminino" );
        }

        System.out.println( "Observações: " + observacoes );
        
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
        return "ProcessaDadosClienteServlet";
    }

}
