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

}
