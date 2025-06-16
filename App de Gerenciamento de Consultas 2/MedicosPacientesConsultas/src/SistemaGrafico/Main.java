package SistemaGrafico;

//Classes do P1
import Dados.Consulta;
import Dados.Medico;
import Dados.Paciente;

import javax.swing.*; // para  interface gráfica
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // não precisa, mas é para deixar a tela "atualizada" com o sistema operacional
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //CARREGAR DADOS BINÁRIOS -------------------------
        //declara listas e carrega os dados
        ArrayList<Medico> todosOsMedicos = (ArrayList<Medico>) lerArquivoBinario("dados_medicos.bin");
        ArrayList<Paciente> todosOsPacientes = (ArrayList<Paciente>) lerArquivoBinario("dados_pacientes.bin");
        ArrayList<Consulta> todasAsConsultas = (ArrayList<Consulta>) lerArquivoBinario("dados_consultas.bin");
        
        // Encerra o programa se houver encontrado algum problema
        if (todosOsMedicos == null || todosOsPacientes == null || todasAsConsultas == null) {
            return;
        }
        
        for (Consulta consulta : todasAsConsultas) {
            System.out.println(consulta.toString());
        }

        // Inicia a interface gráfica de Login-------------
        //tela login
        SwingUtilities.invokeLater(() -> new TelaLogin(todosOsMedicos, todosOsPacientes, todasAsConsultas));
    }

    private static Object lerArquivoBinario(String nomeDoArquivo) {
        try (
            FileInputStream fileStream = new FileInputStream(nomeDoArquivo);
            ObjectInputStream objectStream = new ObjectInputStream(fileStream)
        ) {
            Object resultado = objectStream.readObject();
            System.out.println(String.format("Dados carregados com sucesso do arquivo '%s'!", nomeDoArquivo));
            return resultado;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao carregar dados do arquivo '"+nomeDoArquivo+"'.\n" +
                            "Execute o programa P1 (DadosBinarios.java) primeiro para gerar o arquivo.",
                    "Erro de Carregamento", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }
}