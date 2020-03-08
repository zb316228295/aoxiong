package org.example.partition;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;

public class BananaPartition implements Partitioner {
    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
       /*
       获取主题对应的分区
        */
        List<PartitionInfo> partitionInfos = cluster.availablePartitionsForTopic(s);
        if(null == s || !(o instanceof String))
            throw new RuntimeException("key格式错误");
        if(s.equals("banana")) {
            return partitionInfos.size();
        }
        if (partitionInfos.size() - 1 != 0) {
            return s.hashCode() % (partitionInfos.size() - 1);
        }
        return 0;

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
