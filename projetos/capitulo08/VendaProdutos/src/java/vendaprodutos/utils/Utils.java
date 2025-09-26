package vendaprodutos.utils;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Classe de métodos utilitários.
 * 
 * @author Prof. Dr. David Buzatto
 */
public abstract class Utils {
    
    // tanto o validador quanto o formatador
    // são thread safe
    // a implementação do validador normalmente tem cache
    private static Validator validador = Validation
            .buildDefaultValidatorFactory()
            .getValidator();
    
    private static DateTimeFormatter dtf = DateTimeFormatter
            .ofPattern( "yyyy-MM-dd" );
    
    /*
     * Lê um parâmetro Long do request.
     * Se a String for inválida, retorna null.
     */
    public static Long getLong( 
            HttpServletRequest request,
            String nomeParametro ) {
        
        Long v = null;
        
        try {
            v = Long.valueOf( request.getParameter( nomeParametro ) );
        } catch ( NumberFormatException exc ) {
        }
        
        return v;
        
    }
    
    /*
     * Lê um parâmetro BigDecimal do request.
     * Se a String for inválida, retorna null.
     */
    public static BigDecimal getBigDecimal( 
            HttpServletRequest request,
            String nomeParametro ) {
        
        BigDecimal v = null;
        
        try {
            v = new BigDecimal( request.getParameter( nomeParametro ) );
        } catch ( NumberFormatException exc ) {
        }
        
        return v;
        
    }
    
    /*
     * Converte uma String para Long.
     * Se a String for inválida, retorna null.
     */
    public static Long getLong( String valor ) {
        
        Long v = null;
        
        try {
            v = Long.valueOf( valor );
        } catch ( NumberFormatException exc ) {
        }
        
        return v;
        
    }
    
    /*
     * Converte uma String para BigDecimal.
     * Se a String for inválida, retorna null.
     */
    public static BigDecimal getBigDecimal( String valor ) {
        
        BigDecimal v = null;
        
        try {
            v = new BigDecimal( valor );
        } catch ( NumberFormatException exc ) {
        }
        
        return v;
        
    }
    
    /*
     * Converte uma String no formato dd/MM/yyyy
     * para um java.sql.Date.
     *
     * Se a String for inválida, retorna null.
     */
    public static Date getDate( String data ) {
        
        Date d = null;
        
        try {
            d = Date.valueOf( LocalDate.parse( data, dtf ) );
        } catch ( DateTimeParseException exc ) {
        }
        
        return d;
        
    }
    
    /*
     * Faz a leitura da chave primária após inserção no banco.
     * Assume que o PreparedStatement foi configurado apropriadamente.
     */
    public static Long getChavePrimariaAposInsercao( 
            PreparedStatement stmt, String nomeColunaChave ) 
            throws SQLException {
        
        Long pk = null;
        
        try ( ResultSet rsPK = stmt.getGeneratedKeys() ) {
            if ( rsPK.next() ) {
                pk = rsPK.getLong( nomeColunaChave );
            }
        }
        
        return pk;
        
    }
    
    /*
     * Realiza a validação e retorna um conjunto de violações de
     * restrições.
     *
     * Não insere no retorno os campos que devem ser ignorados.
     */
    private static Set<ConstraintViolation> validarObj( 
            Object obj,
            String... ignorar ) {
        
        // uma lista dos campos à ignorar
        List<String> ignorarCampos = Arrays.<String>asList( ignorar );
        
        // conjunto que conterá todas as violações de restrição
        // que tenham caminho da propriedade igual
        // à alguma da lista de ignorar
        Set<ConstraintViolation> cvs = new LinkedHashSet<>();
        
        // valida e percorre todas as restrições violadas
        for ( ConstraintViolation cv : validador.validate( obj ) ) {
            
            // se a lista de campos à ignorar não tiver
            // o caminho da propriedade
            if ( !ignorarCampos.contains( 
                    cv.getPropertyPath().toString() ) ) {
                cvs.add( cv );
            }
        }
        
        return cvs;
        
    }
    
    /*
     * Realiza a validação do objeto passado e lança
     * uma SQLException com todos os erros obtidos caso o
     * objeto seja inválido.
     */
    public static void validar(
            Object obj,
            String... ignorar )
            throws SQLException {
        
        StringBuilder sb = new StringBuilder();
        Set<ConstraintViolation> cvs = 
                Utils.validarObj( obj, ignorar );
        
        if ( !cvs.isEmpty() ) {
            
            // cria uma mensagem com várias
            // tags <li> que conterão as inconsistências
            // encontradas no objeto validado
            for ( ConstraintViolation cv : cvs ) {
                sb.append( String.format( 
                        "<li>%s: %s</li>", 
                        cv.getPropertyPath(), 
                        cv.getMessage() ) );
            }
            
            // lança a exceção com todos os erros
            throw new SQLException( sb.toString() );
            
        }
        
    }
    
    /*
     * Prepara o despacho para a página de erro de aplicação.
     */
    public static RequestDispatcher prepararDespachoErro( 
            HttpServletRequest request,
            String mensagem ) {
        
        // "Referer" é de onde veio a requisição
        request.setAttribute( "mensagemErro", mensagem );
        request.setAttribute( "voltarPara", request.getHeader( "Referer" ) );
        
        return request.getRequestDispatcher( "/erro.jsp" );
        
    }
    
    /**
     * Processa a requisição e extrai os dados da mesma. Usado quando
     * é enviado JSON no body.
     */
    public static String obterDados( HttpServletRequest request ) throws IOException {
        
        StringBuilder sb = new StringBuilder();
        String line;
        
        try ( BufferedReader reader = request.getReader() ) {
            while ( ( line = reader.readLine() ) != null ) {
                sb.append( line );
            }
        }
        
        return sb.toString();
        
    }
    
}
