package hisui.infinicore.mixin;

import hisui.infinicore.Main;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
abstract class InfinicoreMixin extends ForgingScreenHandler {

	@Shadow
	@Final
	private Property levelCost;

	public InfinicoreMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(type, syncId, playerInventory, context);
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isDamageable()Z", ordinal = 0),
			method = "updateResult", cancellable = true)
	private void inject(CallbackInfo info) {
		ItemStack itemStack1 = this.input.getStack(0).copy();
		this.levelCost.set(10);
		ItemStack itemStack2 = this.input.getStack(1);
		if (itemStack1.isDamageable() && itemStack2.isOf(Main.INFINICORE)) {
			NbtCompound nbt = itemStack1.getOrCreateNbt();
			nbt.putBoolean("Unbreakable", true);
			itemStack1.setNbt(nbt);
			itemStack1.setDamage(0);

			this.output.setStack(0, itemStack1);
			this.levelCost.set(0);
			sendContentUpdates();
			info.cancel();
		}
	}
}