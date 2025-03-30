package net.pivoo.ironspells_botania_mana.mixin;

import io.redspace.ironsspellbooks.item.curios.SimpleAttributeCurio;


import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.pivoo.ironspells_botania_mana.IronSpells_Botania_Mana;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(value=SimpleAttributeCurio.class, remap=false)
public class SimpleAttributeCurioMixin {
	
	@Redirect(method="getAttributeModifiers", at=@At(value="INVOKE",  target= "Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;getOperation()Lnet/minecraft/world/entity/ai/attributes/AttributeModifier$Operation;"))
	public AttributeModifier.Operation Redirect_getAttributeModifiers_GetOperation(AttributeModifier instance, SlotContext slotContext, UUID uuid, ItemStack stack){
		// IronSpells_Botania_Mana.logInfo(String.format("SimpleAttributeCurioMixin attr1: %s, %s",
		// 	instance.getName(),
		// 	String.valueOf(instance.getAmount())
		// ));
		if(instance.getName() == "mana"){
			return AttributeModifier.Operation.MULTIPLY_BASE;
		}
		return instance.getOperation();
	}


	@Redirect(method="getAttributeModifiers", at=@At(value="INVOKE",  target= "Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;getAmount()D") )
	public double Redirect_getAttributeModifiers_GetAmount (AttributeModifier instance, SlotContext slotContext, UUID uuid, ItemStack stack){
		// 		IronSpells_Botania_Mana.logInfo(String.format("SimpleAttributeCurioMixin attr2: %s, %s",
		// 	instance.getName(),
		// 	String.valueOf(instance.getAmount())
		// ));
		if(instance.getName() == "mana"){
			return instance.getAmount() / 2000;
		}
		return instance.getAmount();
	}
}
