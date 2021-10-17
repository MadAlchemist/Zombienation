package com.madalchemist.zombienation.zombies;

import net.minecraft.client.renderer.entity.model.AbstractZombieModel;
import net.minecraft.client.renderer.entity.model.ZombieModel;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChestHeadModel<T extends ZombieEntity> extends ZombieModel<T> {
    public ChestHeadModel(float p_i1168_1_, boolean p_i1168_2_) {
        this(p_i1168_1_, 0.0F, 64, p_i1168_2_ ? 32 : 64);
        this.head.setPos(this.head.x,this.head.y-0.1f,this.head.z);
    }

    protected ChestHeadModel(float p_i48914_1_, float p_i48914_2_, int p_i48914_3_, int p_i48914_4_) {
        super(p_i48914_1_, p_i48914_2_, p_i48914_3_, p_i48914_4_);
        this.head.setPos(this.head.x,this.head.y-0.1f,this.head.z);
    }

    public boolean isAggressive(T p_212850_1_) {
           return p_212850_1_.isAggressive();
    }
}

