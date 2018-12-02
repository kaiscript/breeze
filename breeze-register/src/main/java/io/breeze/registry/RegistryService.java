package io.breeze.registry;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import io.breeze.transport.Connection;
import io.breeze.transport.UnresolvedAddress;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.concurrent.ConcurrentMap;

/**
 * 注册Service
 * 怎么保证client拿到是同个实例
 * Created by kaiscript on 2018/11/14.
 */
public class RegistryService extends AbstractRegistryService{

    //连接到注册中心时为map赋值
    private final ConcurrentMap<UnresolvedAddress, Registry> clients = Maps.newConcurrentMap();

    public RegistryService() {

    }

    /**
     *
     * 从注册中心订阅，遍历clients
     */
    @Override
    public void doSubscribe(RegisterMeta.ServiceMeta serviceMeta) {

    }

    /**
     * connect
     * @param addresses ip1:port1,ip2:port2
     */
    public void connectToRegisterCenter(String addresses) {
        Preconditions.checkNotNull(addresses,"connect addresses");
        String[] address = addresses.split(",");
        for (String a : address) {
            String[] addressStr = a.split(":");
            String host = addressStr[0];
            int port = NumberUtils.toInt(addressStr[1]);
            UnresolvedAddress unresolvedAddress = new UnresolvedAddress(host, port);
            Registry registry = clients.get(unresolvedAddress);
            if (registry == null) {
                Registry newRegistry = new Registry();
                registry = clients.putIfAbsent(unresolvedAddress, newRegistry);
                if (registry == null) {
                    registry = newRegistry;
                    Connection connection = registry.connect(unresolvedAddress);
                    registry.connectionManager().manage(connection);//保存connection
                }
            }

        }
    }

}
