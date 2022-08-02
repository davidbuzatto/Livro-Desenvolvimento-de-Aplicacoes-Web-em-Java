package padroesempratica.testes;

import java.sql.Connection;
import java.sql.SQLException;
import padroesempratica.jdbc.ConnectionFactory;

/**
 * Teste de conexão.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TesteConnectionFactory {

    public static void main( String[] args ) {

        // tenta criar uma conexão
        try {

            Connection conexao = ConnectionFactory.getConnection();
            System.out.println( "Conexão criada com sucesso!" );

        } catch ( SQLException exc ) {

            System.err.println( "Erro ao tentar criar a conexão!" );
            exc.printStackTrace();

        }

    }

}
