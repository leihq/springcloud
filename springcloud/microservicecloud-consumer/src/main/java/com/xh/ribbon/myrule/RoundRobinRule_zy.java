package com.xh.ribbon.myrule;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

public class RoundRobinRule_zy extends AbstractLoadBalancerRule {
	

    private AtomicInteger nextServerCyclicCounter;
    private int totalCount; //总共被调用的次数，现在要求每个机器循环5次
    private int currentIndex; // 当前调用机器的编号

    private static Logger log = LoggerFactory.getLogger(MySelfRule.class);

    public RoundRobinRule_zy() {
        nextServerCyclicCounter = new AtomicInteger(0);
    }

    public RoundRobinRule_zy(ILoadBalancer lb) {
        this();
        setLoadBalancer(lb);
    }

    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            log.warn("no load balancer");
            return null;
        }

        Server server = null;
        int count = 0;
        while (server == null && count++ < 10) {
            List<Server> reachableServers = lb.getReachableServers();
            List<Server> allServers = lb.getAllServers();
            int upCount = reachableServers.size();
            int serverCount = allServers.size();

            if ((upCount == 0) || (serverCount == 0)) {
                log.warn("No up servers available from load balancer: " + lb);
                return null;  
            }

//            int nextServerIndex = incrementAndGetModulo(serverCount);
//            server = allServers.get(nextServerIndex);
            if(totalCount < 5){
            	 server = reachableServers.get(currentIndex);
            	 totalCount ++;
            }else{
            	totalCount = 0;
            	currentIndex = incrementAndGetModulo(upCount);
            }
            
            if(currentIndex>=upCount-1){
            	currentIndex = 0;
            }

            if (server == null) { 
            	totalCount = 0;
            	currentIndex = incrementAndGetModulo(upCount);
                /* Transient. */
                Thread.yield(); 
                continue;
            }

            if (server.isAlive() && (server.isReadyToServe())) {
                return (server);
            }

            // Next.
            server = null;
        }

        if (count >= 10) {
            log.warn("No available alive servers after 10 tries from load balancer: "
                    + lb);
        }
        return server;
    }

    /**
     * Inspired by the implementation of {@link AtomicInteger#incrementAndGet()}.
     *
     * @param modulo The modulo to bound the value of the counter.
     * @return The next value.
     */
    private int incrementAndGetModulo(int modulo) {
        for (;;) {
            int current = nextServerCyclicCounter.get();
            int next = (current + 1) % modulo;
            if (nextServerCyclicCounter.compareAndSet(current, next))
                return next;
        }
    }

    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(), key);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
    }

}

