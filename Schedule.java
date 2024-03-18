import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;

public class Schedule {

    protected Semester[][] schedule;
    public Schedule () {
        this.schedule = new Semester[5][4];
    }

    //1. Find out which classes are left.
    // SPECIAL CASE FOR COOP IS THAT ONLY COOP CAN BE TAKEN BY ITSELF

   private int startYear;
    private int startSemester;
    private void fillInCOOPCycle(boolean isFall) {
        int semesterCycleStart = isFall? 3 : 1;
        int semesterCycleEnd = isFall? 0 : 2;
        Course COOP = Database.allCourses.get("COOP 1000");
        for (int year = 0; year < 5; year++) {
            if (schedule[year][semesterCycleStart] == null) {
                Semester COOPSemester1 = isFall ? new SummerSemester() : new FallSpringSemester();
                Semester COOPSemester2 = isFall ? new FallSpringSemester() : new SummerSemester();
                COOPSemester1.courses.add(COOP);
                COOPSemester2.courses.add(COOP);

                schedule[year][semesterCycleStart] = COOPSemester1;
                schedule[year][semesterCycleEnd] = COOPSemester2;
            }
        }
    }

    // Method to find the difference between two HashMaps
    public static <K, V> Map<K, V> findDifference(Map<K, V> map1, Map<K, V> map2) {
        Map<K, V> difference = new HashMap<>();
        for (Map.Entry<K, V> entry : map1.entrySet()) {
            K key = entry.getKey();
            V value1 = entry.getValue();
            V value2 = map2.get(key);

            // If the key exists in both maps and values are different
            if (value2 != null && !value1.equals(value2)) {
                difference.put(key, value2);
            }
        }
        return difference;
    }

    // Method to remove shared courses from a Map<String, Set<Course>> based on a Set<Course>
    public static void removeSharedCourses(Map<String, Set<Course>> map, Set<Course> courseSet) {
        // Iterate over the set of courses
        for (Course course : courseSet) {
            // Iterate over the map entries
            for (Map.Entry<String, Set<Course>> entry : map.entrySet()) {
                // Get the set of courses for the current key
                Set<Course> courseSetInMap = entry.getValue();
                // Remove the course from the set if it exists
                courseSetInMap.remove(course);
            }
        }
    }

    private void fillSuccessiveCourse(Map<String, Course> coursesTaken) {

        Map<String, Set<Course>> allRequiredCourses = new HashMap<>(Major.majorCoreRequirements);
        Set<Course> takenCourses = new HashSet<>(coursesTaken.values());
        removeSharedCourses(allRequiredCourses, takenCourses);

        for (Set<Course> sets : allRequiredCourses.values()) {
            Set<Course> prerequisite;
            for (Course course : sets) {
                prerequisite = course.getPrerequisite();
                prerequisite.removeAll(coursesTaken.values());






            }
        }


private void fillElectiveCourses(){

}
/*

1. successive courses
2. requirements
3. electives with paths


 */





}
