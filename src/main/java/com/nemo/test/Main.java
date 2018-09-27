package com.nemo.test;


import com.nemo.concurrent.QueueExecutor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;

public class Main {
    public static final QueueExecutor EXECUTOR = new QueueExecutor("场景公共驱动线程",100,100);

    static Map<String, Integer> map = new ConcurrentHashMap<>();
    static Map<Integer, Rank> rankMap = new ConcurrentHashMap<>();

    static final Object LOCK = new Object();
    static final Object LOCK3 = new Object();
    static final Object LOCK4 = new Object();

    public static void update1(){
        Integer i = map.get("test1");
        if(i == null) {
            map.put("test1", 0);
        }
        map.put("test1", map.get("test1") + 1);
        System.out.println("test1: " + map.get("test1"));
    }

    public static void update2(){
        Integer i = map.get("test2");
        if(i == null) {
            map.put("test2", 0);
        }
        map.put("test2", map.get("test2") + 1);
        System.out.println("test2: " + map.get("test2"));
    }

    public static void update3(){
        Rank rank = rankMap.get(1);
        if(rank == null) {
            synchronized (LOCK3) {
                rank = rankMap.get(1);
                if(rank == null) {
                    rank = new Rank();
                    rankMap.put(1, rank);
                }
            }
        }
        int i;
        synchronized (LOCK3) {
            i = rank.getRank();
            rank.setRank(i+1);
        }

        System.out.println("test3: " + rankMap.get(1).getRank());
    }

    public static void update4(){
        Rank rank = rankMap.get(2);
        if(rank == null) {
            synchronized (LOCK4) {
                rank = rankMap.get(2);
                if(rank == null) {
                    rank = new Rank();
                    rankMap.put(2, rank);
                }
            }
        }
        int i;
        synchronized (LOCK4) {
            i = rank.getRank();
            rank.setRank(i+1);
        }

        System.out.println("test4: " + rankMap.get(2).getRank());
    }

    public static void main(String[] args) throws Exception{
//        double a = 100;
//        int b = 12929;
//        int c = (int) (b * (a / 10000));
//        System.out.println(c);
//
//        String s = "Hello World";
//        String ss = StringUtils.uncapitalize(s);
//        System.out.println(ss);

//        map.put("test1", 1);
//        map.put("test2", 1);
//        for(int i = 0; i < 2000; i++) {
//            if(i % 2 == 0) {
//                new Thread(() -> update1()).start();
//            } else {
//                new Thread(() -> update2()).start();
//            }
//        }

//        for(int i = 0; i < 2000; i++) {
//            if(i % 2 == 0) {
//                new Thread(() -> update3()).start();
//            } else {
//                new Thread(() -> update4()).start();
//            }
//        }

//        Thread.sleep(5000);
//        System.out.println("test3: " + rankMap.get(1).getRank());
//        System.out.println("test4: " + rankMap.get(2).getRank());
//        List<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(100);
//        System.out.println(list);
//        list.add(list.size(), 99);
//        list.add(list.size(), 1);
//        System.out.println(list);
//        System.out.println(list.indexOf(99));
//
//        Map<Integer, Rank> rankMap = new HashMap<>();
//        System.out.println(rankMap.size() + " " +rankMap.get(111));

        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("dd");
                return "cc";
            }
        });



        System.out.println("aa");
        new Thread(futureTask).start();
        System.out.println("bb");
        while (!futureTask.isDone()) {
            System.out.println("未完成");
        }
        System.out.println("已完成");
        String s = futureTask.get();
        System.out.println(s);

        int i = 1998 * 25000 / 10000;
        System.out.println(i);

        char a = 0b1111;
        System.out.println(a);
    }

}

class Rank {
    private int rank;
    private int active;

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }
}