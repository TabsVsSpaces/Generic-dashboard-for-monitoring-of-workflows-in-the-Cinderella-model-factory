/*
TODO: 
    -Docs for JDBC Pooling
        http://commons.apache.org/proper/commons-dbcp/
        http://commons.apache.org/proper/commons-pool/
        http://commons.apache.org/proper/commons-logging/
 */
package Model;

import Helper.*;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

public class JDBC_ConnPool {
    
    private static final String PropFile = "DBconnection.properties";
    
    public static DataSource getDataSource() {

        BasicDataSource ds = new BasicDataSource();

        ds.setDriverClassName(DataManager.getPropFile(PropFile).getProperty("JDBC_DRIVER"));
        ds.setUsername(DataManager.getPropFile(PropFile).getProperty("USER"));
        ds.setPassword(DataManager.getPropFile(PropFile).getProperty("PASS"));
        ds.setUrl(DataManager.getPropFile(PropFile).getProperty("DB_URL"));
        
        return ds;
    }
    
    //public void close()
}
