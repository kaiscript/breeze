package io.breeze.registry;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * 抽象的注册Service，提供connect，subscribe
 * Created by kaiscript on 2018/12/2.
 */
public abstract class AbstractRegistryService {

    //维护注册中心信息的一个集合 serviceMeta 订阅服务信息--> 服务的地址
    private ConcurrentMap<RegisterMeta.ServiceMeta, RegisterValue> subscribeService = Maps.newConcurrentMap();

    //listener

    //添加到本地client端监听的listener集合中
    //发送订阅服务信号到注册中心
    //注册中心发送发布服务信号到client端
    //触发client端的listener，进行连接管理等操作 // TODO: 2018/12/2
    public void subscribe(RegisterMeta.ServiceMeta serviceMeta) {

        doSubscribe(serviceMeta);

    }

    protected abstract void doSubscribe(RegisterMeta.ServiceMeta serviceMeta);


    protected class RegisterValue{

        private Set<RegisterMeta> registerMeta = Sets.newHashSet();
    }

}
