package hisui.infinicore;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.NetherStarItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("infinicore");

	public static final Item INFINICORE = new NetherStarItem(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON));

	@Override
	public void onInitialize() {

		Registry.register(Registries.ITEM, new Identifier("infinicore","infinicore"), INFINICORE);
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> {
			content.addAfter(Items.NETHER_STAR, INFINICORE);
		});

		LOGGER.info("InfiniCore initialized!");
	}
}