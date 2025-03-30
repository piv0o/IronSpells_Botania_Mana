package net.pivoo.ironspells_botania_mana.mixin;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.config.ServerConfigs;

import static io.redspace.ironsspellbooks.api.registry.AttributeRegistry.MAX_MANA;
import io.redspace.ironsspellbooks.api.events.SpellOnCastEvent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.pivoo.ironspells_botania_mana.IronSpells_Botania_Mana;
import net.pivoo.ironspells_botania_mana.config.CommonConfigs;              
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.mana.ManaItemHandler;              
@Mixin(value = AbstractSpell.class, remap = false)
public abstract class AbstractSpellMixin {

    private static final int manaToGet = Integer.MAX_VALUE;

    private static final Set<String> spellBookIDs = Set.of(
        "irons_spellbooks:netherite_spell_book",
        "irons_spellbooks:diamond_spell_book",
        "irons_spellbooks:gold_spell_book",
        "irons_spellbooks:iron_spell_book",
        "irons_spellbooks:copper_spell_book",
        "irons_spellbooks:evoker_spell_book",
        "irons_spellbooks:necronomicon_spell_book",
        "irons_spellbooks:rotten_spell_book",
        "irons_spellbooks:blaze_spell_book",
        "irons_spellbooks:dragonskin_spell_book",
        "irons_spellbooks:villager_spell_book",
        "irons_spellbooks:druidic_spell_book"
    );

    private static int getManaConversionRate() {
        return CommonConfigs.ManaConversionRate.get();
    }


    @Shadow(remap=false)
    public abstract int getManaCost(int Level); 

    @Shadow(remap=false)
    public abstract String getSpellId();

    @Shadow(remap=false)
    public abstract SchoolType getSchoolType();



    @Redirect(method="canBeCastedBy", at = @At(value = "INVOKE", target = "Lio/redspace/ironsspellbooks/api/magic/MagicData;getMana()F"))
    public float redirect_GetMana_canBeCastedBy(MagicData instance, int spellLevel, CastSource castSource, MagicData playerMagicData, Player player){
        IronSpells_Botania_Mana.logInfo("Redirect from canBeCastedBy, Botania Override");
        
        ItemStack itemStack = getCastItem(castSource, player);
        // should be redirected to book if casting from book, use CastSource for this
        // ItemStack itemStack = ItemStack.EMPTY;

        int mana = ManaItemHandler.instance().requestMana(itemStack, player, manaToGet, false);
        IronSpells_Botania_Mana.logInfo(String.valueOf(mana));
        
        return (float) mana / getManaConversionRate();
    }

    @Redirect(method="castSpell", at = @At(value = "INVOKE", target = "Lio/redspace/ironsspellbooks/api/magic/MagicData;getMana()F"))
    public float redirect_GetMana_castSpell(MagicData instance, Level world, int spellLevel, ServerPlayer serverPlayer, CastSource castSource, boolean triggerCooldown){
        // IronSpells_Botania_Mana.logInfo("Redirect from CastSpell, Botania Override");
        ItemStack itemStack = getCastItem(castSource, serverPlayer);
        // should be redirected to book if casting from book, use CastSource for this
        // ItemStack itemStack = ItemStack.EMPTY;
        int mana = ManaItemHandler.instance().requestMana(itemStack, serverPlayer, manaToGet, false);
        // IronSpells_Botania_Mana.logInfo(String.valueOf(mana));
        
        return (float) mana / getManaConversionRate();
    }
    
    
    @Redirect(method="castSpell", at= @At(value="INVOKE", target= "Lio/redspace/ironsspellbooks/api/magic/MagicData;setMana(F)V"))
    public void redirect_setMana_castSpell(MagicData instance, float manaCost, Level world, int spellLevel, ServerPlayer serverPlayer, CastSource castSource, boolean triggerCooldown){
        // IronSpells_Botania_Mana.logInfo("redirect_setMana_castSpell");
        
        // SpellOnCastEvent event = new SpellOnCastEvent(serverPlayer, this.getSpellId(), spellLevel, getManaCost(spellLevel), this.getSchoolType(), castSource);
        // float newMana = Math.max(instance.getMana() - event.getManaCost(), 0);
        
        ItemStack itemStack = getCastItem(castSource, serverPlayer);
        float mana = ManaItemHandler.instance().requestMana(itemStack, serverPlayer, manaToGet, false); // * manaConversionRate;
        
        IronSpells_Botania_Mana.logInfo(String.format("ManaCost:%s, Mana: %s, FinalAmount: %s",
            String.valueOf(manaCost * getManaConversionRate()),
            String.valueOf(mana),
            String.valueOf(Math.round(mana - (manaCost * getManaConversionRate())))
        ));
        ManaItemHandler.instance().requestManaExact(itemStack, serverPlayer, Math.round(mana - (manaCost * getManaConversionRate())), true);
    }

