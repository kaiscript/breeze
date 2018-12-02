package io.breeze.transport;

/**
 * Created by kaiscript on 2018/12/2.
 */
public interface AbstractConnector {

    ConnectionManager connectionManager();

    interface ConnectorWatcher{

        void start();

    }

}
