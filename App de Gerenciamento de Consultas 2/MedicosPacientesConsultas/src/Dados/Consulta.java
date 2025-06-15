package Dados;

//Consulta (DATA(LOCALDATE), CPF PACI(STRING), COD MED(INT)) = quanto, quem, com quem

import java.io.Serializable; // Salvar em arquivo bainário
import java.time.LocalDate; // Datas e horas
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; // data -> texto ou ao contrário
import java.util.ArrayList; // Lista

public class Consulta implements Serializable {
    //formatar
    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    final static DateTimeFormatter formatterDia = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    final static DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm");

    //final: porque o valor não se altera
    private final LocalDateTime dataHorario;
    private final String cpfPaciente;
    private final int codigoMedico;

    //CONSTRUTOR------------------------------
    public Consulta(String[] linhaCsv) {
        String dataString = linhaCsv[0]; //ler
        String horarioString = linhaCsv[1];
        if (horarioString.length() == 4) //acrescenta um 0 ex: 4:30 vira 04:30
            horarioString = "0"+horarioString;
        this.dataHorario = LocalDateTime.parse(dataString + " " + horarioString, formatter); // texto -> data
        this.cpfPaciente = linhaCsv[2];
        this.codigoMedico = Medico.parseCodigo(linhaCsv[3]); //tira o . se tiver
    }

    //GETTERS-----------------------------
    public LocalDateTime getDataHorario() {
        return dataHorario;
    }

    public String getCpfPaciente() {
        return cpfPaciente;
    }

    public int getCodigoMedico() {
        return codigoMedico;
    }

    //MÉTODOS----------------------------
    //paciente(cpf) tem médico(cod)
    public void conectar(ArrayList<Paciente> pacientes, ArrayList<Medico> medicos) {
        Paciente paciente = Paciente.encontrarPorCpf(pacientes, cpfPaciente);
        if (paciente == null) //está vazia?
            return;
        paciente.adicionarConsulta(this); //adiciona na lista
        Medico medico = Medico.encontrarPorCodigo(medicos, codigoMedico);
        if (medico == null) //está vazia?
            return;
        medico.adicionarPaciente(paciente); //adiona na lista
    }

    //Métodos de formatação---
    public String dataHorarioFormatado() {
        return dataHorario.format(formatter);
    }

    public String dataFormatada() {
        return dataHorario.format(formatterDia);
    }

    public String horarioFormatado() {
        return dataHorario.format(formatterHora);
    }

    //Entrega tudo formatado (pelo que entendi)---
    public String toString() {
        return String.format(
            "Consulta do paciente de CPF %s com o médico de código %s, no dia %s às %s",
            cpfPaciente,
            Medico.formatarCodigo(codigoMedico),
            dataFormatada(),
            horarioFormatado()
        );
    }

    //Métodos de Filtrar------------------------------
    //Paciente = futuras consultas
    public static ArrayList<Consulta> consultasAgendadasPaciente(ArrayList<Consulta> consultas, Paciente paciente) {
        return filtrarPorDatas(encontrarPorPaciente(consultas, paciente),LocalDateTime.now(),LocalDate.parse("31/12/9999",formatterDia).atStartOfDay());
    }

    //Paciente = Consultas Passadas
    public static ArrayList<Consulta> consultasPassadasPacienteMedico(ArrayList<Consulta> consultas, Paciente paciente, Medico medico) {
        return filtrarPorDatas(encontrarPorPaciente(encontrarPorMedico(consultas, medico), paciente),LocalDate.parse("01/01/1970",formatterDia).atStartOfDay(),LocalDateTime.now());
    }

    //Consulta de Medico(cod)
    public static ArrayList<Consulta> encontrarPorCodigoMedico(ArrayList<Consulta> todasAsConsultas, int codigoMedico) {
        ArrayList<Consulta> resultado = new ArrayList<>();
        for(Consulta consulta : todasAsConsultas) {
            if(consulta.codigoMedico == codigoMedico)
                resultado.add(consulta);
        }
        return resultado;
    }

    //Consulta de Paciente(cpf)
    public static ArrayList<Consulta> encontrarPorCpfPaciente(ArrayList<Consulta> todasAsConsultas, String cpf) {
        ArrayList<Consulta> resultado = new ArrayList<>();
        for(Consulta consulta : todasAsConsultas) {
            if(consulta.cpfPaciente.equals(cpf))
                resultado.add(consulta);
        }
        return resultado;
    }

    public static ArrayList<Consulta> encontrarPorMedico(ArrayList<Consulta> todasAsConsultas, Medico medico) {
        return encontrarPorCodigoMedico(todasAsConsultas, medico.getCodigo());
    }

    public static ArrayList<Consulta> encontrarPorPaciente(ArrayList<Consulta> todasAsConsultas, Paciente paciente) {
        return encontrarPorCpfPaciente(todasAsConsultas, paciente.getCpf());
    }

    public static ArrayList<Consulta> filtrarPorDatas(ArrayList<Consulta> todasAsConsultas, LocalDateTime dateTimeInicial, LocalDateTime dateTimeFinal) {
        ArrayList<Consulta> resultado = new ArrayList<>();
        for(Consulta consulta : todasAsConsultas) {
            if (consulta.dataHorario.compareTo(dateTimeInicial) >= 0 && consulta.dataHorario.compareTo(dateTimeFinal) <= 0)
                resultado.add(consulta);
        }
        return resultado;
    }
}