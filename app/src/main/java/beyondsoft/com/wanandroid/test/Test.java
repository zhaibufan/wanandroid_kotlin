package beyondsoft.com.wanandroid.test;

public class Test {


    /**
     * 泛型类  所有泛型都在类名后面
     *
     * @param <A>
     * @param <B>
     * @param <C>
     * @param <T>
     */
    public class Student<A, B, C, T>{

        Student(A a, B b) {

        }

        private void play(C c) {

        }

        private T Study(T t) {
            return t;
        }
    }

    /**
     * 泛型接口 所有泛型都在接口名后面
     *
     * @param <D>
     * @param <E>
     */
    public interface Listener<D, E> {
        void list(D d);
        E let();
    }
}



