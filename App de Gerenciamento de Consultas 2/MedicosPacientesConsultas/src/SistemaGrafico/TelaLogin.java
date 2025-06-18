package SistemaGrafico;

import Dados.*;
import javax.swing.*;
import java.util.ArrayList;

public class TelaLogin {

    //listas de dados carregadas------------
    private final ArrayList<Medico> medicos;
    private final ArrayList<Paciente> pacientes;
    private final ArrayList<Consulta> consultas;

    // Atributos Gráficos-----------------------
    private final JFrame janela; // janela da tela.
    private final JTextField nomeField; // Campo de nome
    private final JTextField codCpfField; // Campo de texto para o ID/CPF.
    private final JRadioButton medicoRadioBotao;

    //COSNTRUTOR---------------------------------------
    public TelaLogin(ArrayList<Medico> medicos, ArrayList<Paciente> pacientes, ArrayList<Consulta> consultas) {
        this.medicos = medicos;
        this.pacientes = pacientes;
        this.consultas = consultas;

        //CRIAÇÃO E CONFIGURAÇÃO----------------------------
        janela = new JFrame("Login - Sistema de Consultas"); // Cria a janela com um título
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fechar
        janela.setSize(400, 400); // Tamanho da janela
        janela.setLocationRelativeTo(null); // Centraliza a janela na tela
        janela.setLayout(null); // layout como manual (usar coordenadas)

        //CRIAÇÃO E POSICIONAMENTO DOS COMPONENTES NA JANELA ---------------

        JLabel labelTipoUsuario = new JLabel("Tipo de Usuário:");
        janela.add(labelTipoUsuario); // o rótulo à janela.
        labelTipoUsuario.setBounds(50, 40, 150, 25);


        //Caixa de seleção --------------------
        medicoRadioBotao = new JRadioButton("Médico");
        JRadioButton pacienteRadioBotao = new JRadioButton("Paciente");
        medicoRadioBotao.setBounds(50, 70, 100, 25);
        pacienteRadioBotao.setBounds(160, 70, 100, 25);

        //Paciente" pré-selecionado
        pacienteRadioBotao.setSelected(true);

        // só um selecionado
        ButtonGroup tipoUsuarioBotao = new ButtonGroup();
        tipoUsuarioBotao.add(medicoRadioBotao);
        tipoUsuarioBotao.add(pacienteRadioBotao);

        // add
        janela.add(medicoRadioBotao);
        janela.add(pacienteRadioBotao);

        //Campo de nome-------
        JLabel labelNome = new JLabel("Nome:");
        labelNome.setBounds(50, 110, 100, 25);
        janela.add(labelNome);

        // Campo nome do usuário------------------
        nomeField = new JTextField();
        nomeField.setBounds(50, 140, 280, 25);
        janela.add(nomeField);

        //Campo de ID/CPF---------------
        JLabel labelIdCpf = new JLabel("ID (Médico) ou CPF (Paciente):");
        labelIdCpf.setBounds(50, 180, 250, 25);
        janela.add(labelIdCpf);

        // Campo de texto para o ID/CPF--------------------
        codCpfField = new JTextField();
        codCpfField.setBounds(50, 210, 280, 25);
        janela.add(codCpfField);

        //Botão de Login-----------------
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(115, 260, 150, 30);
        janela.add(loginButton);

        //Ação que será executada quando o botão for clicado
        loginButton.addActionListener(e -> realizarLogin());

        // janela visível
        janela.setVisible(true);
    }

    //MÉTODOS-----------------------------------------------------------
    private void realizarLogin() {
        try {
            // Pega os dados que o usuário inseriu na tela.
            String nome = nomeField.getText();
            String codCpf = codCpfField.getText();

            //verifica se os campos estão vazios.
            if (nome.trim().isEmpty() || codCpf.trim().isEmpty()) {
                // Se estiverem vazios,Erro de Entrada.
                throw new EntradaException("Nome e ID/CPF não podem estar vazios.");
            }


            // qual  está selecionado
            if (medicoRadioBotao.isSelected()) {
                loginMedico(nome, codCpf);
            } else { // Se o de médico não está, então é o de paciente
                loginPaciente(nome, codCpf);
            }

            // erros
        } catch (CredenciaisException | EntradaException ex) {
            JOptionPane.showMessageDialog(janela, ex.getMessage(), "Erro de Login", JOptionPane.ERROR_MESSAGE);

        } catch (NumberFormatException ex) {
            //texto no campo de ID do médico.
            JOptionPane.showMessageDialog(janela, "O ID do médico deve ser um número.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void loginMedico(String nome, String idStr) throws CredenciaisException {
        int id = Medico.parseCodigo(idStr); // Converte o ID para número.
        Medico medico = Medico.encontrarPorCodigo(medicos, id); // Busca médico

        // encontrou o médico e o nome correto
        if (medico != null && medico.getNome().equalsIgnoreCase(nome)) {
            JOptionPane.showMessageDialog(janela, "Login de médico bem-sucedido!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            new TelaMedico(medico, consultas); // Abre tela do médico.
            janela.dispose(); // Fecha tela de login.
        } else {
            //erro de credenciais.
            throw new CredenciaisException("Médico não encontrado ou informações incorretas.");
        }
    }

    private void loginPaciente(String nome, String cpf) throws CredenciaisException {
        Paciente paciente = Paciente.encontrarPorCpf(pacientes, cpf); // Busca paciente na lista.

        //encontrou o paciente e o nome correto
        if (paciente != null && paciente.getNome().equalsIgnoreCase(nome)) {
            JOptionPane.showMessageDialog(janela, "Login de paciente bem-sucedido!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            new TelaPaciente(paciente, consultas, medicos); //abre a tela do paciente.
            janela.dispose(); // Fecha tela de login.
        } else {
            // erro de credenciais.
            throw new CredenciaisException("Paciente não encontrado ou informações incorretas.");
        }
    }
}