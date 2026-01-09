package miprimermod;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;

import java.util.List;

public class InventorySorter {

    public static void sortNearbyChests(ServerPlayer player) {

        List<ChestBlockEntity> chests = ChestFinder.findNearbyChests(player, 6);

        if (chests.isEmpty()) {
            player.sendSystemMessage(
                    Component.literal("❌ No hay cofres cerca")
            );
            return;
        }

        boolean movedAnyItem = false;
        Container playerInv = player.getInventory();

        for (ChestBlockEntity chest : chests) {

            Container chestInv = ChestBlock.getContainer(
                    (ChestBlock) chest.getBlockState().getBlock(),
                    chest.getBlockState(),
                    chest.getLevel(),
                    chest.getBlockPos(),
                    true
            );

            if (chestInv == null) continue;

            // Recorremos inventario del jugador
            for (int i = 0; i < playerInv.getContainerSize(); i++) {

                ItemStack playerStack = playerInv.getItem(i);
                if (playerStack.isEmpty()) continue;

                // Solo mover si el cofre ya tiene ese item
                if (!containsItem(chestInv, playerStack)) continue;

                // 1️⃣ Llenar stacks existentes
                for (int slot = 0; slot < chestInv.getContainerSize(); slot++) {

                    if (playerStack.isEmpty()) break;

                    ItemStack chestStack = chestInv.getItem(slot);

                    if (!chestStack.isEmpty()
                            && ItemStack.isSameItemSameComponents(playerStack, chestStack)
                            && chestStack.getCount() < chestStack.getMaxStackSize()) {

                        int space = chestStack.getMaxStackSize() - chestStack.getCount();
                        int move = Math.min(space, playerStack.getCount());

                        chestStack.grow(move);
                        playerStack.shrink(move);
                        movedAnyItem = true;
                    }
                }

                // 2️⃣ Crear nuevos stacks en slots vacíos
                for (int slot = 0; slot < chestInv.getContainerSize(); slot++) {

                    if (playerStack.isEmpty()) break;

                    if (chestInv.getItem(slot).isEmpty()) {

                        int move = Math.min(
                                playerStack.getMaxStackSize(),
                                playerStack.getCount()
                        );

                        ItemStack newStack = playerStack.copy();
                        newStack.setCount(move);

                        chestInv.setItem(slot, newStack);
                        playerStack.shrink(move);
                        movedAnyItem = true;
                    }
                }

                if (playerStack.isEmpty()) {
                    playerInv.setItem(i, ItemStack.EMPTY);
                }
            }

            chestInv.setChanged();
        }

        // 📢 Mensajes de resultado
        if (movedAnyItem) {
            player.sendSystemMessage(
                    Component.literal("✅ Ítems guardados en cofres cercanos")
            );
        } else {
            player.sendSystemMessage(
                    Component.literal("👀 No se encontraron cofres con ítems compatibles")
            );
        }
    }

    private static boolean containsItem(Container container, ItemStack stack) {
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack s = container.getItem(i);
            if (!s.isEmpty() && ItemStack.isSameItemSameComponents(s, stack)) {
                return true;
            }
        }
        return false;
    }
}
