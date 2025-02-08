import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;

public class Automatizar_Teste_Threads {

    public static void main(String[] args) {
        String classpath = "bin;lib\\*";
        String mainClass = "BenchmarkNumerosPrimos";
        int maxValue = 40;
        int endValue = 110000;
        String workingDirectory = "C:\\Users\\bruns\\Downloads\\Analise-desempenho-de-sistemas\\ADS-Lab1"; // Diretório de trabalho (Altere conforme a localização dele no seu dispositivo)

        for (int startValue = 2; startValue <= maxValue; startValue += 2) {
            try {
                // Construindo o comando
                String command = String.format(
                        "java -cp \"%s\" %s %d %d",
                        classpath, mainClass, startValue, endValue
                );

                System.out.println("Executando comando: " + command);

                // Criando o ProcessBuilder
                ProcessBuilder pb = new ProcessBuilder(command.split(" "));
                pb.directory(new File(workingDirectory)); // Configurando o diretório de trabalho
                Process process = pb.start();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream())
                );
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                int exitCode = process.waitFor();
                System.out.println("Comando terminou com código de saída: " + exitCode);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
