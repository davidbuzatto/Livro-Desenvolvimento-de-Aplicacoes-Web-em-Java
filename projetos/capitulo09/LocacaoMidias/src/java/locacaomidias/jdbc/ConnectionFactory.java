package locacaomidias.jdbc;

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
     * locacao_midias.
     *
     * @return Uma conexão com o banco de dados venda_produtos.
     * @throws SQLException Caso ocorra algum problema durante a conexão.
     */
    public static Connection getConnection() throws SQLException {

        try {
            // força o registro do driver MariaDB no classloader da aplicação,
            // necessário para que o Tomcat encontre o driver em WEB-INF/lib
            Class.forName( "org.mariadb.jdbc.Driver" );
        } catch ( ClassNotFoundException exc ) {
            throw new SQLException( "Driver MariaDB não encontrado.", exc );
        }
        
        return DriverManager.getConnection(
                "jdbc:mariadb://localhost/locacao_midias",
                "root",
                "root" );

    }

}
