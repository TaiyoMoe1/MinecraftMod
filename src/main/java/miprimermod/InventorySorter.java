package miprimermod;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class InventorySorter {

    public static void sort(ServerPlayer player) {

        AbstractContainerMenu menu = player.containerMenu;
        if (menu == null) return;

        int totalSlots = menu.slots.size();
        int playerSlots = 36;

        if (totalSlots <= playerSlots) return;

        List<Slot> containerSlots = menu.slots.subList(0, totalSlots - playerSlots);
        List<Slot> playerInvSlots = menu.slots.subList(totalSlots - playerSlots, totalSlots);

        for (Slot playerSlot : playerInvSlots) {

            ItemStack playerStack = playerSlot.getItem();
            if (playerStack.isEmpty()) continue;

            // 1️⃣ Llenar stacks existentes
            for (Slot containerSlot : containerSlots) {

                ItemStack containerStack = containerSlot.getItem();

                if (!containerStack.isEmpty()
                        && ItemStack.isSameItemSameComponents(playerStack, containerStack)
                        && containerStack.getCount() < containerStack.getMaxStackSize()) {

                    int space = containerStack.getMaxStackSize() - containerStack.getCount();
                    int move = Math.min(space, playerStack.getCount());

                    containerStack.grow(move);
                    playerStack.shrink(move);

                    containerSlot.set(containerStack);
                    playerSlot.set(playerStack);

                    if (playerStack.isEmpty()) break;
                }
            }

            // 2️⃣ Crear nuevos stacks
            for (Slot containerSlot : containerSlots) {

                if (playerStack.isEmpty()) break;

                if (!containerSlot.hasItem()) {

                    int move = Math.min(
                            playerStack.getMaxStackSize(),
                            playerStack.getCount()
                    );

                    ItemStack newStack = playerStack.copy();
                    newStack.setCount(move);

                    containerSlot.set(newStack);
                    playerStack.shrink(move);
                    playerSlot.set(playerStack);
                }
            }
        }
    }
}
