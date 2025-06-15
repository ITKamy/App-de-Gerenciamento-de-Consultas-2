/*package Dados;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CsvReaderWriter.pathBase = "MedicosPacientesConsultas/src/";
        ArrayList<Medico> todosOsMedicos = lerMedicos();
        ArrayList<Paciente> todosOsPacientes = lerPacientes();
        ArrayList<Consulta> todasAsConsultas = lerConsultas();
        for(Consulta consulta : todasAsConsultas) {
            consulta.conectar(todosOsPacientes, todosOsMedicos);
        }
        
        InterfaceLogin.login(todosOsMedicos, todosOsPacientes, todasAsConsultas);
    }

    public static ArrayList<Consulta> lerConsultas() {
        List<String[]> linhas = CsvReaderWriter.lerCsv("Dados/P_Consulta.CSV",';');
        ArrayList<Consulta> resultado = new ArrayList<>();
        for(String[] linha : linhas) {
            if (linha[0] == null)
                continue;
            resultado.add(new Consulta(linha));
        }
        return resultado;
    }

    public static ArrayList<Medico> lerMedicos() {
        List<String[]> linhas = CsvReaderWriter.lerCsv("Dados/P_Medico.CSV",';');
        ArrayList<Medico> resultado = new ArrayList<Medico>();
        for(String[] linha : linhas) {
            if (linha[0] == null)
                continue;
            resultado.add(new Medico(linha));
        }
        return resultado;
    }

    public static ArrayList<Paciente> lerPacientes() {
        List<String[]> linhas = CsvReaderWriter.lerCsv("Dados/P_Paciente.CSV",';');
        ArrayList<Paciente> resultado = new ArrayList<>();
        for(String[] linha : linhas) {
            if (linha[0] == null)
                continue;
            resultado.add(new Paciente(linha));
        }
        return resultado;
    }
}
*/
