package dsoc7.vendaprodutosspring.controladores;

import dsoc7.vendaprodutosspring.entidades.Fornecedor;
import dsoc7.vendaprodutosspring.repositorios.CidadeRepository;
import dsoc7.vendaprodutosspring.repositorios.FornecedorRepository;
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
 * Controlador para cadastros de Fornecedores.
 * 
 * @author Prof. Dr. David Buzatto
 */
@Controller
@RequestMapping( "/fornecedores" )
public class FornecedorController {
    
    @Autowired
    private FornecedorRepository fornecedorRepo;
    
    @Autowired
    private CidadeRepository cidadeRepo;
    
    @GetMapping( "/listar" )
    public String listar( Model model ) {
        model.addAttribute( "fornecedores", fornecedorRepo.findAll() );
        return "/formularios/fornecedores/listagem";
    }
    
    @GetMapping( "/prepararInsercao" )
    public String prepararInsercao( Fornecedor fornecedor, Model model ) {
        model.addAttribute( "cidades", cidadeRepo.findAll() );
        return "/formularios/fornecedores/inserir";
    }
    
    @PostMapping( "/inserir" )
    public String inserir( @Valid Fornecedor fornecedor,
                           BindingResult result, 
                           Model model ) {
        
        if ( result.hasErrors() ) {
            model.addAttribute( "cidades", cidadeRepo.findAll() );
            return "/formularios/fornecedores/inserir";
        }
        
        fornecedorRepo.save( fornecedor );
        return "redirect:/fornecedores/listar";
        
    }
    
    @GetMapping( "/prepararAlteracao/{id}" )
    public String prepararAlteracao( @PathVariable( "id" ) Long id, 
                                     Model model ) {
        
        Fornecedor fornecedor = fornecedorRepo.findById( id )
                .orElseThrow( () -> 
                        new IllegalArgumentException( "Id inválido:" + id ) );
        
        model.addAttribute( "cidades", cidadeRepo.findAll() );
        model.addAttribute( "fornecedor", fornecedor );
        
        return "/formularios/fornecedores/alterar";
        
    }
    
    @PostMapping( "/alterar/{id}" )
    public String alterar( @PathVariable( "id" ) Long id, 
                           @Valid Fornecedor fornecedor, 
                           BindingResult result,
                           Model model ) {
        
        if ( result.hasErrors() ) {
            model.addAttribute( "cidades", cidadeRepo.findAll() );
            fornecedor.setId( id );
            return "/formularios/fornecedores/alterar";
        }
        
        fornecedorRepo.save( fornecedor );
        return "redirect:/fornecedores/listar";
        
    }
    
    @GetMapping( "/prepararExclusao/{id}" )
    public String prepararExclusao( @PathVariable( "id" ) Long id, 
                                    Model model ) {
        
        Fornecedor fornecedor = fornecedorRepo.findById( id )
                .orElseThrow( () -> 
                        new IllegalArgumentException( "Id inválido:" + id ) );
        
        model.addAttribute( "fornecedor", fornecedor );
        return "/formularios/fornecedores/excluir";
        
    }
    
    @PostMapping( "/excluir/{id}" )
    public String excluir( @PathVariable( "id" ) Long id, 
                           @Valid Fornecedor fornecedor, 
                           BindingResult result,
                           Model model ) {
        
        fornecedorRepo.delete( fornecedor );
        return "redirect:/fornecedores/listar";
        
    }
    
}
