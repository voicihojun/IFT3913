import java.io.IOException;
public class Main {
    public static void main(String[] args) throws IOException {
        if(args.length != 2) {
            System.out.println("Usage: java LineCounter [directory] [extension]");
            System.exit(1);
        }
        ClassLineCounter classLineCounter = new ClassLineCounter();
        classLineCounter.run(args[0], args[1]);
        System.exit(0);
    }
}
