package vendaprodutos.controladores.filtros;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro para habilitar CORS nos serviços RESTful.
 * 
 * @author Prof. Dr. David Buzatto
 */
@WebFilter( filterName = "CORSFilter", urlPatterns = { "/api/*" } )
public class CORSFilter implements Filter {
    
    private static final boolean BLOQUEAR_POR_DOMINIO = false;
    
    @Override
    public void doFilter( 
            ServletRequest request,
            ServletResponse response,
            FilterChain chain )
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
                
        // configurar cabeçalhos CORS
        if ( !BLOQUEAR_POR_DOMINIO ) {
            httpResponse.setHeader( "Access-Control-Allow-Origin", "*" );
        } else {
            String origin = httpRequest.getHeader( "Origin" );
            if ( origin != null && ( origin.equals( "http://localhost:8080" ) || origin.equals( "http://www.outrodominio.com" ) ) ) {
                httpResponse.setHeader( "Access-Control-Allow-Origin", origin );
            }
        }
        httpResponse.setHeader( "Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS" );
        httpResponse.setHeader( "Access-Control-Allow-Headers", "Content-Type, Authorization" );
        httpResponse.setHeader( "Access-Control-Max-Age", "3600" );
        
        // se for uma requisição OPTIONS (preflight), retornar OK.
        if ( httpRequest.getMethod().equalsIgnoreCase( "OPTIONS" ) ) {
            httpResponse.setStatus( HttpServletResponse.SC_OK );
            return;
        }
        
        // continuar com a cadeia de filtros
        chain.doFilter( request, response );
        
    }
    
}
