package net.pivoo.ironspells_botania_mana.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
// import org.spongepowered.asm.mixin.injection/CallbackInfoReturnable;
// import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import io.redspace.ironsspellbooks.item.armor.ExtendedArmorItem;
import io.redspace.ironsspellbooks.item.armor.IronsExtendedArmorMaterial;
import net.pivoo.ironspells_botania_mana.IronSpells_Botania_Mana;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;

@Mixin(value=ExtendedArmorItem.class, remap=false)
public class ExtenderArmorItemMixin {
	

	@Redirect(method="<init>", at=@At(value="INVOKE",  target= "Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;getOperation()Lnet/minecraft/world/entity/ai/attributes/AttributeModifier$Operation;"))
	public AttributeModifier.Operation redirect_Constructor_getOperation(AttributeModifier instance, IronsExtendedArmorMaterial material, ArmorItem.Type type, ArmorItem.Properties settings){
		IronSpells_Botania_Mana.logInfo(String.format("attr: %s, %s",
			instance.getName(),
			String.valueOf(instance.getAmount())
		));
		if(instance.getName() == "Max Mana"){
			return AttributeModifier.Operation.MULTIPLY_BASE;
		}
		return instance.getOperation();
	}

	@Redirect(method="<init>", at=@At(value="INVOKE",  target= "Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;getAmount()D"))
	public double redirect_Constructor_getAmount(AttributeModifier instance, IronsExtendedArmorMaterial material, ArmorItem.Type type, ArmorItem.Properties settings){
		IronSpells_Botania_Mana.logInfo(String.format("attr: %s, %s",
			instance.getName(),
			String.valueOf(instance.getAmount())
		));
		if(instance.getName() == "Max Mana"){
			return instance.getAmount() / 2000;
		}
		return instance.getAmount();
	}

	// @Inject(method="withManaAttribute", at = @At("HEAD"))
	// public void inject_withManaAttribute( cir as CallbackInfoReturnable){

	// }
}
