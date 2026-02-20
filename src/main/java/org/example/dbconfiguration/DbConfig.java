package org.example.dbconfiguration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.Exception.DataException;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

public final class DbConfig {

    private DbConfig() {
    };

    private static final HikariDataSource datasource ;

    static {

        try{
            Properties props=new Properties();


            InputStream input=Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("db.properties");


            if(input==null){
                throw new DataException("liquibase properties files are not found ");
            }

            props.load(input);
            Class.forName(props.getProperty("driver"));

            HikariConfig config=new HikariConfig();
            config.setJdbcUrl(props.getProperty("url"));
            config.setUsername(props.getProperty("username"));
            config.setPassword(props.getProperty("password"));

            config.setMaximumPoolSize(Integer.parseInt(props.getProperty("Hikari.maximumPool")));
            config.setMinimumIdle(Integer.parseInt(props.getProperty("Hikari.minimumIdle")));
            config.setIdleTimeout(Long.parseLong(props.getProperty("Hikari.idletimeout")));
            config.setConnectionTimeout(Long.parseLong(props.getProperty("Hikari.connectionTimeout")));
            datasource=new HikariDataSource(config);

        } catch (Exception e) {
            throw new DataException("Failed to Initialize Database connection ",e);
        }
    }
    public static DataSource getConnect(){
        return datasource;
    }

}