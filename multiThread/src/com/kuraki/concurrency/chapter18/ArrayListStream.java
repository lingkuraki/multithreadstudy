package com.kuraki.concurrency.chapter18;

import java.util.Arrays;
import java.util.List;

public class ArrayListStream {

    public static void main(String[] args) {
        // 定义一个List并且使用Arrays的方法进行初始化
        List<String> list = Arrays.asList("Java", "Thread", "Concurrency", "Scala", "Clojure");
        // 获取并行的stream，然后通过map函数对list中的数据进行加工，最后输出
        list.parallelStream().map(String::toUpperCase).forEach(System.out::println);
        list.forEach(System.err::println);
    }
}
