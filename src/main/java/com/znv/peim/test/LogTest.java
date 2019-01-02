package com.znv.peim.test;

import org.apache.log4j.Logger;

/**
 * @author MaHuiming
 * @date 2018/12/30.
 */
public class LogTest {
    //log4j的方式
    final static Logger logger = Logger.getLogger(LogTest.class);
    public static void main(String[] args) {
        logger.debug("hello debug,{}");
        logger.info("hello info,{}");
        logger.error("hello error,{}");
    }
}
