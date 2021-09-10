package util;

public class ToolsUtils {

    public static String[] titleFormat(String fileName, int numbersAuthors) {

        String nameFormatted[] = new String[numbersAuthors];

        nameFormatted = fileName.split("_");
        int tamanho = nameFormatted.length - 1;
        nameFormatted[tamanho] = nameFormatted[tamanho].replace(".mp3", "");

        if (tamanho > 2) {
            for (int i = 3; i <= tamanho; i++) {
                nameFormatted[2] += ", " + nameFormatted[i];
            }
        }

        return nameFormatted;
    }

    public static String hourFormatted(double tempoTotalSegundos) {

        String tempo = "";
        int segundos = (int) tempoTotalSegundos % 60; // Pego os segundos restantes
        tempo = String.format("%d:" + (segundos < 10 ? "0" : "") + "%d", (int) (tempoTotalSegundos / 60), segundos);

        return tempo;

    }

}
