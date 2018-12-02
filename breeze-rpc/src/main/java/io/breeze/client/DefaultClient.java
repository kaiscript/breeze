package io.breeze.client;

import io.breeze.registry.RegisterMeta;
import io.breeze.registry.RegistryService;
import io.breeze.transport.ConnectionManager;
import io.breeze.transport.Connector;

/**
 * Created by kaiscript on 2018/11/14.
 */
public class DefaultClient {

    private RegistryService registryService;

    //client端自己也需要维持一条连接
    private Connector connector;

    public DefaultClient(RegistryService registryService,Connector connector) {
        this.registryService = registryService;
        this.connector = connector;
    }

    public void watchConnection() {

        ConnectionManager connectionManager = connector.connectionManager();

        Connector.ConnectorWatcher watcher = () -> {

        };

    }

    public void subscribe(RegisterMeta.ServiceMeta serviceMeta) {
        registryService.subscribe(serviceMeta);
    }

    public void connectToRegisterCenter(String connectString) {
        registryService.connectToRegisterCenter(connectString);
    }

}
