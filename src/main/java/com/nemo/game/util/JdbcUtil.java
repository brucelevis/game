package com.nemo.game.util;

import com.nemo.common.jdbc.JdbcTemplate;

public class JdbcUtil {
    private static JdbcTemplate template;

    public static void init(JdbcTemplate template) {
        JdbcUtil.template = template;
    }






}
