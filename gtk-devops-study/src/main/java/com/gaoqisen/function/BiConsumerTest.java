package com.gaoqisen.function;

import java.util.*;
import java.util.function.BiConsumer;

public class BiConsumerTest {

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(3, 4);

        // 乘法计算后加法
        map.forEach(new MultipBiConsumer().andThen(new AddBiConsumer()));

        // 加法计算后乘法
        map.forEach(new AddBiConsumer().andThen(new MultipBiConsumer()));

    }


    static class MultipBiConsumer implements BiConsumer<Integer, Integer>{

        private BiConsumer<Integer, Integer> biConsumer;

        @Override
        public void accept(Integer o, Integer o2) {
            System.out.println(o * o2);
            if(biConsumer == null) {
                return;
            }
            biConsumer.accept(o, o2);
        }

        @Override
        public BiConsumer<Integer, Integer> andThen(BiConsumer after) {
            biConsumer = after;
            return this;
        }
    }

    static class AddBiConsumer implements BiConsumer<Integer, Integer>{

        private BiConsumer<Integer, Integer> biConsumer;

        @Override
        public void accept(Integer o, Integer o2) {
            System.out.println(o + o2);
            if(biConsumer == null) {
                return;
            }
            biConsumer.accept(o, o2);
        }

        @Override
        public BiConsumer<Integer, Integer> andThen(BiConsumer after) {
            biConsumer = after;
            return this;
        }
    }

}
