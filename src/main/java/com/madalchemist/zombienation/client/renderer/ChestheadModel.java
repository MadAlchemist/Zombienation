package com.madalchemist.zombienation.client.renderer;

import com.madalchemist.zombienation.entity.Chesthead;
import net.minecraft.client.model.AbstractZombieModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChestheadModel<T extends Chesthead> extends ZombieModel<T> {
    public ChestheadModel(ModelPart p_171090_) {
        super(p_171090_);
        this.head.visible = false;
    }

    public boolean isAggressive(T p_104155_) {
        return p_104155_.isAggressive();
    }

    @Override
    public void setAllVisible(boolean p_102880_) {
        this.head.visible = false;
        this.hat.visible = p_102880_;
        this.body.visible = p_102880_;
        this.rightArm.visible = p_102880_;
        this.leftArm.visible = p_102880_;
        this.rightLeg.visible = p_102880_;
        this.leftLeg.visible = p_102880_;
    }
}