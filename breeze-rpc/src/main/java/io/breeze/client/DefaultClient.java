package io.breeze.client;

import io.breeze.registry.RegisterMeta;
import io.breeze.registry.RegistryService;

/**
 * Created by kaiscript on 2018/11/14.
 */
public class DefaultClient {

    private RegistryService registryService;

    public DefaultClient(RegistryService registryService) {
        this.registryService = registryService;
    }

    public void watchConnection() {

    }

    void connectToRegisterCenter(RegisterMeta meta) {

    }

}
