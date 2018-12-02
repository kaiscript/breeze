package io.breeze.example;

import io.breeze.client.DefaultClient;
import io.breeze.registry.RegistryService;
import io.breeze.transport.Connector;

/**
 * Created by kaiscript on 2018/12/2.
 */
public class ClientTest {

    public static void main(String[] args) {
        DefaultClient defaultClient = new DefaultClient(new RegistryService(), new Connector());
        defaultClient.connectToRegisterCenter("127.0.0.1:10086");
    }

}
