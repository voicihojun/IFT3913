public class Classe {
    private String className;
    private String path;
    private int totalLine;
    private int lineOfComment;
    private float density;

    public Classe(String path, String className, int totalLine, int lineOfComment) {
        this.path = path;
        this.className = className;
        this.totalLine = totalLine;
        this.lineOfComment = lineOfComment;
        this.density = (float) lineOfComment / (float) totalLine;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public int getLineOfClass() {
        return totalLine;
    }

    public void setLineOfClass(int lineOfClass) {
        this.totalLine = lineOfClass;
    }

    public int getLineOfComment() {
        return lineOfComment;
    }

    public void setLineOfComment(int lineOfComment) {
        this.lineOfComment = lineOfComment;
    }
}
