package com.madalchemist.zombienation.zombies;

import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.ZombieModel;
import com.madalchemist.zombienation.zombies.ChestHeadModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class ChestHeadRenderer extends AbstractZombieRenderer<Chesthead,ChestHeadModel<Chesthead>> {

    public ChestHeadRenderer(EntityRendererManager rendererManager) {
        super(rendererManager,
              new ChestHeadModel<>(0.0f, false),
              new ChestHeadModel<>(0.5f, true),
              new ChestHeadModel<>(1.0f, true));

        this.addLayer(new BipedArmorLayer<>(this, new BipedModel(0.5F), new BipedModel(1.0F)));
        this.addLayer(new HeldItemLayer<>(this));
        this.model.head.visible = false;
    }

    @Nonnull
    public ResourceLocation getTextureLocation(@Nonnull Chesthead zombie) {
        return this.getTexture("zombie");
    }

    private ResourceLocation getTexture(String fileName) {
        return new ResourceLocation("minecraft",
                                    "textures/entity/zombie/" + fileName + ".png");
    }
}