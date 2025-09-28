package vendaprodutos.controladores.restful;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import vendaprodutos.dao.ClienteDAO;
import vendaprodutos.dao.ItemVendaDAO;
import vendaprodutos.dao.ProdutoDAO;
import vendaprodutos.dao.VendaDAO;
import vendaprodutos.entidades.Cliente;
import vendaprodutos.entidades.ItemVenda;
import vendaprodutos.entidades.vos.ItemVendaVO;
import vendaprodutos.entidades.Produto;
import vendaprodutos.entidades.Venda;
import vendaprodutos.entidades.vos.VendaVO;
import vendaprodutos.utils.Utils;

/**
 * Servlet de serviços RESTful para Vendas.
 * 
 * @author Prof. Dr. David Buzatto
 */
@WebServlet( name = "VendaRestfulServlet", urlPatterns = { "/api/vendas/*" } )
public class VendaRestfulServlet extends HttpServlet {
    
    private Jsonb jsonb = JsonbBuilder.create();
    
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
        throws ServletException, IOException {
        
        response.setContentType( "application/json" );
        
        String jsonResposta;
        int status;

        try ( VendaDAO dao = new VendaDAO() ) {
            
            String pathInfo = request.getPathInfo();
            
            if ( pathInfo != null && pathInfo.length() > 1 ) {
                
                // GET /api/vendas/1 - busca específica
                Long id = Long.valueOf( pathInfo.substring( 1 ) );
                Venda venda = dao.obterPorId( id );
                
                if ( venda != null ) {
                    status = HttpServletResponse.SC_OK;
                    jsonResposta = jsonb.toJson( venda );
                } else {
                    status = HttpServletResponse.SC_NOT_FOUND;
                    jsonResposta = jsonb.toJson( new Resposta( "Venda não encontrada.", "" ) );
                }
                
            } else {
                // GET /api/vendas/ - listar todos
                status = HttpServletResponse.SC_OK;
                jsonResposta = jsonb.toJson( dao.listarTodos() );
            }
            
        } catch ( SQLException exc ) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            jsonResposta = jsonb.toJson( new Resposta( "Erro ao obter venda(s).", exc.getMessage() ) );
        } catch ( NumberFormatException exc ) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            jsonResposta = jsonb.toJson( new Resposta( "ID inválido.", exc.getMessage() ) );
        }
        
        try ( PrintWriter out = response.getWriter() ) {
            response.setStatus( status );
            out.write( jsonResposta );
        }
        
    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
        throws ServletException, IOException {
        
        response.setContentType( "application/json" );
        
        String jsonResposta;
        int status;
        
        try ( VendaDAO daoVenda = new VendaDAO();
              ClienteDAO daoCliente = new ClienteDAO();
              ItemVendaDAO daoItemVenda = new ItemVendaDAO();
              ProdutoDAO daoProduto = new ProdutoDAO() ) {
            
            VendaVO vendaVO = jsonb.fromJson( 
                Utils.obterDados( request ),
                VendaVO.class
            );
            
            Cliente c = daoCliente.obterPorId( vendaVO.idCliente() );
            
            Venda venda = new Venda();
            venda.setData( Date.valueOf( LocalDate.now() ) );
            venda.setCancelada( false );
            venda.setCliente( c );
            
            Utils.validar( venda, "id" );
            daoVenda.salvar( venda );
            
            for ( ItemVendaVO item : vendaVO.itens() ) {
                
                Produto produto = daoProduto.obterPorId( item.idProduto() );
                produto.setEstoque( produto.getEstoque().subtract( item.quantidade() ) );
                
                ItemVenda novoItem = new ItemVenda();
                novoItem.setVenda( venda );
                novoItem.setProduto( produto );
                novoItem.setValor( produto.getValorVenda() );
                novoItem.setQuantidade( item.quantidade() );
                
                // não validaremos o produto, pois
                // permitiremos estoque negativo na venda
                daoProduto.atualizar( produto );
                daoItemVenda.salvar( novoItem );
                
            }
            
            status = HttpServletResponse.SC_CREATED;
            response.setHeader( "Location", "/api/vendas/" + venda.getId() );
            jsonResposta = jsonb.toJson( new Resposta( "Venda inserida com successo!", venda ) );

        } catch ( SQLException exc ) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            jsonResposta = jsonb.toJson( new Resposta( "Erro ao inserir a venda.", exc.getMessage() ) );
        }
        
        try ( PrintWriter out = response.getWriter() ) {
            response.setStatus( status );
            out.write( jsonResposta );
        }
        
    }
    
    @Override
    protected void doPut( HttpServletRequest request, HttpServletResponse response )
        throws ServletException, IOException {
        
        response.setContentType( "application/json" );
        
        String jsonResposta;
        int status;
        
        try ( VendaDAO daoVenda = new VendaDAO();
              ItemVendaDAO daoItemVenda = new ItemVendaDAO();
              ProdutoDAO daoProduto = new ProdutoDAO() ) {
            
            String pathInfo = request.getPathInfo();
            
            if ( pathInfo.startsWith( "/cancelar/" ) ) {
                
                Long id = Long.valueOf( pathInfo.replace( "/cancelar/", "" ) );

                Venda venda = daoVenda.obterPorId( id );
                venda.setCancelada( true );
                daoVenda.atualizar( venda );
                
                for ( ItemVenda iv : daoItemVenda.obterPorIdVenda( id ) ) {
                    Produto p = iv.getProduto();
                    p.setEstoque( p.getEstoque().add( iv.getQuantidade() ) );
                    daoProduto.atualizarEstoque( p );
                }

                status = HttpServletResponse.SC_OK;
                jsonResposta = jsonb.toJson( new Resposta( "Venda cancelada com successo!", venda ) );
                
            } else {
                status = HttpServletResponse.SC_BAD_REQUEST;
                jsonResposta = jsonb.toJson( new Resposta( "Método inesistente.", pathInfo ) );
            }

        } catch ( SQLException exc ) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            jsonResposta = jsonb.toJson( new Resposta( "Erro ao cancelar a venda.", exc.getMessage() ) );
        } catch ( NumberFormatException exc ) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            jsonResposta = jsonb.toJson( new Resposta( "ID inválido.", exc.getMessage() ) );
        }
        
        try ( PrintWriter out = response.getWriter() ) {
            response.setStatus( status );
            out.write( jsonResposta );
        }
        
    }

    @Override
    public String getServletInfo() {
        return "Servlet de Serviços RESTful para Vendas";
    }

}
