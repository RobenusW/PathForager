import java.util.*;

public class StudentWithCourses {

    private int costPerSemester;
    private Map<String, Course> coursesTaken;
    private final Major intendedMajor;
    private final Set<NUPath> takenNUPaths;

    Database db = new Database();

    public StudentWithCourses(Map<String, Course> coursesTaken, Major intendedMajor, Set<NUPath> takenNUPaths, int cost) {
        this.coursesTaken = coursesTaken;
        this.intendedMajor = intendedMajor;
        this.takenNUPaths = takenNUPaths;
        this.costPerSemester = cost;
    }

    public int calculateSemesterCost(int NumOfSemesters) {
        return costPerSemester * NumOfSemesters;
    }

    public List<Course> getOptimizedCoursePath() {
        List<Course> result = new ArrayList<>();
        Map<Course, List<Course>> graph = buildGraph();
        Set<Course> visited = new HashSet<>();
        Set<Course> visiting = new HashSet<>();

        // Get the keys of the graph and sort them based on the number of prerequisites
        List<Course> courses = new ArrayList<>(graph.keySet());
        courses.sort(Comparator.comparingInt(course -> graph.get(course).size()));

        for (Course course : courses) {
            if (!visited.contains(course) && !dfs(course, graph, visited, visiting, result)) {
                return null; // Cycle detected, no valid course order exists
            }
        }

        Collections.reverse(result); // Reverse to get correct order
        return result;
    }

    private Map<Course, List<Course>> buildGraph() {
        Map<Course, List<Course>> graph = new HashMap<>();
        for (Course course : coursesTaken.values()) {
            List<Course> prerequisites = new ArrayList<>();
            for (Course prerequisiteCourseCode : course.getPrerequisite()) {
                Course prerequisiteCourse = coursesTaken.get(prerequisiteCourseCode);
                if (prerequisiteCourse != null) {
                    prerequisites.add(prerequisiteCourse);
                }
            }
            graph.put(course, prerequisites);
        }
        return graph;
    }

    private boolean dfs(Course course, Map<Course, List<Course>> graph, Set<Course> visited, Set<Course> visiting, List<Course> result) {
        visiting.add(course);
        for (Course prerequisite : graph.get(course)) {
            if (visited.contains(prerequisite)) {
                continue;
            }
            if (visiting.contains(prerequisite) || !dfs(prerequisite, graph, visited, visiting, result)) {
                return false; // Cycle detected
            }
        }
        visiting.remove(course);
        visited.add(course);
        result.add(course);
        return true;
    }

    protected void addCompletedNUPath(NUPath path) {
        takenNUPaths.add(path);
    }

    protected void addCompletedCourse(String courseID, Course course) {
        coursesTaken.put(courseID, course);
    }

    protected void addAllTakenNUPaths() {
        // analyzes all courses taken
        // adds each nuPath of the course to takenNUPaths set
        for (Course takenCourse : coursesTaken.values()) {
            takenNUPaths.addAll((takenCourse.getNUPaths(takenCourse.getCode())));
        }//adds NUPaths naturally fulfilled from the major.
        takenNUPaths.addAll(intendedMajor.getTakenNUPaths());
    }

    /**
     * this method returns all the NUPaths the student hasn't fulfilled
     *
     * @return
     */
    protected Set<NUPath> getNUPathsLeft() {
        Set<NUPath> allPaths = new HashSet<>(Database.allNUPaths);
        allPaths.removeAll(takenNUPaths);
        return allPaths;
    }


    public Set<NUPath> getCompletedNUPaths() {
        return takenNUPaths;
    }

    public Map<String, Course> getCoursesTaken() {
        return coursesTaken;
    }

    private Set<Course> allCoursesFromMajorRequirements() {
        Set<Course> allCourses = new HashSet<>();

        // Get core requirements and add courses to the set
        Map<String, Set<Course>> coreRequirements = intendedMajor.getMajorCoreRequirements();
        for (Set<Course> courses : coreRequirements.values()) {
            allCourses.addAll(courses);
        }

        // Get other requirements and add courses to the set
        Map<String, Set<Course>> otherRequirements = intendedMajor.getMajorOtherRequirements();
        for (Set<Course> courses : otherRequirements.values()) {
            allCourses.addAll(courses);
        }

        return allCourses;
    }

    private Set<Course> getElectiveCourses() {
        Set<Course> allRecordedCourses = new HashSet<>(Database.allCourses.values());
        Set<Course> takenCourses = new HashSet<>(coursesTaken.values());
        Set<Course> electiveCourses = new HashSet<>();

        allRecordedCourses.removeAll(allCoursesFromMajorRequirements());
        allRecordedCourses.removeAll(takenCourses);

        electiveCourses.addAll(allRecordedCourses);
        return electiveCourses;
    }
    private void calculateBestElectivesHelper(Set<Course> electiveCourses, Set<Course> currentCombination, int currentIndex, Set<Course> bestElectiveCombination, int maxNUPathCoverage) {
        // Base case: If no more elective courses or reached the end of the recursion
        if (currentIndex >= electiveCourses.size()) {
            int nuPathCoverage = calculateNUPathCoverage(currentCombination);
            if (nuPathCoverage > maxNUPathCoverage) {
                maxNUPathCoverage = nuPathCoverage;
                bestElectiveCombination.clear();
                bestElectiveCombination.addAll(currentCombination);
            }
            return;
        }
        // Explore including the current elective course
        Course[] courses = electiveCourses.toArray(new Course[0]);
        currentCombination.add(courses[currentIndex]);
        calculateBestElectivesHelper(electiveCourses, currentCombination, currentIndex + 1, bestElectiveCombination, maxNUPathCoverage);
        currentCombination.remove(courses[currentIndex]);

        // Explore excluding the current elective course
        calculateBestElectivesHelper(electiveCourses, currentCombination, currentIndex + 1, bestElectiveCombination, maxNUPathCoverage);
    }

    private int calculateNUPathCoverage(Set<Course> courses) {
        Set<NUPath> nuPathsCovered = new HashSet<>();
        for (Course course : courses) {
            nuPathsCovered.addAll(course.getNUPaths());
        }
        return nuPathsCovered.size();
    }

    public Set<Course> calculateBestElectives() {
        Set<Course> bestElectiveCombination = new HashSet<>();
        int maxNUPathCoverage = 0;
        Set<Course> electiveCourses = getElectiveCourses(); // Get available elective courses
        Set<Course> currentCombination = new HashSet<>();
        calculateBestElectivesHelper(electiveCourses, currentCombination, 0, bestElectiveCombination, maxNUPathCoverage);
        return bestElectiveCombination;
    }


}