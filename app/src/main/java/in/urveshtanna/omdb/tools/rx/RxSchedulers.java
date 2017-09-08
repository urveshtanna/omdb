package in.urveshtanna.omdb.tools.rx;


import io.reactivex.Scheduler;

/**
 * @author urveshtanna
 * @created 07/09/17
 */

public interface RxSchedulers {


    Scheduler runOnBackground();

    Scheduler io();

    Scheduler compute();

    Scheduler androidThread();

    Scheduler internet();
}
