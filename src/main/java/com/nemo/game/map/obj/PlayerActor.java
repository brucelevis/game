package com.nemo.game.map.obj;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Setter
public class PlayerActor extends Performer {
    public static final Logger LOGGER = LoggerFactory.getLogger(PlayerActor.class);

    //跟随
    private boolean slave;


    @Override
    public int getType() {
        return 0;
    }
}