    @Redirect(method="canBeCastedBy", at= @At(value="INVOKE", target="Lio/redspace/ironsspellbooks/api/spells/AbstractSpell;getManaCost(I)I"))
    public int redirect_getManaCost_CanBeCastedBy(AbstractSpell instance, int lvl,int spellLevel, CastSource castSource, MagicData playerMagicData, Player player){
        int initialCost = instance.getManaCost(lvl);
        int discount = (int) Math.floor(initialCost * getSpellDiscount(player));
        IronSpells_Botania_Mana.logInfo(String.format("discount:%s, discountPercent:%s, initCost:%s, finalCost:%s",
            String.valueOf(discount), 
            String.valueOf(getSpellDiscount(player)), 
            String.valueOf(initialCost),
            String.valueOf(initialCost - discount)
        ));
        return initialCost - discount;
    }
    
    @Redirect(method="castSpell", at= @At(value="INVOKE", target="Lio/redspace/ironsspellbooks/api/spells/AbstractSpell;getManaCost(I)I"))
    public int redirect_getManaCost_CastSpell(AbstractSpell instance, int lvl, Level world, int spellLevel, ServerPlayer serverPlayer, CastSource castSource, boolean triggerCooldown){
        int initialCost = instance.getManaCost(lvl);
        int discount = (int) Math.floor(initialCost * getSpellDiscount(serverPlayer));
        IronSpells_Botania_Mana.logInfo(String.format("discount:%s, discountPercent:%s, initCost:%s, finalCost:%s",
            String.valueOf(discount), 
            String.valueOf(getSpellDiscount(serverPlayer)), 
            String.valueOf(initialCost),
            String.valueOf(initialCost - discount)
        ));
        return initialCost - discount;
    }

    public float getSpellDiscount(Player player){
        AttributeInstance attribute = player.getAttribute(MAX_MANA.get());
        if(attribute == null){
            return 1.0f;
        }
        double maxMana = attribute.getValue() - attribute.getBaseValue();
    IronSpells_Botania_Mana.logInfo(String.format("attrVal:%s, attrBase:%s, maxMana:%s, discount:%s",
            String.valueOf(attribute.getValue()), 
            String.valueOf(attribute.getBaseValue()), 
            String.valueOf(maxMana),
            String.valueOf((float) maxMana / 100)
        ));
        return (float) maxMana / 100;
    }
    


    public ItemStack getCastItem(CastSource castSource, Player player){
        if(castSource == CastSource.SPELLBOOK){
            Container acc = BotaniaAPI.instance().getAccessoriesInventory(player);
            for (int slot = 0; slot < acc.getContainerSize(); slot++) {
                ItemStack stackInSlot = acc.getItem(slot);
                String registryName = BuiltInRegistries.ITEM.getKey(stackInSlot.getItem()).toString();
                if(spellBookIDs.contains(registryName)){
                    return stackInSlot;
                }
                // if stackInSlot.Item.
                
		    }
            return player.getMainHandItem(); 
        } else {
            return player.getMainHandItem(); 
        }
    }

}
