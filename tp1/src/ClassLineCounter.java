import java.io.*;
import java.util.regex.Pattern;
public class ClassLineCounter {
    protected String[] run(String directory, String extension) throws IOException {
        File dir = new File(directory);
        String[] result = new String[5];
        result[0] = directory;
        result[1] = "className";

        if(dir.exists()) {
            int lineSum = this.processDirectory(dir,extension)[0];
            int commentSum = this.processDirectory(dir,extension)[1];
            float density = (float)commentSum / (float)lineSum;
            System.out.println("Lines Of Code with comment Line : " + lineSum);
            System.out.println("Lines Of Only Comments : " + commentSum);
            System.out.println("Density Of Comments(CLOC / LOC) : " + density);
            result[2] = Integer.toString(lineSum);
            result[3] = Integer.toString(commentSum);
            result[4] = Float.toString(density);

            return result;
        } else {
            System.err.printf("%s there is no directory", directory);
            return result;
        }
    }
    private int[] processDirectory(File directory, String extension) throws IOException {
        File[] files = directory.listFiles();
        int lineCount = 0;
        int commentCount = 0;
        int[] count = new int[2];
        for(File file : files) {
            //if it's directory, it will be recursive call.
            if(file.isDirectory()) {
                lineCount += this.processDirectory(file, extension)[0];
                commentCount += this.processDirectory(file, extension)[1];
            } else {
                // if it's normal file, not directory
                if(file.getName().toLowerCase().endsWith("." + extension.toLowerCase())) {
                    lineCount += this.countLines_LOC(file);
                    commentCount += this.countComments_CLOC(file);
                }
            }
        }
        count[0] = lineCount;
        count[1] = commentCount;
        return count;
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

