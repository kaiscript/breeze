package io.breeze.serialization;


import org.nustaq.serialization.FSTConfiguration;

/**
 * Created by chenkai on 2017/9/18.
 */
public class FSTSerializer extends Serializer{

    private static FSTConfiguration config = FSTConfiguration.createDefaultConfiguration();

    @Override
    public <T> byte[] writeObject(T obj) {
        return config.asByteArray(obj);
    }

    @Override
    public <T> T readObject(byte[] bytes, int offset, int length, Class<T> clazz) {
        return (T)config.asObject(bytes);
    }


}
