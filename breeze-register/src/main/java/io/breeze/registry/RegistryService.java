package io.breeze.registry;

import com.google.common.collect.Maps;
import io.breeze.transport.connector.UnresolvedAddress;

import java.util.concurrent.ConcurrentMap;

/**
 * 注册Service
 * Created by kaiscript on 2018/11/14.
 */
public class RegistryService {

    //连接到注册中心时为map赋值
    private final ConcurrentMap<UnresolvedAddress, Registry> clients = Maps.newConcurrentMap();

    /**
     * 从注册中心订阅，遍历clients
     */
    public void subscribe() {

    }


    /**
     *
     * @param address ip1:port1,ip2:port2
     */
    public void connectToRegisterCenter(String address) {

    }

}
