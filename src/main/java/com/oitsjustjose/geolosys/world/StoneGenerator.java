package com.oitsjustjose.geolosys.world;

import com.oitsjustjose.geolosys.Geolosys;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.ArrayList;
import java.util.Random;

/**
 * A modified version of:
 * https://github.com/BluSunrize/ImmersiveEngineering/blob/master/src/main/java/blusunrize/immersiveengineering/common/world/IEWorldGen.java
 * Original Source & Credit: BluSunrize
 **/

public class StoneGenerator implements IWorldGenerator
{
    private static ArrayList<StoneGen> stoneSpawnList = new ArrayList<>();

    public static void addStoneGen(IBlockState state, int minY, int maxY, int weight)
    {
        StoneGen gen = new StoneGen(state, minY, maxY, weight);
        stoneSpawnList.add(gen);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if(stoneSpawnList.size() > 0 && world.provider.getDimension() != -1 && world.provider.getDimension() != 1)
        {
            stoneSpawnList.get(random.nextInt(stoneSpawnList.size())).generate(world, random, (chunkX * 16), (chunkZ * 16));
        }
    }

    public static class StoneGen
    {
        WorldGenStonePluton pluton;
        IBlockState state;
        int minY;
        int maxY;
        int weight;


        StoneGen(IBlockState state, int minY, int maxY, int weight)
        {
            this.pluton = new WorldGenStonePluton(state, 96);
            this.state = state;
            this.minY = minY;
            this.maxY = maxY;
            this.weight = weight;
        }

        void generate(World world, Random rand, int x, int z)
        {
            if (!Geolosys.getInstance().chunkOreGen.canGenerateInChunk(new ChunkPos(x / 16, z / 16)))
            {
                return;
            }
            if (rand.nextInt(100) < weight)
            {
                pluton.generate(world, rand, new BlockPos(x + 8, minY + rand.nextInt(maxY - minY), z + 8));
            }
        }
    }
}
