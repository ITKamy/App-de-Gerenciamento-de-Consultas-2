package SistemaGrafico;

import Dados.*;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class TelaMedico {
    private final JFrame janela;
    private final JTextArea displayArea;
    private final Medico medico;
    private final ArrayList<Consulta> todasAsConsultas;

    //CONSTRUTOR---------------------------------
    public TelaMedico(Medico medico, ArrayList<Consulta> todasAsConsultas) {
        this.medico = medico;
        this.todasAsConsultas = todasAsConsultas;

        //CRIAÇÃO E CONFIGURAÇÃO------------------------------
        janela = new JFrame("Painel do Médico: " + medico.getNome());
        janela.setSize(600, 400); //tamanho da janela
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // DISPOSE_ON_CLOSE fecha só esta janela
        janela.setLocationRelativeTo(null); //Centraliza a janela na tela
        janela.setLayout(null); // Layout manual

        //Tela de Texto com Barra de Rolagem ------------------
        displayArea = new JTextArea();
        displayArea.setEditable(false); // O usuário não pode digitar aqui
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBounds(20, 20, 545, 280); // Posição e tamanho da área de texto
        janela.add(scrollPane);

        //Botões-------------------------------------------
        JButton btnListaPacientes = new JButton("Listar Pacientes");
        btnListaPacientes.setBounds(20, 320, 170, 30); // Posição e tamanho do botão
        janela.add(btnListaPacientes);

        JButton btnConsultas = new JButton("Ver Todas as Consultas");
        btnConsultas.setBounds(205, 320, 170, 30);
        janela.add(btnConsultas);

        JButton btnPacientesInativos = new JButton("Ver Pacientes Inativos");
        btnPacientesInativos.setBounds(390, 320, 175, 30);
        janela.add(btnPacientesInativos);

        //Ações dos botões---------------------------
        //executar método quando cada botão for clicado:
        btnListaPacientes.addActionListener(e -> listarPacientes());
        btnConsultas.addActionListener(e -> verConsultas());
        btnPacientesInativos.addActionListener(e -> verPacientesInativos());

        //janela visível
        janela.setVisible(true);
    }

    //Quais são os pacientes
    private void listarPacientes() {
        StringBuilder sb = new StringBuilder("Lista de Pacientes de " + medico.getNome() + ":\n\n");
        for (Paciente p : medico.getPacientes()) {
            sb.append(p.toString()).append("\n");
        }
        displayArea.setText(sb.toString());
        salvarRelatorio("Lista de Pacientes", sb.toString());
    }

    //Consultas marcadas
    private void verConsultas() {
        StringBuilder sb = new StringBuilder("Todas as consultas do(a) Dr(a). " + medico.getNome() + ":\n\n");
        ArrayList<Consulta> consultasDoMedico = Consulta.encontrarPorMedico(todasAsConsultas, medico);

        if (consultasDoMedico.isEmpty()) {
            sb.append("Nenhuma consulta encontrada.");
        } else {
            for (Consulta c : consultasDoMedico) {
                sb.append("Paciente (CPF): ").append(c.getCpfPaciente())
                        .append(" | Data: ").append(c.dataHorarioFormatado()).append("\n");
            }
        }
        displayArea.setText(sb.toString());
        salvarRelatorio("Relatório de Todas as Consultas", sb.toString());
    }

    //pacientes passados, escolha o mês
    private void verPacientesInativos() {
        String mesesStr = JOptionPane.showInputDialog(janela, "Verificar inatividade em quantos meses?");
        try {
            if (mesesStr == null || mesesStr.trim().isEmpty()) return;
            int meses = Integer.parseInt(mesesStr);

            ArrayList<Paciente> inativos = medico.pacientesInativos(meses, todasAsConsultas);
            StringBuilder sb = new StringBuilder("Pacientes inativos há pelo menos " + meses + " meses:\n\n");

            if (inativos.isEmpty()) {
                sb.append("Nenhum paciente inativo encontrado neste período.");
            } else {
                for (Paciente p : inativos) {
                    sb.append(p.toString()).append("\n");
                }
            }
            displayArea.setText(sb.toString());
            salvarRelatorio("Relatório de Pacientes Inativos (" + meses + " meses)", sb.toString());

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(janela, "Por favor, insira um número válido.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarRelatorio(String titulo, String conteudo) {
        try {
            Relatorio.gravarLog(titulo, conteudo); //Relatorio
            JOptionPane.showMessageDialog(janela, "Relatório salvo em 'relatorio_consultas.txt'", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(janela, "Erro ao salvar o relatório.", "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}