package test;

public class Test1 {
    private int countLineOfCode;
    private int countLineOfComment;
    private String className;
    private String classPath;

    public Test1(int countLineOfCode, int countLineOfComment, String className, String classPath) {
        this.countLineOfCode = countLineOfCode;
        this.countLineOfComment = countLineOfComment;
        this.className = className;
        this.classPath = classPath;
    }

    public int getCountLineOfCode() {
        return countLineOfCode;
    }

    public void setCountLineOfCode(int countLineOfCode) {
        this.countLineOfCode = countLineOfCode;
    }

    public int getCountLineOfComment() {
        return countLineOfComment;
    }

    public void setCountLineOfComment(int countLineOfComment) {
        this.countLineOfComment = countLineOfComment;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String toString() {
        return "LOC::::: "+ getCountLineOfCode() + "::filePath " + getClassPath();
    }
}
