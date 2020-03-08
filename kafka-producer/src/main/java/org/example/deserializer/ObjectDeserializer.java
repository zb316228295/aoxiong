package org.example.deserializer;

import org.apache.kafka.common.serialization.Deserializer;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

public class ObjectDeserializer implements Deserializer<Object> {
    @Override
    public Object deserialize(String s, byte[] bytes) {
        if(null == bytes || bytes.length == 0)
        return null;
        ByteArrayInputStream bs = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = null;
        Object o = null;
        try {
            ois = new ObjectInputStream(bs);
            o = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ois = null;
            }
        }
        return o;
    }
}
