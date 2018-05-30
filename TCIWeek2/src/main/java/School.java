import java.time.LocalDate;
import java.util.ArrayList;

public class School {
    private String name;
    private LocalDate openingDate;
    private ArrayList<Course> courses;


    public School(String name, LocalDate date, ArrayList<Course> courses) {
        this.name = name;
        this.openingDate = date;
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) throws CourseException{
        if(course.getStartDate().isBefore(this.openingDate)){
            throw new CourseException(String.format("Courses cannot start before the school opens. School opening date: %1$s Course start date and name: %2$s, %3$s ", this.openingDate, course.getStartDate(), course.getName()));
        }

        for (Course c : courses) {
            if (c.getName().equals(course.getName())) {
                return;
            }
        }

        courses.add(course);
    }

    public Course getCourseByName(String name) {
        for (Course c : courses) {
            if (c.getName().equals(name)) {
                return c;
            }
        }

        return null;
    }

    public ArrayList<String> getCourseNames() {
        ArrayList<String> toRet = new ArrayList<>();

        for(Course c : courses){
            toRet.add(c.getName());
        }

        return toRet;
    }
}
