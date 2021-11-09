package com.madalchemist.zombienation.zombies;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.DolphinCarriedItemLayer;
import net.minecraft.client.renderer.entity.model.DolphinModel;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ZolphinRenderer extends MobRenderer<ZolphinEntity, DolphinModel<ZolphinEntity>> {
   private static final ResourceLocation ZOLPHIN_LOCATION = new ResourceLocation("zombienation","textures/entity/zombie/zolphin.png");

   public ZolphinRenderer(EntityRendererManager p_i48949_1_) {
      super(p_i48949_1_, new DolphinModel<>(), 0.7F);
   }

   public ResourceLocation getTextureLocation(ZolphinEntity p_110775_1_) {
      return ZOLPHIN_LOCATION;
   }
}