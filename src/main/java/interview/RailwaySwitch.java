package interview;

import java.util.Optional;

public interface RailwaySwitch {

    @FunctionalInterface
    interface Finder {
        Optional<String> find();

        default Finder or(Finder finder) {
            return () -> find().or(finder::find);
        }
    }

    static Finder findFromA() {
        return () -> Optional.ofNullable("A");
    }

    static Finder findFromB() {
        return () -> Optional.ofNullable("B");
    }

    static String findFromMutilMethod() {
        return findFromA()
                .or(findFromB())
                .find()
                .orElse("defalut");
    }

    static void main(String[] args) {
        System.out.println(findFromMutilMethod());
    }
}
