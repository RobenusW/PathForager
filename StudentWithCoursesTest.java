import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StudentWithCoursesTest {
    private StudentWithCourses student;
    private Major intendedMajor; // Replace with actual Major object
    private Map<String, Course> coursesTaken;
    private Set<NUPath> takenNUPaths;

    @BeforeEach
    void setUp() {
        coursesTaken = new HashMap<>();
        //intendedMajor = new Major(); // Replace with actual Major object
        takenNUPaths = new HashSet<>();
        //student = new StudentWithCourses(coursesTaken, intendedMajor, takenNUPaths);
    }

        @Test
        public void testGetOptimizedCoursePath() {
            // Create courses
            Course course1 = new Course("CS 2500|Fundamentals of Computer Science 1|ND,FQ||5");
            Course course2 = new Course("CS 2510|Fundamentals of Computer Science 2|ND,AD|CS 2500|5");
            Course course3 = new Course("CS 3500|Object-Oriented Design|ND,AD|CS 2510|5");
            Course course4 = new Course("CY 2550|Foundations of Cybersecurity|||4");

            // Add courses to map
            Map<String, Course> coursesTaken = new HashMap<>();
            coursesTaken.put("CS 2500", course1);
            coursesTaken.put("CS 2510", course2);
            coursesTaken.put("CS 3500", course3);
            coursesTaken.put("CS 2550", course4);

            // Create student
            StudentWithCourses student = new StudentWithCourses(coursesTaken, null, new HashSet<>(), 0);

            // Get optimized course path
            List<Course> optimizedCoursePath = student.getOptimizedCoursePath();

            // Check that the courses are in the correct order
            assertEquals(Arrays.asList(course1, course2, course3, course4), optimizedCoursePath);
        }

    @Test
    void testAddCompletedNUPath() {
        NUPath path = NUPath.IC; // Replace with actual NUPath
        student.addCompletedNUPath(path);
        assertTrue(student.getCompletedNUPaths().contains(path));
    }

    @Test
    void testAddCompletedCourse() {
        String courseID = "CS 2810|Mathematics of Data Models|FQ,AD|CS 1800|4";
        Course course = new Course(courseID); // Replace with actual Course object
        student.addCompletedCourse(courseID, course);
        assertEquals(course, student.getCoursesTaken().get(courseID));
    }

    @Test
    void testGetNUPathsLeft() {
        // Test with no NUPaths taken
        Set<NUPath> expected = new HashSet<>(Database.allNUPaths);
        assertEquals(expected, student.getNUPathsLeft());

        // Test with one NUPath taken
        NUPath takenPath = NUPath.IC; // Replace with actual NUPath
        student.addCompletedNUPath(takenPath);
        expected.remove(takenPath);
        assertEquals(expected, student.getNUPathsLeft());
    }
}