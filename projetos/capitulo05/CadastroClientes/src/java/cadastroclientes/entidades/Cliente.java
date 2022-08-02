package cadastroclientes.entidades;

import java.sql.Date;

/**
 * Entidade Cliente.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Cliente {

    private int id;
    private String nome;
    private String sobrenome;
    private Date dataNascimento;
    private String cpf;
    private String email;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cep;
    private Cidade cidade;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome( String nome ) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome( String sobrenome ) {
        this.sobrenome = sobrenome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento( Date dataNascimento ) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf( String cpf ) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro( String logradouro ) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero( String numero ) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro( String bairro ) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep( String cep ) {
        this.cep = cep;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade( Cidade cidade ) {
        this.cidade = cidade;
    }

}
