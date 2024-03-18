import java.util.ArrayList;
import java.util.List;

public abstract class Semester {

    private final int minCredits;
    private final int maxCredits;
    protected final List<Course> courses;

    public Semester(int minCredits, int maxCredits) {
        this.minCredits = minCredits;
        this.maxCredits = maxCredits;
        courses = new ArrayList<>();
    }

    protected int getMinCredits() {
        return minCredits;
    }

    protected int getMaxCredits() {
        return maxCredits;
    }

    protected List<Course> getCourses() {
        return courses;
    }

}