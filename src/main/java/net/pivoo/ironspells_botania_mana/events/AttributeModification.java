package net.pivoo.ironspells_botania_mana.events;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import static io.redspace.ironsspellbooks.api.registry.AttributeRegistry.MAX_MANA;
import net.pivoo.ironspells_botania_mana.attributes.MagicPercentAttribute;

@Mod.EventBusSubscriber(modid=IronsSpellbooks.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AttributeModification {
	
	@SubscribeEvent
	public static void modifyAttirbutes(EntityAttributeModificationEvent event){
		RangedAttribute originalMaxMana = (RangedAttribute) MAX_MANA.get();

		Attribute newMaxMana = new MagicPercentAttribute(
            "attribute.irons_spellbooks.max_mana",  
            originalMaxMana.getDefaultValue() ,  
            originalMaxMana.getMinValue(),  
            originalMaxMana.getMaxValue()   
        ).setSyncable(true);

		

		event.add(EntityType.PLAYER, newMaxMana);
	}

}
