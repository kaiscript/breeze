package io.breeze.example;

import io.breeze.server.DefaultRegisterServer;

/**
 * Created by kaiscript on 2018/12/2.
 */
public class ClusterTest {

    public static void main(String[] args) {
        DefaultRegisterServer registerServer = DefaultRegisterServer.createRegisterServer(10086);
        registerServer.startRegisterServer();

    }

}
