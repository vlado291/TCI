import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class SchoolTest {
    private String schoolName = "Kliment Ohridski";
    private LocalDate openingDate = LocalDate.of(2000, 1, 1);
    private ArrayList<Course> courses = new ArrayList<Course>();
    private School testSchool = new School(schoolName, openingDate, courses);

    @Test
    public void schoolHasNameOpeningDateAndACoursesCollection() {
        assertEquals("Expected school name was " + schoolName + ", but " + testSchool.getName() + " was returned.", schoolName, testSchool.getName());
        assertEquals("Expected school opening date was " + openingDate + ", but " + testSchool.getOpeningDate() + " was returned.", openingDate, testSchool.getOpeningDate());
        assertEquals("The list of expected and real courses seems to differ.", courses, testSchool.getCourses());
    }

    @Test
    public void aCourseCanBeAddedToTheSchool() {
        int courseCount = 10;

        assertEquals("The school has " + testSchool.getCourses().size() + "courses after a clear().", 0, testSchool.getCourses().size());

        try {
            for (int i = 0; i < courseCount; i++) {
                testSchool.addCourse(new Course("Math" + i, LocalDate.of(2010, 10, 10), LocalDate.of(2012, 12, 12)));
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals("The school was supposed to have " + courseCount + " courses after course addition, it has " + testSchool.getCourses().size(), courseCount, testSchool.getCourses().size());
    }

    @Test
    public void theNameOfACourseIsUnique() throws CourseException {
        int expectedCourseCount = 2;

        testSchool.addCourse(new Course("Math", LocalDate.of(2010, 10, 10), LocalDate.of(2012, 12, 12)));
        testSchool.addCourse(new Course("History", LocalDate.of(2010, 10, 10), LocalDate.of(2012, 12, 12)));

        assertEquals("The school should have " + expectedCourseCount + " courses, but it has " + testSchool.getCourses().size(), expectedCourseCount, testSchool.getCourses().size());

        testSchool.addCourse(new Course("Math", LocalDate.of(2010, 10, 11), LocalDate.of(2012, 12, 12)));

        assertEquals("A course of the same name has been added more than once.", expectedCourseCount, testSchool.getCourses().size());
    }

    private static final Object[] getUniqueCourses() {
        return new Object[][]{
                new Object[]{"History", LocalDate.of(2010, 10, 10), LocalDate.of(2012, 12, 12)},
                new Object[]{"Maths", LocalDate.of(2010, 10, 10), LocalDate.of(2012, 12, 12)},
                new Object[]{"Arts", LocalDate.of(2010, 10, 10), LocalDate.of(2012, 12, 12)}
        };
    }

    @Test
    @Parameters(method = "getUniqueCourses")
    public void aCourseCanBeRetrievedByName(String name, LocalDate startingDate, LocalDate endingDate) throws CourseException {
        Course testCourse = new Course(name, startingDate, endingDate);
        testSchool.addCourse(testCourse);
        Course retrievedCourse = testSchool.getCourseByName(name);

        assertEquals("School wasn't able to return course " + name + " by name. Was expected: " + testCourse + ", found: " + retrievedCourse, testCourse, retrievedCourse);
    }

    @Test
    public void aListOfAllCourseNamesCanBeRetrieved() throws CourseException {
        testSchool.getCourses().clear();

        ArrayList<Course> allCourses = new ArrayList<>();

        allCourses.add(new Course("History", LocalDate.of(2010, 10, 10), LocalDate.of(2012, 12, 12)));
        allCourses.add(new Course("Maths", LocalDate.of(2010, 10, 10), LocalDate.of(2012, 12, 12)));
        allCourses.add(new Course("Arts", LocalDate.of(2010, 10, 10), LocalDate.of(2012, 12, 12)));

        for (Course c : allCourses) {
            testSchool.addCourse(c);
        }

        ArrayList<String> allActualCourseNames = testSchool.getCourseNames();

        for (int i = 0; i < allCourses.size(); i++) {
            assertEquals("There's a mismatch for the course names in the list of course names of the School class. At index " + i + " was expected: " + allCourses.get(i).getName() + ", found: " + allActualCourseNames.get(i), allCourses.get(i).getName(), allActualCourseNames.get(i));
        }
    }

    @Test(expected = CourseException.class)
    public void throwCourseExceptionIfCourseStartIsBeforeSchoolYearStart() throws CourseException {
        School septemberOpeningTestSchool = new School("New School", LocalDate.of(2018, 9, 15), new ArrayList<Course>());

        septemberOpeningTestSchool.addCourse(new Course("History", LocalDate.of(2018, 8, 10), LocalDate.of(2018, 12, 12)));
    }


    @Test
    public void collectionOfAllCoursesAcquirable() {
        setupAddCoursesToSchool();

        for (int i = 0; i < testSchool.getCourses().size(); i++) {
            assertEquals(String.format("On index %1$s the expected course was expected to be %2$s, but was %3$s. The returned list of courses was not the same as the expected one.", i, courses.get(i), testSchool.getCourses().get(i)), courses.get(i), testSchool.getCourses().get(i));
        }
    }

    @Test
    public void schoolCoursesCannotBeChangedFromTheOutside(){
        setupAddCoursesToSchool();

        //everything in the course is private, how to test this?
    }

    private void setupAddCoursesToSchool() {
        setupAddCoursesToCoursesArrayList();

        try {
            for (Course c : courses) {
                testSchool.addCourse(new Course(c.getName(), c.getStartDate(), c.getEndDate()));
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    private void setupAddCoursesToCoursesArrayList() {
        try {
            courses.add(new Course("Math", LocalDate.of(2010, 10, 10), LocalDate.of(2012, 12, 12)));
            courses.add(new Course("History", LocalDate.of(2010, 12, 12), LocalDate.of(2012, 12, 12)));
            courses.add(new Course("Arts", LocalDate.of(2010, 10, 10), LocalDate.of(2012, 12, 12)));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}