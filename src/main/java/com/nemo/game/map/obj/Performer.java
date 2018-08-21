package com.nemo.game.map.obj;

import com.nemo.commons.tuple.TwoTuple;
import com.nemo.game.map.buffer.BufferState;
import com.nemo.game.map.scene.Point;
import com.nemo.game.system.attr.constant.AttrinbuteConst.*;
import com.nemo.game.system.attr.entity.*;
import com.nemo.game.system.cd.entity.Cd;
import com.nemo.game.system.cd.entity.Cdable;
import com.nemo.game.system.miji.entity.Skill;
import javafx.scene.control.Skin;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    //buff状态和属性
    private BufferState bufferState = new BufferState();
    //所有系统的属性集合
    private Map<AttributeType, TwoTuple<Attribute, Attribute>> attributes = new HashMap<>();
    //仇恨列表
    private Map<Long, Integer> threatMap = new ConcurrentHashMap<>();

    //战斗对象
    private long fightTarget;
    //攻击我的对象
    private long whoAttackMe;
    private long whoAttackMeTime;
    //我的攻击目标
    private long whoMyTarget;
    private long whoMyTargetTime;

    //主角
    protected Performer master;
    //是否跟随
    private boolean slave;

    //当前路径
    private List<Point> pathList = new ArrayList<>();
    //路径目标点
    private Point pathTargetPoint;
    //移动速度
    private int moveSpeed;
    //移动间隔
    private int moveInterval = 500;

    //不可叠加的buff
    private Map<Integer, Buffer> bufferMap = new HashMap<>();
    //可叠加的buff
    private Map<Integer, Map<Integer, Buffer>> overlyingBufferMap = new HashMap<>();

    //杀手id
    private long killerId;
    //死亡时间
    private long deadTime;

    //技能释放目标的类型（用于判断善恶模式）
    private int skillTargetType;
    //技能释放目标的id（用于判断善恶模式）
    private long skillTargetId;

    //移动
    private Point lastPoint;
    private int lastMoveSpeed;
    private long lastMoveTime;
    private boolean moved = false;

    //技能buff
    private Map<Integer, Skill> skillMap = new HashMap<>();


}
