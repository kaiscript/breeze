package io.breeze.serialization;

/**
 * Created by kaiscript on 2018/11/13.
 */
public class SerializerFactory {

    private static final FSTSerializer fstSerializer = new FSTSerializer();

    public static FSTSerializer getFSTSerializer() {
        return fstSerializer;
    }

}
