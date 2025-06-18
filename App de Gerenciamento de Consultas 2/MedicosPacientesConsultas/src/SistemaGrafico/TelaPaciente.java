// MedicosPacientesConsultas/src/SistemaGrafico/TelaPaciente.java
package SistemaGrafico;

import Dados.*;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class TelaPaciente {

    private final JFrame janela;
    private final JTextArea displayArea;
    private final Paciente paciente;
    private final ArrayList<Consulta> todasAsConsultas;
    private final ArrayList<Medico> todosOsMedicos;

    //CONTRUTOR---------------------------------------------
    public TelaPaciente(Paciente paciente, ArrayList<Consulta> todasAsConsultas, ArrayList<Medico> todosOsMedicos) {
        this.paciente = paciente;
        this.todasAsConsultas = todasAsConsultas;
        this.todosOsMedicos = todosOsMedicos;

        //CRIAÇÃO E CONFIGURAÇÃO--------------------------------------
        janela = new JFrame("Painel do Paciente: " + paciente.getNome());
        janela.setSize(600, 400);
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        janela.setLayout(null);

        //Área de Texto com Barra de Rolagem --------------
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBounds(20, 20, 545, 280);
        janela.add(scrollPane);

        //Botões---------------------------------
        JButton btnAgendadas = new JButton("Consultas Agendadas");
        btnAgendadas.setBounds(90, 320, 200, 30);
        janela.add(btnAgendadas);

        JButton btnPassadas = new JButton("Consultas Passadas");
        btnPassadas.setBounds(310, 320, 200, 30);
        janela.add(btnPassadas);

        //Ações dos botões----------------------
        btnAgendadas.addActionListener(e -> verConsultasAgendadas());
        btnPassadas.addActionListener(e -> verConsultasPassadas());

        //janela visível
        janela.setVisible(true);
    }

    private void verConsultasAgendadas() {
        ArrayList<Consulta> agendadas = Consulta.consultasAgendadasPaciente(todasAsConsultas, paciente);
        StringBuilder sb = new StringBuilder("Suas consultas agendadas:\n\n");
        if (agendadas.isEmpty()) {
            sb.append("Nenhuma consulta agendada encontrada.");
        } else {
            for (Consulta c : agendadas) {
                Medico m = Medico.encontrarPorCodigo(todosOsMedicos, c.getCodigoMedico());
                sb.append("Médico: ").append(m != null ? m.getNome() : "Não encontrado")
                        .append(" | Data: ").append(c.dataHorarioFormatado()).append("\n");
            }
        }
        displayArea.setText(sb.toString());
        salvarRelatorio("Relatório de Consultas Agendadas", sb.toString());
    }

    private void verConsultasPassadas() {
        // Pega a lista de médicos com quem o paciente já consultou
        ArrayList<Medico> medicosDoPaciente = Medico.filtrarPorPaciente(paciente, todosOsMedicos);
        if (medicosDoPaciente.isEmpty()) {
            JOptionPane.showMessageDialog(janela, "Você não tem histórico com nenhum médico.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // --- INÍCIO DA CORREÇÃO ---
        // 1. Criamos um array de Strings apenas com os NOMES dos médicos
        String[] nomesDosMedicos = new String[medicosDoPaciente.size()];
        for (int i = 0; i < medicosDoPaciente.size(); i++) {
            nomesDosMedicos[i] = medicosDoPaciente.get(i).getNome();
        }

        // 2. Mostramos a caixa de diálogo de seleção com a lista de NOMES
        String nomeSelecionado = (String) JOptionPane.showInputDialog(
                janela,
                "Selecione o médico para ver o histórico:",
                "Seleção de Médico",
                JOptionPane.PLAIN_MESSAGE,
                null,
                nomesDosMedicos, // Usando a lista de nomes aqui
                nomesDosMedicos[0]
        );

        // 3. Se o usuário escolheu um nome, encontramos o objeto Medico correspondente
        if (nomeSelecionado != null) {
            Medico medicoSelecionado = null;
            for (Medico m : medicosDoPaciente) {
                if (m.getNome().equals(nomeSelecionado)) {
                    medicoSelecionado = m;
                    break; // Achamos o médico, podemos parar o loop
                }
            }
            // --- FIM DA CORREÇÃO ---

            // O resto do código continua igual, mas agora usando o medicoSelecionado que encontramos
            if (medicoSelecionado != null) {
                ArrayList<Consulta> passadas = Consulta.consultasPassadasPacienteMedico(todasAsConsultas, paciente, medicoSelecionado);
                StringBuilder sb = new StringBuilder("Histórico com Dr(a). " + medicoSelecionado.getNome() + ":\n\n");
                if (passadas.isEmpty()) {
                    sb.append("Nenhuma consulta passada encontrada com este médico.");
                } else {
                    for (Consulta c : passadas) {
                        sb.append("Data: ").append(c.dataHorarioFormatado()).append("\n");
                    }
                }
                displayArea.setText(sb.toString());
                salvarRelatorio("Relatório de Consultas Passadas com " + medicoSelecionado.getNome(), sb.toString());
            }
        }
    }

    private void salvarRelatorio(String titulo, String conteudo) {
        try {
            Relatorio.gravarLog(titulo, conteudo);
            JOptionPane.showMessageDialog(janela, "Relatório salvo em 'relatorio_consultas.txt'", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(janela, "Erro ao salvar o relatório.", "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}