package a03b.e1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.text.html.ParagraphView;

public class LensFactoryImpl implements LensFactory {

    @Override
    public <E> Lens<List<E>, E> indexer(int i) {
        return new Lens<List<E>,E>() {
            
            @Override
            public E get(List<E> s) {
                return s.get(i);
            }

            @Override
            public List<E> set(E a, List<E> s) {
                List<E> temp = new ArrayList<>(s);
                temp.set(i, a);
                return temp;
            }
            
        };
    }

    @Override
    public <E> Lens<List<List<E>>, E> doubleIndexer(int i, int j) {
        return new Lens<List<List<E>>,E>() {

            @Override
            public E get(List<List<E>> s) {
                return s.get(i).get(j);
            }

            @Override
            public List<List<E>> set(E a, List<List<E>> s) {
                List<List<E>> temp = new ArrayList<>();
                temp.addAll(s.stream().map(l -> new ArrayList<>(l)).toList());

                temp.get(i).set(j, a);
                return temp;
            }
            
        };
    }

    @Override
    public <A, B> Lens<Pair<A, B>, A> left() {
        return new Lens<Pair<A,B>,A>() {

            @Override
            public A get(Pair<A, B> s) {
                return s.get1();
            }

            @Override
            public Pair<A, B> set(A a, Pair<A, B> s) {
                return new Pair<A,B>(a, s.get2());
            }
            
        };
    }

    @Override
    public <A, B> Lens<Pair<A, B>, B> right() {
        return new Lens<Pair<A,B>,B>() {

            @Override
            public B get(Pair<A, B> s) {
                return s.get2();
            }

            @Override
            public Pair<A, B> set(B a, Pair<A, B> s) {
                return new Pair<A,B>(s.get1(), a);
            }
            
        };
    }

    @Override
    public <A, B, C> Lens<List<Pair<A, Pair<B, C>>>, C> rightRightAtPos(int i) {
        Lens<List<Pair<A, Pair<B, C>>>, Pair<A, Pair<B, C>>> indexer = indexer(i);
        Lens<Pair<A, Pair<B, C>>, Pair<B, C>> right1 = right();
        Lens<Pair<B, C>, C> right2 = right();


        return new Lens<List<Pair<A,Pair<B,C>>>,C>() {

            @Override
            public C get(List<Pair<A, Pair<B, C>>> s) {
                return right2.get(right1.get(indexer.get(s)));
            }

            @Override
            public List<Pair<A, Pair<B, C>>> set(C a, List<Pair<A, Pair<B, C>>> s) {
                right2.set(a, right1.set(new Pair<A, Pair<B, C>>(right1.get(indexer.get(s))), );
            }
            
        };
    }

}
