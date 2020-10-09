import java.io.*;
import java.util.ArrayList;

public class MethodCounter {
    public String methodPattern = "^[\\s]*(public|private|protected|static|void)+.*\\{$";
    public String classPattern = ".*[\\s](class|enum|interface)[\\s].*";
    public String constructorPattern = ".*[\\s](public|private|protected)[\\s][\\w]*\\([\\w\\s<>,]*\\)\\s?\\{";
    public String commentPattern = ".*(\\/\\*([^*]|[\\r\\n]|(\\*+([^*\\/]|[\\r\\n])))*\\*+\\/).*|.*(\\/\\/).*";
    public String openBracePattern = ".*\\s\\{\\s?.*";
    public String invalidOpenBracePattern = ".*\".*\\{.*\" .*";
    public String closeBracePattern = ".*\\s\\}\\s?.*";
    public String invalidCloseBracePattern = ".*\".*\\}.*\".*";
    public String ccPattern = "(.*if.*|.*while.*)";
    public String invalidCcPattern = ".*(\\/\\/)\\s*(if|while).*";
    public String invalidCcPattern2 = ".*\"(.*if.*|.*while.*)\".*";
    public String commentPatternForMultiLines = ".*(\\/\\*([^*]|[\\r\\n]|(\\*+([^*\\/]|[\\r\\n])))*\\*+\\/).*";

    public static ArrayList<MethodCountData> countDataList = new ArrayList<>();

    /**
     *
     * @param filePath
     * @return ArrayList donnée des methodes
     */
    public ArrayList<MethodCountData> getData(String filePath) {
        File file = new File(filePath);

        ArrayList<MethodCountData> countDataList = new ArrayList<>();
        try {
            if(file.isDirectory()) {
                countDataList = generatedDataByDirectory(file);
            } else {
                countDataList = generateData(file);
            }
        } catch (IOException e) {
            System.out.println("MethodCounter.getData(filePath"+filePath+")" + e.getMessage()) ;
        }
        return countDataList;
    }

    /**
     *
     * @param directory
     * @return
     * @throws IOException
     */
    private ArrayList<MethodCountData> generatedDataByDirectory(File directory) throws IOException {
        File[] files = directory.listFiles();

        for(File file : files) {
            if(file.isDirectory()) {
                generatedDataByDirectory(file);
            } else {
                if(generateData(file) != null) {
                    countDataList.addAll(generateData(file));
                }
            }
        }

        return countDataList;
    }

    /**
     *
     * @param braces open : {, close: } // Quand le nombre de open et de close est le même, un methode est fini.
     * @return true ou false
     */
    private boolean braceIsValid(int[] braces) {
        int open = braces[0];
        int close = braces[1];
        return open == close;
    }

    /**
     *
     * @param file
     * @return ArrayList de MethodCountData incluant chemin, class, method, method LOC / CLOC / DC / CC / BC
     * @throws IOException
     * En utilisant regular expression, identifier et calculer le nom de method et LOC / CLOC / CC,
     * et avec les informations au dessus, on peut obtenir BC / DC
     */
    /*
    alksdflakjs
    asdlkfjalskdjf
            asldkfjalskdjf */
    private ArrayList<MethodCountData> generateData(File file) throws IOException {
        if(!file.getName().endsWith("java")) {
            return null;
        }
        ArrayList<MethodCountData> methodDataList = new ArrayList<>();
        BufferedReader bufferedReader = null;
        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        String line = "";
        int cc = 1;
        int open = 0;
        int close = 0;
        int methodLineOfCode = 0;
        int methodLineOfComment = 0;
        String className = "";
        int[] braces = {open, close};

        while((line = bufferedReader.readLine()) != null) {
            String methodName = "";

            if(!line.matches(commentPattern) && line.matches(classPattern)) {
                String[] splitLine = line.split(" ");
                for(int i = 0; i < splitLine.length; i++) {
                    if (className.isEmpty() && splitLine[i].equals("class")) {
                        className = splitLine[i+1].replace("{", "").trim();
                        break;
                    }
                }
            }

            if(line.matches(methodPattern) && !line.matches(classPattern) && !line.matches(constructorPattern)) {
                String[] methodNameSplit = line.trim().split(" ");

                for(String name: methodNameSplit) {
                    if(name.contains("(")){
                        methodName = name.substring(0, name.lastIndexOf("("));
                    }
                }

                methodLineOfCode++;
                open++;
                braces[0] = open;

                if(line.matches(commentPattern)) {
                    methodLineOfComment++;
                }

                while(!braceIsValid(braces)) {
                    if((line = bufferedReader.readLine()) != null) {
                        if(line.matches(openBracePattern) && !line.matches(invalidOpenBracePattern)) {
                            open++;
                            braces[0] = open;
                        }
                        if(line.matches(closeBracePattern) && !line.matches(invalidCloseBracePattern)) {
                            close++;
                            braces[1] = close;
                        }
                        if(!line.isEmpty()) {
                            methodLineOfCode++;
                        }
                        if(!line.isEmpty() && line.matches(commentPattern)) {
                            methodLineOfComment++;
                        }
                        if(!line.matches(commentPatternForMultiLines) && !line.matches(invalidCcPattern2) && line.matches(ccPattern) && !line.matches(invalidCcPattern)) {
                            cc++;
                        }
                    }
                }

                MethodCountData data = new MethodCountData(
                        file.getPath(), className, methodName, methodLineOfCode, methodLineOfComment, cc
                );
                methodDataList.add(data);

                open = 0;
                close = 0;
                cc = 1;
                methodLineOfComment = 0;
                methodLineOfCode = 0;
                braces[0] = open;
                braces[1] = close;
            }
        }
        return methodDataList;
    }

}
