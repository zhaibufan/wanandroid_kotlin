package beyondsoft.com.wanandroid.test;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

    public static void main(String args[]) {
        lengthOfLongestSubstring("bacadc");
    }

//    public static int lengthOfLongestSubstring(String str) {
//        ArrayList<String> list = new ArrayList<>();
//        for (int i = 0; i<str.length(); i++) {
//            StringBuilder sb = new StringBuilder();
//            sb.append(str.charAt(i));
//            for (int j = i+1; j<str.length();j++) {
//                String c = String.valueOf(str.charAt(j));
//                if (sb.toString().contains(c)) {
//                    break;
//                }
//                sb.append(c);
//            }
//            list.add(sb.toString());
//        }
//        int maxLength = 0;
//        for (String result : list) {
//            int length = result.length();
//            if (length > maxLength) {
//                maxLength = length;
//            }
//        }
//        return maxLength;
//    }

    public static int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>();
        int maxLength = 0;
        int i =0;
        int j = 0;
        int length = s.length();
        while (i<length && j <length) {
            if (!set.contains(s.charAt(j++))) {
                maxLength = Math.max(maxLength, j-i);
                set.add(s.charAt(j));
            } else {
                set.remove(i++);
            }
        }
        return maxLength;
    }
}




