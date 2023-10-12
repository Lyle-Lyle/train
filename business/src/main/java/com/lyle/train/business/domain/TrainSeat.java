package com.lyle.train.business.domain;

import lombok.Data;

import java.util.Date;

@Data
public class TrainSeat {
    private Long id;

    private String trainCode;

    private Integer carIndex;

    private String rowNum;

    private String colNum;

    private String seatType;

    private Integer carriageSeatIndex;

    private Date createTime;

    private Date updateTime;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", trainCode=").append(trainCode);
        sb.append(", carIndex=").append(carIndex);
        sb.append(", rowNum=").append(rowNum);
        sb.append(", colNum=").append(colNum);
        sb.append(", seatType=").append(seatType);
        sb.append(", carriageSeatIndex=").append(carriageSeatIndex);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}