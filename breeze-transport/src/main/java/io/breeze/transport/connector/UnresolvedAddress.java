package io.breeze.transport.connector;

/**
 * Created by kaiscript on 2018/11/15.
 */
public class UnresolvedAddress {

    private String ip;
    private int port;

    public String getIp() {
        return ip;
    }

    public UnresolvedAddress setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public int getPort() {
        return port;
    }

    public UnresolvedAddress setPort(int port) {
        this.port = port;
        return this;
    }
}
