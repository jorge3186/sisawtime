package net.jordanalphonso.commons.exception;

/**
 * Created by jordan.alphonso on 11/28/2016.
 */

public class WatchFaceException extends Exception {

    public WatchFaceException(String message) {
        super(message);
    }

    public WatchFaceException(Throwable throwable) {
        super(throwable);
    }

    public WatchFaceException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
