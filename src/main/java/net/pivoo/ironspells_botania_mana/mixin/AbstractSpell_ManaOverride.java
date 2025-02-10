package net.pivoo.ironspells_botania_mana.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.redspace.ironsspellbooks.api.magic.MagicData;

@Mixin(MagicData.class)
public class AbstractSpell_ManaOverride {
	
	@Inject(method="getMana", at = @At("HEAD"), cancellable=true )
	public void OverrideManaCost(CallbackInfoReturnable<Float> cir){
		System.out.println("attempting to override getMana()");
		cir.setReturnValue(100.0f);
		// return 0;
	}
}
