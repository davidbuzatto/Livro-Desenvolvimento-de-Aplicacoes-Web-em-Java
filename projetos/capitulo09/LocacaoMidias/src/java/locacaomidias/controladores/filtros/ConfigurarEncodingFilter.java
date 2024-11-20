package locacaomidias.controladores.filtros;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

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
