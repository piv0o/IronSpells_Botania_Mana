package net.pivoo.ironspells_botania_mana.mixin;

import java.util.UUID;
// import java.util.Properties;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import io.redspace.ironsspellbooks.item.UpgradeOrbItem;
import io.redspace.ironsspellbooks.item.armor.UpgradeType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.pivoo.ironspells_botania_mana.IronSpells_Botania_Mana;
import top.theillusivec4.curios.api.SlotContext;

import net.minecraft.resources.ResourceLocation;

@Mixin(value=UpgradeOrbItem.class, remap=false)
public class UpgradeOrbItemMixin {

	@Redirect(method="<init>", at=@At(value="INVOKE",  target= "Lio/redspace/ironsspellbooks/item/armor/UpgradeType;getOperation()Lnet/minecraft/world/entity/ai/attributes/AttributeModifier$Operation;"))
	public AttributeModifier.Operation Redirect_getAttributeModifiers_GetOperation(UpgradeType instance, UpgradeType upgrade, Item.Properties pProperties){
		IronSpells_Botania_Mana.logInfo(String.format("UpgradeOrbItemMixin attr1: %s, %s, %s",
			instance.getId().getPath(),
			String.valueOf(instance.getAmountPerUpgrade()),
			String.valueOf(instance.getId().getPath() == "mana")
		));
		if(instance.getId().getPath() == "mana"){
			return AttributeModifier.Operation.MULTIPLY_BASE;
		}
		return instance.getOperation();
	}

	@Redirect(method="<init>", at=@At(value="INVOKE",  target= "Lio/redspace/ironsspellbooks/item/armor/UpgradeType;getAmountPerUpgrade()F"))
	public float Redirect_getAttributeModifiers_getAmountPerUpgrade(UpgradeType instance, UpgradeType upgrade, Item.Properties pProperties){
		IronSpells_Botania_Mana.logInfo(String.format("UpgradeOrbItemMixin attr2: %s, %s, %s",
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
