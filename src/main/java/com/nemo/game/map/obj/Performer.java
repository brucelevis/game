package com.nemo.game.map.obj;

import com.nemo.game.system.cd.attr.entity.Attribute;
import com.nemo.game.system.cd.entity.Cd;
import com.nemo.game.system.cd.entity.Cdable;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public abstract class Performer extends MapObject implements Cdable{
    public static final Logger LOGGER = LoggerFactory.getLogger(Performer.class);

    private int level;

    private int sex;

    private int career;

    protected long hp = 0;

    protected int mp = 0;

    protected boolean dead = false;

    protected Map<Long, Cd> cdMap = new HashMap<>();

    //最终属性
    private Attribute finalAttribute;


}
