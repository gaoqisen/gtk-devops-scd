package com.gaoqisen.feature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListStream {

    public static void main(String[] agrs) {
        // 原始数据
        List<String> list = Arrays.asList("a", "b", "c", "d", "", "f", "g", "a");
        List<Integer> integerList = Arrays.asList(1, 4, 6, 8, 2, 4);
        print(list);

        // filter过滤, limit限制
        List<String> list1 = list.stream().limit(6).filter(a -> !a.isEmpty()).collect(Collectors.toList());
        print(list1);

        // map映射, distinct去重, parallelStream并行处理
        List<String> list2 = list.parallelStream().distinct().map(a -> a+ "1").collect(Collectors.toList());
        print(list2);

        // 返回null
        List<String> listNull = list.parallelStream().distinct().map(a -> {
            String b = a+ "1";
            if(b.equals("a1")) {
                return null;
            }
            return b;
        }).collect(Collectors.toList());
        print(listNull);

        // sorted排序
        printi(integerList);
        List<Integer> integerList1 = integerList.stream().sorted().collect(Collectors.toList());
        printi(integerList1);

        // count统计
        System.out.println(integerList.stream().count());

        // 合并字符串
        System.out.println(list.stream().collect(Collectors.joining(", ")));

        // 拼接数组为字符串，每个数组后面拼接123，并且过滤出数字为1的
        System.out.println(integerList.stream().filter(a -> a != 1).map(a -> a+"123").collect(Collectors.joining(", ")));

        // 分组
        List<Person> list3 = new ArrayList<>();
        Person person = new Person();
        person.setAge("18");
        person.setName("a");
        list3.add(person);
        list3.add(person);
        Person person1 = new Person();
        person1.setAge("19");
        person1.setName("a");
        list3.add(person1);
        Map<String, List<Person>> map = list3.stream().collect(Collectors.groupingBy(Person::getAge));
        map.forEach((key, value) -> {
            System.out.println(key);
            value.forEach(System.out::println);
        });
    }


    private static void print(List<String> list) {
        System.out.println("length: " + list.size());
        list.forEach(System.out::print);
        System.out.println("");
    }

    private static void printi(List<Integer> list) {
        System.out.println("length: " + list.size());
        list.forEach(System.out::print);
        System.out.println("");
    }
}

class Person{
    private String name;
    private String age;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}