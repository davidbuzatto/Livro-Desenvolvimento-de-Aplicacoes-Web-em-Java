package padroesempratica.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Uma fábrica de conexões.
 *
 * @author Prof. Dr. David Buzatto
 */
public class ConnectionFactory {
    
    /**
     * O método getConnection retorna uma conexão com a base de dados
     * testes_padroes.
     *
     * @return Uma conexão com o banco de dados testes_padroes.
     * @throws SQLException Caso ocorra algum problema durante a conexão.
     */
    public static Connection getConnection() throws SQLException {

        /* O método getConnection de DriverManagaer recebe como parâmetro
         * a URL da base de dados, o usuário usado para conectar na base
         * e a senha deste usuário. O Driver JDBC apropriado será
         * carregado com base na biblioteca configurada.
         */
        return DriverManager.getConnection(
                "jdbc:mariadb://localhost/testes_padroes",
                "root",
                "" );

    }

}
