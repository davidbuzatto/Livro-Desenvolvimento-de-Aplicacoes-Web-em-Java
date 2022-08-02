package dsoc7.vendaprodutosspring.controladores;

import dsoc7.vendaprodutosspring.entidades.Produto;
import dsoc7.vendaprodutosspring.repositorios.FornecedorRepository;
import dsoc7.vendaprodutosspring.repositorios.ProdutoRepository;
import dsoc7.vendaprodutosspring.repositorios.UnidadeMedidaRepository;
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
 * Controlador para cadastros de Produtos.
 * 
 * @author Prof. Dr. David Buzatto
 */
@Controller
@RequestMapping( "/produtos" )
public class ProdutoController {
    
    @Autowired
    private ProdutoRepository produtoRepo;
    
    @Autowired
    private FornecedorRepository fornecedorRepo;
    
    @Autowired
    private UnidadeMedidaRepository unidadeMedidaRepo;
    
    @GetMapping( "/listar" )
    public String listar( Model model ) {
        model.addAttribute( "produtos", produtoRepo.findAll() );
        return "/formularios/produtos/listagem";
    }
    
    @GetMapping( "/prepararInsercao" )
    public String prepararInsercao( Produto produto, Model model ) {
        model.addAttribute( "fornecedores", fornecedorRepo.findAll() );
        model.addAttribute( "unidadesMedida", unidadeMedidaRepo.findAll() );
        return "/formularios/produtos/inserir";
    }
    
    @PostMapping( "/inserir" )
    public String inserir( @Valid Produto produto, 
                           BindingResult result, 
                           Model model ) {
        
        if ( result.hasErrors() ) {
            model.addAttribute( "fornecedores", fornecedorRepo.findAll() );
            model.addAttribute( "unidadesMedida", unidadeMedidaRepo.findAll() );
            return "/formularios/produtos/inserir";
        }
        
        produtoRepo.save( produto );
        return "redirect:/produtos/listar";
        
    }
    
    @GetMapping( "/prepararAlteracao/{id}" )
    public String prepararAlteracao( @PathVariable( "id" ) Long id, 
                                     Model model ) {
        
        Produto produto = produtoRepo.findById( id )
                .orElseThrow( () -> 
                        new IllegalArgumentException( "Id inválido:" + id ) );
        
        model.addAttribute( "fornecedores", fornecedorRepo.findAll() );
        model.addAttribute( "unidadesMedida", unidadeMedidaRepo.findAll() );
        model.addAttribute( "produto", produto );
        
        return "/formularios/produtos/alterar";
        
    }
    
    @PostMapping( "/alterar/{id}" )
    public String alterar( @PathVariable( "id" ) Long id, 
                           @Valid Produto produto, 
                           BindingResult result,
                           Model model ) {
        
        if ( result.hasErrors() ) {
            model.addAttribute( "fornecedores", fornecedorRepo.findAll() );
            model.addAttribute( "unidadesMedida", unidadeMedidaRepo.findAll() );
            produto.setId( id );
            return "/formularios/produtos/alterar";
        }
        
        Produto produtoBase = produtoRepo.findById( id )
                .orElseThrow( () -> 
                        new IllegalArgumentException( "Id inválido:" + id ) );
        
        produtoBase.setDescricao( produto.getDescricao() );
        produtoBase.setCodigoBarras( produto.getCodigoBarras() );
        produtoBase.setValorVenda( produto.getValorVenda() );
        produtoBase.setEstoque( produto.getEstoque() );
        produtoBase.setFornecedor( produto.getFornecedor() );
        produtoBase.setUnidadeMedida( produto.getUnidadeMedida() );
        
        produtoRepo.save( produtoBase );
        return "redirect:/produtos/listar";
        
    }
    
    @GetMapping( "/prepararExclusao/{id}" )
    public String prepararExclusao( @PathVariable( "id" ) Long id, 
                                    Model model ) {
        
        Produto produto = produtoRepo.findById( id )
                .orElseThrow( () -> 
                        new IllegalArgumentException( "Id inválido:" + id ) );
        
        model.addAttribute( "produto", produto );
        return "/formularios/produtos/excluir";
        
    }
    
    @PostMapping( "/excluir/{id}" )
    public String excluir( @PathVariable( "id" ) Long id, 
                           @Valid Produto produto, 
                           BindingResult result,
                           Model model ) {
        
        produtoRepo.delete( produto );
        return "redirect:/produtos/listar";
        
    }
    
}
