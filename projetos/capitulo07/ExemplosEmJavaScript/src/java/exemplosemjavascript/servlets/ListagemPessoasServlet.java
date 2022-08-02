package exemplosemjavascript.servlets;

import exemplosemjavascript.pojo.Pessoa;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.Jsonb;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cria uma lista de pessoas, serializa em JSON e
 * retorna ao cliente.
 * 
 * @author Prof. Dr. David Buzatto
 */
@WebServlet( name = "ListagemPessoasServlet",
             urlPatterns = { "/listarPessoas" } )
public class ListagemPessoasServlet extends HttpServlet {

    protected void processRequest( 
            HttpServletRequest request, 
            HttpServletResponse response )
            throws ServletException, IOException {
        
        response.setContentType( "application/json;charset=UTF-8" );
        
        Jsonb jb = JsonbBuilder.create();
        List<Pessoa> pessoas = new ArrayList<>();
        
        int quantidade = Integer.parseInt(
                request.getParameter( "quantidade" ) );
                
        for ( int i = 1; i <= quantidade; i++ ) {
            
            Pessoa p = new Pessoa();
            p.setNome( String.format( "João da Silva %do", i ) );
            
            LocalDate d = LocalDate.now();
            d = d.plus( i, ChronoUnit.DAYS );
            p.setDataNasc( d );
            
            p.setSalario( 1000 * i );
            pessoas.add( p );
            
        }
        
        try ( PrintWriter out = response.getWriter() ) {
            out.print( jb.toJson( pessoas ) );
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
        return "ListagemPessoasServlet";
    }

}
