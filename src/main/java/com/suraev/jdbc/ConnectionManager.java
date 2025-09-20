package com.suraev.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;



public final class ConnectionManager {
    private static final String USERNAME = "db.username";
    private static final String PASSWORD = "db.password";
    private static final String URL = "db.url";
    private static final String POOL_SIZE = "db.pool.size";
    private static final Integer DEFAULT_POOL_SIZE=10;
    private static  BlockingQueue <Connection> pool;
    private static  List <Connection> sourcePool;

    private ConnectionManager() {}


//    static {
//        Class.forName(Driver);
//    }

    static {
        initConnectionPool();
    }

    private static void initConnectionPool() {
        var poolSize = PropertiesUtil.get(POOL_SIZE);
        var size = poolSize == null ? DEFAULT_POOL_SIZE : Integer.parseInt(poolSize);
        pool = new ArrayBlockingQueue<>(size);
        sourcePool = new ArrayList<>(size);

        for(int i=0; i<size; i++) {
            Connection realConnection = get();

            WrapperConnection wrapped = new WrapperConnection(realConnection, pool);

            pool.add(wrapped);
            sourcePool.add(realConnection);
        }

        
    }

    public static Connection getConnection() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



    private static Connection get() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(URL),
                    PropertiesUtil.get(USERNAME),
                    PropertiesUtil.get(PASSWORD));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConectionPool() {
        for (Connection connection : sourcePool) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }   
        }
    }    
    
}
