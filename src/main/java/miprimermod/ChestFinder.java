package miprimermod;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.ChestBlockEntity;

import java.util.ArrayList;
import java.util.List;

public class ChestFinder {

    public static List<ChestBlockEntity> findNearbyChests(ServerPlayer player, int radius) {

        List<ChestBlockEntity> result = new ArrayList<>();
        BlockPos origin = player.blockPosition();

        for (BlockPos pos : BlockPos.betweenClosed(
                origin.offset(-radius, -radius, -radius),
                origin.offset(radius, radius, radius)
        )) {
            if (player.level().getBlockEntity(pos) instanceof ChestBlockEntity chest) {
                result.add(chest);
            }
        }
        return result;
    }
}

