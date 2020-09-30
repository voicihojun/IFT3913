import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ClassLineCounter {
    public ArrayList<Classe> classeArrayList = new ArrayList<>();
    private static String path = "";
    private static String partOfChildPath = "";

    public ArrayList<Classe> run(String directory, String extension) throws IOException {
        path = directory;
        File dir = new File(directory);
        System.out.println("file(directory) : " + dir);
        if(dir.exists()) {
            this.processDirectory(dir, extension);
            return classeArrayList;
        } else {
            System.err.printf("%s there is no directory", directory);
            return classeArrayList;
        }
    }
    private void processDirectory(File directory, String extension) throws IOException {
        File[] files = directory.listFiles();

        for(File file : files) {

            String className = "";
            int lineCount = 0;
            int commentCount = 0;

            //if it's directory, it will be recursive call.

            if(file.isDirectory()) {
                System.out.println("+++++++++++++++++++");
                System.out.println("THERE IS DIRECTORY");
                System.out.println("+++++++++++++++++++");
                partOfChildPath = "/" + file.getName();
                File dir2 = new File(path + partOfChildPath);
                System.out.println("dir2 : " + dir2);
                System.out.println("extension : " + extension);
                this.processDirectory(dir2, extension);
            } else if(file.getName().endsWith("." + extension.toLowerCase())) {
                // if it's normal file, not directory
                className = this.findClassName(file);
                lineCount = this.countLines_LOC(file);
                commentCount = this.countComments_CLOC(file);

                Classe newClasse = new Classe(path + partOfChildPath, className, lineCount, commentCount);
                partOfChildPath = "";
                System.out.println("Path : " + newClasse.getPath());
                System.out.println("Class Name : " + className);
                System.out.println("Lines Of Code with comment Line : " + lineCount);
                System.out.println("Lines Of Only Comments : " + commentCount);
                System.out.println("Density Of Comments(CLOC / LOC) : " + newClasse.getDensity());
                System.out.println("");

                classeArrayList.add(newClasse);
            }
        }
    }

    private String findClassName(File file) throws IOException {
        // find class name with regex
        BufferedReader bufferedReader = null;
        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String className = "";
        String line = "";
        String regex = ".*[\\s](class|enum|interface)[\\s].*";
        while((line = bufferedReader.readLine()) != null) {
            if(line.matches(regex)) {
                System.out.println(file.getName());
                System.out.println(line);
                className = line
                        .replace("class", "")
                        .replace("enum", "")
                        .replace("interface", "")
                        .replace("public", "")
                        .replace("private", "")
                        .replace("protected", "")
                        .replace("{", "")
                        .trim();
                break;
            }
        }
        return className;
    }
    private int countLines_LOC(File file) throws IOException {
        // counting all the line except the empty line.
        BufferedReader bufferedReader = null;
        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        int count = 0;
        String line= "";
        while((line = bufferedReader.readLine()) != null) {
            if(line.trim().isEmpty()) {
                continue;
            } else {
                count++;
            }
        }
        return count;
    }
    private int countComments_CLOC(File file) throws IOException {
        // counting only comments
        BufferedReader bufferedReader = null;
        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        int count = 0;
        String line= "";
        String pattern = ".*\".*\\/\\/.*\".*"; // this regex pattern is for this kind of things "//". With "" it's not the comments.
        String pattern2 = ".*\\/\\/.*";  // this regex pattern is for //. this is for the comments

//        String pattern3 = "(/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/)|(//.*)";  // this pattern is for all the comments symbole
        //url for reference  https://blog.ostermiller.org/finding-comments-in-source-code-using-regular-expressions/
        while((line = bufferedReader.readLine()) != null) {
            boolean pattern_check = Pattern.matches(pattern, line);
            boolean pattern_check2 = Pattern.matches(pattern2, line);
            if(!pattern_check && pattern_check2) {
                count++;
            }
        }
        return count;
    }
}

