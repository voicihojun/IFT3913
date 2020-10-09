
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Counter {
    /**
     * En utilisant les methodes writeClassCSV, writeethodCSV, cela écrit les donneés dans les fichiers de csv
     * @param filePath
     */
    public void run(String filePath){
        ClassCounter classCounter = new ClassCounter();
        MethodCounter methodCounter = new MethodCounter();

        ArrayList<ClassCountData> countDataList = classCounter.getData(filePath);
        ArrayList<MethodCountData> methodCountDataList = methodCounter.getData(filePath);

        try {
            writeClassCSV("class.csv", countDataList);
            writeMethodCSV("method.csv", methodCountDataList);
        } catch(Exception e){
            System.out.println("Counter.run::" + e.getMessage());
        }
    }

    /**
     * Cette methode est pour écrire les données dans le fichier de class.csv
     * @param filePath
     * @param countDataList
     * @throws IOException
     */
    private void writeClassCSV(String filePath, ArrayList<ClassCountData> countDataList) throws IOException {
        String label ="chemin,class,classe_LOC,classe_CLOC,classe_DC,WMC,classe_BC";

        FileWriter fw = new FileWriter(filePath);
        String[] arrayLabel = label.split(",");
        for(int i = 0; i < arrayLabel.length; i++) {
            fw.append(arrayLabel[i]);
            if(i != arrayLabel.length - 1) {
                fw.append(",");
            } else {
                fw.append("\n");
            }
        }
        for(ClassCountData data : countDataList) {
            fw.append(data.getClassPath());
            fw.append(",");
            fw.append(data.getClassName());
            fw.append(",");
            fw.append("" + data.getCountLineOfCode());
            fw.append(",");
            fw.append("" + data.getCountLineOfComment());
            fw.append(",");
            fw.append("" + data.getDensity());
            fw.append(",");
            fw.append("" + data.getWmc());
            fw.append(",");
            fw.append("" + data.getClassBc());
            fw.append("\n");
        }
        fw.flush();
        fw.close();
    }

    /**
     * Cette methode est pour écrire les données dans le fichier de method.csv
     * @param filePath
     * @param countDataList
     * @throws IOException
     */
    private void writeMethodCSV(String filePath, ArrayList<MethodCountData> countDataList) throws IOException {
        String label ="chemin,class,methode,methode_LOC,methode_CLOC,methode_DC,CC,methode_BC";

        FileWriter fw = new FileWriter(filePath);
        String[] arrayLabel = label.split(",");
        for(int i = 0; i < arrayLabel.length; i++) {
            fw.append(arrayLabel[i]);
            if(i != arrayLabel.length - 1) {
                fw.append(",");
            } else {
                fw.append("\n");
            }
        }
        for(MethodCountData data : countDataList) {
            fw.append(data.getClassPath());
            fw.append(",");
            fw.append(data.getClassName());
            fw.append(",");
            fw.append(data.getMethodName());
            fw.append(",");
            fw.append("" + data.getCountLineOfCode());
            fw.append(",");
            fw.append("" + data.getCountLineOfComment());
            fw.append(",");
            fw.append("" + data.getDensity());
            fw.append(",");
            fw.append("" + data.getCyclomaticComplexity());
            fw.append(",");
            fw.append("" + data.getMethodBc());
            fw.append("\n");
        }
        fw.flush();
        fw.close();
    }
}
