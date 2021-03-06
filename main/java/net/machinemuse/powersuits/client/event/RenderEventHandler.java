package net.machinemuse.powersuits.client.event;

import net.machinemuse.numina.api.module.ModuleManager;
import net.machinemuse.numina.math.geometry.Colour;
import net.machinemuse.numina.math.geometry.DrawableMuseRect;
import net.machinemuse.numina.client.render.MuseIconUtils;
import net.machinemuse.numina.client.render.MuseTextureUtils;
import net.machinemuse.powersuits.api.constants.MPSModuleConstants;
import net.machinemuse.powersuits.client.gui.tinker.clickable.ClickableKeybinding;
import net.machinemuse.powersuits.client.gui.tinker.clickable.ClickableModule;
import net.machinemuse.powersuits.common.config.MPSConfig;
import net.machinemuse.powersuits.control.KeybindManager;
import net.machinemuse.powersuits.item.module.vision.BinocularsModule;
import net.machinemuse.numina.utils.render.MuseRenderer;
import net.machinemuse.powersuits.utils.MuseItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Ported to Java by lehjr on 10/24/16.
 */
public class RenderEventHandler {
    private static boolean ownFly;
    private final DrawableMuseRect frame = new DrawableMuseRect(MPSConfig.getInstance().keybindHUDx(),
                                                                MPSConfig.getInstance().keybindHUDy(),
                                                          MPSConfig.getInstance().keybindHUDx() + (double)16,
                                                        MPSConfig.getInstance().keybindHUDy() + (double)16,
                                                true,
                                                                Colour.DARKGREEN.withAlpha(0.2),
                                                                Colour.GREEN.withAlpha(0.2));

    public RenderEventHandler() {
        this.ownFly = false;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void preTextureStitch(TextureStitchEvent.Pre event) {
        MuseIcon.registerIcons(event);
//        ModelHelper.loadArmorModels(false);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Post event) {

    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void renderLast(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution screen = new ScaledResolution(mc);
    }

    @SubscribeEvent
    public void onPreRenderPlayer(RenderPlayerEvent.Pre event) {
        if (!event.getEntityPlayer().capabilities.isFlying && !event.getEntityPlayer().onGround && this.playerHasFlightOn(event.getEntityPlayer())) {
            event.getEntityPlayer().capabilities.isFlying = true;
            RenderEventHandler.ownFly =true;
        }
    }

    private boolean playerHasFlightOn(EntityPlayer player) {
        return ModuleManager.getInstance().itemHasActiveModule(player.getItemStackFromSlot(EntityEquipmentSlot.CHEST), MPSModuleConstants.MODULE_JETPACK) ||
                ModuleManager.getInstance().itemHasActiveModule(player.getItemStackFromSlot(EntityEquipmentSlot.CHEST), MPSModuleConstants.MODULE_GLIDER) ||
                ModuleManager.getInstance().itemHasActiveModule(player.getItemStackFromSlot(EntityEquipmentSlot.FEET), MPSModuleConstants.MODULE_JETBOOTS) ||
                ModuleManager.getInstance().itemHasActiveModule(player.getItemStackFromSlot(EntityEquipmentSlot.HEAD), MPSModuleConstants.MODULE_FLIGHT_CONTROL);
    }

    @SubscribeEvent
    public void onPostRenderPlayer(RenderPlayerEvent.Post event) {
        if (RenderEventHandler.ownFly) {
            RenderEventHandler.ownFly = false;
            event.getEntityPlayer().capabilities.isFlying = false;
        }
    }

    @SubscribeEvent
    public void onFOVUpdate(FOVUpdateEvent e) {
        ItemStack helmet = e.getEntity().getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        if (ModuleManager.getInstance().itemHasActiveModule(helmet, MPSModuleConstants.BINOCULARS_MODULE)) {
            e.setNewfov(e.getNewfov() / (float)ModuleManager.getInstance().computeModularPropertyDouble(helmet, BinocularsModule.FOV_MULTIPLIER));
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onPostRenderGameOverlayEvent(RenderGameOverlayEvent.Post e) {
        RenderGameOverlayEvent.ElementType elementType = e.getType();
        if (RenderGameOverlayEvent.ElementType.HOTBAR.equals(elementType)) {
            this.drawKeybindToggles();
        }
    }
    @SideOnly(Side.CLIENT)
    public void drawKeybindToggles() {
        if (MPSConfig.getInstance().keybindHUDon()) {
            Minecraft mc = Minecraft.getMinecraft();
            EntityPlayerSP player = mc.player;
            ScaledResolution screen = new ScaledResolution(mc);
            frame.setLeft(MPSConfig.getInstance().keybindHUDx());
            frame.setTop(MPSConfig.getInstance().keybindHUDy());
            frame.setBottom(frame.top() + 16);
            for (ClickableKeybinding kb:   KeybindManager.getKeybindings()) {
                if (kb.displayOnHUD) {
                    double stringwidth = MuseRenderer.getStringWidth(kb.getLabel());
                    frame.setWidth(stringwidth + kb.getBoundModules().size() * 16);
                    frame.draw();
                    MuseRenderer.drawString(kb.getLabel(), frame.left() + 1, frame.top() + 3, (kb.toggleval) ? Colour.RED : Colour.GREEN);
                    double x = frame.left() + stringwidth;
                    for (ClickableModule module:  kb.getBoundModules()) {
                        MuseTextureUtils.pushTexture(MuseTextureUtils.TEXTURE_QUILT);
                        boolean active = false;
                        for (ItemStack stack : MuseItemUtils.modularItemsEquipped(player)) {
                            if (ModuleManager.getInstance().itemHasActiveModule(stack, module.getModule().getUnlocalizedName()))
                                active = true;
                        }

                        MuseIconUtils.drawIconAt(x, frame.top(), module.getModule().getIcon(null), (active) ? Colour.WHITE : Colour.DARKGREY.withAlpha(0.5));
                        MuseTextureUtils.popTexture();
                        x += 16;
                    }
                    frame.setTop(frame.top() + 16);
                    frame.setBottom(frame.top() + 16);
                }
            }
        }
    }
}