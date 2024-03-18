//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.*;

public class Major {
    private Set<NUPath> fulfilledNUPath;
    protected static Map<String, Set<Course>> majorCoreRequirements;
    protected static Map<String, Set<Course>> majorOtherRequirements;

    private final int TOTAL_CREDITS = 128;

    public Set<NUPath> setOfAllNUPaths;

    private final int TOTAL_ELECTIVE_CREDITS = 28;

    public Major(Map<String, Set<Course>> majorCoreRequirements, Map<String, Set<Course>> majorOtherRequirements) {
        this.majorCoreRequirements = majorCoreRequirements;
        this.majorOtherRequirements = majorOtherRequirements;
    }

    public Map<String, Set<Course>> getMajorCoreRequirements(){
        return majorCoreRequirements;
    }
    public Map<String, Set<Course>> getMajorOtherRequirements(){
        return majorOtherRequirements;
    }
    public Set<NUPath> getTakenNUPaths() {
        Set<NUPath> takenNUPaths = new HashSet<>();

        // Iterate through majorCoreRequirements
        for (Set<Course> coreCourses : majorCoreRequirements.values()) {
            for (Course course : coreCourses) {
                takenNUPaths.addAll(course.getCourseNUPathCoverage());
            }
        }

        // Iterate through majorOtherRequirements
        for (Set<Course> otherCourses : majorOtherRequirements.values()) {
            for (Course course : otherCourses) {
                takenNUPaths.addAll(course.getCourseNUPathCoverage());
            }
        }

        return takenNUPaths;
    }


}
