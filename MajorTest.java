import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class MajorTest {
    private Major major;

    @BeforeEach
    void setUp() {
        //major = new Major();
    }

    @Test
    void testMajorElective() {
        Set<Course> majorElective = new HashSet<>();
        majorElective.add(new Course("CS 2810|Mathematics of Data Models|FQ,AD|CS 1800|4"));
        //major.setMajorElective(majorElective);
        //assertEquals(majorElective, major.getMajorElective());
    }
}