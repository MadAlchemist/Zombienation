package com.madalchemist.zombienation.client.renderer;

import com.madalchemist.zombienation.entity.Chesthead;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChestheadRenderer extends AbstractZombieRenderer<Chesthead, ZombieModel<Chesthead>> {
   public ChestheadRenderer(EntityRendererProvider.Context p_174456_) {
      this(p_174456_, ModelLayers.ZOMBIE, ModelLayers.ZOMBIE_INNER_ARMOR, ModelLayers.ZOMBIE_OUTER_ARMOR);
   }

   public ChestheadRenderer(EntityRendererProvider.Context p_174458_, ModelLayerLocation p_174459_, ModelLayerLocation p_174460_, ModelLayerLocation p_174461_) {
      super(p_174458_, new ChestheadModel<>(p_174458_.bakeLayer(p_174459_)), new ChestheadModel<>(p_174458_.bakeLayer(p_174460_)), new ChestheadModel<>(p_174458_.bakeLayer(p_174461_)));
   }
}