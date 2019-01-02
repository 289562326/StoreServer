package com.znv.framework.dao.mapper;

import com.znv.peim.bean.MeteData;

import java.util.List;

/**
 * //TODO 如何传bean参数？
 * Created by MaHuiming on 2018/12/5.
 */
public interface RealDataMapper {
//    void addDeviceToDBAllParam(
//    @Param(value = "fsuId") String fsuId,
//    @Param(value = "deviceId") String deviceId,
//    @Param(value = "meteId") String meteId,
//    @Param(value = "type") int type,
//    @Param(value = "measuredval") float measuredval,
//    @Param(value = "setupval") float setupval,
//    @Param(value = "status") int status,
//    @Param(value = "recordtime") String recordtime);

    void insertRealData(MeteData meteData);

    void insertRealDataList(List<MeteData> meteDatas);
}
