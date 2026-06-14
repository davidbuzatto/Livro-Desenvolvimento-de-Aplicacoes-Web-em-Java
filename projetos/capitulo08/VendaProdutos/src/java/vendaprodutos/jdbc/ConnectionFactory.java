package vendaprodutos.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Uma fábrica de conexões.
 *
 * @author Prof. Dr. David Buzatto
 */
public class ConnectionFactory {

    // força o registro do driver MariaDB no classloader da aplicação,
    // necessário para que o Tomcat encontre o driver em WEB-INF/lib.
    // executado uma única vez quando a classe é carregada.
    static {
        try {
            Class.forName( "org.mariadb.jdbc.Driver" );
        } catch ( ClassNotFoundException exc ) {
            throw new RuntimeException( "Driver MariaDB não encontrado.", exc );
        }
    }
    
    /**
     * O método getConnection retorna uma conexão com a base de dados
     * venda_produtos.
     *
     * @return Uma conexão com o banco de dados venda_produtos.
     * @throws SQLException Caso ocorra algum problema durante a conexão.
     */
    public static Connection getConnection() throws SQLException {

        // o método getConnection de DriverManager recebe como parâmetro
        // a URL da base de dados, o usuário usado para conectar na base
        // e a senha deste usuário.
        return DriverManager.getConnection(
                "jdbc:mariadb://localhost/venda_produtos",
                "root",
                "root" );

    }

}
