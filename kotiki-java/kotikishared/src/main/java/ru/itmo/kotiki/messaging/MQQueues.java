package ru.itmo.kotiki.messaging;

public enum MQQueues {

    FANOUT_EXCHANGE(Constants.FANOUT_VALUE),
    OWNER_SERVICE(Constants.OWNER_SERVICE_VALUE),
    KOTIKI_API(Constants.KOTIK_API_SERVICE_VALUE),
    KOTIKI_SERVICE(Constants.KOTIK_SERVICE_VALUE),
    OWNER_ALL(Constants.OWNER_ALL),

    OWNER_ID(Constants.OWNER_ID),
    OWNER_EDIT(Constants.OWNER_EDIT),
    OWNER_DELETE(Constants.OWNER_DELETE),
    KOTIKI_ALL(Constants.KOTIKI_ALL),
    KOTIK_ID(Constants.KOTIK_ID),
    KOTIK_EDIT(Constants.KOTIK_EDIT),

    KOTIK_DELETE(Constants.KOTIK_DELETE),
    KOTIKI_FRIENDS(Constants.KOTIKI_FRIENDS);

    private final String queueName;

    MQQueues(String queueName) {
        this.queueName = queueName;
    }

    public String getQueueName(){
        return queueName;
    }

    public static class Constants {
        public static final String FANOUT_VALUE = "kotiki:topic";
        public static final String OWNER_SERVICE_VALUE = "ownerservice:queue";
        public static final String KOTIK_API_SERVICE_VALUE = "kotikapi:queue";
        public static final String KOTIK_SERVICE_VALUE = "kotikservice:queue";
        public static final String OWNER_ALL = "ownerall:queue";

        public static final String OWNER_ID = "ownerid:queue";
        public static final String OWNER_EDIT = "owneredit:queue";
        public static final String OWNER_DELETE = "ownerdelete:queue";

        public static final String KOTIKI_ALL = "kotikiall:queue";

        public static final String KOTIK_ID = "kotikid:queue";

        public static final String KOTIK_EDIT = "kotikiedit:queue";

        public static final String KOTIK_DELETE = "kotikidelete:queue";
        public static final String KOTIKI_FRIENDS = "kotikifriends:queue";
    }
}
