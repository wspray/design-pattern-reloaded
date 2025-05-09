package memoizer;

import java.util.HashMap;

import static java.util.stream.IntStream.range;

public interface memoizer1 {
    abstract class Memoizer<V, R> {
        private final HashMap<V, R> map = new HashMap<>();

        public final R memoize(V value) {
            return map.computeIfAbsent(value, this::compute);
        }

        protected abstract R compute(V value);
    }

    static void main(String[] args) {
        var memoizer = new Memoizer<Integer, Integer>() {
            @Override
            protected Integer compute(Integer n) {
                if (n < 2) {
                    return 1;
                }
                return memoize(n - 1) + memoize(n - 2);
            }
        };

        range(0, 20).map(memoizer::memoize).forEach(System.out::println);
    }
}
