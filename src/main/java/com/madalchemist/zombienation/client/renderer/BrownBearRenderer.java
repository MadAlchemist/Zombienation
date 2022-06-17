package com.madalchemist.zombienation.client.renderer;

import com.madalchemist.zombienation.entity.BrownBear;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PolarBearModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BrownBearRenderer extends MobRenderer<BrownBear, PolarBearModel<BrownBear>> {
    private static final ResourceLocation BEAR_LOCATION = new ResourceLocation("zombienation","textures/entity/brown_bear.png");

    public BrownBearRenderer(EntityRendererProvider.Context p_174356_) {
        super(p_174356_, new PolarBearModel<>(p_174356_.bakeLayer(ModelLayers.POLAR_BEAR)), 0.9F);
    }

    public ResourceLocation getTextureLocation(BrownBear p_115732_) {
        return BEAR_LOCATION;
    }

    protected void scale(BrownBear p_115734_, PoseStack p_115735_, float p_115736_) {
        p_115735_.scale(1.2F, 1.2F, 1.2F);
        super.scale(p_115734_, p_115735_, p_115736_);
    }
}