package com.imooc.concurrency.Example.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @program: concurrency
 * @description:
 *
 *  Guava Cache的使用
 *
 * @author: Karl Guo
 * @create: 2018-11-08 18:17
 **/
@Slf4j
public class GuavaCacheExample {

    public static void main(String[] args) {

        //返回值类型也可以用Cache,只不过new CacheLoader方法就放在get()方法后面了.
        LoadingCache<String, Integer> cache = CacheBuilder.newBuilder()
                .maximumSize(10)   //最多存放10个数据 (基于容量的回收 )
                .expireAfterWrite(10, TimeUnit.SECONDS)  //在10s内没有被写,就被回收 (基于时间的回收)
     //         .expireAfterAccess(10, TimeUnit.SECONDS)   //在10s内没有被读或者写,就被回收
                .recordStats()   //开启记录状态数据的功能
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    //用固定的方式根据key来加载或者计算value的值,比如可以从数据库中获取数据
                    public Integer load(String s) throws Exception {
                        return -1;
                    }
                });

        log.info("{}", cache.getIfPresent("key1"));     //getIfPresent() , 查询缓存,没有命中的话返回null
        cache.put("key1", 1);                         //cache.put() , 放入键值对
        log.info("{}", cache.getIfPresent("key1"));     //1
        cache.invalidate("key1");                        // invalidate() 移除某个指定key的value
        log.info("{}", cache.getIfPresent("key1"));     //null

        try {
            Integer key1 = cache.get("key2");          //get() , 没有命中的话加载load方法,返回-1 , load方法会抛出异常,所以要try-catch
            cache.put("key2", 2);
            log.info("{}", cache.get("key2"));     //2

            log.info("{}", cache.size());     //1
            for (int i = 3; i < 13; i++) {
                cache.put("key" + i, i);     //测试容量缓存的回收机制, 放入10个数
            }
            log.info("{}", cache.size());      // 10
            log.info("{}", cache.getIfPresent("key2"));   //null ,  key2被清除

            Thread.sleep(11000);    //测试时间缓存回收机制,睡11s
            log.info("{}", cache.get("key6"));   // -1

            //看cache命中率,未命中率
            log.info("{}, {}", cache.stats().hitCount(), cache.stats().missCount());
            log.info("{}, {}", cache.stats().hitRate(), cache.stats().missRate());
        } catch (Exception e) {
            log.error("catch exception", e);
        }


    }

}
