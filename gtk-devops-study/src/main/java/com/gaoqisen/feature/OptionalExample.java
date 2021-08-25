package com.gaoqisen.feature;

import java.util.Optional;

public class OptionalExample {

    public static void main(String[] args) {

        Optional<Integer> optional = Optional.ofNullable(null);
        System.out.println(optional.isPresent());

    }

}
