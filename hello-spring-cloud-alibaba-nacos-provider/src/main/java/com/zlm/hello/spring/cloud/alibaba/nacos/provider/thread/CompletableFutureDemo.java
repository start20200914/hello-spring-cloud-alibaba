package com.zlm.hello.spring.cloud.alibaba.nacos.provider.thread;


import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.User;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.utils.ThreadPoolUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

@Component
public class CompletableFutureDemo {

    //消息重复投递时间隔messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
    private final static List<Integer> MESSAGE_DELAY_LEVEL = Arrays.asList(1,5,10,30,16,60,120,180,240,300,360,420,480,540,600,1200,1800,3600,7200);

    //消息重复投递次数
    public final static Integer MESSAGE_RETRY_TIME = MESSAGE_DELAY_LEVEL.size();


    public void check(List<User> list){

        ExecutorService exs = ThreadPoolUtil.createBoundLessFixedThreadPool();
        try {
            List<CompletableFuture> futures = new ArrayList<>();
            for (int i =0 ;i<list.size() ;i++){
                User user = list.get(i);
                CompletableFuture<User> future = CompletableFuture.supplyAsync(() -> doSomething(user), exs)
                       .whenComplete((v, e)->{
                     v.setPassword(Thread.currentThread().getName());
                    //System.out.println("V:"+v+";e:"+e);

                    if (e!=null){
                        //throw new RuntimeException("校验出错");
                        System.err.println(e.getMessage());
                    }
                });
                futures.add(future);
            }
            CompletableFuture[] objects = futures.toArray(new CompletableFuture[]{});
            CompletableFuture.allOf(objects).join();

            System.err.println("执行结束");
        }catch (Exception e){

            System.err.println("异常情况："+e.getMessage());
            //throw new RuntimeException("线程已满");
        }finally {
            exs.shutdown();
        }
    }
    public void checkForech(List<User> list){
        list.forEach(this::doSomething);
    }
    public void checkStream(List<User> list){
        list.parallelStream().forEach(this::doSomething);
    }


    private User doSomething(User user){
        if(user.getId()%2==0){
            user.setPassword("false");
        }
        Random rand = new Random();
        int i = rand.nextInt(30);
        user.setName(user.getName()+":"+i);
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(user.getId()==3){
            //int j =1/0;
        }
        return user;
    }

}
