package Dados;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DadosBinarios {

    public static void main(String[] args) {
        String pathBase = "MedicosPacientesConsultas/src/Dados/";

        CsvReaderWriter leitorMedicos = new CsvReaderWriter(pathBase + "P_Medico.CSV");
        CsvReaderWriter leitorPacientes = new CsvReaderWriter(pathBase + "P_Paciente.CSV");
        CsvReaderWriter leitorConsultas = new CsvReaderWriter(pathBase + "P_Consulta.CSV");

        // Lê os dados de cada arquivo
        List<String[]> medicosCsv = leitorMedicos.ler();
        List<String[]> pacientesCsv = leitorPacientes.ler();
        List<String[]> consultasCsv = leitorConsultas.ler();

        // Remove o cabeçalho de cada lista
        if (!medicosCsv.isEmpty()) {
            medicosCsv.remove(0);
        }
        if (!pacientesCsv.isEmpty()) {
            pacientesCsv.remove(0);
        }
        if (!consultasCsv.isEmpty()) {
            consultasCsv.remove(0);
        }

        System.out.println("Dados lidos dos arquivos CSV com sucesso.");

        // Cria listas
        ArrayList<Medico> todosOsMedicos = new ArrayList<>();
        for (String[] linha : medicosCsv) {
            todosOsMedicos.add(new Medico(linha));
        }

        ArrayList<Paciente> todosOsPacientes = new ArrayList<>();
        for (String[] linha : pacientesCsv) {
            todosOsPacientes.add(new Paciente(linha));
        }

        ArrayList<Consulta> todasAsConsultas = new ArrayList<>();
        for (String[] linha : consultasCsv) {
            todasAsConsultas.add(new Consulta(linha));
        }

        // Salva as listas de objetos em diferentes arquivos binários
        salvarArquivoBinario(todasAsConsultas, "dados_consultas");
        salvarArquivoBinario(todosOsMedicos, "dados_medicos");
        salvarArquivoBinario(todosOsPacientes, "dados_pacientes");
    }

    private static void salvarArquivoBinario(Object obj, String nomeDoArquivo) {
        String nomeCompleto = nomeDoArquivo + ".bin";
        try (
            FileOutputStream fileStream = new FileOutputStream(nomeCompleto);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
        ) {
            objectStream.writeObject(obj);
            System.out.println(String.format("Arquivo '%s' criado com sucesso!", nomeCompleto));
        } catch (Exception e) {
            System.err.println(String.format("Erro ao salvar os dados de '%s' em formato binário.", nomeCompleto));
            e.printStackTrace();
        }
    }
}