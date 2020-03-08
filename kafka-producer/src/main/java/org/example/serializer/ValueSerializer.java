package org.example.serializer;

import org.apache.kafka.common.serialization.Serializer;
import scala.util.control.Exception;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ValueSerializer implements Serializer<Object> {

    @Override
    public byte[] serialize(String s, Object o) {
        if(null == o) {
            return new byte[0];
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        byte[] bytes = new byte[0];
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(o);
            bytes = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }
}
