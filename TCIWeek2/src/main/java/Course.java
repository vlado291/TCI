import java.time.LocalDate;
import java.util.Objects;

public class Course {
    private String name;
    private LocalDate startDate, endDate;

    public Course(String name, LocalDate startDate, LocalDate endDate) throws CourseException {
        this.name = name;

        if (endDate.isBefore(startDate)) {
                throw new CourseException("Course end date cannot be before the course beginning date.");
        }

        this.startDate = startDate;
        this.endDate = endDate;
    }


    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(name, course.name) &&
                Objects.equals(startDate, course.startDate) &&
                Objects.equals(endDate, course.endDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, startDate, endDate);
    }

    @Override
    public String toString() {
        return name + " (" + this.startDate + " - " + this.endDate + ")";
    }
}
