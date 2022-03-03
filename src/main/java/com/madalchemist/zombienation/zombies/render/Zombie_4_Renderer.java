package com.madalchemist.zombienation.zombies.render;

import com.madalchemist.zombienation.zombies.Zombie4;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.ZombieModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class Zombie_4_Renderer extends AbstractZombieRenderer<Zombie4,ZombieModel<Zombie4>> {

    public Zombie_4_Renderer(EntityRendererManager rendererManager) {
        super(rendererManager,
              new ZombieModel<>(0.0f, false),
              new ZombieModel<>(0.5f, true),
              new ZombieModel<>(1.0f, true));

        this.addLayer(new BipedArmorLayer<>(this, new BipedModel(0.5F), new BipedModel(1.0F)));
        this.addLayer(new HeldItemLayer<>(this));
    }

    @Nonnull
    public ResourceLocation getTextureLocation(@Nonnull Zombie4 zombie) {
        return this.getTexture("zombie_warrior");
    }

    private ResourceLocation getTexture(String fileName) {
        return new ResourceLocation("zombienation",
                                    "textures/entity/zombie/" + fileName + ".png");
    }
}