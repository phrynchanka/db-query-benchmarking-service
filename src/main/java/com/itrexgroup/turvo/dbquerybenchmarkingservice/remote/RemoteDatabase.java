package com.itrexgroup.turvo.dbquerybenchmarkingservice.remote;

public interface RemoteDatabase {
    void execute(String sql) throws RemoteDatabaseInvocationException;
}
