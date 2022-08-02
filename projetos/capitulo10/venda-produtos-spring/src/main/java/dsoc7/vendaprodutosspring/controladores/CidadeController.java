package dsoc7.vendaprodutosspring.controladores;

import dsoc7.vendaprodutosspring.entidades.Cidade;
import dsoc7.vendaprodutosspring.repositorios.CidadeRepository;
import dsoc7.vendaprodutosspring.repositorios.EstadoRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador para cadastros de Cidades.
 * 
 * @author Prof. Dr. David Buzatto
 */
@Controller
@RequestMapping( "/cidades" )
public class CidadeController {
    
    @Autowired
    private CidadeRepository cidadeRepo;
    
    @Autowired
    private EstadoRepository estadoRepo;
    
    @GetMapping( "/listar" )
    public String listar( Model model ) {
        model.addAttribute( "cidades", cidadeRepo.findAll() );
        return "/formularios/cidades/listagem";
    }
    
    @GetMapping( "/prepararInsercao" )
    public String prepararInsercao( Cidade cidade, Model model ) {
        model.addAttribute( "estados", estadoRepo.findAll() );
        return "/formularios/cidades/inserir";
    }
    
    @PostMapping( "/inserir" )
    public String inserir( @Valid Cidade cidade,
                           BindingResult result, 
                           Model model ) {
        
        if ( result.hasErrors() ) {
            model.addAttribute( "estados", estadoRepo.findAll() );
            return "/formularios/cidades/inserir";
        }
        
        cidadeRepo.save( cidade );
        return "redirect:/cidades/listar";
        
    }
    
    @GetMapping( "/prepararAlteracao/{id}" )
    public String prepararAlteracao( @PathVariable( "id" ) Long id, 
                                     Model model ) {
        
        Cidade cidade = cidadeRepo.findById( id )
                .orElseThrow( () -> 
                        new IllegalArgumentException( "Id inválido:" + id ) );
        
        model.addAttribute( "estados", estadoRepo.findAll() );
        model.addAttribute( "cidade", cidade );
        
        return "/formularios/cidades/alterar";
        
    }
    
    @PostMapping( "/alterar/{id}" )
    public String alterar( @PathVariable( "id" ) Long id, 
                           @Valid Cidade cidade, 
                           BindingResult result,
                           Model model ) {
        
        if ( result.hasErrors() ) {
            model.addAttribute( "estados", estadoRepo.findAll() );
            cidade.setId( id );
            return "/formularios/cidades/alterar";
        }
        
        cidadeRepo.save( cidade );
        return "redirect:/cidades/listar";
        
    }
    
    @GetMapping( "/prepararExclusao/{id}" )
    public String prepararExclusao( @PathVariable( "id" ) Long id, 
                                    Model model ) {
        
        Cidade cidade = cidadeRepo.findById( id )
                .orElseThrow( () -> 
                        new IllegalArgumentException( "Id inválido:" + id ) );
        
        model.addAttribute( "cidade", cidade );
        return "/formularios/cidades/excluir";
        
    }
    
    @PostMapping( "/excluir/{id}" )
    public String excluir( @PathVariable( "id" ) Long id, 
                           @Valid Cidade cidade, 
                           BindingResult result,
                           Model model ) {
        
        cidadeRepo.delete( cidade );
        return "redirect:/cidades/listar";
        
    }
    
}
