package io.breeze.transport;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by kaiscript on 2018/11/15.
 */
public class UnresolvedAddress {

    private String host;
    private int port;

    public UnresolvedAddress(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public UnresolvedAddress setHost(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public UnresolvedAddress setPort(int port) {
        this.port = port;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
