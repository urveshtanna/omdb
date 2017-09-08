package in.urveshtanna.omdb.tools.rx;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * @author urveshtanna
 * @created 07/09/17
 */

public class AppRxSchedulers implements RxSchedulers {


    private static Executor backgroundExecutor = Executors.newCachedThreadPool();
    private static Scheduler BACKGROUND_SCHEDULERS = Schedulers.from(backgroundExecutor);
    private static Executor internetExecutor = Executors.newCachedThreadPool();
    private static Scheduler INTERNET_SCHEDULERS = Schedulers.from(internetExecutor);

    @Override
    public Scheduler runOnBackground() {
        return BACKGROUND_SCHEDULERS;
    }

    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    public Scheduler compute() {
        return Schedulers.computation();
    }

    @Override
    public Scheduler androidThread() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler internet() {
        return INTERNET_SCHEDULERS;
    }
}
