package com.znv.framework.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * web拦截器
 * @author MaHuiming
 * @date 2018/12/13.
 */
@Slf4j
@Component
public class WebConfigHandler implements HandlerInterceptor {
    /**
     * 请求开始时间
     */
    ThreadLocal<Long> stThreadLocal = new ThreadLocal<Long>();
    /**
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //输出入参等信息
        boolean paramSwitch = CommonParamConfig.restParamSwitch;
        if(paramSwitch){
            printParams(request,response,handler);
        }
        return true;
    }

    /**
     *  输出入参等信息
     * @param request  request
     * @param response response
     * @param handler handler
     */
    private void printParams(HttpServletRequest request, HttpServletResponse response, Object handler){

        long st=System.currentTimeMillis();
        stThreadLocal.set(st);
        // 接收到请求，记录请求内容
        StringBuilder sb=new StringBuilder();
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        sb.append("本次请求信息如下:\r\n");
        sb.append("RequestId:"+st+"\r\n");
        sb.append("RequestURL:"+request.getRequestURL().toString()+"\r\n");
        sb.append("RemoteAddr : " + request.getRemoteAddr()+"\r\n");
        sb.append("Method:"+handlerMethod.getBean().getClass().getName()+"."+handlerMethod.getMethod().getName()+"\r\n");
        //获取所有请求参数：
        Enumeration<String> enu = request.getParameterNames();
        sb.append("RequestParams:[");
        while (enu.hasMoreElements()) {
            String paraName = enu.nextElement();
            sb.append(paraName+": "+request.getParameter(paraName));
            if(enu.hasMoreElements()){
                sb.append(",");
            }
        }
        sb.append("]");
        log.info(sb.toString());
    }
}
