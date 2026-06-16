package vendaprodutos.excecoes;

/**
 * Exceção lançada quando um objeto não passa na validação das restrições
 * definidas pela Jakarta Bean Validation.
 *
 * @author Prof. Dr. David Buzatto
 */
public class ValidacaoException extends RuntimeException {

    public ValidacaoException( String message ) {
        super( message );
    }

}
