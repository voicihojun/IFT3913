
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriteCSV {
    public static void writeDataToCsv(String label, ArrayList<Classe> data, String filepath) throws IOException {
        FileWriter fw = new FileWriter(filepath);
        String[] arrayLabel = label.split("#");
        for(int i = 0; i < arrayLabel.length; i++) {
            fw.append(arrayLabel[i]);
            if(i != arrayLabel.length - 1) {
                fw.append(",");
            } else {
                fw.append("\n");
            }
        }
        for(int i = 0; i < data.size(); i++) {
            Classe classe = data.get(i);
            fw.append(classe.getPath());
            fw.append(",");
            fw.append(classe.getClassName());
            fw.append(",");
            fw.append("" + classe.getLineOfClass());
            fw.append(",");
            fw.append("" + classe.getLineOfComment());
            fw.append(",");
            fw.append("" + classe.getDensity());
            fw.append("\n");

        }
        fw.flush();
        fw.close();
    }
}
