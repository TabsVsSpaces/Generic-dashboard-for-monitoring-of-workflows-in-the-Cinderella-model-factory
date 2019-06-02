/*
TODO:
    -close() and getState() are missing!
    -Docs for JDBC Pooling
        http://commons.apache.org/proper/commons-dbcp/
        http://commons.apache.org/proper/commons-pool/
        http://commons.apache.org/proper/commons-logging/
 */
package Model;

import Helper.*;
import org.apache.commons.dbcp2.BasicDataSource;

public class JDBCPool {

    private static final String PROP_NAME = "DBconnection.properties";

    private static JDBCPool pool;
    private BasicDataSource basicDS = new BasicDataSource();

    private JDBCPool() {

        basicDS.setDriverClassName(DataManager.getProperties(PROP_NAME).getProperty("JDBC_DRIVER"));
        basicDS.setUsername(DataManager.getProperties(PROP_NAME).getProperty("USER"));
        basicDS.setPassword(DataManager.getProperties(PROP_NAME).getProperty("PASS"));
        basicDS.setUrl(DataManager.getProperties(PROP_NAME).getProperty("DB_URL"));

        basicDS.setInitialSize(Integer.parseInt(DataManager.getProperties(PROP_NAME).getProperty("initialSize")));
        basicDS.setMaxTotal(Integer.parseInt(DataManager.getProperties(PROP_NAME).getProperty("maxSize")));
        basicDS.setMinIdle(Integer.parseInt(DataManager.getProperties(PROP_NAME).getProperty("minIdle")));
        basicDS.setMaxIdle(Integer.parseInt(DataManager.getProperties(PROP_NAME).getProperty("maxIdle")));

    }

    public static JDBCPool getInstance() {
        if (pool == null) {
            pool = new JDBCPool();
        }
        return pool;
    }

    public BasicDataSource getBasicDS() {
        return basicDS;
    }

    public void setBasicDS(BasicDataSource basicDS) {
        this.basicDS = basicDS;
    }
}

//public void close()
//Public void getState()
