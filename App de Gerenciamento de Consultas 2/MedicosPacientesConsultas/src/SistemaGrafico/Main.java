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
        //declara listas
        ArrayList<Medico> todosOsMedicos;
        ArrayList<Paciente> todosOsPacientes;
        ArrayList<Consulta> todasAsConsultas;

        //carrega os dados
        try (FileInputStream fileStream = new FileInputStream("dados_clinica.bin");
             ObjectInputStream objectStream = new ObjectInputStream(fileStream)) {
            //lê
            todosOsMedicos = (ArrayList<Medico>) objectStream.readObject();
            todosOsPacientes = (ArrayList<Paciente>) objectStream.readObject();
            todasAsConsultas = (ArrayList<Consulta>) objectStream.readObject();

            System.out.println("Dados carregados com sucesso do arquivo 'dados_clinica.bin'!");

        } catch (Exception e) { //erro:
            JOptionPane.showMessageDialog(null,
                    "Erro ao carregar dados do arquivo 'dados_clinica.bin'.\n" +
                            "Execute o programa P1 (DadosBinarios.java) primeiro para gerar o arquivo.",
                    "Erro de Carregamento", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return; // Termina a execução se não conseguir carregar os dados
        }

        // Inicia a interface gráfica de Login-------------
        //coloca final para n mudar
        ArrayList<Medico> finalTodosOsMedicos = todosOsMedicos;
        ArrayList<Paciente> finalTodosOsPacientes = todosOsPacientes;
        ArrayList<Consulta> finalTodasAsConsultas = todasAsConsultas;
        //tela login
        SwingUtilities.invokeLater(() -> new TelaLogin(finalTodosOsMedicos, finalTodosOsPacientes, finalTodasAsConsultas)); }
}