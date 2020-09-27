
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriteCSV {
    public static void writeDataToCsv(String label, String[] data, String filepath) throws IOException {
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
        for(int i = 0; i < data.length; i++) {
            fw.append(data[i]);
            if(i != data.length - 1) {
                fw.append(",");
            } else {
                fw.append("\n");
            }
        }
        fw.flush();
        fw.close();
    }
}
