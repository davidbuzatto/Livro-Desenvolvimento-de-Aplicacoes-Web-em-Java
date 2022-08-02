package dsoc7.vendaprodutosspring.controladores;

import dsoc7.vendaprodutosspring.entidades.Estado;
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
 * Controlador para cadastros de Estados.
 * 
 * @author Prof. Dr. David Buzatto
 */
@Controller
@RequestMapping( "/estados" )
public class EstadoController {
    
    @Autowired
    private EstadoRepository estadoRepo;
    
    @GetMapping( "/listar" )
    public String listar( Model model ) {
        model.addAttribute( "estados", estadoRepo.findAll() );
        return "/formularios/estados/listagem";
    }
    
    @GetMapping( "/prepararInsercao" )
    public String prepararInsercao( Estado estado ) {
        return "/formularios/estados/inserir";
    }
    
    @PostMapping( "/inserir" )
    public String inserir( @Valid Estado estado, 
                           BindingResult result, 
                           Model model ) {
        
        if ( result.hasErrors() ) {
            return "/formularios/estados/inserir";
        }
        
        estadoRepo.save( estado );
        return "redirect:/estados/listar";
        
    }
    
    @GetMapping( "/prepararAlteracao/{id}" )
    public String prepararAlteracao( @PathVariable( "id" ) Long id, 
                                     Model model ) {
        
        Estado estado = estadoRepo.findById( id )
                .orElseThrow( () -> 
                        new IllegalArgumentException( "Id inválido:" + id ) );
        
        model.addAttribute( "estado", estado );
        return "/formularios/estados/alterar";
        
    }
    
    @PostMapping( "/alterar/{id}" )
    public String alterar( @PathVariable( "id" ) Long id, 
                           @Valid Estado estado, 
                           BindingResult result,
                           Model model ) {
        
        if ( result.hasErrors() ) {
            estado.setId( id );
            return "/formularios/estados/alterar";
        }
        
        estadoRepo.save( estado );
        return "redirect:/estados/listar";
        
    }
    
    @GetMapping( "/prepararExclusao/{id}" )
    public String prepararExclusao( @PathVariable( "id" ) Long id, 
                                    Model model ) {
        
        Estado estado = estadoRepo.findById( id )
                .orElseThrow( () -> 
                        new IllegalArgumentException( "Id inválido:" + id ) );
        
        model.addAttribute( "estado", estado );
        return "/formularios/estados/excluir";
        
    }
    
    @PostMapping( "/excluir/{id}" )
    public String excluir( @PathVariable( "id" ) Long id, 
                           @Valid Estado estado, 
                           BindingResult result,
                           Model model ) {
        
        estadoRepo.delete( estado );
        return "redirect:/estados/listar";
        
    }
    
}
