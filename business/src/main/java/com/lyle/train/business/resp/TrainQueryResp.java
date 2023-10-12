package com.lyle.train.business.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;

@Data
public class TrainQueryResp {

    /**
     * id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /**
     * 车次编号
     */
    private String code;

    /**
     * 车次类型|枚举[TrainTypeEnum]
     */
    private String type;

    /**
     * 始发站
     */
    private String origin;


    /**
     * 出发时间
     */
    @JsonFormat(pattern = "HH:mm:ss",timezone = "GMT-4")
    private Date departure;

    /**
     * 终点站
     */
    private String destination;


    /**
     * 到站时间
     */
    @JsonFormat(pattern = "HH:mm:ss",timezone = "GMT-4")
    private Date arrival;

    /**
     * 新增时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT-4")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT-4")
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
