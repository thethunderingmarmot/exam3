package a02c.e1;

import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class UniversityProgramImpl implements UniversityProgram {

    private Map<String, Pair<Set<Group>, Integer>> courses;

    @Override
    public void setCourses(Map<String, Pair<Set<Group>, Integer>> courses) {
        this.courses = courses;
    }

    private Stream<Pair<Set<Group>, Integer>> select(Set<String> courseNames) {
        return courses.entrySet().stream().filter(e -> courseNames.contains(e.getKey())).map(e -> e.getValue());
    }

    private boolean checkConstraint(Pair<Predicate<Set<Group>>, Predicate<Integer>> constraint, Set<String> courseNames) {
        return constraint.get2().test(select(courseNames).filter(p -> constraint.get1().test(p.get1())).mapToInt(p -> p.get2()).sum());
    }

    public abstract Set<Pair<Predicate<Set<Group>>, Predicate<Integer>>> getConstraints();

    @Override
    public boolean isValid(Set<String> courseNames) {
        return getConstraints().stream().allMatch(p -> checkConstraint(p, courseNames));
    }
    
}
