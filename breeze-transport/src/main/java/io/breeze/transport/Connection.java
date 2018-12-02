package io.breeze.transport;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by kaiscript on 2018/12/2.
 */
public class Connection {

    private UnresolvedAddress address;

    public Connection(UnresolvedAddress address) {
        this.address = address;
    }

    public UnresolvedAddress getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
