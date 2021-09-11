package com.madalchemist.zombienation.zombies;

import com.madalchemist.zombienation.animals.BrownBearEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.PolarBearModel;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class ZombieBearRenderer extends MobRenderer<ZombieBear, ZombieBearModel<ZombieBear>> {
   private static final ResourceLocation BEAR_LOCATION = new ResourceLocation("zombienation","textures/entity/zombie_bear.png");

   public ZombieBearRenderer(EntityRendererManager p_i47197_1_) {
      super(p_i47197_1_, new ZombieBearModel<>(), 0.9F);
   }

   protected void scale(ZombieBear p_225620_1_, MatrixStack p_225620_2_, float p_225620_3_) {
      p_225620_2_.scale(1.2F, 1.2F, 1.2F);
      super.scale(p_225620_1_, p_225620_2_, p_225620_3_);
   }

   @Nonnull
   public ResourceLocation getTextureLocation(@Nonnull ZombieBear bear) {
      return BEAR_LOCATION;
   }
}