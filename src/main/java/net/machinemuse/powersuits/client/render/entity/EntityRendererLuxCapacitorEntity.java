package net.machinemuse.powersuits.client.render.entity;

import net.machinemuse.powersuits.entity.EntityLuxCapacitor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.entity.RenderManager;

//import net.minecraftforge.client.model.AdvancedModelLoader;
//import net.minecraftforge.client.model.obj.WavefrontObject;

public class EntityRendererLuxCapacitorEntity extends MuseEntityRenderer <EntityLuxCapacitor> {
    public EntityRendererLuxCapacitorEntity(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(EntityLuxCapacitor entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }


//    protected static WavefrontObject lightmodel;
//    protected static WavefrontObject framemodel;
//
//    public static WavefrontObject getLightModel() {
//        if (lightmodel == null) {
//            lightmodel = (WavefrontObject) AdvancedModelLoader.loadModel(new ResourceLocation(Config.RESOURCE_PREFIX + "models/lightCore.obj"));
//        }
//        return lightmodel;
//    }
//
//    public static WavefrontObject getFrameModel() {
//        if (framemodel == null) {
//            framemodel = (WavefrontObject) AdvancedModelLoader.loadModel(new ResourceLocation(Config.RESOURCE_PREFIX + "models/lightBase.obj"));
//        }
//        return framemodel;
//    }
//
//    @Override
//    public void doRender(Entity undifferentiatedentity, double x, double y, double z, float yaw, float partialTickTime) {
//        EntityLuxCapacitor entity = (EntityLuxCapacitor) undifferentiatedentity;
//        MuseTextureUtils.pushTexture(Config.TEXTURE_PREFIX + "models/thusters_uvw_2.png");
//        glPushMatrix();
//        glTranslated(x, y, z);
//        double scale = 0.0625;
//        glScaled(scale, scale, scale);
//        getFrameModel().renderAll();
//        RenderState.glowOn();
//        new Colour(entity.red, entity.green, entity.blue, 1.0).doGL();
//        getLightModel().renderAll();
//        RenderState.glowOff();
//        glPopMatrix();
//        MuseTextureUtils.popTexture();
//    }
}
