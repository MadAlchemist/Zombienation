package com.madalchemist.zombienation.client.renderer;

import com.madalchemist.zombienation.entity.Zolphin;
import net.minecraft.client.model.DolphinModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.DolphinCarryingItemLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ZolphinRenderer extends MobRenderer<Zolphin, DolphinModel<Zolphin>> {
    private static final ResourceLocation ZOLPHIN_LOCATION = new ResourceLocation("zombienation","textures/entity/zombie/zolphin.png");

    public ZolphinRenderer(EntityRendererProvider.Context p_173960_) {
        super(p_173960_, new DolphinModel<>(p_173960_.bakeLayer(ModelLayers.DOLPHIN)), 0.7F);
        //this.addLayer(new DolphinCarryingItemLayer(this));
    }

    public ResourceLocation getTextureLocation(Zolphin p_114059_) {
        return ZOLPHIN_LOCATION;
    }
}