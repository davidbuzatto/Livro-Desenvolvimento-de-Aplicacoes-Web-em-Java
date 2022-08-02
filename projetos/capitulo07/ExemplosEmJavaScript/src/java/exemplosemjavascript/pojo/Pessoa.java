package exemplosemjavascript.pojo;

import java.time.LocalDate;

/**
 * Um Plain Old Java Object (POJO).
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Pessoa {
    
    private String nome;
    private LocalDate dataNasc;
    private double salario;

    public String getNome() {
        return nome;
    }

    public void setNome( String nome ) {
        this.nome = nome;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc( LocalDate dataNasc ) {
        this.dataNasc = dataNasc;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario( double salario ) {
        this.salario = salario;
    }
    
}
