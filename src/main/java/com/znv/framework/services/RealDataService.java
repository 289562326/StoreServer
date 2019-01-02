package com.znv.framework.services;

import com.znv.peim.bean.MeteData;

import java.util.List;

/**
 *
 * @author  MaHuiming
 * @date 2018/12/5
 */
public interface RealDataService {

    void insertRealData(MeteData meteData);
    void insertRealDataList(List<MeteData> meteDatas);
}
