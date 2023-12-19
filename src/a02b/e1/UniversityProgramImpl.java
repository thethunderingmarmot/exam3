package a02b.e1;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class UniversityProgramImpl implements UniversityProgram {

    private record Course(Sector sector, Integer credits) {};
    
    protected final Map<String, Course> courses = new HashMap<>();

    @Override
    public void addCourse(String name, Sector sector, int credits) {
        courses.put(name, new Course(sector, credits));
    }

    protected Stream<Course> select(Set<String> courseNames) {
        return courses.entrySet().stream().filter(e -> courseNames.contains(e.getKey())).map(e -> e.getValue());
    }

    public int getCreditsOf(Predicate<Sector> selectedSectors, Set<String> courseNames) {
        return select(courseNames).filter(p -> selectedSectors.test(p.sector)).mapToInt(p -> p.credits).sum();
    }

    public int getAllCredits(Set<String> courseNames) {
        return select(courseNames).mapToInt(p -> p.credits).sum();
    }

    @Override
    public abstract boolean isValid(Set<String> courseNames);
    
}