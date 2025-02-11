package com.gobrs.async.threadpool;

import com.alibaba.ttl.threadpool.TtlExecutors;
import com.gobrs.async.autoconfig.GobrsAsyncProperties;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * The type Gobrs async thread pool factory.
 *
 * @program: gobrs -async-starter
 * @ClassName GobrsAsyncThreadPoolFactory
 * @description: Thread pool factory
 * @author: sizegang
 * @create: 2022 -02-20
 * @Version 1.0
 */
public class GobrsAsyncThreadPoolFactory {

    private GobrsAsyncProperties gobrsAsyncProperties;

    /**
     * Instantiates a new Gobrs async thread pool factory.
     *
     * @param gobrsAsyncProperties the gobrs async properties
     */
    public GobrsAsyncThreadPoolFactory(GobrsAsyncProperties gobrsAsyncProperties) {
        this.gobrsAsyncProperties = gobrsAsyncProperties;
        this.COMMON_POOL = TtlExecutors.getTtlExecutorService(createDefaultThreadPool());
        this.threadPoolExecutor = defaultThreadPool();
    }

    /**
     * The default thread pool is not long
     */
    private final ExecutorService COMMON_POOL;

    private ExecutorService threadPoolExecutor;

    /**
     * Gets thread pool executor.
     *
     * @return the thread pool executor
     */
    public ExecutorService getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

    /**
     * The user dynamically sets the thread pool parameters
     *
     * @param threadPoolExecutor the thread pool executor
     */
    public void setThreadPoolExecutor(ExecutorService threadPoolExecutor) {

        this.threadPoolExecutor = TtlExecutors.getTtlExecutorService(threadPoolExecutor);
    }

    private ExecutorService defaultThreadPool() {
        return COMMON_POOL;
    }

    /**
     * Create default thread pool thread pool executor.
     *
     * @return the thread pool executor
     */
    ThreadPoolExecutor createDefaultThreadPool() {
        GobrsAsyncProperties.ThreadPool threadPool = gobrsAsyncProperties.getThreadPool();
        return new MDCThreadPoolExecutor(threadPool.getCorePoolSize(),
                threadPool.getMaxPoolSize(), threadPool.getKeepAliveTime(), threadPool.getTimeUnit(),
                threadPool.getWorkQueue(), ThreadPoolBuilder.caseReject(threadPool.getRejectedExecutionHandler()));
    }


}
