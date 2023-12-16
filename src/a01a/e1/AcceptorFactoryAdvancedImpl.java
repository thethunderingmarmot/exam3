package a01a.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AcceptorFactoryAdvancedImpl implements AcceptorFactory {

    @Override
    public Acceptor<String, Integer> countEmptyStringsOnAnySequence() {
        return generalised(0,
                           (input, previousState) -> input.length() == 0 ? Optional.of(previousState + 1) : Optional.of(previousState),
                           state -> Optional.of(state));
    }

    private <I> List<I> addAndReturn(I element, List<I> list) {
        list.add(element);
        return list;
    }

    @Override
    public Acceptor<Integer, String> showAsStringOnlyOnIncreasingSequences() {
        return generalised(new ArrayList<Integer>(),
                           (input, state) -> state.isEmpty() || input > state.get(state.size() - 1) ? Optional.of(addAndReturn(input, state)) : Optional.empty(),
                           state -> Optional.of(state.stream().map(number -> number.toString()).collect(Collectors.joining(":"))));
    }

    @Override
    public Acceptor<Integer, Integer> sumElementsOnlyInTriples() {
        return generalised(new ArrayList<Integer>(),
                           (input, state) -> state.size() < 3 ? Optional.of(addAndReturn(input, state)) : Optional.empty(),
                           state -> state.size() == 3 ? state.stream().reduce((a, b) -> a + b) : Optional.empty());
    }

    @Override
    public <E, O1, O2> Acceptor<E, Pair<O1, O2>> acceptBoth(Acceptor<E, O1> a1, Acceptor<E, O2> a2) {
        return new Acceptor<E,Pair<O1,O2>>() {

            @Override
            public boolean accept(E e) {
                return a1.accept(e) && a2.accept(e);
            }

            @Override
            public Optional<Pair<O1, O2>> end() {
                Optional<Pair<O1, O2>> result = Optional.empty();
                if(a1.end().isPresent() && a2.end().isPresent()) {
                    result = Optional.of(new Pair<O1, O2>(a1.end().get(), a2.end().get()));
                }
                return result;
            }
            
        };
    }

    @Override
    public <E, O, S> Acceptor<E, O> generalised(S initial, BiFunction<E, S, Optional<S>> stateFun,
            Function<S, Optional<O>> outputFun) {
        return new Acceptor<E,O>() {

            private Optional<S> state = Optional.ofNullable(initial);

            @Override
            public boolean accept(E e) {
                state = state.isPresent() ? stateFun.apply(e, state.get()) : Optional.empty();
                return state.isPresent();
            }

            @Override
            public Optional<O> end() {
                return state.isPresent() ? outputFun.apply(state.get()) : Optional.empty();
            }
            
        };
    }

}
