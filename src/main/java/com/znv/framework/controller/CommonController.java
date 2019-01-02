package com.znv.framework.controller;

import com.znv.framework.common.CommonParamConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MaHuiming
 * @date 2018/12/13.
 */
@RestController
@Slf4j
public class CommonController {
    @RequestMapping(value = "/setParam", method = RequestMethod.GET)
    public String setParam(@RequestParam(value = "key", required = true) String key,
    @RequestParam(value = "value", required = true) String value) {
        return "success";
    }

    /**
     *
     * @param value
     * @return
     */
    @RequestMapping(value = "/setRestSwitchParam", method = RequestMethod.GET)
    public String setParam(
    @RequestParam(value = "value", required = true) String value) {
        boolean restParamSwitch= Boolean.valueOf(value);
        CommonParamConfig.restParamSwitch = restParamSwitch;
        return "success："+ CommonParamConfig.restParamSwitch;
    }
}
