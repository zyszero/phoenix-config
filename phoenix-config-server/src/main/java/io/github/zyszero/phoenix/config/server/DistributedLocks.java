package io.github.zyszero.phoenix.config.server;

import io.github.zyszero.phoenix.config.server.dal.LocksMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * distributed locks.
 *
 * @Author: zyszero
 * @Date: 2024/6/4 0:20
 */
@Slf4j
@Component
public class DistributedLocks {

    @Autowired
    DataSource dataSource;

    Connection connection;


    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);


    @Getter
    private AtomicBoolean locked = new AtomicBoolean(false);


    @PostConstruct
    public void init() {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        executor.scheduleWithFixedDelay(this::tryLock, 1, 5, TimeUnit.SECONDS);
    }

    public boolean lock() throws SQLException {
        connection.setAutoCommit(false);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        connection.createStatement().execute("set innodb_lock_wait_timeout = 5");
        // lock 5 seconds
        connection.createStatement().execute("select app from locks where id = 1 for update");


        if (locked.get()) {
            log.info(" ===>>>> reentrant this dist lock.");
        } else {
            // TODO 从变成主时，更新缓存
            log.info(" ===>>>> get a dist lock.");
        }

        return true;
    }


    private void tryLock() {
        try {
            lock();
            locked.set(true);
        } catch (Exception e) {
            log.debug(" ===>>>> lock failed...");
            locked.set(false);
        }

    }


    @PreDestroy
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.rollback();
                connection.close();
            }
        } catch (Exception ignored) {
            log.info("ignore this close exception.");
        }
    }


}
