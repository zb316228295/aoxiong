package org.example.stream.high;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.kstream.internals.KStreamReduce;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.ArrayList;
import java.util.Properties;

public class HighLevelStream {
    private KStream<String,String> kafkaStream;
    private KafkaStreams kss;
    private String topic ;
    StreamsBuilder streamsBuilder;
    Properties config = new Properties();
    {
        config.put(StreamsConfig.APPLICATION_ID_CONFIG,"testprocessor");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"47.96.121.230:9092");
    }

    public HighLevelStream(String topic) {
        this.topic = topic;
    }


    {
        streamsBuilder  = new StreamsBuilder();
        kafkaStream = streamsBuilder.stream("country-topic", Consumed.with(Serdes.String(),Serdes.String()));
    }
    public void testGroupByKey() {
        /*kafkaStream.filter(new Predicate<String, String>() {
            @Override
            public boolean test(String key, String value) {
                return key.equals("cq");
            }
        })*/
        /*kafkaStream.groupByKey()
                .count(Materialized.with(Serdes.String(),Serdes.Long()))
                //.count()
                .toStream()
                .foreach(new ForeachAction<String, Long>() {
                    @Override
                    public void apply(String key, Long value) {
                        System.out.println(key + "==" + value);
                    }
                });*/
        kafkaStream.groupByKey()
                .aggregate(new Initializer<Object>() {
                    @Override
                    public Object apply() {
                        return null;
                    }
                }, new Aggregator<String, String, Object>() {
                    @Override
                    public Object apply(String key, String value, Object aggregate) {
                        return null;
                    }
                })



        //doForeach(kafkaStream);
        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), config);

        kafkaStreams.start();
    }

    public static void main(String[] args) {
        HighLevelStream country = new HighLevelStream("country");
        country.testGroupByKey();
    }

    /**
     * 消息过滤
     * @param ks
     */
    private void doFilter(KStream<String,String> ks) {
        ks.filter(new Predicate<String, String>() {
            @Override
            public boolean test(String key, String value) {
                if(key!= null && key.equals("cq")){
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 消息映射
     * @param ks
     */
    private void doMap(KStream<String,String> ks) {
        ks.map(new KeyValueMapper<String, String, KeyValue<String, String>>() {
            @Override
            public KeyValue<String, String> apply(String key, String value) {
                return new KeyValue<>(key + "map",value + "map");
        }
        });
    }

    /**
     * 消息展开
     * @param ks
     */
    private void doFlatmap(KStream<String,String> ks) {
        ks.flatMap(new KeyValueMapper<String, String, Iterable<? extends KeyValue<?, ?>>>() {
            @Override
            public Iterable<? extends KeyValue<String, String>> apply(String key, String value) {
                ArrayList<KeyValue<String, String>> list = new ArrayList<>();
                String[] split = value.split(",");
                for (String s : split) {
                    list.add(new KeyValue<>(key,s));
                }
                return list;
            }
        });
    }
    /**
     * 更改消息的key
     */
    private void doSelectKey(KStream<String,String> ks) {
        ks.selectKey(new KeyValueMapper<String, String, String>() {
            @Override
            public String apply(String key, String value) {
                if(key != null || key.equals("wh"))
                    return key + "key";
                return key;
            }
        });
    }
    /**
     * 消息拆分 ?
     */
    private void doThrought(KStream<String,String> ks) {
        ks.through("throught",Produced.with(Serdes.String(),Serdes.String()));
    }
    /**
     * 分组
     */
    private KStream<String,Long> doGroupByKey(KStream<String,String> ks) {
        KTable<String, Long> group = ks.groupByKey()
                .count();
        return group.toStream();
    }
    /**
     * 消息输出
     */
    private void doForeach(KStream<String,String> ks) {
        ks.foreach(new ForeachAction<String, String>() {
            @Override
            public void apply(String key, String value) {
                System.out.println("key:" + key + ";value:" + value);
            }
        });
    }
    /**
     * 有状态的流处理
     */

}
