import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class CourseTest {


    @Test(expected = CourseException.class)
    public void courseExceptionIsThrownIfCourseBeginDateIsAfterCourseEndDate() throws CourseException{
        Course c = new Course(" Math", LocalDate.of(2012, 12,12), LocalDate.of(2010, 10, 10));
    }
}