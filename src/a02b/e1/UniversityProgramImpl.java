package a02b.e1;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class UniversityProgramImpl implements UniversityProgram {

    private record Course(Sector sector, Integer credits) {};
    public record Constraint<T1, T2>(Predicate<T1> c1, Predicate<T2> c2) {};

    protected final Map<String, Course> courses = new HashMap<>();

    @Override
    public void addCourse(String name, Sector sector, int credits) {
        courses.put(name, new Course(sector, credits));
    }

    private Stream<Course> select(Set<String> courseNames) {
        return courses.entrySet().stream().filter(e -> courseNames.contains(e.getKey())).map(e -> e.getValue());
    }

    private boolean checkConstraint(Constraint<Sector, Integer> constraint, Set<String> courseNames) {
        return constraint.c2.test(
            select(courseNames).filter(c -> constraint.c1.test(c.sector)).mapToInt(c -> c.credits).sum()
        );
    }

    public abstract Set<Constraint<Sector, Integer>> getConstraints();

    @Override
    public boolean isValid(Set<String> courseNames) {
        return getConstraints().stream().allMatch(c -> checkConstraint(c, courseNames));
    }
    
}