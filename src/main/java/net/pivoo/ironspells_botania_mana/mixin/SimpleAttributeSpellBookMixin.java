package net.pivoo.ironspells_botania_mana.mixin;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import io.redspace.ironsspellbooks.item.spell_books.SimpleAttributeSpellBook;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import net.pivoo.ironspells_botania_mana.IronSpells_Botania_Mana;

@Mixin(value=SimpleAttributeSpellBook.class, remap=false)
public class SimpleAttributeSpellBookMixin {
	@Shadow
	private Multimap<Attribute, AttributeModifier> defaultModifiers;

		@Redirect(method="getAttributeModifiers", at=@At(value="INVOKE",  target= "Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;getOperation()Lnet/minecraft/world/entity/ai/attributes/AttributeModifier$Operation;"))
		public AttributeModifier.Operation Redirect_getAttributeModifiers_GetOperation(AttributeModifier instance, SlotContext slotContext, UUID uuid, ItemStack stack){
			IronSpells_Botania_Mana.logInfo(String.format("SimpleAttributeSpellBookMixin attr1: %s, %s",
			instance.getName(),
			String.valueOf(instance.getAmount())
		));
			if(instance.getOperation() == AttributeModifier.Operation.ADDITION){
				return AttributeModifier.Operation.MULTIPLY_BASE;
			}
			return instance.getOperation();
		}


		@Redirect(method="getAttributeModifiers", at=@At(value="INVOKE",  target= "Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;getAmount()D") )
		public double Redirect_getAttributeModifiers_GetAmount (AttributeModifier instance, SlotContext slotContext, UUID uuid, ItemStack stack){
			IronSpells_Botania_Mana.logInfo(String.format("SimpleAttributeSpellBookMixin attr2: %s, %s",
			instance.getName(),
			String.valueOf(instance.getAmount())
		));
			if(instance.getOperation() == AttributeModifier.Operation.ADDITION){
				return instance.getAmount() / 2000;
			}
			return instance.getAmount();
		}
}
