package com.znv.framework.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * 配置文件的动态修改
 * @author MaHuiming
 * @date 2018/12/13.
 */
@Data
public class CommonParamConfig {

    @Value("${web.rest.param.switch}")
    public static boolean restParamSwitch ;
}
