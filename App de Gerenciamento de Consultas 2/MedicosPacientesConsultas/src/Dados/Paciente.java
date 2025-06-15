package Dados;
//Paciente (NOME, CPF, LISTA DE CONSULTAS)

import java.io.Serializable; // Salvar em arquivos binários
import java.util.ArrayList;
import java.util.Collections; // Lista imútavel
import java.util.List;

public class Paciente implements Serializable {
    private final String nome;
    // Deixei CPF como String porque int só suporta 9-10 dígitos, e CPFs têm 11 dígitos!
    private final String cpf;
    private final ArrayList<Consulta> consultas = new ArrayList<>();

    //CONSTRUTOR------------------------
    public Paciente(String[] linhaCsv) {
        this.nome = linhaCsv[0];
        this.cpf = linhaCsv[1];
    }

    //GETTERS--------------------------
    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public List<Consulta> getConsultas() {
        return Collections.unmodifiableList(consultas); //lista que não muda
    }

    //MÉTODOS--------------------------
    public void adicionarConsulta(Consulta consulta) {
        if (!consultas.contains(consulta))
            consultas.add(consulta);
    }

    //paciente=cpf:
    public static Paciente encontrarPorCpf(ArrayList<Paciente> pacientes, String cpf) {
        for(Paciente paciente : pacientes) {
            if (paciente.cpf.equals(cpf))
                return paciente;
        }
        return null;
    }

    public String toString() {
        String resultado = String.format("%s, com as seguintes consultas:",toStringSimples());
        for (Consulta consulta : consultas) {
            resultado += String.format("\n-> %s",consulta.toString());
        }
        return resultado;
    }

    public String toStringSimples() {
        return String.format("Paciente %s, de CPF %s", nome, cpf);
    }
}
