package com.znv.peim.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author MaHuiming
 * @date 2018/12/31.
 */
public class Slf4jTest {
    final static Logger logger = LoggerFactory.getLogger(Slf4jTest.class);

    public static void main(String[] args) {
        logger.debug("hello debug,{}");
        logger.info("hello info,{}");
        logger.error("hello error,{}");
    }
}
