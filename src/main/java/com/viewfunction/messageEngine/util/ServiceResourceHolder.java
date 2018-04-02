package com.viewfunction.messageEngine.util;

import org.apache.cayenne.configuration.server.ServerRuntime;

public class ServiceResourceHolder {

    private static ServerRuntime cayenneServerRuntime;

    public static ServerRuntime getCayenneServerRuntime() {
        return cayenneServerRuntime;
    }

    public static void setCayenneServerRuntime(ServerRuntime cayenneServerRuntime) {
        ServiceResourceHolder.cayenneServerRuntime = cayenneServerRuntime;
    }
}
