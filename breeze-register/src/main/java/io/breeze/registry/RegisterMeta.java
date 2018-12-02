package io.breeze.registry;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 注册消息元数据
 * Created by kaiscript on 2018/11/14.
 */
public class RegisterMeta implements Serializable{

    private Address address = new Address();

    private ServiceMeta serviceMeta = new ServiceMeta();

    public static class ServiceMeta implements Serializable{

        public String serviceName;

        @Override
        public String toString() {
            return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }
    }

    public static class Address implements Serializable{

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

        @Override
        public String toString() {
            return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }
    }

    public Address getAddress() {
        return address;
    }

    public RegisterMeta setAddress(Address address) {
        this.address = address;
        return this;
    }

    public ServiceMeta getServiceMeta() {
        return serviceMeta;
    }

    public RegisterMeta setServiceMeta(ServiceMeta serviceMeta) {
        this.serviceMeta = serviceMeta;
        return this;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
