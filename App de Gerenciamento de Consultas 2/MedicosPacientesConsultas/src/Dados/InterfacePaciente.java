/*
package Dados;

//testando se salvou a  branch


// Imports ----------------------------------------------------------------------
import java.util.Scanner;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;


// Início ----------------------------------------------------------------------
public class InterfacePaciente{
    static Scanner teclado = new Scanner(System.in);


    // Variáveis -----------------------------------------------------------------
    public static String CpfPaciente = InterfaceLogin.CpfPaciente;
    public static String NomePaciente = InterfaceLogin.nomePaciente;


    // Formatter para datas (supondo que esteja sendo usado em Consulta)
    static DateTimeFormatter formatterDia = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    // Tela inicial de escolha de consultas
    public static void tiposDeConsulta(Paciente paciente, ArrayList<Consulta> consultas, ArrayList<Medico> medicos) {
        CpfPaciente = paciente.cpf;
        NomePaciente = paciente.nome;


        System.out.println("Olá, " + NomePaciente + ", escolha a opção que deseja ver:");
        System.out.println("[1] Consultas Agendadas");
        System.out.println("[2] Consultas Passadas");
        System.out.println("[3] Voltar");
        int opcao = teclado.nextInt();


        // Verifica se a opção é válida
        if (opcao != 1 && opcao != 2 && opcao != 3) {
            System.out.println("Por favor, digite uma opção válida");
            tiposDeConsulta(paciente, consultas, medicos);
            return;
        }


        // Opções
        if (opcao == 1) {
            consultasAgendadas(paciente, consultas, medicos);
        } else if (opcao == 2) {
            consultasPassadas(paciente, consultas, medicos);
        }
        tiposDeConsulta(paciente, consultas, medicos);
    }


    // Exibe consultas agendadas (futuras)
    public static void consultasAgendadas(Paciente paciente, ArrayList<Consulta> consultas, ArrayList<Medico> medicos) {
        Medico medico = selecionarMedico(medicos, paciente);

        System.out.println("Suas consultas agendadas com o médico "+medico.nome+":");


        ArrayList<Consulta> consultasAgendadas = Consulta.consultasAgendadasPaciente(consultas, paciente);


        if (consultasAgendadas.isEmpty()) {
            System.out.println("Nenhuma consulta agendada encontrada.");
        } else {
            for (Consulta consulta : consultasAgendadas) {
                System.out.println("Consulta com o médico " + medico.nome + " (código: " + Medico.formatarCodigo(medico.codigo) + ") em " + consulta.dataHorarioFormatado());
            }
        }
    }


    // Exibe consultas passadas com um médico específico
    public static void consultasPassadas(Paciente paciente, ArrayList<Consulta> consultas, ArrayList<Medico> medicos) {
        Medico medico = selecionarMedico(medicos, paciente);

        System.out.println("Suas consultas passadas com o médico " + medico.nome + ":");


        ArrayList<Consulta> consultasPassadas = Consulta.consultasPassadasPacienteMedico(consultas, paciente, medico);


        if (consultasPassadas.isEmpty()) {
            System.out.println("Nenhuma consulta passada encontrada com esse médico.");
        } else {
            for (Consulta consulta : consultasPassadas) {
                System.out.println("Consulta com o médico " + medico.nome +
                        " em " + consulta.dataHorarioFormatado());
            }
        }
    }

    public static Medico selecionarMedico(ArrayList<Medico> todosOsMedicos, Paciente paciente) {
        ArrayList<Medico> medicosDoPaciente = Medico.filtrarPorPaciente(paciente, todosOsMedicos);
        System.out.println("Selecione o médico a verificar: ");
        int i = 1;
        for (Medico medico : medicosDoPaciente) {
            System.out.println(i+" - "+medico.nome+" ("+Medico.formatarCodigo(medico.codigo)+")");
        }
        System.out.print("\n-> ");
        int resultado = teclado.nextInt()-1;
        if (resultado < 0 || resultado > medicosDoPaciente.size() ) {
            System.out.println("Por favor insira um valor válido");
            return selecionarMedico(todosOsMedicos, paciente);
        }
        return medicosDoPaciente.get(resultado);
    }
}
*/