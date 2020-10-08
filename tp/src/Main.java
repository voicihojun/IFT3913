
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        if(args.length != 2) {
            System.out.println("Usage: java Main [directory] [extension]");
            System.exit(1);
        }
        String filePath = args[0];
        String extension = args[1];

        if(!extension.isEmpty() && !extension.equals("java")) {
            System.out.println("Wrong extension");
            System.exit(1);
        }

        Counter counter = new Counter();
        counter.run(filePath);
    }
}
