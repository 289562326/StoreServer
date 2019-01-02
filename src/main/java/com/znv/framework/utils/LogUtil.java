package com.znv.framework.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.spi.ExtendedLogger;
import org.apache.logging.log4j.util.ReflectionUtil;

/**
 * log4j2
 * 框架不用,刻意注释，默认使用logback
 */
public class LogUtil {

    public static void trace(String msg, Object... params) {
        String callerFQNC = ReflectionUtil.getCallerClass(1).getName();
        String className = ReflectionUtil.getCallerClass(2).getName();
        ExtendedLogger log = LogManager.getContext().getLogger(className);
        log.logIfEnabled(callerFQNC, Level.TRACE, null, msg, params);
    }

    public static void debug(String msg, Object... params) {
        String callerFQNC = ReflectionUtil.getCallerClass(1).getName();
        String className = ReflectionUtil.getCallerClass(2).getName();
        ExtendedLogger log = LogManager.getContext().getLogger(className);
        log.logIfEnabled(callerFQNC, Level.DEBUG, null, msg, params);
    }

    public static void info(String msg, Object... params) {
        String callerFQNC = ReflectionUtil.getCallerClass(1).getName();
        String className = ReflectionUtil.getCallerClass(2).getName();
        ExtendedLogger log = LogManager.getContext().getLogger(className);
        log.logIfEnabled(callerFQNC, Level.INFO, null, msg, params);
    }

    public static void warn(String msg, Object... params) {
        String callerFQNC = ReflectionUtil.getCallerClass(1).getName();
        String className = ReflectionUtil.getCallerClass(2).getName();
        ExtendedLogger log = LogManager.getContext().getLogger(className);
        log.logIfEnabled(callerFQNC, Level.WARN, null, msg, params);
    }

    public static void error(String msg, Object... params) {
        String callerFQNC = ReflectionUtil.getCallerClass(1).getName();
        String className = ReflectionUtil.getCallerClass(2).getName();
        ExtendedLogger log = LogManager.getContext().getLogger(className);
        log.logIfEnabled(callerFQNC, Level.ERROR, null, msg, params);
    }

    public static void fatal(String msg, Object... params) {
        String callerFQNC = ReflectionUtil.getCallerClass(1).getName();
        String className = ReflectionUtil.getCallerClass(2).getName();
        ExtendedLogger log = LogManager.getContext().getLogger(className);
        log.logIfEnabled(callerFQNC, Level.FATAL, null, msg, params);
    }

    public static void exception(Throwable e) {
        String callerFQNC = ReflectionUtil.getCallerClass(1).getName();
        String className = ReflectionUtil.getCallerClass(2).getName();
        ExtendedLogger log = LogManager.getContext().getLogger(className);
        log.logIfEnabled(callerFQNC, Level.ERROR, null, "", e);
    }

    public static void exception(Throwable e, String msg) {
        String callerFQNC = ReflectionUtil.getCallerClass(1).getName();
        String className = ReflectionUtil.getCallerClass(2).getName();
        ExtendedLogger log = LogManager.getContext().getLogger(className);
        log.logIfEnabled(callerFQNC, Level.ERROR, null, msg, e);
    }

    public static String getLevel() {
        String className = ReflectionUtil.getCallerClass(2).getName();
        ExtendedLogger log = LogManager.getContext().getLogger(className);
        return log.getLevel().name();
    }
}