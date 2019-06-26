package Model;

import Helper.*;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

public class JDBCPool {

    private static final String PROP_FILE = "./src/properties/DBconnection.properties";

    private static JDBCPool pool;
    private BasicDataSource basicDS = new BasicDataSource();

    private JDBCPool() {

        basicDS.setDriverClassName(DataManager.getProperties(PROP_FILE).getProperty("JDBC_DRIVER"));
        basicDS.setUsername(DataManager.getProperties(PROP_FILE).getProperty("USER"));
        basicDS.setPassword(DataManager.getProperties(PROP_FILE).getProperty("PASS"));
        basicDS.setUrl(DataManager.getProperties(PROP_FILE).getProperty("DB_URL"));

        basicDS.setInitialSize(Integer.parseInt(DataManager.getProperties(PROP_FILE).getProperty("initialSize")));
        basicDS.setMaxTotal(Integer.parseInt(DataManager.getProperties(PROP_FILE).getProperty("maxSize")));
        basicDS.setMinIdle(Integer.parseInt(DataManager.getProperties(PROP_FILE).getProperty("minIdle")));
        basicDS.setMaxIdle(Integer.parseInt(DataManager.getProperties(PROP_FILE).getProperty("maxIdle")));
    }

    public static synchronized JDBCPool getInstance() {
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

    public void close() throws SQLException {
        if (basicDS.isClosed() == false) {
            basicDS.close();
        }
    }
}
