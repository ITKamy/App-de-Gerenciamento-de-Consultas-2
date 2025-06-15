/*package Dados;

import java.util.Scanner;
import java.util.ArrayList;


public class InterfaceLogin {
    static Scanner teclado = new Scanner(System.in);


    public static String CpfPaciente;
    public static int idMed;
    public static String nomeMed;
    public static String nomePaciente;


    public static void login(ArrayList<Medico> medicos, ArrayList<Paciente> pacientes, ArrayList<Consulta> consultas) {
        System.out.print("[1] Médico \n[2] Paciente\n-> ");
        int opcao = teclado.nextInt();
        if (opcao == 1) {
            loginMed(medicos, consultas); //  passa consultas
        } else if (opcao == 2) {
            loginPaciente(pacientes, consultas, medicos); // passa consultas e medicos
        } else {
            System.out.println("Escolha uma opção válida.");
            login(medicos, pacientes, consultas);
        }
    }


    public static void loginMed(ArrayList<Medico> medicos, ArrayList<Consulta> consultas) {
        System.out.println("Bem-vindo, faça seu login como médico:");
        teclado.nextLine();
        System.out.print("Nome:\n-> ");
        String nomeMed = teclado.nextLine();
        System.out.print("ID:\n-> ");
        int idMed = Medico.parseCodigo(teclado.nextLine());

        Medico medico = Medico.encontrarPorCodigo(medicos, idMed);
        if (medico != null && medico.nome.equals(nomeMed)) {
            System.out.println("Login bem-sucedido como médico!");
            InterfaceMedico.Inicio(medico, consultas); // redireciona para a interface do médico
        } else {
            System.out.println("Médico não encontrado ou informações incorretas.");
        }
    }


    public static void loginPaciente(ArrayList<Paciente> pacientes, ArrayList<Consulta> consultas, ArrayList<Medico> medicos) {
        System.out.println("Bem-vindo, faça seu login como paciente:");
        teclado.nextLine();
        System.out.print("Nome:\n-> ");
        String nomePaciente = teclado.nextLine();
        System.out.print("CPF:\n-> ");
        String CpfPaciente = teclado.nextLine();


        Paciente paciente = Paciente.encontrarPorCpf(pacientes, CpfPaciente);
        if (paciente != null && paciente.nome.equals(nomePaciente)) {
            System.out.println("Login bem-sucedido como paciente!");
            InterfacePaciente.tiposDeConsulta(paciente, consultas, medicos); // interface do paciente
        } else {
            System.out.println("Paciente não encontrado ou informações incorretas.");
        }
    }
}
*/


