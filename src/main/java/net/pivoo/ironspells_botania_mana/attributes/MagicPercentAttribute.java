package net.pivoo.ironspells_botania_mana.attributes;

import io.redspace.ironsspellbooks.api.attribute.MagicRangedAttribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.network.chat.Component;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
public class MagicPercentAttribute extends MagicRangedAttribute {
	public MagicPercentAttribute(String pDescriptionId, double pDefaultValue, double pMin, double pMax) {
        super(pDescriptionId, pDefaultValue, pMin, pMax);
    }

    // @Override
    // public Multimap<Attribute, AttributeModifier> getAttributeModifiers() {
    //     // Modify the attribute values so they are divided by 100
    //     Multimap<Attribute, AttributeModifier> modifiers = super.getAttributeModifiers();
    //     modifiers.entries().forEach(entry -> {
    //         AttributeModifier modifier = entry.getValue();
    //         double newValue = modifier.getAmount() / 100.0;
    //         entry.setValue(new AttributeModifier(modifier.getId(), modifier.getName(), newValue, modifier.getOperation()));
    //     });
    //     return modifiers;
    // }
}
