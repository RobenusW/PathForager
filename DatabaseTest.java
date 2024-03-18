import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    private Database db;

    @BeforeEach
    void setUp() {
        db = new Database();
    }

    @Test
    void testAddCourse() {
        db.addCourse("CS 2810|Mathematics of Data Models|FQ,AD|CS 1800|4");
        System.out.println(db.allCourses.keySet());
        System.out.println(db.allCourses.containsKey("CS 1800"));
        assertTrue(Database.allCourses.containsKey("CS 1800"));
        assertTrue(Database.allCourses.containsKey("CS 2810"));
    }

    @Test
    public void testAddAllCourse() {
        db.addAllCourse();
        assertEquals(Database.allCourses.size(), Database.courseArray.length);
    }

    @Test
    void testAddAllNUPaths() {
        db.addAllNUPaths();
        assertEquals(NUPath.values().length, db.allNUPaths.size());
    }

    @Test
    void testSplitString() {
        String[] result = db.splitString("CS 2810|Mathematics of Data Models|FQ,AD|CS 1800|4");
        assertEquals(5, result.length);
    }
}