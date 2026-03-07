package com.ruse.world.content.transmorgify;

import com.ruse.model.Flag;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Transmog {

    @Getter
    private final List<Transformations> transformations = new ArrayList<>();

    private final Player player;

    @Getter
    private Transformations currentTransformation;

    public Transmog(Player player){
        this.player = player;
    }

    public void addTransformation(Transformations transformation){
        transformations.add(transformation);
    }

    public void load(List<Transformations> transformations){
        this.transformations.addAll(transformations);
    }

    public void returnToNormal(){
        if(currentTransformation != null){
            currentTransformation = null;
            player.getUpdateFlag().flag(Flag.APPEARANCE);
        }
    }

    public void transmogify(Transformations trans){
        if(currentTransformation != null){
            currentTransformation = null;
            player.getUpdateFlag().flag(Flag.APPEARANCE);
        }

        currentTransformation = trans;
        player.getUpdateFlag().flag(Flag.APPEARANCE);
    }
}
