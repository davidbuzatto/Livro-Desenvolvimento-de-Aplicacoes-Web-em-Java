package cadastroclientes.controladores.filtros;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Filtro para configurar o encoding das requisições de todos
 * os recursos da aplicação para UTF-8.
 * 
 * @author Prof. Dr. David Buzatto
 */
@WebFilter( filterName = "ConfigurarEncodingFilter", 
            urlPatterns = { "/*" } )
public class ConfigurarEncodingFilter implements Filter {
    
    @Override
    public void doFilter( 
            ServletRequest request,
            ServletResponse response,
            FilterChain chain )
            throws IOException, ServletException {
        
        request.setCharacterEncoding( "UTF-8" );
        chain.doFilter( request, response );
        
    }
    
}
