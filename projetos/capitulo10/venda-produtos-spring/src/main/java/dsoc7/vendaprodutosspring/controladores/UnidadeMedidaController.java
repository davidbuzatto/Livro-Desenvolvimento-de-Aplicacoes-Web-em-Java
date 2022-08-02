/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsoc7.vendaprodutosspring.controladores;

import dsoc7.vendaprodutosspring.entidades.UnidadeMedida;
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
 * Controlador para cadastros de Unidades de Medida.
 * 
 * @author Prof. Dr. David Buzatto
 */
@Controller
@RequestMapping( "/unidadesMedida" )
public class UnidadeMedidaController {
    
    @Autowired
    private UnidadeMedidaRepository unidadeMedidaRepo;
    
    @GetMapping( "/listar" )
    public String listar( Model model ) {
        model.addAttribute( "unidadesMedida", unidadeMedidaRepo.findAll() );
        return "/formularios/unidadesMedida/listagem";
    }
    
    @GetMapping( "/prepararInsercao" )
    public String prepararInsercao( UnidadeMedida unidadeMedida ) {
        return "/formularios/unidadesMedida/inserir";
    }
    
    @PostMapping( "/inserir" )
    public String inserir( @Valid UnidadeMedida unidadeMedida, 
                           BindingResult result, 
                           Model model ) {
        
        if ( result.hasErrors() ) {
            return "/formularios/unidadesMedida/inserir";
        }
        
        unidadeMedidaRepo.save( unidadeMedida );
        return "redirect:/unidadesMedida/listar";
        
    }
    
    @GetMapping( "/prepararAlteracao/{id}" )
    public String prepararAlteracao( @PathVariable( "id" ) Long id, 
                                     Model model ) {
        
        UnidadeMedida unidadeMedida = unidadeMedidaRepo.findById( id )
                .orElseThrow( () -> 
                        new IllegalArgumentException( "Id inválido:" + id ) );
        
        model.addAttribute( "unidadeMedida", unidadeMedida );
        return "/formularios/unidadesMedida/alterar";
        
    }
    
    @PostMapping( "/alterar/{id}" )
    public String alterar( @PathVariable( "id" ) Long id, 
                           @Valid UnidadeMedida unidadeMedida, 
                           BindingResult result,
                           Model model ) {
        
        if ( result.hasErrors() ) {
            unidadeMedida.setId( id );
            return "/formularios/unidadesMedida/alterar";
        }
        
        unidadeMedidaRepo.save( unidadeMedida );
        return "redirect:/unidadesMedida/listar";
        
    }
    
    @GetMapping( "/prepararExclusao/{id}" )
    public String prepararExclusao( @PathVariable( "id" ) Long id, 
                                    Model model ) {
        
        UnidadeMedida unidadeMedida = unidadeMedidaRepo.findById( id )
                .orElseThrow( () -> 
                        new IllegalArgumentException( "Id inválido:" + id ) );
        
        model.addAttribute( "unidadeMedida", unidadeMedida );
        return "/formularios/unidadesMedida/excluir";
        
    }
    
    @PostMapping( "/excluir/{id}" )
    public String excluir( @PathVariable( "id" ) Long id, 
                           @Valid UnidadeMedida unidadeMedida, 
                           BindingResult result,
                           Model model ) {
        
        unidadeMedidaRepo.delete( unidadeMedida );
        return "redirect:/unidadesMedida/listar";
        
    }
    
}
