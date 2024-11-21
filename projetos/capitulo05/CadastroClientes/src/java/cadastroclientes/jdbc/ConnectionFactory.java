package cadastroclientes.jdbc;

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
     * cadastro_clientes.
     *
     * @return Uma conexão com o banco de dados cadastro_clientes.
     * @throws SQLException Caso ocorra algum problema durante a conexão.
     */
    public static Connection getConnection() throws SQLException {

        // o método getConnection de DriverManager recebe como parâmetro
        // a URL da base de dados, o usuário usado para conectar na base
        // e a senha deste usuário.
        return DriverManager.getConnection(
                "jdbc:mariadb://localhost/cadastro_clientes",
                "root",
                "root" );

    }

}
