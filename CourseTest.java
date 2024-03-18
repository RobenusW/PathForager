import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    private Course course;

    @BeforeEach
    void setUp() {
        course = new Course("CS 2810|Mathematics of Data Models|FQ,AD|CS 1800|4");
    }

    @Test
    void testGetCode() {
        assertEquals("CS 2810", course.getCode());
    }

    @Test
    void testGetTitle() {
        assertEquals("Mathematics of Data Models", course.getTitle());
    }

    @Test
    void testGetCredits() {
        assertEquals(4, course.getCredits());
    }

    @Test
    void testSetCourse() {
        course.setCourse("CS 2811|Advanced Data Models|FQ,AD|CS 1801|5");
        assertEquals("CS 2811", course.getCode());
        assertEquals("Advanced Data Models", course.getTitle());
        assertEquals(5, course.getCredits());
    }
}