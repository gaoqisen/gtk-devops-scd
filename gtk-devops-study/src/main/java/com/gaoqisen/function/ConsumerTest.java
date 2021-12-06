package com.gaoqisen.function;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ConsumerTest {

    public static void main(String[] args) {
        List<Integer> arrayList = Arrays.asList(1, 2, 3, 4, 5);

        /**
         * 先dubbo后加1
         */
        Consumer<Integer> consumer = new DoubleConsumer().andThen(new AddOneConsumer());
        arrayList.forEach(consumer);

        /**
         * 先加一后在Double
         */
        Consumer<Integer> consumer1 = new AddOneConsumer().andThen(new DoubleConsumer());
        arrayList.forEach(consumer1);

    }

    /**
     * 双倍数据
     */
    static class DoubleConsumer implements Consumer<Integer> {

        private Consumer<? super Integer> consumer;

        @Override
        public void accept(Integer integer) {
            Integer dubboNum = integer + integer;
            System.out.println("双倍处理数据: " + dubboNum );
            if(consumer == null) {
                return;
            }
            consumer.accept(dubboNum);
        }

        /**
         * 一步接异步的处理
         */
        @Override
        public Consumer<Integer> andThen(Consumer<? super Integer> after) {
            consumer = after;
            System.out.println("andThen: " + after);
            return this;
        }
    }

    /**
     * 加一
     */
    static class AddOneConsumer implements Consumer<Integer> {

        private Consumer<? super Integer> consumer;


        @Override
        public void accept(Integer integer) {
            Integer i = integer + 1;
            System.out.println("加一处理数据: " + i );
            if(consumer == null) {
                return;
            }
            consumer.accept(i);
        }


        @Override
        public Consumer<Integer> andThen(Consumer<? super Integer> after) {
            consumer = after;
            System.out.println("andThen: " + after);
            return this;
        }

    }


}
