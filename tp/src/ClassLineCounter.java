import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
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
        // first for
        for(File file : files) {

            String className = "";
            int lineCount = 0;
            int commentCount = 0;
            System.out.println("in 33---------------------- : " + file.getName()+" --- extension :"+extension);
            //if it's directory, it will be recursive call.

            if(file.getName().endsWith(extension.toLowerCase())) {
                // if it's normal file, not directory
                className = this.findClassName(file);
                lineCount = this.countLines_LOC(file);
                commentCount = this.countComments_CLOC(file);

                Classe newClasse = new Classe(path + partOfChildPath, className, lineCount, commentCount);
                System.out.println("Path : " + newClasse.getPath());
                System.out.println("Class Name : " + className);
                System.out.println("Lines Of Code with comment Line : " + lineCount);
                System.out.println("Lines Of Only Comments : " + commentCount);
                System.out.println("Density Of Comments(CLOC / LOC) : " + newClasse.getDensity());
                System.out.println("");

                classeArrayList.add(newClasse);
            }
        }
        // second for
        for(File file : files) {
            String className = "";
            int lineCount = 0;
            int commentCount = 0;

            //if it's directory, it will be recursive call.
            if(file.isDirectory()) {
                System.out.println("+++++++++++++++++++");
                System.out.println("DIRECTORY FILE");
                System.out.println("+++++++++++++++++++");
                partOfChildPath = "/" + file.getName();
                path = path + partOfChildPath;
                File dir2 = new File(path);
                partOfChildPath = "";
                System.out.println("dir2 : " + dir2);
                System.out.println("extension : " + extension);
                this.processDirectory(dir2, extension);
            }
        }
    }

    @SuppressWarnings("resource")
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
    
    /*
    private int countComments_CLOC(File file) throws IOException {
        // counting only comments
        BufferedReader bufferedReader = null;
        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        int count = 0;
        String line= "";
        String pattern = ".*(\\/\\*([^*]|[\\r\\n]|(\\*+([^*\\/]|[\\r\\n])))*\\*+\\/).*|.*(\\/\\/).*";
        String pattern1 = ".*(\\/\\/).*"; // "//" regex
        String pattern2 = ".*(\\/\\*).*|.*(\\/\\**).*"; //comment open regex
        String pattern3 = ".*(\\*\\/)"; //comment close regex
        
        //url for reference  https://blog.ostermiller.org/finding-comments-in-source-code-using-regular-expressions/
         
        while((line = bufferedReader.readLine()) != null) {
            boolean pattern_check = Pattern.matches(pattern, line);
            boolean pattern_check1 = Pattern.matches(pattern1, line);
            boolean pattern_check2 = Pattern.matches(pattern2, line); //comments open
            boolean pattern_check3 = Pattern.matches(pattern3, line);
            if(pattern_check) {
                count++;
            }
           
        }
        return count;


    }
    */
    public int countComments_CLOC(File file) {
  	  List<Match> commentMatches = new ArrayList<Match>();
      Pattern commentsPattern = Pattern.compile("(//.*?$)|(/\\*.*?\\*/)", Pattern.MULTILINE | Pattern.DOTALL);
      Pattern stringsPattern = Pattern.compile("(\".*?(?<!\\\\)\")");
      List<Match> commentsToCount = new ArrayList<Match>();
     // BufferedReader bufferedReader = null;
      try {
    	  
     // bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
      String text= readFileAsString(file.getAbsolutePath());
      
		//while((line = bufferedReader.readLine()) != null) {
		      Matcher commentsMatcher = commentsPattern.matcher(text);
		      while (commentsMatcher.find()) {
		          Match match = new Match();
		          match.start = commentsMatcher.start();
		          match.text = commentsMatcher.group();
		          //System.out.println("start ---------------- "+match.start);
		          //System.out.println("text ---------------- "+match.text);

		          commentMatches.add(match);
		      }
		      
		      
		     /* System.out.println("in 170 ----commentMatches---- "+commentMatches.toString());
		      Matcher stringsMatcher = stringsPattern.matcher(text);
		      while (stringsMatcher.find()) {
		          for (Match comment : commentMatches) {
		              if (comment.start > stringsMatcher.start() && comment.start < stringsMatcher.end())
		              	commentsToCount.add(comment);
		          }
		      }*/
		  //}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
      return commentMatches.size();
  }

    public  String readFileAsString(String fileName)throws Exception 
    { 
      String data = ""; 
      data = new String(Files.readAllBytes(Paths.get(fileName))); 
      
      //System.out.println("text content ---------------- "+data);
      return data; 
      
    }  
    
  static class Match {
      int start;
      String text;
  }
}

