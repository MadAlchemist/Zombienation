package com.madalchemist.zombienation.zombies.render;

import com.madalchemist.zombienation.zombies.Zombie8;
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
public class Zombie_8_Renderer extends AbstractZombieRenderer<Zombie8,ZombieModel<Zombie8>> {

    public Zombie_8_Renderer(EntityRendererManager rendererManager) {
        super(rendererManager,
              new ZombieModel<>(0.0f, false),
              new ZombieModel<>(0.5f, true),
              new ZombieModel<>(1.0f, true));

        this.addLayer(new BipedArmorLayer<>(this, new BipedModel(0.5F), new BipedModel(1.0F)));
        this.addLayer(new HeldItemLayer<>(this));
    }

    @Nonnull
    public ResourceLocation getTextureLocation(@Nonnull Zombie8 zombie) {
        return this.getTexture("zombie_girl_3");
    }

    private ResourceLocation getTexture(String fileName) {
        return new ResourceLocation("zombienation",
                                    "textures/entity/zombie/" + fileName + ".png");
    }
}