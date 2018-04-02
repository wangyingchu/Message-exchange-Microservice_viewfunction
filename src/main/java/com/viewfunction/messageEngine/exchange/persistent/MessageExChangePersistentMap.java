package com.viewfunction.messageEngine.exchange.persistent;

import com.viewfunction.messageEngine.exchange.persistent.auto._MessageExChangePersistentMap;

public class MessageExChangePersistentMap extends _MessageExChangePersistentMap {

    private static MessageExChangePersistentMap instance;

    private MessageExChangePersistentMap() {}

    public static MessageExChangePersistentMap getInstance() {
        if(instance == null) {
            instance = new MessageExChangePersistentMap();
        }

        return instance;
    }
}
