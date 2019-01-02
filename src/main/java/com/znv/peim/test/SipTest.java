package com.znv.peim.test;

import com.znv.ModuleType;
import com.znv.command.CommandManager;
import com.znv.command.ICommand;
import com.znv.exception.ScopException;
import com.znv.log.Logger;
import com.znv.protocol.ProtocolBuilder;
import com.znv.protocol.ProtocolNode;
import com.znv.scop.proxy.ICallback;
import com.znv.scop.proxy.IProxy;
import com.znv.scop.proxy.IWebProxy;
import com.znv.scop.proxy.factory.ProxyFactory;
import com.znv.scop.proxy.factory.ProxyType;
import com.znv.utils.SecureHelper;

import java.io.File;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * @author MaHuiming
 * @date 2018/12/31.
 */
public class SipTest {
    static CommandManager cm = new CommandManager();
    static IWebProxy proxy = null;

    public static void main(String[] args) {
        //tcp连接
        try {
            proxy=(IWebProxy) ProxyFactory.getProxy("10.45.157.66", 8000, ProxyType.Web, ModuleType.SIP);
        } catch (Exception e) {
            System.out.println("连接失败!");
            System.exit(0);
        }
        //web注册
        try {
             proxy.regist("00001006000000000606", "104");
        } catch (Exception e) {
            System.out.println("注册失败!");
            System.exit(0);
        }
        proxy.setTimeout(10000);

        // 打印是否已经连接上
        if (proxy.isConnected()) {
            System.out.println("connect to server success!");
        }
        try {
            System.out.println(SecureHelper.encrypt("888888"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ProtocolBuilder pb = new ProtocolBuilder("0050003");
        ProtocolNode pn = pb.createNode("IE_USER_LOGIN");
        pn.addAttribute("account_id", "admin");
        try {
            pn.addAttribute("password",SecureHelper.encrypt( "888888"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        pn.addAttribute("action", "1");
        try {
            pn.addAttribute("client_ip", InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        ProtocolBuilder returnPb = null;
        try {
            returnPb = proxy.send(pb);
        } catch (ScopException e) {
            e.printStackTrace();
        }
        System.out.println(returnPb);

        try {
            proxy.registCallback(new ICallback() {
                @Override
                public ProtocolBuilder process(ProtocolBuilder pb) throws ScopException {
                    System.out.println(pb.toString());
                    return pb;
                }
            });
        } catch (ScopException e) {
            e.printStackTrace();
        }
        String sessionid=returnPb.getSessionID();
        @SuppressWarnings("unused")
        String userId = returnPb.getFirstNode("IE_USER_LOGIN_RES").getAttribute("user_id");
        if (returnPb.getErrorCode()<0) {
            System.out.println("登陆失败!");
            System.exit(0);
        }
        System.out.println("user login success!");
        cm.setPreFix("Client");

        Class<?> clazz = AbstractProtocolCommand.class;
        URL path = clazz.getResource("");
        System.out.println("path:" + path);
        String path2 = path.toString().substring(6, path.toString().length());
        File f = new File(path2);
        RegistCommand(sessionid,f);
        cm.runEventLoop();
    }
    private static void RegistCommand(String sessionid,File f) {

        File[] files = f.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                RegistCommand(sessionid,files[i]);
                continue;
            }
            String path=files[i].toString();

            String className = path.substring(0,path.length()-6)
            .substring(path.indexOf("com")).replace(System.getProperty("file.separator"), ".");
            if (className.contains("AbstractProtocolCommand") || className.contains("$")) {
                continue;
            }
            try {
                Class<?> newcls = Class.forName(className);
                Class<?>[] pt = new Class<?>[] { IProxy.class,String.class };
                ICommand cmd = (ICommand) newcls.getConstructor(pt).newInstance(proxy,sessionid);
                cm.registCommand(cmd);
            } catch (Exception e) {
                Logger.L.debug(files[i].getName());
            }
        }
    }
}
