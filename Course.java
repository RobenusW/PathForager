import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

 class Course {
    private String code;
    private String title;
    private Set<NUPath> CourseNUPathCoverage;
    private Set<Course> prerequisite;
    private boolean isCompleted;
    private int credits;

    public Course(String code) {
        this.isCompleted = false;
        setCourse(code);
        this.prerequisite = getPrerequisite();
        this.CourseNUPathCoverage = getNUPaths(code);
    }


    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public Set<NUPath> getCourseNUPathCoverage() {
        return CourseNUPathCoverage;
    }



    public int getCredits() {
        return credits;
    }

    public void setCourse(String courseString) {
        String[] courseAttributes = splitString(courseString);
        title = courseAttributes[1];
        code = courseAttributes[0];
        credits = Integer.parseInt(courseAttributes[4]);
    }

    public Set<Course> getPrerequisite() {
        // Assuming this.code contains the prerequisite information in a string format
        String[] courseAttributes = splitString(this.code);

        // Ensure that the prerequisites are available
        if (courseAttributes.length < 4) {
            // Handle the case where prerequisites are not available
            return new HashSet<>();
        }

        String[] prereqs = courseAttributes[3].split(",");
        Set<Course> prereqSet = new HashSet<>();
        for (String prereq : prereqs) {
            if (Database.allCourses.containsKey(prereq)) {
                prereqSet.add(Database.allCourses.get(prereq));
            }
        }
        return prereqSet;
    }

    public Set<NUPath> getNUPaths(String courseCode) {
        Set<NUPath> nuPaths = new HashSet<>();
        String nuPathString = splitString(courseCode)[2];

        String[] nuPathAbbreviations = nuPathString.split(",");
        for (String abbreviation : nuPathAbbreviations) {
            for (NUPath nuPath : NUPath.values()) {
                if (nuPath.getAbbreviation().equals(abbreviation.trim())) {
                    nuPaths.add(nuPath);
                    break;
                }
            }
        }
        return nuPaths;
    }


    public String[] splitString(String input) {
        return input.split("\\|");
    }

 }

