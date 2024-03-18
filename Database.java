import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Database {

    protected static String[] courseArray = readCSVFile("COURSELIST.txt");
    protected static Map<String, Course> allCourses = new HashMap<>();
    protected static Set<NUPath> allNUPaths = new HashSet<>();
    public Database() {
        addAllNUPaths();
    }


    protected static String[] readCSVFile(String filename) {
        ArrayList<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lines.toArray(new String[0]);
    }

    public void addAllCourse() {
        for (String course : courseArray) {
            addCourse(course);
        }
    }
    public void addCourse(String courseCode) {
        String[] prerequisiteString = splitString(courseCode)[3].split(",");
        String courseName = splitString(courseCode)[0];

        // Check if there are any prerequisites
        //
        if (prerequisiteString[0].isEmpty()) {
            allCourses.put(courseName, new Course(courseCode));
            return;
        } else {
            // Add each prerequisite course to the map
            for (String requisiteCourse : prerequisiteString) {
                if (!allCourses.containsKey(requisiteCourse)) {
                    // Assuming `searchCourseFormat()` returns the course code based on the course name
                    addCourse(searchCourseFormat(requisiteCourse));
                }
            }
        }

        // After adding all prerequisites, add the current course to the map
        allCourses.put(courseName, new Course(courseCode));
    }

    // Method to print keys and values of a HashMap
    public static void printMap(Map<String, Course> map) {
        for (Map.Entry<String, Course> entry : map.entrySet()) {
            String key = entry.getKey();
            Course value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + value);
        }
    }

    private String searchCourseFormat(String courseName) {
        for (String courseID : courseArray) {
            if (courseID.contains(courseName)) {
                return courseID;
            }
        }
//we never reach this with a proper input
        return "";
    }

    public static void addAllNUPaths() {
        allNUPaths.addAll(Arrays.asList(NUPath.values()));
    }

    public String[] splitString(String input) {
        return input.split("\\|");
    }

    public static void main(String[] args) {
        Database db = new Database();
         db.addAllCourse();
    }

}
