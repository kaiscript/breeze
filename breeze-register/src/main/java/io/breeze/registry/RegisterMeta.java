package io.breeze.registry;

/**
 * 注册消息元数据
 * Created by kaiscript on 2018/11/14.
 */
public class RegisterMeta {

    private Address address = new Address();

    private ServiceMeta serviceMeta = new ServiceMeta();

    public static class ServiceMeta{

        private String serviceName;

    }

    public static class Address{

        private String ip;
        private int port;

        public String getIp() {
            return ip;
        }

        public Address setIp(String ip) {
            this.ip = ip;
            return this;
        }

        public int getPort() {
            return port;
        }

        public Address setPort(int port) {
            this.port = port;
            return this;
        }
    }

    public Address getAddress() {
        return address;
    }

    public RegisterMeta setAddress(Address address) {
        this.address = address;
        return this;
    }
}
