package com.itrexgroup.turvo.dbquerybenchmarkingservice.remote;

public class RemoteDatabaseInvocationException extends Exception {
    public RemoteDatabaseInvocationException(String message) {
        super(message);
    }

    public RemoteDatabaseInvocationException(String message, Throwable cause) {
        super(message, cause);
    }
}
