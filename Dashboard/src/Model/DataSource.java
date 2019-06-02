/*
TODO: 
    -Docs for JDBC Pooling
        http://commons.apache.org/proper/commons-dbcp/
        http://commons.apache.org/proper/commons-pool/
        http://commons.apache.org/proper/commons-logging/
 */
package Model;

import Helper.*;
import org.apache.commons.dbcp2.BasicDataSource;

public class DataSource {

    private static final String PROP_NAME = "DBconnection.properties";

    private static DataSource ds;
    private BasicDataSource basicDS = new BasicDataSource();

    private DataSource() {

        basicDS.setDriverClassName(DataManager.getPropFile(PROP_NAME).getProperty("JDBC_DRIVER"));
        basicDS.setUsername(DataManager.getPropFile(PROP_NAME).getProperty("USER"));
        basicDS.setPassword(DataManager.getPropFile(PROP_NAME).getProperty("PASS"));
        basicDS.setUrl(DataManager.getPropFile(PROP_NAME).getProperty("DB_URL"));

        basicDS.setInitialSize(Integer.parseInt(DataManager.getPropFile(PROP_NAME).getProperty("initialSize")));
        basicDS.setMaxTotal(Integer.parseInt(DataManager.getPropFile(PROP_NAME).getProperty("maxSize")));
        basicDS.setMinIdle(Integer.parseInt(DataManager.getPropFile(PROP_NAME).getProperty("minIdle")));
        basicDS.setMaxIdle(Integer.parseInt(DataManager.getPropFile(PROP_NAME).getProperty("maxIdle")));

    }

    public static DataSource getInstance() {
        if (ds == null) {
            ds = new DataSource();
        }
        return ds;
    }

    public BasicDataSource getBasicDS() {
        return basicDS;
    }

    public void setBasicDS(BasicDataSource basicDS) {
        this.basicDS = basicDS;
    }
}

//public void close()
