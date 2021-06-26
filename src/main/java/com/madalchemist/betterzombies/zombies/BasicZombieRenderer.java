package com.madalchemist.betterzombies.zombies;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class BasicZombieRenderer extends MobRenderer<BasicZombie, PlayerModel<BasicZombie>> {

    public BasicZombieRenderer(EntityRendererManager renderManager) {
        super(renderManager, new PlayerModel<BasicZombie>(0, true), 0.5F);
        this.addLayer(new BipedArmorLayer<>(this, new BipedModel(0.5F), new BipedModel(1.0F)));
        this.addLayer(new HeldItemLayer<>(this));
    }

    @Override
    @Nonnull
    public ResourceLocation getTextureLocation(@Nonnull BasicZombie zombie) {
        return this.getTexture("zombie_maid");
    }

    private ResourceLocation getTexture(String fileName) {
        return new ResourceLocation("betterzombies", "textures/entity/zombie/" + fileName + ".png");
    }
}