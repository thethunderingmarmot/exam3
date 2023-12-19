package a02b.e1;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import a02b.e1.UniversityProgram.Sector;

public class UniversityProgramFactoryImpl implements UniversityProgramFactory {

    @Override
    public UniversityProgram flexible() {
        return new UniversityProgramImpl() {

            @Override
            public boolean isValid(Set<String> courseNames) {
                return getAllCredits(courseNames) == 60;
            }

        };
    }

    @Override
    public UniversityProgram scientific() {
        return new UniversityProgramImpl() {

            @Override
            public boolean isValid(Set<String> courseNames) {
                return getAllCredits(courseNames) == 60
                    && getCreditsOf(sector -> sector == Sector.MATHEMATICS, courseNames) >= 12
                    && getCreditsOf(sector -> sector == Sector.COMPUTER_SCIENCE, courseNames) >= 12
                    && getCreditsOf(sector -> sector == Sector.PHYSICS, courseNames) >= 12;
            }

        };
    }

    @Override
    public UniversityProgram shortComputerScience() {
        return new UniversityProgramImpl() {

            @Override
            public boolean isValid(Set<String> courseNames) {
                return getAllCredits(courseNames) >= 48
                    && getCreditsOf(sector -> sector == Sector.COMPUTER_SCIENCE || sector == Sector.COMPUTER_ENGINEERING, courseNames) >= 30;
            }
            
        };
    }

    @Override
    public UniversityProgram realistic() {
        return new UniversityProgramImpl() {

            @Override
            public boolean isValid(Set<String> courseNames) {
                return getAllCredits(courseNames) == 120
                    && getCreditsOf(sector -> sector == Sector.COMPUTER_SCIENCE || sector == Sector.COMPUTER_ENGINEERING, courseNames) >= 60
                    && getCreditsOf(sector -> sector == Sector.MATHEMATICS || sector == Sector.PHYSICS, courseNames) <= 18
                    && getCreditsOf(sector -> sector == Sector.THESIS, courseNames) == 24;
            }

        };
    }

}
