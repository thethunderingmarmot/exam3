package a03a.e1;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class DecisionChainFactoryImpl implements DecisionChainFactory {

    @Override
    public <A, B> DecisionChain<A, B> oneResult(B b) {
        return new DecisionChain<A,B>() {

            @Override
            public Optional<B> result(A a) {
                return Optional.of(b);
            }

            @Override
            public DecisionChain<A, B> next(A a) {
                return oneResult(b);
            }
            
        };
    }

    @Override
    public <A, B> DecisionChain<A, B> simpleTwoWay(Predicate<A> predicate, B positive, B negative) {
        return new DecisionChain<A,B>() {

            @Override
            public Optional<B> result(A a) {
                return Optional.empty();
            }

            @Override
            public DecisionChain<A, B> next(A a) {
                return predicate.test(a) ? oneResult(positive) : oneResult(negative);
            }
            
        };
    }

    @Override
    public <A, B> DecisionChain<A, B> enumerationLike(List<Pair<A, B>> mapList, B defaultReply) {
        return new DecisionChain<A,B>() {

            @Override
            public Optional<B> result(A a) {
                if(mapList.size() > 0) {
                    return mapList.get(0).get1() == a ? Optional.of(mapList.get(0).get2()) : Optional.empty();
                } else {
                    return Optional.of(defaultReply);
                }
            }

            @Override
            public DecisionChain<A, B> next(A a) {
                List<Pair<A, B>> modifiableMapList = new LinkedList<>(mapList);
                modifiableMapList.remove(0);
                return enumerationLike(modifiableMapList, defaultReply);
            }
        };
    }

    @Override
    public <A, B> DecisionChain<A, B> twoWay(Predicate<A> predicate, DecisionChain<A, B> positive,
            DecisionChain<A, B> negative) {
        return new DecisionChain<A,B>() {

            @Override
            public Optional<B> result(A a) {
                return Optional.empty();
            }

            @Override
            public DecisionChain<A, B> next(A a) {
                return predicate.test(a) ? positive : negative;
            }
            
        };
    }

    @Override
    public <A, B> DecisionChain<A, B> switchChain(List<Pair<Predicate<A>, B>> cases, B defaultReply) {
        return new DecisionChain<A,B>() {

            @Override
            public Optional<B> result(A a) {
                if(cases.size() > 0) {
                    return cases.get(0).get1().test(a) ? Optional.of(cases.get(0).get2()) : Optional.empty();
                } else {
                    return Optional.of(defaultReply);
                }
            }

            @Override
            public DecisionChain<A, B> next(A a) {
                List<Pair<Predicate<A>, B>> modifiableCases = new LinkedList<>(cases);
                modifiableCases.remove(0);
                return switchChain(modifiableCases, defaultReply);
            }
        };
    }

}
