package com.znv.framework.services.impl;

import com.znv.framework.dao.mapper.RealDataMapper;
import com.znv.framework.services.RealDataService;
import com.znv.peim.bean.MeteData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * Created by MHm on 2018/8/13.
 */
@Service
public class RealDataServiceImp implements RealDataService {
    @Autowired
    private RealDataMapper realDataMapper;

    @Override
    public void insertRealData(MeteData meteData) {
        realDataMapper.insertRealData(meteData);
    }

    @Override
    public void insertRealDataList(List<MeteData> meteDatas) {
        realDataMapper.insertRealDataList(meteDatas);
    }
}
