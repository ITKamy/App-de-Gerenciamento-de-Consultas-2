// Conteúdo para o arquivo: src/Dados/CsvReaderWriter.java

package Dados;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CsvReaderWriter {

    private final String caminhoArquivo;

    public CsvReaderWriter(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public void gravar(List<String[]> dados) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.caminhoArquivo), StandardCharsets.UTF_8))) {
            writer.write('\uFEFF'); // BOM para o Excel ler acentos corretamente
            for (String[] linha : dados) {
                writer.write(String.join(";", linha));
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao gravar arquivo CSV: " + e.getMessage(), "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<String[]> ler() {
        List<String[]> dados = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(this.caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("\uFEFF")) {
                    linha = linha.substring(1);
                }
                dados.add(linha.split(";"));
            }
        } catch (IOException e) {
            if (!(e instanceof java.io.FileNotFoundException)) {
                JOptionPane.showMessageDialog(null, "Erro ao ler arquivo CSV: " + e.getMessage(), "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
            }
        }
        return dados;
    }
}