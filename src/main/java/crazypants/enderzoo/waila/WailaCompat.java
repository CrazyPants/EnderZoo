package crazypants.enderzoo.waila;

public class WailaCompat //implements IWailaEntityProvider
{
//
//  private static final WailaCompat INSTANCE = new WailaCompat();
//
//  public static void load(IWailaRegistrar registrar) {
//    registrar.registerBodyProvider(INSTANCE, IEnderZooMob.class);
//  }
//
//  @Override
//  public Entity getWailaOverride(IWailaEntityAccessor accessor, IWailaConfigHandler config) {
//    return null; // not used
//  }
//
//  @Override
//  public List<String> getWailaHead(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
//    return null; // not used
//  }
//
//  @Override
//  public List<String> getWailaBody(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
//    String name = EntityList.getEntityString(entity);
//    //name = name.substring(EnderZoo.MODID.length() + 1, name.length());    
//    String locKey = "entity." + name + ".desc.";
//    String loc = null;
//    for (int line = 1; !(loc = I18n.translateToLocal(locKey + line)).equals(locKey + line); line++) {
//      currenttip.add(loc);
//    }
//    return currenttip;
//  }
//
//  @Override
//  public List<String> getWailaTail(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
//    return null;
//  }
//
//  @Override
//  public NBTTagCompound getNBTData(EntityPlayerMP player, Entity ent, NBTTagCompound tag, World world) {
//    return tag;
//  }
}
