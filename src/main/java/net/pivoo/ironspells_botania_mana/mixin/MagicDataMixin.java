package net.pivoo.ironspells_botania_mana.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import net.minecraft.server.level.ServerPlayer;

@Mixin(value = MagicData.class, remap = false)
public class MagicDataMixin {

	//TODO: Look into how to use @Shadow, or maybe  @Accessor("serverPlayer")
	
	@Shadow(remap = false)
	private ServerPlayer serverPlayer;


	// private static final Map<MagicData, ServerPlayer> MagicPlayerMap = new HashMap<>();
	
	// @Inject(method = "setServerPlayer", at = @At("HEAD"), remap=false)
	// public void injectSetServerPlayer(ServerPlayer serverPlayer, CallbackInfoReturnable<MagicData> cir){
	// 	MagicPlayerMap.put(cir.getReturnValue(), serverPlayer);
	// }



	// public Player botaniaPlayer = null;

	// @Inject(method="getMana", at = @At("HEAD"), cancellable=true, remap = false)
    //     @SuppressWarnings("UseSpecificCatch")
	// public void injectGetMana(CallbackInfoReturnable<Float> cir){
	// 	// try{
	// 		// Player player = (Player) MagicPlayerMap.get((MagicData) (Object) this); // Convert ServerPlayer to Player
	// 		// MagicData magicData = ((MagicData) (Object) this); // Convert ServerPlayer to Player
	// 		// Class<?> classInfo = magicData.getClass();
	// 		// Field field = classInfo.getDeclaredField("serverPlayer");
	// 		// field.setAccessible(true);  
	// 		// Player player = field.get(magicData);
	// 		// Player player = MagicPlayerMap.get(this);
	// 		Player player =  (Player) this.serverPlayer;

	// 		if (player != null) {

	// 			ItemStack itemStack = player.getMainHandItem();
	// 			int manaAvailable = ManaItemHandler.instance().requestMana(itemStack, player, 100, false); // Request 100 mana as example
				
	// 			cir.setReturnValue((float) manaAvailable);
	// 		} else {
	// 			IronSpells_Botania_Mana.logInfo("getMana failed, player is null");

	// 		}	
	// // cir.setReturnValue(ManaItemHandler.instance().requestMana(stack, ));
	// // // cir.setReturnValue(200.0f);
	// 	// } catch(Exception e){
	// 	// 	IronSpells_Botania_Mana.logInfo(e.getMessage());
	// 	// }
	
	// }

	// @Inject(method="setMana", at = @At("HEAD"), cancellable=true, remap = false)
	// public void injectSetMana(float mana, CallbackInfo ci){
	// 	ci.cancel();
	// }
}
