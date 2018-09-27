package com.nemo.log;

import com.nemo.common.jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


public class LogService {
    private static Logger LOGGER = LoggerFactory.getLogger(LogService.class);
    private static LogService INSTANCE = null;
    static JdbcTemplate template;
    private static int coreThreadPoolSize;
    private static int maximumThreadPoolSize;
    private final ThreadPoolExecutor executor;

    private LogService() {
        this.executor = new ThreadPoolExecutor(coreThreadPoolSize, maximumThreadPoolSize, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new LogService.LogThreadFactory(), new LogService.LogRejectedExecutionHandler());
    }







    static class LogThreadFactory implements ThreadFactory {
        AtomicInteger count = new AtomicInteger(0);

        LogThreadFactory() {
        }

        @Override
        public Thread newThread(Runnable r) {
            int curCount = this.count.incrementAndGet();
            return new Thread(r, "日志线程-" + curCount);
        }
    }

    static class LogRejectedExecutionHandler implements RejectedExecutionHandler {
        LogRejectedExecutionHandler() {
        }

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            AbstractLog log = (AbstractLog)r;
            if(r != null) {
//                LogService.LOGGER.error("丢弃日志提交请求,sql:" + log.buildInsertSQL() + ", params:" + Arrays.toString(log.buildInsertParam()));
            }
        }
    }
}
