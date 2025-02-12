package net.pivoo.ironspells_botania_mana.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vazkii.botania.api.mana.ManaItemHandler;

import net.pivoo.ironspells_botania_mana.IronSpells_Botania_Mana;
                
@Mixin(value = AbstractSpell.class, remap = false)
public class AbstractSpellMixin {

    private static final int manaToGet = Integer.MAX_VALUE;

    @Redirect(method="castSpell", at = @At(value = "INVOKE", target = "Lio/redspace/ironsspellbooks/api/magic/MagicData;getMana()F"))
    public float redirect_GetMana_castSpell(MagicData instance, Level world, int spellLevel, ServerPlayer serverPlayer, CastSource castSource, boolean triggerCooldown){
        IronSpells_Botania_Mana.logInfo("Redirect from CastSpell, Botania Override");
        ItemStack itemStack = serverPlayer.getMainHandItem();
        // should be redirected to book if casting from book, use CastSource for this
        // ItemStack itemStack = ItemStack.EMPTY;
        int mana = ManaItemHandler.instance().requestMana(itemStack, serverPlayer, manaToGet, false);
        IronSpells_Botania_Mana.logInfo(String.valueOf(mana));
        
        return (float) mana;
    }
    
    @Redirect(method="canBeCastedBy", at = @At(value = "INVOKE", target = "Lio/redspace/ironsspellbooks/api/magic/MagicData;getMana()F"))
    public float redirect_GetMana_canBeCastedBy(MagicData instance, int spellLevel, CastSource castSource, MagicData playerMagicData, Player player){
        IronSpells_Botania_Mana.logInfo("Redirect from canBeCastedBy, Botania Override");
        ItemStack itemStack = player.getMainHandItem(); 
        // should be redirected to book if casting from book, use CastSource for this
        // ItemStack itemStack = ItemStack.EMPTY;

        int mana = ManaItemHandler.instance().requestMana(itemStack, player, manaToGet, false);
        IronSpells_Botania_Mana.logInfo(String.valueOf(mana));
        
        return (float) mana;
    }

}
