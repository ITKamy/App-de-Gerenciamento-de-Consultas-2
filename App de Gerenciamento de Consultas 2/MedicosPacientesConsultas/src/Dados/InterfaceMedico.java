 /*package Dados; Interface Médico
[1] Pacientes
[2] Consultas Agendadas
[3] Pacientes Inativos (que não vem as 6M)


// IMPORTS =============================================================================================================
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class InterfaceMedico {
    static Scanner teclado = new Scanner(System.in);

    //Variáveis Importadas==============================================================================================
    public static int CodMed = InterfaceLogin.idMed;
    public static String NomeMed = InterfaceLogin.nomeMed;

    //Mensagens ========================================================================================================
    //static String mensagemInicio = "Olá " + NomeMed + CodMed + ", digite o número do que gostaria de acessar? \n[1] Lista de Pacientes \n[2] Consultas \n[3] Lista de Pacientes Inativos\n-> ";
    //static String mensagemInvalida = "Por favor digite uma alternativa válida";
    //static String mensagemListaPacientes = NomeMed + ", aqui está sua lista de pacientes:";
    //static String mensagemConsultas = "A seguir, suas consultas:";
    //static String mensagemPacientesInativos = "Aqui estão seus pacientes inativos há pelo menos 6(seis) meses:";

    //Interface Inicial=================================================================================================
    public static void Inicio(Medico medico, ArrayList<Consulta> consultas) {
        CodMed = medico.codigo;
        NomeMed = medico.nome;
        System.out.print("\n\nOlá " + NomeMed + " (" + Medico.formatarCodigo(CodMed) + "), digite o número do que gostaria de acessar? \n[1] Lista de Pacientes \n[2] Consultas \n[3] Lista de Pacientes Inativos\n-> ");

        int escolha = teclado.nextInt();

        //Resposta Inválida=============================================================================================
        if (escolha != 1 && escolha != 2 && escolha != 3) {
            System.out.println("Por favor digite uma alternativa válida");
            Inicio(medico, consultas);
        }

        //Encaminhamento das Opções ====================================================================================
        if (escolha == 1) {
            ListaPacientes(medico);
        } else if (escolha == 2) {
            Consultas(medico, consultas);
        } else {
            System.out.print("Gostaria de verificar a atividade em quantos meses?\n-> ");
            int meses = teclado.nextInt();
            pacientesInativos(meses, consultas, medico);
        }
        Inicio(medico, consultas);
    }

    //Opções ===========================================================================================================
    //Interface - Lista de Pacientes====================================================================================
    public static void ListaPacientes(Medico medico) {
        System.out.print(NomeMed + ", aqui está sua lista de pacientes:");
        System.out.print("\n");
        //System.out.println("CPF: " + medico.pacientes);
        //Formatar
        for (Paciente paciente : medico.pacientes) {
            System.out.println(paciente.nome + "|" + paciente.cpf);
            // Imprimir só nome e CPF? -> paciente.nome e paciente.cpf
        }
    }

    //Interface - Consultas=============================================================================================
    public static void Consultas(Medico medico, ArrayList<Consulta> todasAsConsultas) {
        ArrayList<Consulta> consultasDesteMedico = Consulta.encontrarPorMedico(todasAsConsultas,medico);
        for(Consulta consulta : consultasDesteMedico)
            System.out.println("Consulta do paciente de CPF: " + consulta.cpfPaciente + ", no dia " + consulta.dataHorarioFormatado());
    }

    //Interface - Lista de Pacientes Inativos===========================================================================
    /*public static void PacientesInativos(Medico medico, ArrayList<Consulta> todasAsConsultas) {

            System.out.print("Aqui estão seus pacientes inativos há pelo menos 6(seis) meses:");
        }

    public static void pacientesInativos(int meses, ArrayList<Consulta> todasAsConsultas, Medico medico) {
        ArrayList<Paciente> inativos = medico.pacientesInativos(meses, todasAsConsultas);

        if (inativos.size() == 0) {
            System.out.println("Não existem pacientes inativos em "+meses+" meses.");
            return;
        }

        System.out.println("Pacientes inativos há pelo menos " + meses + " meses:");
        for (Paciente paciente : inativos) {
            ArrayList<Consulta> consultas = Consulta.consultasPassadasPacienteMedico(todasAsConsultas, paciente, medico);
            if (!consultas.isEmpty()) {
                Consulta ultimaConsulta = consultas.get(consultas.size() - 1);
                System.out.println("Paciente inativo: " + paciente.cpf +
                        " - Última consulta: " + ultimaConsulta.dataHorario.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            }
        }
    }
}
*/
