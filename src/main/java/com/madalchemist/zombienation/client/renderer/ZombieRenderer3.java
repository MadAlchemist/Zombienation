package com.madalchemist.zombienation.client.renderer;


import com.madalchemist.zombienation.Zombienation;
import com.madalchemist.zombienation.entity.Zombie3;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class ZombieRenderer3 extends AbstractZombieRenderer<Zombie3, ZombieModel<Zombie3>> {
    public ZombieRenderer3(EntityRendererProvider.Context p_174456_) {
        this(p_174456_, ModelLayers.ZOMBIE, ModelLayers.ZOMBIE_INNER_ARMOR, ModelLayers.ZOMBIE_OUTER_ARMOR);
    }

    public ZombieRenderer3(EntityRendererProvider.Context p_174458_, ModelLayerLocation p_174459_, ModelLayerLocation p_174460_, ModelLayerLocation p_174461_) {
        super(p_174458_, new ZombieModel<>(p_174458_.bakeLayer(p_174459_)), new ZombieModel<>(p_174458_.bakeLayer(p_174460_)), new ZombieModel<>(p_174458_.bakeLayer(p_174461_)));
    }

    @Override
    @Nonnull
    public ResourceLocation getTextureLocation(@Nonnull Zombie3 zombie) {
        return new ResourceLocation(Zombienation.MODID, "textures/entity/zombie/zombie_3.png");
    }
}