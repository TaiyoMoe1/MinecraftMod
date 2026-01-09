package miprimermod;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;

import java.util.ArrayList;
import java.util.List;

public class ChestFinder {

    public static List<ChestBlockEntity> findNearbyChests(ServerPlayer player, int radius) {

        List<ChestBlockEntity> chests = new ArrayList<>();
        Level level = player.level();

        BlockPos origin = player.blockPosition();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {

                    BlockPos pos = origin.offset(x, y, z);
                    BlockEntity be = level.getBlockEntity(pos);

                    if (be instanceof ChestBlockEntity chest) {
                        chests.add(chest);
                    }
                }
            }
        }

        return chests;
    }
}
