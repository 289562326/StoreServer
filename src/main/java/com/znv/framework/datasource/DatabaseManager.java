package com.znv.framework.datasource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.data.dao.Database;
import com.znv.data.dao.DatabaseFactory;
import com.znv.exception.NvException;
import com.znv.framework.utils.DateUtil;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据库管理。
 *
 * @author Administrator
 */
public final class DatabaseManager {

    private DatabaseManager() {

    }

    /**
     * 数据库。
     */
    private static DatabaseItem dbDatabaseItem = null;
    private static DatabaseItem hisDbDatabaseItem = null;
    private static String hisDbConnParam = "";
    private static final String DEFAULT_P_WD = "";
    /**
     * 数据库管理器,单例。
     */
    public static DatabaseItem getDatabase() {
        if (dbDatabaseItem == null) {
            dbDatabaseItem = new DatabaseItem();
            dbDatabaseItem.setDbDatabase(DatabaseFactory.getDatabase("ZNVDB"));
        }
        return dbDatabaseItem;
    }

    public static DatabaseItem getHisDbDatabase() {
        if (hisDbDatabaseItem == null) {
            hisDbDatabaseItem = new DatabaseItem();
            hisDbDatabaseItem.setDbDatabase(DatabaseFactory.getDatabase("HISDB"));
        }
        return hisDbDatabaseItem;
    }

    /**
     * 与应用服务器获取连接信息
     */
    @SuppressWarnings("unchecked")
    public static void initDataBase() {
        // 向应用服务器发协议获取数据库配置
        File file = new File(
        "D:\\03-znv-peim\\svn_project\\PEIM\\trunk\\V1.30\\src\\ApplicationServer\\icap\\icap.lib\\NvDataAccess4j.xml");
        try {
            DatabaseFactory.initDB(file.getAbsolutePath());
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
    /**
     * his库连接参数，用于报表导入时，通过命令行执行sql文件。形式为：" -h 127.0.0.1 -P 3306 -u root -p123456"
     * @return his库连接参数
     */
    public static String getHisDbConnParam() {
        return hisDbConnParam;
    }

    /**
     * 关闭数据库。
     *
     */
    public static void destroyDatabase() {
        try {
            // 获取、关闭原始JDB连接
            if (dbDatabaseItem != null) {
                Database dbDatabase = dbDatabaseItem.getDbDatabase();
                Connection conn = dbDatabase.getConnection();
                dbDatabase.closeConnection(conn);
            }
            if (hisDbDatabaseItem != null) {
                Database hisDbDatabase = hisDbDatabaseItem.getDbDatabase();
                Connection conn = hisDbDatabase.getConnection();
                hisDbDatabase.closeConnection(conn);
            }
            // 销毁已初始化的所有数据库
            DatabaseFactory.destroyDB();
        } catch (Exception e) {
//           log.error("{}",e);
        }
    }

    public static JSONObject transToLowerObject(JSONObject jsonObject) {
        JSONObject jsonObject2 = new JSONObject();
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            Object object = jsonObject.get(key);
            if (object == null) {
                jsonObject2.put(key.toLowerCase(), object);
            } else if (object instanceof JSONObject) {
                jsonObject2.put(key.toLowerCase(), transToLowerObject((JSONObject) object));
            } else if (object instanceof JSONArray) {
                jsonObject2.put(key.toLowerCase(), transToLowerArray(jsonObject.getJSONArray(key)));
            } else {
                jsonObject2.put(key.toLowerCase(), object);
            }
        }
        return jsonObject2;
    }

    public static JSONArray transToLowerArray(JSONArray jsonArray) {
        JSONArray jsonArray2 = new JSONArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            Object jArray = jsonArray.get(i);
            if (jArray instanceof JSONObject) {
                jsonArray2.add(transToLowerObject((JSONObject) jArray));
            } else if (jArray instanceof JSONArray) {
                jsonArray2.add(transToLowerArray((JSONArray) jArray));
            }
        }
        return jsonArray2;
    }

    public static Object transToLower(Object obj) {
        Object retObj = null;
        if (obj instanceof JSONObject) {
            retObj = transToLowerObject((JSONObject) obj);
        } else if (obj instanceof JSONArray) {
            retObj = transToLowerArray((JSONArray) obj);
        }
        return retObj;
    }

    public static void main(String[] args) throws SQLException, NvException {
        DatabaseManager.initDataBase();
        List<Object[]> meteDataList = new ArrayList<Object[]>();
        for(int i=0;i<1;i++){
            Object[] meteData = new Object[8];
            meteData[0]=("111"+i);
            meteData[1]=("121"+i);
            meteData[2]=("131"+i);
            meteData[3]=(1);
            meteData[4]=(1);
            meteData[5]=(1);
            meteData[6]=(1);
            meteData[7]=(DateUtil.getStringDate());
            meteDataList.add(meteData);
        }
        DatabaseManager.getHisDbDatabase().executeSql("select * from t_zxm_metedatahis;");
//        DatabaseManager.getHisDbDatabase().getDbDatabase().executeBatch("insert into  t_zxm_metedatahis (fsu_id, device_id, mete_id, data_type,\n"
//                    + "        measuredval, setupval, status_fg, recordtime) values(?,?,?,?,?,?,?,?)",meteDataList);

    }
}
