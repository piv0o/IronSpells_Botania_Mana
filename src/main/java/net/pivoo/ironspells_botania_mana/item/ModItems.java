package net.pivoo.ironspells_botania_mana.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.pivoo.ironspells_botania_mana.IronSpells_Botania_Mana;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, IronSpells_Botania_Mana.MODID);

	public static final RegistryObject<Item> SAPPHIRE = ITEMS.register("sapphire", () -> new Item(new Item.Properties()));

	public static void register(IEventBus eventBus){
		ITEMS.register(eventBus);
	}
}
