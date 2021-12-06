package com.gaoqisen.collection.other;


import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class MyTask implements Delayed {
    String name;
    long runningTime;

    public MyTask(String name, long rt) {
        this.name = name;
        this.runningTime = rt;
    }

    @Override
    public int compareTo(Delayed o) {
        if(this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS))
            return -1;
        else if(this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS))
            return 1;
        else
            return 0;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(runningTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public String toString() {
        return "MyTask{" +
                "name='" + name + '\'' +
                ", runningTime=" + runningTime +
                '}';
    }
}
