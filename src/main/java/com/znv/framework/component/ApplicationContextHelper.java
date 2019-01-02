package com.znv.framework.component;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 应用上下文帮助类
 * @author MaHuiming
 * @date 2018/12/8.
 */
@Component
public class ApplicationContextHelper implements ApplicationContextAware {

    private static ApplicationContext APPCONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        APPCONTEXT = applicationContext;
    }

    public static Object getBean(String beanName) {
        return APPCONTEXT.getBean(beanName);
    }

    public static Object getBean(Class<?> class1) {

        return APPCONTEXT.getBean(class1);
    }
}
