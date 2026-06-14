package vendaprodutos.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import vendaprodutos.jdbc.ConnectionFactory;

/**
 * DAO genérico que implementa a interface AutoCloseable,
 * permitindo o uso da construção try-with-resources.
 *
 * @author Prof. Dr. David Buzatto
 */
public abstract class DAO<Tipo> implements AutoCloseable {

    // cada DAO terá uma conexão.
    private Connection conexao;

    /**
     * Construtor do DAO.
     * É nesse construtor que a conexão é criada.
     *
     * @throws SQLException
     */
    public DAO() throws SQLException {

        /*
         * Usa-se o método getConnection() da fábrica de conexões,
         * para criar uma conexão para o DAO.
         */
        conexao = ConnectionFactory.getConnection();

    }

    /**
     * Método para obter a conexão criada.
     *
     * @return Retorna a conexão.
     */
    public Connection getConnection() {
        return conexao;
    }

    /**
     * Método para fechar a conexão aberta.
     * 
     * Não precisa ser invocado explicitamente caso
     * o objeto do DAO tenha sido criado usando a construção
     * try-with-resources.
     * 
     * É sobrescrito da interface AutoCloseable.
     *
     * @throws SQLException Caso ocorra algum erro
     * durante o fechamento da conexão.
     */
    @Override
    public void close() throws SQLException {
        conexao.close();
    }

    /**
     * Método abstrato para salvar uma instância de uma
     * entidade da base de dados.
     * 
     * É o "C" do CRUD.
     *
     * @param obj Instância do objeto da entidade a ser salvo.
     * @throws SQLException Caso ocorra algum erro durante a gravação.
     */
    public abstract void salvar( Tipo obj ) throws SQLException;

    /**
     * Método abstrato para atualizar uma instância de uma
     * entidade da base de dados.
     * 
     * É o "U" do CRUD.
     *
     * @param obj Instância do objeto da entidade a ser atualizado.
     * @throws SQLException Caso ocorra algum erro durante a atualização.
     */
    public abstract void atualizar( Tipo obj ) throws SQLException;

    /**
     * Método abstrato para excluir uma instância de uma
     * entidade da base de dados.
     * 
     * É o "D" do CRUD.
     *
     * @param obj Instância do objeto da entidade a ser salvo.
     * @throws SQLException Caso ocorra algum erro durante a exclusão.
     */
    public abstract void excluir( Tipo obj ) throws SQLException;

    /**
     * Método abstrato para obter todas as instâncias de uma
     * entidade da base de dados.
     * 
     * É o "R" do CRUD.
     *
     * @return Lista de todas as instâncias da entidade.
     * @throws SQLException Caso ocorra algum erro durante a consulta.
     */
    public abstract List<Tipo> listarTodos() throws SQLException;

    /**
     * Método abstrato para obter uma instância de uma
     * entidade pesquisando pelo seu atributo identificador.
     * 
     * É o "R" do CRUD.
     *
     * @param id Identificador da instância a ser obtida.
     * @return Instância relacionada ao id passado, ou null caso não seja
     * encontrada.
     * @throws SQLException Caso ocorra algum erro durante a consulta.
     */
    public abstract Tipo obterPorId( Long id ) throws SQLException;

}