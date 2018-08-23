package com.nemo.game.server;

import io.netty.util.AttributeKey;

public class SessionKey {
    //channel里的session
    public static final AttributeKey<Session> SESSION = AttributeKey.newInstance("SESSION");
    //登出时业务是否处理过的标志
    public static final AttributeKey<Boolean> LOGOUT_HANDLED = AttributeKey.newInstance("LOGOUT_HANDLED");
    public static final AttributeKey<Boolean> BACK_LOGIN = AttributeKey.newInstance("BACK_LOGIN");
}
