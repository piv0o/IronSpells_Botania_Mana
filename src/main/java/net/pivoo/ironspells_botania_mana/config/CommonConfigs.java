package net.pivoo.ironspells_botania_mana.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfigs {
	public static final ForgeConfigSpec.Builder Builder = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec Spec;

	public static final ForgeConfigSpec.ConfigValue<Integer> ManaConversionRate;
	static{
		Builder.push("Configs for ISS Botania Conversion");

		ManaConversionRate = Builder
			.comment("How much botania mana gets consumed per 1 Mana cost in ISS")
			.defineInRange("Mana Conversion Rate",1000, 0, 10000000);


		Builder.pop();
		Spec = Builder.build();
	}
}
