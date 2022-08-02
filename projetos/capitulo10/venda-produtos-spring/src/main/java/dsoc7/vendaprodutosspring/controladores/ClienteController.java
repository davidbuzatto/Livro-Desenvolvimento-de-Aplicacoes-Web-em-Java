package dsoc7.vendaprodutosspring.controladores;

import dsoc7.vendaprodutosspring.entidades.Cliente;
import dsoc7.vendaprodutosspring.repositorios.CidadeRepository;
import dsoc7.vendaprodutosspring.repositorios.ClienteRepository;
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
 * Controlador para cadastros de Clientes.
 * 
 * @author Prof. Dr. David Buzatto
 */
@Controller
@RequestMapping( "/clientes" )
public class ClienteController {
    
    @Autowired
    private ClienteRepository clienteRepo;
    
    @Autowired
    private CidadeRepository cidadeRepo;
    
    @GetMapping( "/listar" )
    public String listar( Model model ) {
        model.addAttribute( "clientes", clienteRepo.findAll() );
        return "/formularios/clientes/listagem";
    }
    
    @GetMapping( "/prepararInsercao" )
    public String prepararInsercao( Cliente cliente, Model model ) {
        model.addAttribute( "cidades", cidadeRepo.findAll() );
        return "/formularios/clientes/inserir";
    }
    
    @PostMapping( "/inserir" )
    public String inserir( @Valid Cliente cliente,
                           BindingResult result, 
                           Model model ) {
        
        if ( result.hasErrors() ) {
            model.addAttribute( "cidades", cidadeRepo.findAll() );
            return "/formularios/clientes/inserir";
        }
        
        clienteRepo.save( cliente );
        return "redirect:/clientes/listar";
        
    }
    
    @GetMapping( "/prepararAlteracao/{id}" )
    public String prepararAlteracao( @PathVariable( "id" ) Long id, 
                                     Model model ) {
        
        Cliente cliente = clienteRepo.findById( id )
                .orElseThrow( () -> 
                        new IllegalArgumentException( "Id inválido:" + id ) );
        
        model.addAttribute( "cidades", cidadeRepo.findAll() );
        model.addAttribute( "cliente", cliente );
        
        return "/formularios/clientes/alterar";
        
    }
    
    @PostMapping( "/alterar/{id}" )
    public String alterar( @PathVariable( "id" ) Long id, 
                           @Valid Cliente cliente, 
                           BindingResult result,
                           Model model ) {
        
        if ( result.hasErrors() ) {
            model.addAttribute( "cidades", cidadeRepo.findAll() );
            cliente.setId( id );
            return "/formularios/clientes/alterar";
        }
        
        clienteRepo.save( cliente );
        return "redirect:/clientes/listar";
        
    }
    
    @GetMapping( "/prepararExclusao/{id}" )
    public String prepararExclusao( @PathVariable( "id" ) Long id, 
                                    Model model ) {
        
        Cliente cliente = clienteRepo.findById( id )
                .orElseThrow( () -> 
                        new IllegalArgumentException( "Id inválido:" + id ) );
        
        model.addAttribute( "cliente", cliente );
        return "/formularios/clientes/excluir";
        
    }
    
    @PostMapping( "/excluir/{id}" )
    public String excluir( @PathVariable( "id" ) Long id, 
                           @Valid Cliente cliente, 
                           BindingResult result,
                           Model model ) {
        
        clienteRepo.delete( cliente );
        return "redirect:/clientes/listar";
        
    }
    
}
