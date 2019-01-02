package com.znv.peim.test;

import com.znv.command.AbstractCommand;
import com.znv.exception.ErrorCode;
import com.znv.exception.ScopException;
import com.znv.log.Logger;
import com.znv.protocol.ProtocolBuilder;
import com.znv.protocol.ProtocolNode;
import com.znv.scop.proxy.IProxy;

import java.util.List;

/**
 *
 */
public abstract class AbstractProtocolCommand extends AbstractCommand {
    String sessionID;

    public AbstractProtocolCommand(IProxy proxy) {
        m_Proxy = (IProxy) proxy;
    }

    public AbstractProtocolCommand(IProxy proxy, String sessionID) {
        m_Proxy = (IProxy) proxy;
        this.sessionID = sessionID;
    }

    IProxy m_Proxy;

    protected IProxy getProxy() {
        return m_Proxy;
    }

    public String getDemoCommandLine() {
        return "";
    }

    @Override
    public String getCommandResult() {
        return "";
    }

    @Override
    protected void printProtocolToConsole(ProtocolBuilder pb) {
        Logger.L.debug(pb);
        System.out.print(
            "协议号:" + pb.getID() + "\tSessionID:" + (pb.getSessionID().isEmpty() ? "空" : pb.getSessionID()) + "\t错误号: ["
                + pb.getErrorCode() + "]" + (pb.getErrorCode() == 0 ? "" : ErrorCode.getName(pb.getErrorCode())));
        for (ProtocolNode pn : pb.getNodes()) {
            String str = "";
            printprotolNode(str, pn);
        }
        System.out.print("\n");

    }

    private void printprotolNode(String str, ProtocolNode pn) {
        StringBuffer sb = new StringBuffer();
        str += '\t';
        sb.append(str);
        System.out.print("\n" + sb.toString() + "<");
        System.out.print(pn.getName());
        System.out.print(" ");
        String[] args = pn.getAttributeNames();
        for (int i = 0; i < args.length; i++) {
            String key = args[i];
            String val = pn.getAttribute(args[i]);
            if (key != "") {
                System.out.print(key);
                System.out.print("=");
            }
            System.out.print("\"" + val + "\"");
            System.out.print(" ");
        }
        System.out.print("/>");
        if (pn.getNodes().size() > 0) {
            for (ProtocolNode pNode : pn.getNodes()) {
                printprotolNode(str, pNode);
            }
        }

    }

    public abstract void internalRun(List<String> args) throws ScopException;

    @Override
    public void run(List<String> args) {
        try {
            internalRun(args);
        } catch (ScopException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
