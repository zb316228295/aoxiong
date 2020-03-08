package org.example.message;

import java.io.Serializable;

public class PartitionMessage implements Serializable {
    private static final long serialVersionUID = -6512182632036454583L;
    private String partitionName;
    private Integer version;

    public String getPartitionName() {
        return partitionName;
    }

    public void setPartitionName(String partitionName) {
        this.partitionName = partitionName;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public PartitionMessage(String partitionName, Integer version) {
        this.partitionName = partitionName;
        this.version = version;
    }
}
