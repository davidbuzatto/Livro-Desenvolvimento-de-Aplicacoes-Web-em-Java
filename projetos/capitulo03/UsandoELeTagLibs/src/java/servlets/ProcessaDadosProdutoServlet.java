package servlets;

import entidades.Produto;
import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet para processamento de dados de produtos.
 * 
 * @author Prof. Dr. David Buzatto
 */
@WebServlet( name = "ProcessaDadosProdutoServlet", 
             urlPatterns = { "/processaDadosProduto" } )
public class ProcessaDadosProdutoServlet extends HttpServlet {

    protected void processRequest( 
            HttpServletRequest request, 
            HttpServletResponse response )
            throws ServletException, IOException {
        
        request.setCharacterEncoding( "UTF-8" );
        
        // obtém os dados do formulário
        int codigo = 0;
        int quantidade = 0;
        String descricao = request.getParameter( "descricao" );
        String unidadeMedida = request.getParameter( "unidade" );

        try {
            codigo = Integer.parseInt( request.getParameter( "codigo" ));
        } catch ( NumberFormatException exc ) {
            System.out.println( "Erro ao converter o codigo." );
        }

        try {
            quantidade = Integer.parseInt( request.getParameter( "quantidade" ) );
        } catch ( NumberFormatException exc ) {
            System.out.println( "Erro ao converter a quantidade." );
        }

        // cria um novo produto e configura suas propriedades
        // usando os dados obtidos do formulário
        Produto prod = new Produto();
        prod.setCodigo( codigo );
        prod.setDescricao( descricao );
        prod.setUnidadeMedida( unidadeMedida );
        prod.setQuantidade( quantidade );

        // configura um atributo no request chamado "produtoObtido"
        // sendo que o valor do atributo é o objeto "prod"
        request.setAttribute( "produtoObtido", prod );

        // prepara um RequestDispatcher para direcionar para a página
        // "exibeDados.jsp" que está no mesmo diretório em relação
        // ao mapeamento deste Servlet
        RequestDispatcher disp = request.getRequestDispatcher( "exibeDados.jsp" );

        // faz o direcionamento, chamando o método forward.
        disp.forward( request, response );
        
    }

    @Override
    protected void doGet( 
            HttpServletRequest request, 
            HttpServletResponse response )
            throws ServletException, IOException {
        processRequest( request, response );
    }

    @Override
    protected void doPost( 
            HttpServletRequest request, 
            HttpServletResponse response )
            throws ServletException, IOException {
        processRequest( request, response );
    }

    @Override
    public String getServletInfo() {
        return "ProcessaDadosProdutoServlet";
    }

}
