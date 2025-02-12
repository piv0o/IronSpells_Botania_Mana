package net.pivoo.ironspells_botania_mana.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import io.redspace.ironsspellbooks.api.magic.MagicData;

import net.minecraft.server.level.ServerPlayer;

@Mixin(value = MagicData.class, remap = false)
public interface MagicDataAccessor {
    @Accessor("serverPlayer") ServerPlayer getServerPlayer();
}