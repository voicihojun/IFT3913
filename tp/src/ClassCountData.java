import java.util.Comparator;

public class ClassCountData implements Comparable<ClassCountData>{
    private String classPath;
    private String className;
    private int countLineOfCode;
    private int countLineOfComment;
    private float density;
    private int wmc;
    private float classBc;

    public ClassCountData(String classPath, String className, int countLineOfCode, int countLineOfComment, int wmc) {
        this.countLineOfCode = countLineOfCode;
        this.countLineOfComment = countLineOfComment;
        this.className = className;
        this.classPath = classPath;
        this.density = (float) countLineOfComment / (float) countLineOfCode;
        this.wmc = wmc;
        this.classBc = density / (float) wmc;

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

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public int getWmc() {
        return wmc;
    }

    public void setWmc(int wmc) {
        this.wmc = wmc;
    }

    public float getClassBc() {
        return classBc;
    }

    public void setClassBc(float classBc) {
        this.classBc = classBc;
    }

    public String toString() {
        return "::FilePath:: " + getClassPath() +"\n"
                + "::Class name:: "+ getClassName() + "\n"
                + "::Class_LOC:: "+ getCountLineOfCode() + "\n"
                + "::Class_CLOC:: "+ getCountLineOfComment() + "\n"
                + "::Class_DC:: " + getDensity()
                + "::WMC:: " + getDensity()
                + "::Class_DC:: " + getDensity();
    }

    @Override
    public int compareTo(ClassCountData classCountData) {
        int compareage = classCountData.getCountLineOfComment();
        /* For Ascending order*/
//        return this.countLineOfComment-compareage;
        /* For Descentding order*/
        return compareage-this.countLineOfComment;
    }

    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
}
