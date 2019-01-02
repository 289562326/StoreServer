/**
 * <pre>
 * 标  题: DBTransaction.java.
 * 版权所有: 版权所有(C)2001-2017
 * 公   司: 深圳中兴力维技术有限公司
 * 内容摘要: // 简要描述本文件的内容，包括主要模块、函数及其功能的说明
 * 其他说明: // 其它内容的说明
 * 完成日期: // 输入完成日期，例：2000年2月25日
 * </pre>
 * <pre>
 * 修改记录1:
 *    修改日期：
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * @version 1.0
 * @author lixia
 */

package com.znv.framework.datasource;

import com.znv.data.dao.Transaction;

import java.sql.SQLException;

/**
 * 事务
 * DBTransaction
 */
public class DBTransaction {

    public DBTransaction() {

    }

    private Transaction t = null;

    public Transaction getT() {
        return t;
    }

    public void setT(Transaction t) {
        this.t = t;
    }

    public void rollback() {
        if (t != null) {
            t.rollback();
        }

    }

    public void commit() throws SQLException {
        if (t != null) {
            t.commit();
        }

    }
}
