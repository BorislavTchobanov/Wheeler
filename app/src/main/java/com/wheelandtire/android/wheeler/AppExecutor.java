package com.wheelandtire.android.wheeler;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class AppExecutor {

    private static final Object LOCK = new Object();
    private static AppExecutor sInstance;
    private final Executor discIO;

    private AppExecutor(Executor discIO) {
        this.discIO = discIO;
    }

    static AppExecutor getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AppExecutor(Executors.newSingleThreadExecutor());
            }
        }
        return sInstance;
    }

    Executor discIO() { return discIO; }
}
