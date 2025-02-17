package net.pivoo.ironspells_botania_mana.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.redspace.ironsspellbooks.gui.overlays.ManaBarOverlay;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.ForgeGui;
@Mixin(value=ManaBarOverlay.class, remap=false)
public class ManaBarOverlayMixin {

	@Inject(method="render", at= @At(value="HEAD"), cancellable = true)
	public void injectRender(ForgeGui gui, GuiGraphics guiHelper, float partialTick, int screenWidth, int screenHeight, CallbackInfo ci){
		ci.cancel();
	}

}
