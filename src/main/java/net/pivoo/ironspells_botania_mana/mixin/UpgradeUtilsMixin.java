package net.pivoo.ironspells_botania_mana.mixin;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import io.redspace.ironsspellbooks.util.UpgradeUtils;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import com.google.common.collect.Multimap;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.capabilities.magic.UpgradeData;
import io.redspace.ironsspellbooks.item.armor.UpgradeType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.pivoo.ironspells_botania_mana.IronSpells_Botania_Mana;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;

@Mixin(value=UpgradeUtils.class, remap=false)
public class UpgradeUtilsMixin {
	
	@Redirect(method="handleAttributeEvent", at=@At(value="INVOKE",  target= "Lio/redspace/ironsspellbooks/item/armor/UpgradeType;getOperation()Lnet/minecraft/world/entity/ai/attributes/AttributeModifier$Operation;"))
	private static AttributeModifier.Operation Redirect_handleAttributeEvent_getOperation(UpgradeType instance, Multimap<Attribute, AttributeModifier> modifiers, UpgradeData upgradeData, BiConsumer<Attribute, AttributeModifier> addCallback, BiConsumer<Attribute, AttributeModifier> removeCallback, Optional<UUID> uuidOverride){
		IronSpells_Botania_Mana.logInfo(String.format("UpgradeUtilsMixin attr1: %s, %s, %s",
			instance.getId().getPath(),
			String.valueOf(instance.getAmountPerUpgrade()),
			String.valueOf(instance.getId().getPath() == "mana")
		));
		if(instance.getId().getPath() == "mana"){
			return AttributeModifier.Operation.MULTIPLY_BASE;
		}
		return instance.getOperation();
	}	
	@Redirect(method="handleAttributeEvent", at=@At(value="INVOKE",  target= "Lio/redspace/ironsspellbooks/item/armor/UpgradeType;getAmountPerUpgrade()F"))
	private static float Redirect_handleAttributeEvent_getAmountPerUpgrade(UpgradeType instance, Multimap<Attribute, AttributeModifier> modifiers, UpgradeData upgradeData, BiConsumer<Attribute, AttributeModifier> addCallback, BiConsumer<Attribute, AttributeModifier> removeCallback, Optional<UUID> uuidOverride){
		IronSpells_Botania_Mana.logInfo(String.format("UpgradeUtilsMixin attr2: %s, %s, %s",
			instance.getId().getPath(),
			String.valueOf(instance.getAmountPerUpgrade()),
			String.valueOf(instance.getId().getPath() == "mana")
		));
		if(instance.getId().getPath() == "mana"){
			return instance.getAmountPerUpgrade() / 2000;
		}
		return instance.getAmountPerUpgrade();
	}
}
