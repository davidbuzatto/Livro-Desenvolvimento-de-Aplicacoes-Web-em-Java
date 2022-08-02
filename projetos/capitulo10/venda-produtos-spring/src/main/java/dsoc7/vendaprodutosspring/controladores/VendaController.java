package dsoc7.vendaprodutosspring.controladores;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dsoc7.vendaprodutosspring.entidades.ItemVenda;
import dsoc7.vendaprodutosspring.entidades.Produto;
import dsoc7.vendaprodutosspring.entidades.Venda;
import dsoc7.vendaprodutosspring.repositorios.ClienteRepository;
import dsoc7.vendaprodutosspring.repositorios.ItemVendaRepository;
import dsoc7.vendaprodutosspring.repositorios.ProdutoRepository;
import dsoc7.vendaprodutosspring.repositorios.VendaRepository;
import java.time.LocalDate;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controlador para cadastros de Vendas.
 * 
 * @author Prof. Dr. David Buzatto
 */
@Controller
@RequestMapping( "/vendas" )
public class VendaController {
    
    @Autowired
    private VendaRepository vendaRepo;
    
    @Autowired
    private ClienteRepository clienteRepo;
    
    @Autowired
    private ProdutoRepository produtoRepo;
    
    @Autowired
    private ItemVendaRepository itemVendaRepo;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @GetMapping( "/listar" )
    public String listar( Model model ) {
        model.addAttribute( "vendas", vendaRepo.findAll() );
        return "/formularios/vendas/listagem";
    }
    
    @GetMapping( "/prepararInsercao" )
    public String prepararInsercao( Venda venda, Model model ) {
        model.addAttribute( "clientes", clienteRepo.findAll() );
        model.addAttribute( "produtos", produtoRepo.findAll() );
        return "/formularios/vendas/inserir";
    }
    
    @PostMapping( "/inserir" )
    public String inserir( @Valid Venda venda,
                           BindingResult result, 
                           Model model,
                           @RequestParam String itensVenda ) {
        
        if ( result.hasErrors() ) {
            model.addAttribute( "clientes", clienteRepo.findAll() );
            model.addAttribute( "produtos", produtoRepo.findAll() );
            return "/formularios/vendas/inserir";
        }
        
        venda.setData( LocalDate.now() );
        vendaRepo.save( venda );
        
        try {
            
            ItemVenda[] itens = objectMapper.readValue( 
                    itensVenda, ItemVenda[].class );
            
            for ( ItemVenda item : itens ) {
                
                Produto p = produtoRepo.findById( 
                        item.getProduto().getId() ).get();
                p.setEstoque( p.getEstoque().subtract( 
                        item.getQuantidade() ) );
                produtoRepo.save( p ); // atualiza o estoque
                
                item.setVenda( venda );
                item.setProduto( p );
                // quantidade e valor já estão prontos
                
                itemVendaRepo.save( item );
                
            }
            
        } catch ( JacksonException exc ) {
            System.out.println( exc );
        }
        
        
        
        return "redirect:/vendas/listar";
        
    }
    
    @GetMapping( "/cancelar/{id}" )
    public String cancelar( @PathVariable( "id" ) Long id ) {
        
        Venda venda = vendaRepo.findById( id )
                .orElseThrow( () -> 
                        new IllegalArgumentException( "Id inválido:" + id ) );
        
        for ( ItemVenda item : venda.getItensDaVenda() ) {
            Produto p = item.getProduto();
            p.setEstoque( p.getEstoque().add( item.getQuantidade() ) );
            produtoRepo.save( p );
        }
        
        venda.setCancelada( Boolean.TRUE );
        vendaRepo.save( venda );
        
        return "redirect:/vendas/listar";
        
    }
    
}
