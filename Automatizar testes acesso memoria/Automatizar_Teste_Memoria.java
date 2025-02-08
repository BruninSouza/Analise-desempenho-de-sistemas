import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;

public class Automatizar_Teste_Memoria {

    public static void main(String[] args) {
        String classpath = "bin/;lib/*";
        String mainClass = "BenchmarkAcessoMemoria";
        String workingDirectory = "C:\\Users\\bruns\\Downloads\\Analise-desempenho-de-sistemas\\ADS-Lab1";

        int firstLimit = 102400;
        int secondLimit = 921600;
        int finalValue = 1048576;
        int initialIncrement = 10240;
        int secondIncrement = 102400;

        // Executando o teste inicial com 1024
        executeCommand(classpath, mainClass, workingDirectory, 1024);

        // Executando os testes com incremento de 10240 até 102400, sem incluir 102400 no final
        for (int startValue = 10240; startValue < firstLimit; startValue += initialIncrement) {
            executeCommand(classpath, mainClass, workingDirectory, startValue);
        }

        // Executando o valor 102400 exatamente uma vez
        executeCommand(classpath, mainClass, workingDirectory, firstLimit);

        // Executando os testes com incremento de 102400 até 921600, iniciando após 102400
        for (int startValue = firstLimit + secondIncrement; startValue <= secondLimit; startValue += secondIncrement) {
            executeCommand(classpath, mainClass, workingDirectory, startValue);
        }

        // Executando o teste com valor fixo de 1048576
        executeCommand(classpath, mainClass, workingDirectory, finalValue);
    }

    private static void executeCommand(String classpath, String mainClass, String workingDirectory, int startValue) {
        try {
            // Limite fixo
            int endValue = 110000000;

            // Construindo o comando
            String command = String.format(
                    "java -cp \"%s\" %s %d %d",
                    classpath, mainClass, startValue, endValue
            );

            System.out.println("Executando comando: " + command);

            // Criando o ProcessBuilder
            ProcessBuilder pb = new ProcessBuilder(command.split(" "));
            pb.directory(new File(workingDirectory));
            Process process = pb.start();

            // Lendo a saída do programa
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Esperando o processo terminar
            int exitCode = process.waitFor();
            System.out.println("Comando terminou com código de saída: " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
