package dsoc7.vendaprodutosspring.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Classe de configuração de views.
 *
 * @author Prof. Dr. David Buzatto
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addViewControllers( ViewControllerRegistry registry ) {
        registry.addViewController( "/index" ).setViewName( "index" );
        registry.addViewController( "/index.html" ).setViewName( "index" );
    }
    
}
