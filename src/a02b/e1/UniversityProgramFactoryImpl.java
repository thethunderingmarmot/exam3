package a02b.e1;

import java.util.Set;

public class UniversityProgramFactoryImpl implements UniversityProgramFactory {

    @Override
    public UniversityProgram flexible() {
        return new UniversityProgramImpl() {

            @Override
            public Set<Constraint<Sector, Integer>> getConstraints() {
                return Set.of(
                    new Constraint<>(s -> true, c -> c == 60)
                );   
            }

        };
    }

    @Override
    public UniversityProgram scientific() {
        return new UniversityProgramImpl() {

            @Override
            public Set<Constraint<Sector, Integer>> getConstraints() {
                return Set.of(
                    new Constraint<>(s -> true, c -> c == 60),
                    new Constraint<>(s -> s == Sector.MATHEMATICS, c -> c >= 12),
                    new Constraint<>(s -> s == Sector.COMPUTER_SCIENCE, c -> c >= 12),
                    new Constraint<>(s -> s == Sector.PHYSICS, c -> c >= 12)
                );   
            }


        };
    }

    @Override
    public UniversityProgram shortComputerScience() {
        return new UniversityProgramImpl() {

            @Override
            public Set<Constraint<Sector, Integer>> getConstraints() {
                return Set.of(
                    new Constraint<>(s -> true, c -> c >= 48),
                    new Constraint<>(s -> s == Sector.COMPUTER_SCIENCE || s == Sector.COMPUTER_ENGINEERING, c -> c >= 30)
                );  
            }


        };
    }

    @Override
    public UniversityProgram realistic() {
        return new UniversityProgramImpl() {

            @Override
            public Set<Constraint<Sector, Integer>> getConstraints() {
                return Set.of(
                    new Constraint<>(s -> true, c -> c == 120),
                    new Constraint<>(s -> s == Sector.COMPUTER_SCIENCE || s == Sector.COMPUTER_ENGINEERING, c -> c >= 60),
                    new Constraint<>(s -> s == Sector.MATHEMATICS || s == Sector.PHYSICS, c -> c <= 18),
                    new Constraint<>(s -> s == Sector.THESIS, c -> c == 24)
                );  
            }


        };
    }

}
