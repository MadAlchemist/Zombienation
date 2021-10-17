package com.madalchemist.zombienation.mixin;

import com.madalchemist.zombienation.ConfigHandler;
import net.minecraft.entity.monster.ZombieEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ZombieEntity.class)
public abstract class MixinZombieEntity {
    @Overwrite(remap = true)
    public boolean isSunSensitive() {
        if(ConfigHandler.GENERAL.burnAtDay.get()) {
            return(true);
        } else {
            return(false);
        }
    }
}
