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

    private static final String PropFile = "DBconnection.properties";
    private static DataSource ds;
    private BasicDataSource basicDS = new BasicDataSource();

    private DataSource() {

        basicDS.setDriverClassName(DataManager.getPropFile(PropFile).getProperty("JDBC_DRIVER"));
        basicDS.setUsername(DataManager.getPropFile(PropFile).getProperty("USER"));
        basicDS.setPassword(DataManager.getPropFile(PropFile).getProperty("PASS"));
        basicDS.setUrl(DataManager.getPropFile(PropFile).getProperty("DB_URL"));

        basicDS.setInitialSize(2);
        basicDS.setMaxTotal(10);
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
