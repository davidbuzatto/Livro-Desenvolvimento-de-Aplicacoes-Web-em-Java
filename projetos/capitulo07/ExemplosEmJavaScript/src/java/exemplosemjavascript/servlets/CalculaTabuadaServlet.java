package exemplosemjavascript.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Calcula a tabuada de 0 a 10 de um valor passado como
 * parâmetro na requisição e retorna o resultado como
 * texto puro.
 * 
 * @author Prof. Dr. David Buzatto
 */
@WebServlet( name = "CalculaTabuadaServlet",
             urlPatterns = { "/calcularTabuada" } )
public class CalculaTabuadaServlet extends HttpServlet {

    protected void processRequest( 
            HttpServletRequest request, 
            HttpServletResponse response )
            throws ServletException, IOException {
        
        // resposta em texto puro codificado em UTF-8
        response.setContentType( "text/html;charset=UTF-8" );
        
        StringBuilder sb = new StringBuilder();
        
        int numero = Integer.parseInt(
                request.getParameter( "numero" ) );
        
        for ( int i = 0; i <= 10; i++ ) {
            sb.append( String.format( "%d * %d = %d<br>", 
                    numero, i, numero * i ) );
        }
        
        // escreve na resposta
        try ( PrintWriter out = response.getWriter() ) {
            out.print( sb.toString() );
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
        return "CalculaTabuadaServlet";
    }

}
