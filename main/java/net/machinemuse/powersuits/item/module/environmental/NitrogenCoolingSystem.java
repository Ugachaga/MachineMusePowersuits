package net.machinemuse.powersuits.item.module.environmental;

import net.machinemuse.numina.api.module.EnumModuleTarget;
import net.machinemuse.numina.api.module.IPlayerTickModule;
import net.machinemuse.numina.api.module.IToggleableModule;
import net.machinemuse.numina.api.module.ModuleManager;
import net.machinemuse.numina.utils.heat.MuseHeatUtils;
import net.machinemuse.powersuits.api.constants.MPSModuleConstants;
import net.machinemuse.powersuits.client.event.MuseIcon;
import net.machinemuse.powersuits.item.ItemComponent;
import net.machinemuse.item.powersuits.module.PowerModuleBase;
import net.machinemuse.utils.ElectricItemUtils;
import net.machinemuse.powersuits.utils.MuseItemUtils;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by Eximius88 on 1/17/14.
 */
public class NitrogenCoolingSystem extends PowerModuleBase implements IPlayerTickModule, IToggleableModule {
     public static final String COOLING_BONUS = "Cooling Bonus";
    public static final String ENERGY = "Energy Consumption";

    public NitrogenCoolingSystem(String resourceDommain, String UnlocalizedName) {
        super(EnumModuleTarget.TORSOONLY, resourceDommain, UnlocalizedName);
        //addInstallCost(new ItemStack(Item.netherStar, 1));
        addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.liquidNitrogen, 1));
        addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.rubberHose, 2));
        addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.controlCircuit, 1));
        addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.computerChip, 2));
        addTradeoffPropertyDouble("Power", COOLING_BONUS, 7, "%");
        addTradeoffPropertyInt("Power", ENERGY, 7, "RF/t");
    }

    @Override
    public void onPlayerTickActive(EntityPlayer player, ItemStack item) {
        double heatBefore = MuseHeatUtils.getPlayerHeatLegacy(player);
        MuseHeatUtils.coolPlayerLegacy(player, 0.210 * ModuleManager.getInstance().computeModularPropertyDouble(item, COOLING_BONUS));
        double cooling = heatBefore - MuseHeatUtils.getPlayerHeatLegacy(player);
        ElectricItemUtils.drainPlayerEnergy(player, (int) (cooling * ModuleManager.getInstance().computeModularPropertyInteger(item, ENERGY)));
    }

    @Override
    public void onPlayerTickInactive(EntityPlayer player, ItemStack item) {
    }

    @Override
    public String getCategory() {
        return MPSModuleConstants.CATEGORY_ENVIRONMENTAL;
    }

    @Override
    public TextureAtlasSprite getIcon(ItemStack item) {
        return MuseIcon.nitrogenCoolingSystem;
    }
}