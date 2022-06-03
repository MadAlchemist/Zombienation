package com.madalchemist.zombienation.mixin;

import com.madalchemist.zombienation.utils.ConfigurationHandler;
import net.minecraft.world.entity.monster.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Zombie.class)
public abstract class MixinZombie {

/*    @Overwrite(remap = false)
    public boolean isSunSensitive() {
        if(ConfigurationHandler.GENERAL.burnAtDay.get()) {
            return(true);
        } else {
            return(false);
        }
    }
*/

    @Inject(method = "isSunSensitive", at = @At("HEAD"), cancellable = true, remap=true)
    protected void isSunSensitive(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(ConfigurationHandler.GENERAL.burnAtDay.get());
    }

}