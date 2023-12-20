package a03b.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class LensFactoryImpl implements LensFactory {

    private <Source, Item> Lens<Source, Item> generic(Function<Source, Item> getter, BiFunction<Item, Source, Source> setter) {
        return new Lens<Source,Item>() {

            @Override
            public Item get(Source s) {
                return getter.apply(s);
            }

            @Override
            public Source set(Item a, Source s) {
                return setter.apply(a, s);
            }
            
        };
    }

    private <Item> List<Item> copyListAndSet(List<Item> sourceList, Item itemToSet, int atIndex) {
        List<Item> newList = new ArrayList<>(sourceList);
        newList.set(atIndex, itemToSet);
        return newList;
    }

    private <Left, Right> Pair<Left, Right> copyPairAndSetLeft(Pair<Left, Right> sourcePair, Left itemToSet) {
        return new Pair<Left,Right>(itemToSet, sourcePair.get2());
    } 

    private <Left, Right> Pair<Left, Right> copyPairAndSetRight(Pair<Left, Right> sourcePair, Right itemToSet) {
        return new Pair<Left,Right>(sourcePair.get1(), itemToSet);
    } 

    @Override
    public <E> Lens<List<E>, E> indexer(int i) {
        return generic(source -> source.get(i), (item, source) -> copyListAndSet(source, item, i));
    }

    @Override
    public <A, B> Lens<Pair<A, B>, A> left() {
        return generic(source -> source.get1(), (item, source) -> copyPairAndSetLeft(source, item));
    }

    @Override
    public <A, B> Lens<Pair<A, B>, B> right() {
        return generic(source -> source.get2(), (item, source) -> copyPairAndSetRight(source, item));
    }

    private <Source, SourceItem, Item> Lens<Source, Item> fusionator9000(Lens<Source, SourceItem> lens1, Lens<SourceItem, Item> lens2) {
        return generic(source -> lens2.get(lens1.get(source)), (item, source) -> lens1.set(lens2.set(item, lens1.get(source)), source));
    }

    @Override
    public <E> Lens<List<List<E>>, E> doubleIndexer(int i, int j) {
        return fusionator9000(indexer(i), indexer(j));
    }

    @Override
    public <A, B, C> Lens<List<Pair<A, Pair<B, C>>>, C> rightRightAtPos(int i) {
        return fusionator9000(indexer(i), fusionator9000(right(), right()));
    }

}
