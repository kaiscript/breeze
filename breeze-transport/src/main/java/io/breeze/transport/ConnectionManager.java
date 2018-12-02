package io.breeze.transport;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by kaiscript on 2018/12/2.
 */
public class ConnectionManager {

    private ConcurrentMap<UnresolvedAddress, CopyOnWriteArrayList<Connection>> connectionList = Maps.newConcurrentMap();

    public void manage(Connection connection) {
        UnresolvedAddress address = connection.getAddress();
        CopyOnWriteArrayList<Connection> list = connectionList.get(address);
        if (list == null) {
            CopyOnWriteArrayList<Connection> newList = Lists.newCopyOnWriteArrayList();
            list = connectionList.putIfAbsent(address, newList);
            if (list == null) {
                list = newList;
            }
        }
        list.add(connection);
    }

}
