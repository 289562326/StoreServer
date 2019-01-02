package com.znv.peim.bean;

import lombok.Data;

/**
 * 自动setter/getter
 * Created by MaHuiming on 2018/12/5.
 */
@Data
public class MeteData {
    String fsuId;
    String deviceId;
    String meteId;
    int type;
    float measuredval;
    float setupval;
    int status;
    String recordtime;
}
