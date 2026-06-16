package vendaprodutos.excecoes;

/**
 * Exceção lançada quando um recurso não é encontrado pelo seu identificador.
 *
 * @author Prof. Dr. David Buzatto
 */
public class NaoEncontradoException extends RuntimeException {

    public NaoEncontradoException( String message ) {
        super( message );
    }

}
