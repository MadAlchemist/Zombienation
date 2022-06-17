package com.madalchemist.zombienation.client.renderer;

import com.madalchemist.zombienation.entity.ZombieBear;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PolarBearModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ZombieBearRenderer extends MobRenderer<ZombieBear, ZombieBearModel<ZombieBear>> {
    private static final ResourceLocation BEAR_LOCATION = new ResourceLocation("zombienation","textures/entity/zombie_bear.png");

    public ZombieBearRenderer(EntityRendererProvider.Context p_174356_) {
        super(p_174356_, new ZombieBearModel<>(p_174356_.bakeLayer(ModelLayers.POLAR_BEAR)), 0.9F);
    }

    public ResourceLocation getTextureLocation(ZombieBear p_115732_) {
        return BEAR_LOCATION;
    }

    protected void scale(ZombieBear p_115734_, PoseStack p_115735_, float p_115736_) {
        p_115735_.scale(1.2F, 1.2F, 1.2F);
        super.scale(p_115734_, p_115735_, p_115736_);
    }
}