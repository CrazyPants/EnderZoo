package crazypants.enderzoo.gen;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import crazypants.enderzoo.gen.structure.Structure;
import crazypants.enderzoo.gen.structure.StructureTemplate;
import crazypants.enderzoo.gen.structure.TemplateRegister;
import crazypants.enderzoo.vec.Point3i;

public class StructureGenerator implements IWorldGenerator {

  public static StructureGenerator create() {
    StructureGenerator sm = new StructureGenerator();
    sm.init();
    return sm;
  }

  private final Map<Integer, WorldStructures> worldManagers = new HashMap<Integer, WorldStructures>();

  private File saveDir;

  private final Set<Point3i> generating = new HashSet<Point3i>();

  private StructureGenerator() {
  }

  private void init() {
    MinecraftForge.EVENT_BUS.register(this);
    TemplateRegister.instance.loadDefaultTemplates();
    GameRegistry.registerWorldGenerator(this, 50000);
  }

  @Override
  public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {

    if(!world.getWorldInfo().isMapFeaturesEnabled()) {
      return;
    }

    Point3i p = new Point3i(world.provider.dimensionId, chunkX, chunkZ);
    if(generating.contains(p)) {
      return;
    }
    generating.add(p);

    long worldSeed = world.getSeed();
    Random fmlRandom = new Random(worldSeed);
    long xSeed = fmlRandom.nextLong() >> 2 + 1L;
    long zSeed = fmlRandom.nextLong() >> 2 + 1L;
    long chunkSeed = (xSeed * chunkX + zSeed * chunkZ) ^ worldSeed;

    WorldStructures structures = getWorldManOrCreate(world);
    try {
      for (StructureTemplate template : TemplateRegister.instance.getTemplates()) {
        Random r = new Random(chunkSeed ^ template.getUid().hashCode());
        Collection<Structure> s = template.generate(structures, r, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        if(s != null) {
          structures.addAll(s);
        }
      }
    } finally {
      generating.remove(p);
    }
  }

  public void generate(World world, Structure s) {
    StructureTemplate tp = s.getTemplate();
    ChunkCoordIntPair cc = s.getChunkCoord();
    WorldStructures structures = getWorldManOrCreate(world);
    if(tp.buildStructure(s, structures, world.rand, cc.chunkXPos, cc.chunkZPos, world, world.getChunkProvider(), world.getChunkProvider())) {
      structures.add(s);
    }
  }

  public void serverStopped(FMLServerStoppedEvent event) {
    worldManagers.clear();
    saveDir = null;
    generating.clear();
  }

  @SubscribeEvent
  public void eventWorldSave(WorldEvent.Save evt) {
    WorldStructures wm = getWorldManOrCreate(evt.world);
    if(wm != null) {
      wm.save();
    }
  }

  public WorldStructures getWorldMan(World world) {
    if(world == null) {
      return null;
    }
    return worldManagers.get(world.provider.dimensionId);
  }

  public WorldStructures getWorldManOrCreate(World world) {
    WorldStructures res = getWorldMan(world);
    if(res == null) {
      res = new WorldStructures(world);
      res.load();
      worldManagers.put(world.provider.dimensionId, res);
    }
    return res;
  }

}
