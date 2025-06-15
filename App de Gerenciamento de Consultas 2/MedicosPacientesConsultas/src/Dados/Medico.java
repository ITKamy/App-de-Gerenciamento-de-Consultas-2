package Dados;
// Medico ( NOME, COD(INT), LISTA DOS PACIENTES)

import java.time.LocalDateTime; //Data
import java.time.Duration; // Duração de datas/horas
import java.util.ArrayList; //Lista
import java.io.Serializable; // Salva em arquivos binários
import java.util.Collections; // Coleção (lista não modificavel)
import java.util.List;//Lista

public class Medico implements Serializable {
    private final String nome;
    private final int codigo;
    private final ArrayList<Paciente> pacientes = new ArrayList<>();

    //CONSTRUTOR---------------------
    public Medico(String[] linhaCsv) {
        this.nome = linhaCsv[0];
        codigo = parseCodigo(linhaCsv[1]);//tira o (.) do COD
    }

    //GETTERS------------------------
    public String getNome() {
        return nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public List<Paciente> getPacientes() {
        return Collections.unmodifiableList(pacientes); // retorna uma lista que não pode mudar
    }

    //MÉTODOS-------------
    //tem paciente? = paciente na lista
    public void adicionarPaciente(Paciente paciente) {
        if(!pacientes.contains(paciente))
            pacientes.add(paciente);
    }
    //tem med? = med na lista
    public static Medico encontrarPorCodigo(ArrayList<Medico> medicos, int codigo) {
        for(Medico medico : medicos) {
            if (medico.codigo == codigo)
                return medico;
        }
        return null;
    }

    public static String formatarCodigo(int codigo) {
        StringBuilder codigoString = new StringBuilder("" + codigo); //PERGUNTA : ainda  não entendi como o StringBuilder funciona
        while (codigoString.length() < 6)
            codigoString.insert(0, "0"); //Adiciona 0 a esquerda
        return String.format("%s.%s",codigoString.substring(0,3),codigoString.substring(3));
    }

    public static int parseCodigo(String codigoString) { //(formata para inteiro, retira o ponto que tem ex:no cpf)
        return Integer.parseInt(codigoString.replaceAll("\\.",""));
    }

    //Verificar meses---
    public ArrayList<Paciente> pacientesInativos(int mesesMaximos, ArrayList<Consulta> todasAsConsultas) {
        ArrayList<Paciente> resultado = new ArrayList<>();
        LocalDateTime agora = LocalDateTime.now();//data/h atual

        for (Paciente paciente : this.pacientes) {
            //teve consulta:
            ArrayList<Consulta> consultasComPaciente = Consulta.encontrarPorPaciente(todasAsConsultas, paciente);
            //não teve:
            if (consultasComPaciente.isEmpty())//consultasComPaciente.size() <= 0
                continue;

            LocalDateTime dataMaisRecenteDoPaciente = consultasComPaciente.get(0).getDataHorario();
            for (Consulta consulta : consultasComPaciente) {
                if (consulta.getDataHorario().isAfter(dataMaisRecenteDoPaciente))
                    dataMaisRecenteDoPaciente = consulta.getDataHorario();
            }

            double diferencaMeses = (double) Duration.between(dataMaisRecenteDoPaciente, agora).toDays() / 30.0;
            if (diferencaMeses > mesesMaximos)
                resultado.add(paciente);
        }
        return resultado;
    }

    //Filtra medico->paciente
    public static ArrayList<Medico> filtrarPorPaciente(Paciente paciente, ArrayList<Medico> medicos) {
        ArrayList<Medico> resultado = new ArrayList<>();
        for (Medico medico : medicos) {
            if (medico.pacientes.contains(paciente))
                resultado.add(medico);
        }
        return resultado;
    }

    public String toString() { //adicionar paciente na lista
        StringBuilder resultado = new StringBuilder(String.format("Médico %s, de código %s, com os seguintes pacientes: ", nome, formatarCodigo(codigo)));
        for (Paciente paciente : pacientes) {
            resultado.append(String.format("\n-> %s", paciente.toStringSimples()));
        }
        return resultado.toString();
    }
}
