package crazypants.enderzoo.potion;

import java.awt.Color;

import crazypants.enderzoo.ColorUtil;
import crazypants.enderzoo.EnderZoo;
import crazypants.enderzoo.config.Config;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class FloatingPotion extends Potion {

  private static final String NAME = EnderZoo.MODID + ".floatingPotion";

  public static FloatingPotion create() {
    FloatingPotion res = new FloatingPotion();
    EnderZoo.instance.register(res);
    return res;
  }

  private FloatingPotion() {
    super(false, 0);
    setPotionName(NAME);
    setRegistryName(NAME);
  }

  @Override
  public boolean isReady(int p_76397_1_, int p_76397_2_) {
    return true;
  }

  @Override
  public void performEffect(EntityLivingBase entityIn, int amplifier) {    
    if (entityIn.posY < 255) {
      if (amplifier > 0) {
        entityIn.motionY += Config.floatingPotionTwoAcceleration;        
        if (entityIn.motionY > Config.floatingPotionTwoSpeed) {
          entityIn.motionY = Config.floatingPotionTwoSpeed;
        }
      } else {     
        entityIn.motionY += Config.floatingPotionAcceleration;
        if (entityIn.motionY > Config.floatingPotionSpeed) {
          entityIn.motionY = Config.floatingPotionSpeed;
        }
      }
    }
  }

  @Override
  public int getLiquidColor() {
    return ColorUtil.getRGB(Color.green.darker().darker());
  }

}
