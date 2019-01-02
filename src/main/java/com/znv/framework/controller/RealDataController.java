package com.znv.framework.controller;

import com.znv.framework.services.RealDataService;
import com.znv.framework.utils.DateUtil;
import com.znv.peim.bean.MeteData;
import com.znv.peim.test.RedisCapability;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MaHuiming
 * @date 2018/12/05
 */
@RestController
@Slf4j
public class RealDataController {
    @Autowired
    private RealDataService realDataService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisCapability redisCapabilityTest;
//    @Autowired
//    private RedisUtil redisUtil;

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public String insert(@RequestParam(value = "fsuId", required = true) String fsuId,
    @RequestParam(value = "deviceId", required = true) String deviceId,
    @RequestParam(value = "meteId", required = true) String meteId,
    @RequestParam(value = "type", required = false) int type,
    @RequestParam(value = "measuredval", required = false) float measuredval,
    @RequestParam(value = "setupval", required = false) float setupval,
    @RequestParam(value = "status", required = false) int status,
    @RequestParam(value = "recordtime", required = false) String recordtime) {
        MeteData meteData = new MeteData();
        meteData.setFsuId(fsuId);
        meteData.setDeviceId(deviceId);
        meteData.setMeteId(meteId);
        meteData.setType(type);
        meteData.setMeasuredval(measuredval);
        meteData.setSetupval(setupval);
        meteData.setRecordtime(recordtime);
        realDataService.insertRealData(meteData);
        return "insert ok";
    }
    @RequestMapping(value = "/insertBatch", method = RequestMethod.GET)
    public String insertBatch(@RequestParam(value = "num", required = false) int num) {
        List<MeteData> meteDatas = new ArrayList<MeteData>();
        for(int i=0;i<num;i++){
            MeteData meteData = new MeteData();
            meteData.setFsuId("111"+i);
            meteData.setDeviceId("121"+i);
            meteData.setMeteId("131"+i);
            meteData.setType(1);
            meteData.setMeasuredval(1);
            meteData.setSetupval(1);
            meteData.setRecordtime(DateUtil.getStringDate());
            meteDatas.add(meteData);
        }

        List<Object[]> meteDataList = new ArrayList<Object[]>();
        for(int i=0;i<num;i++){
            Object[] meteData = new Object[8];
            meteData[0]=("111"+i);
            meteData[1]=("121"+i);
            meteData[2]=("131"+i);
            meteData[3]=(1);
            meteData[4]=(1);
            meteData[5]=(1);
            meteData[6]=(1);
            meteData[7]=(DateUtil.getStringDate());
            meteDataList.add(meteData);
        }
        long start = System.currentTimeMillis();
        realDataService.insertRealDataList(meteDatas);
//        try {
//            DatabaseManager.getHisDbDatabase().getDbDatabase().executeBatch("insert into  t_zxm_metedatahis (fsu_id, device_id, mete_id, data_type,\n"
//            + "        measuredval, setupval, status_fg, recordtime) values(?,?,?,?,?,?,?,?)",meteDataList);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        long end = System.currentTimeMillis();
        log.info("time:{}ms",(end-start));
        return "hello test";
    }
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        try {
            redisCapabilityTest.init();
            redisCapabilityTest.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello test";
    }

    @RequestMapping(value = "/debug", method = RequestMethod.GET)
    public void debug(HttpServletResponse response) {
        try {
            response.sendRedirect("swagger-ui.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return "redirect:swagger-ui.html";
    }

    @RequestMapping(value = "/insertRedis", method = RequestMethod.GET)
    public String insertRedis(@RequestParam(value = "key", required = true) String key,
    @RequestParam(value = "value", required = true) String value) {
        long start = System.currentTimeMillis();
        redisTemplate.opsForValue().set(key,value);
        long end = System.currentTimeMillis();
        log.info("set key use time:{}ms",(end-start));
        return "success";
    }

    @RequestMapping(value = "/batchInsertRedis", method = RequestMethod.GET)
    public String batchInsertRedis(@RequestParam(value = "num", required = true) int num) {
        long start = System.currentTimeMillis();
        for(int i=0;i<num;i++){
            redisTemplate.opsForValue().set("key"+i,"value"+i);
        }
        long end = System.currentTimeMillis();
        log.info("set key use time:{}ms",(end-start));
        return "success";
    }

    @RequestMapping(value = "/getKey", method = RequestMethod.POST)
    public String getKey(@RequestParam(value = "key", required = true) String key) {
        long start = System.currentTimeMillis();
        Object o = redisTemplate.opsForValue().get(key);
        long end = System.currentTimeMillis();
        log.info("get key use time:{}ms",(end-start));
        return "success";
    }
}
