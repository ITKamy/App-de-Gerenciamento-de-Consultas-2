package Dados;

import java.io.FileWriter; //Caracteres em um arquivo
import java.io.IOException; // Erros
import java.io.PrintWriter; //
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Relatorio {

    private static final String NOME_ARQUIVO = "relatorio_consultas.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Método que usa throws
    //possiveis erros ()
    public static void gravarLog(String titulo, String conteudo) throws IOException {

        try (FileWriter fileWriter = new FileWriter(NOME_ARQUIVO, true); //evitar vazamento de resources (fecha)
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println("==========================================================");
            printWriter.printf("Relatório Gerado em: %s\n", LocalDateTime.now().format(FORMATTER));
            printWriter.printf("Tipo de Relatório: %s\n", titulo);
            printWriter.println("----------------------------------------------------------");
            printWriter.println(conteudo);
            printWriter.println("==========================================================\n");
        }
        // A exceção IOException é repassada (throws)
    }
}