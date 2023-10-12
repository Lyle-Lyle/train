package com.lyle.train.business.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Train {
    private Long id;

    private String code;

    private String type;

    private String origin;

    private Date departure;

    private String destination;

    private Date arrival;

    private Date createTime;

    private Date updateTime;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", code=").append(code);
        sb.append(", type=").append(type);
        sb.append(", origin=").append(origin);
        sb.append(", departure=").append(departure);
        sb.append(", destination=").append(destination);
        sb.append(", arrival=").append(arrival);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}