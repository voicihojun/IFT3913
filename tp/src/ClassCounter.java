import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ClassCounter {

    public String commentPattern = ".*(\\/\\*([^*]|[\\r\\n]|(\\*+([^*\\/]|[\\r\\n])))*\\*+\\/).*|.*(\\/\\/).*";
    public String classNamePattern = ".*[\\s](class|enum|interface)[\\s].*";
    public static ArrayList<ClassCountData> countDataList = new ArrayList<>();

    public ArrayList<ClassCountData> getData(String filePath) {
        File file = new File(filePath);

        ClassCountData countData = null;
        ArrayList<ClassCountData> countDataList = new ArrayList<>();
        try {
            if(file.isDirectory()) {
                countDataList = generatedDataByDirectory(file);
            } else {
                countData = generateData(file);
                countDataList.add(countData);
            }
        } catch (IOException e) {
            System.out.println("ClassCounter.getData(filePath"+filePath+")" + e.getMessage()) ;
        }
        return countDataList;
    }

    private ArrayList<ClassCountData> generatedDataByDirectory(File directory) throws IOException {
        File[] files = directory.listFiles();

        for(File file : files) {
            if(file.isDirectory()) {
                generatedDataByDirectory(file);
            } else {

                ClassCountData countData = generateData(file);
                if (countData != null) {
                    countDataList.add(countData);
                }
            }
        }
        return countDataList;
    }

    private ClassCountData generateData(File file) throws IOException {
        if(!file.getName().endsWith("java")) {
            return null;
        }
        BufferedReader bufferedReader = null;
        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        String line= "";
        String className = "";
        int countLineOfCode = 0;
        int countLineOfComment = 0;
//        String className = file.getName().replace(".java", "");

        while((line = bufferedReader.readLine()) != null) {
            if(!line.matches(commentPattern) && line.matches(classNamePattern)) {
                String[] splitLine = line.split(" ");
                for(int i = 0; i < splitLine.length; i++) {
                    if (splitLine[i].equals("class")) {
                        className = splitLine[i+1].replace("{", "").trim();
                        break;
                    }
                }
            }

            if(!line.trim().isEmpty()) {
                countLineOfCode++;
            }

            if(Pattern.matches(commentPattern, line)) {
                countLineOfComment++;
            }
        }
        ClassCountData data =  new ClassCountData(file.getPath(), className, countLineOfCode, countLineOfComment);
        return data;
    }
}
