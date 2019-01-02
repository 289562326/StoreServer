package com.znv.framework.datasource;

import com.alibaba.fastjson.JSONArray;
import com.znv.data.dao.Database;
import com.znv.exception.NvException;
import com.znv.utils.Encoding;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据库连接对象，只允许DatabaseManager调用初始化
 *
 * @author Administrator
 */
public final class DatabaseItem {

    DatabaseItem() {

    }

    private Database dbDatabase = null;

    public Database getDbDatabase() {
        return dbDatabase;
    }

    public void setDbDatabase(Database dbDatabase) {
        this.dbDatabase = dbDatabase;
    }

    /**
     * 事务
     *
     * @return 数据库交互对象。
     * @throws SQLException 执行异常。
     */
    public DBTransaction beginTransaction() throws SQLException {
        DBTransaction dbt = new DBTransaction();
        dbt.setT(dbDatabase.beginTransaction());
        return dbt;
    }

    /**
     * 查询数据集。
     *
     * @param command 命令及参数。
     * @return 数据列表
     * @throws NvException 执行异常。
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public List<List<Map<String, Object>>> executeDataSets(String command, Object... objects) throws NvException,
        SQLException {
        List<List<Map<String, Object>>> all = new ArrayList<List<Map<String, Object>>>();
        try {

            Map<String, Object> map1 = dbDatabase.executeDataSet(2 * 60, command, objects);
            int count = (Integer) (map1.get("#RESULT-SET-COUNT"));
            for (int i = 1; i <= count; i++) {
                List<Map<String, Object>> rs1 = (List<Map<String, Object>>) map1.get("#RESULT-SET-" + i);
                if (rs1 != null && rs1.size() > 0) {
                    all.add(rs1);
                }
            }
        } catch (SQLException e) {
//            log.error("{}",e);
            throw new NvException();
        }
        return all;
    }

    /**
     * @param command
     * @param objects
     * @return
     * @throws SQLException
     * @throws NvException
     */
    public JSONArray executeDataSetsReturnJSON(String command, Object... objects) throws SQLException, NvException {
        JSONArray array = new JSONArray();
        try {
            Map<String, Object> map1 = dbDatabase.executeDataSet(2 * 60, command, objects);
            int count = (Integer) (map1.get("#RESULT-SET-COUNT"));
            JSONArray obj = null;
            for (int i = 1; i <= count; i++) {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> rs1 = (List<Map<String, Object>>) map1.get("#RESULT-SET-" + i);
                if (rs1 != null) {
                    obj = (JSONArray) JSONArray.toJSON(rs1);
                    array.addAll(obj);
                }
            }
        } catch (SQLException e) {
//            log.error("{}",e);
            throw new NvException();
        }

        return array;
    }

    /**
     * 查询数据集。
     *
     * @param t 数据库交互对象。
     * @param command 命令及参数。
     * @return 数据列表
     * @throws NvException 执行异常。
     */
    @SuppressWarnings("unchecked")
    public List<List<Map<String, Object>>> executeDataSets(DBTransaction t, String command) throws NvException {
        List<List<Map<String, Object>>> all = new ArrayList<List<Map<String, Object>>>();
        try {
            Map<String, Object> map1 = null;
            if (t == null || t.getT() == null) {
                throw new NvException();
            } else {
                map1 = dbDatabase.executeDataSet(t.getT(), command);
            }
            int count = (Integer) (map1.get("#RESULT-SET-COUNT"));
            for (int i = 1; i <= count; i++) {
                List<Map<String, Object>> rs1 = (List<Map<String, Object>>) map1.get("#RESULT-SET-" + i);
                if (rs1 != null) {
                    all.add(rs1);
                }
            }
        } catch (SQLException e) {
//            log.error("{}",e);
            throw new NvException();
        }

        return all;
    }

    /**
     * 查询数据集。
     *
     * @param command 命令及参数。
     * @param print 是否打印
     * @return 数据列表
     * @throws NvException 执行异常。
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> executeDataSet(String command, boolean print) throws NvException, SQLException {
        List<Map<String, Object>> rs1 = new ArrayList<Map<String, Object>>();
        try {
            Map<String, Object> map1 = dbDatabase.executeDataSet(command);
            rs1 = (List<Map<String, Object>>) map1.get("#RESULT-SET-1");
            if (print) {
                printTable(rs1);
            }
        } catch (SQLException e) {
//            log.error("{}",e);
            throw new NvException();
        }
        return rs1;
    }

    /**
     * 执行不带参数、不返回结果集的sql语句
     *
     * @param command 命令及参数。
     * @throws NvException 执行异常。
     * @throws SQLException
     */
    public void executeSql(String command) throws SQLException, NvException {
        try {
            Object[] params = new Object[0];
            dbDatabase.executeNonQuery(command, params);
        } catch (SQLException e) {
//            log.error("{}",e);
            throw new NvException();
        }
    }

    /**
     * 执行带参数、不返回结果集的sql语句
     *
     * @param command 命令及参数。
     * @throws NvException 执行异常。
     * @throws SQLException
     */
    public void executeSql(String command, Object... objects) throws SQLException, NvException {
        try {
            dbDatabase.executeNonQuery(command, objects);
        } catch (SQLException e) {
//            log.error("{}",e);
            throw new NvException();
        }
    }

    /**
     * 执行返回单个结果的sql语句
     *
     * @param command 命令及参数。
     * @throws NvException 执行异常。
     * @throws SQLException
     */
    public Object executeScalar(String command) throws SQLException, NvException {
        Object result = null;
        try {
            Object[] params = new Object[0];
            result = dbDatabase.executeScalar(command, params);
        } catch (SQLException e) {
//            log.error("{}",e);
            throw new NvException();
        }
        return result;
    }

    /**
     * 执行返回单个结果的sql语句
     *
     * @param command 命令及参数。
     * @throws NvException 执行异常。
     * @throws SQLException
     */
    public Object executeScalar(String command, Object... params) throws SQLException, NvException {
        Object result = null;
        try {
            result = dbDatabase.executeScalar(command, params);
        } catch (SQLException e) {
//            log.error("{}",e);
            throw new NvException();
        }
        return result;
    }

    /**
     * 打印表。
     *
     * @param rs 返回的数据集。
     */
    public void printTable(List<Map<String, Object>> rs) {
        boolean isHeadPrinted = false;

        for (Map<String, Object> m : rs) {
            Set<String> set = m.keySet();
            if (!isHeadPrinted) {
                String s = "";
                for (String str : set) {
                    s += "[" + str + "]" + '\t';
                }
//                log.debug(s);
                isHeadPrinted = true;
            }

            String s = "";
            for (String str : set) {
                try {
                    Object o = m.get(str);
                    s += "[" + (o == null ? "NULL" : Encoding.deStr(o.toString())) + "]" + '\t';
                } catch (UnsupportedEncodingException e) {
//                    log.error("{}",e);
                } catch (NullPointerException e) {
//                    log.error("{}",e);
                }
            }
//            log.debug(s);
        }
    }
}
