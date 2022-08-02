package olamundoweb.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Nosso primeiro Servlet!
 * 
 * A anotação @WebServlet é usada para indicar que esta classe
 * é um Servlet, configurando seu nome o padrão de URL que
 * será associado à esse componente.
 * 
 * @author Prof. Dr. David Buzatto
 */
@WebServlet( name = "OlaServlet", urlPatterns = { "/ola" } )
public class OlaServlet extends HttpServlet {

    /**
     * Método para tratar requisições que usam o método
     * GET do protocolo HTTP. É um método herdado da
     * classe HttpServlet que precisa ser sobrescrito.
     *
     * A anotação @Override indica ao compilador que 
     * estamos sobrescrevendo um método herdado da
     * classe que está sendo estendida, no caso 
     * HttpServlet.
     *
     * @param request Referência ao objeto que contém
     * os dados da requisição.
     * @param response Referência ao objeto que conterá
     * os dados da resposta.
     *
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet( 
            HttpServletRequest request, 
            HttpServletResponse response ) 
            throws ServletException, IOException {
        
        // chama o método processRequest
        processRequest( request, response );
        
    }

    /**
     * Método para tratar requisições que usam o método 
     * POST do protocolo HTTP.
     * 
     * @param request Referência ao objeto que contém
     * os dados da requisição.
     * @param response Referência ao objeto que conterá
     * os dados da resposta.
     * 
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost( 
            HttpServletRequest request, 
            HttpServletResponse response ) 
            throws ServletException, IOException {
        
        // chama o método processRequest
        processRequest( request, response );
        
    }
    
    protected void processRequest( 
            HttpServletRequest request, 
            HttpServletResponse response ) 
            throws ServletException, IOException {
        
        /* 
         * Aqui vem o código que queremos que o
         * nosso Servlet execute.
         */
        System.out.println( "Olá Mundo!" );
        System.out.println( "Meu Primeiro Servlet!" );
        
    }

}
