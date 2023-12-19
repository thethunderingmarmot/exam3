package a02c.e1;

import java.util.Set;
import java.util.function.Predicate;

public class UniversityProgramFactoryImpl implements UniversityProgramFactory {

    @Override
    public UniversityProgram flexible() {
        return new UniversityProgramImpl() {

            @Override
            public Set<Pair<Predicate<Set<Group>>, Predicate<Integer>>> getConstraints() {
                return Set.of(
                    new Pair<>(g -> true, c -> c == 48)
                );
            }
            
        };
    }

    @Override
    public UniversityProgram fixed() {
        return new UniversityProgramImpl() {

            @Override
            public Set<Pair<Predicate<Set<Group>>, Predicate<Integer>>> getConstraints() {
                return Set.of(
                    new Pair<>(g -> true, c -> c == 60),
                    new Pair<>(g -> g.contains(Group.MANDATORY), c -> c == 12),
                    new Pair<>(g -> g.contains(Group.OPTIONAL), c -> c == 36),
                    new Pair<>(g -> g.contains(Group.THESIS), c -> c == 12)
                );
            }
            
        };
    }

    @Override
    public UniversityProgram balanced() {
        return new UniversityProgramImpl() {

            @Override
            public Set<Pair<Predicate<Set<Group>>, Predicate<Integer>>> getConstraints() {
                return Set.of(
                    new Pair<>(g -> true, c -> c == 60),
                    new Pair<>(g -> g.contains(Group.MANDATORY), c -> c == 24),
                    new Pair<>(g -> g.contains(Group.OPTIONAL), c -> c >= 24),
                    new Pair<>(g -> g.contains(Group.FREE), c -> c <= 12),
                    new Pair<>(g -> g.contains(Group.THESIS), c -> c <= 12)
                );
            }
            
        };
    }

    @Override
    public UniversityProgram structured() {
        return new UniversityProgramImpl() {

            @Override
            public Set<Pair<Predicate<Set<Group>>, Predicate<Integer>>> getConstraints() {
                return Set.of(
                    new Pair<>(g -> true, c -> c == 60),
                    new Pair<>(g -> g.contains(Group.MANDATORY), c -> c == 12),
                    new Pair<>(g -> g.contains(Group.OPTIONAL_A), c -> c >= 6),
                    new Pair<>(g -> g.contains(Group.OPTIONAL_B), c -> c >= 6),
                    new Pair<>(g -> g.contains(Group.OPTIONAL_A) || g.contains(Group.OPTIONAL_B), c -> c == 30),
                    new Pair<>(g -> g.contains(Group.FREE) || g.contains(Group.THESIS), c -> c == 18)
                );
            }
            
        };
    }

}
