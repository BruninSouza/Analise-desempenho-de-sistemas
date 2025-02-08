import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;

public class Automatizar_Testes_Primos {

    public static void main(String[] args) {
        String classpath = "bin;lib\\*";
        String mainClass = "BenchmarkNumerosPrimos";
        String workingDirectory = "C:\\Users\\bruns\\Downloads\\Analise-desempenho-de-sistemas\\ADS-Lab1"; // coloque o endereço da pasta no seu dispositivo

        int[] startValues = {6, 36}; // Numeros de threads escolhidos
        int initialIncrement = 10000;
        int finalIncrement = 100000;
        int firstLimit = 100000;
        int finalLimit = 1000000;

        for (int startValue : startValues) {
            // Valores de 10000 a 100000 com incremento de 10000
            for (int limit = 10000; limit < firstLimit; limit += initialIncrement) {
                executeCommand(classpath, mainClass, workingDirectory, startValue, limit);
            }

            // Valores de 100000 a 1000000 com incremento de 100000
            for (int limit = 100000; limit <= finalLimit; limit += finalIncrement) {
                executeCommand(classpath, mainClass, workingDirectory, startValue, limit);
            }
        }
    }

    private static void executeCommand(String classpath, String mainClass, String workingDirectory, int startValue, int limit) {
        try {
            
            String command = String.format(
                    "java -cp \"%s\" %s %d %d",
                    classpath, mainClass, startValue, limit
            );

            System.out.println("Executando comando: " + command);

            ProcessBuilder pb = new ProcessBuilder(command.split(" "));
            pb.directory(new File(workingDirectory));
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
