//package net.machinemuse.powersuits.client.model.item;
//
//import net.machinemuse.numina.api.module.ModuleManager;
//import net.machinemuse.numina.utils.MuseLogger;
//import net.machinemuse.numina.math.geometry.Colour;
//import net.machinemuse.powersuits.api.constants.MPSModuleConstants;
//import net.machinemuse.powersuits.client.event.ModelBakeEventHandler;
//import net.machinemuse.powersuits.client.helper.ModelPowerFistHelper;
//import net.machinemuse.numina.api.item.IModularItemBase;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.client.renderer.block.model.BakedQuad;
//import net.minecraft.client.renderer.block.model.IBakedModel;
//import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
//import net.minecraft.client.renderer.block.model.ItemOverrideList;
//import net.minecraft.client.renderer.texture.TextureAtlasSprite;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.world.World;
//import net.minecraftforge.common.model.TRSRTransformation;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//import org.apache.commons.lang3.tuple.Pair;
//
//import javax.annotation.Nullable;
//import javax.vecmath.Matrix4f;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
///**
// * Created by lehjr on 12/19/16.
// */
//@SideOnly(Side.CLIENT)
//public class ModelPowerFist implements IBakedModel {
//    static ItemCameraTransforms.TransformType cameraTransformType;
//    static ItemStack itemStack;
//    static Item item;
//    static Colour colour;
//    static World world;
//    static EntityLivingBase entity;
//    static boolean isFiring = false;
//    static IBakedModel iconModel;
//
//    @Override
//    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformTypeIn) {
//        cameraTransformType = cameraTransformTypeIn;
//        TRSRTransformation transform = ModelPowerFistHelper.getInstance().powerfistState.apply(Optional.of(cameraTransformType)).orElse(TRSRTransformation.identity());
//        if(transform != TRSRTransformation.identity())
//            return Pair.of(this, TRSRTransformation.blockCornerToCenter(transform).getMatrix());
//        return Pair.of(this, (iconModel).handlePerspective(cameraTransformTypeIn).getValue());
//    }
//
//    @Override
//    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
//        if (side != null) return Collections.EMPTY_LIST;
//        try {
//            return ModelPowerFistHelper.getInstance().colouredPowerFistQuadMap.get(
//                    new ModelPowerFistHelper.PowerFistQuadMapKey(colour, cameraTransformType, isFiring));
//        } catch (Exception e) {
//            MuseLogger.logException("failed to load get quads from cache: ", e);
//            return Collections.EMPTY_LIST;
//        }
//    }
//
//    @Override
//    public boolean isAmbientOcclusion() {
//        return iconModel.isAmbientOcclusion();
//    }
//
//    @Override
//    public boolean isGui3d() {
//        if (iconModel == null)
//            iconModel = ModelBakeEventHandler.getInstance().powerFistIconModel;
//        return iconModel.isGui3d();
//    }
//
//    @Override
//    public boolean isBuiltInRenderer() {
//        return false;
//    }
//
//    @Override
//    public TextureAtlasSprite getParticleTexture() {
//        return iconModel.getParticleTexture();
//    }
//
//    @Override
//    public ItemCameraTransforms getItemCameraTransforms() {
//        return iconModel.getItemCameraTransforms();
//    }
//
//    @Override
//    public ItemOverrideList getOverrides() {
//        return new PowerFistItemOverrideList();
//    }
//
//    public class PowerFistItemOverrideList  extends ItemOverrideList {
//        public PowerFistItemOverrideList() {
//            super(Collections.EMPTY_LIST);
//        }
//
//        @Override
//        public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stackIn, World worldIn, EntityLivingBase entityIn) {
//            itemStack = stackIn;
//            world = worldIn;
//            entity = entityIn;
//            item = itemStack.getItem();
//            colour = ((IModularItemBase) item).getColorFromItemStack(itemStack);
//            if (entityIn instanceof EntityPlayer) {
//                if (itemStack != null && itemStack == entityIn.getHeldItemMainhand() && entityIn.isHandActive()
//                        && ModuleManager.getInstance().itemHasActiveModule(itemStack, MPSModuleConstants.MODULE_PLASMA_CANNON)) {
//                    isFiring = true;
//                }
//                else isFiring = false;
//            }
//            else isFiring = false;
//
//            return originalModel;
//        }
//    }
//
//    private static ModelPowerFist INSTANCE;
//
//    public static ModelPowerFist getInstance() {
//        if (INSTANCE == null) {
//            synchronized (ModelPowerFist.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = new ModelPowerFist();
//                }
//            }
//        }
//        return INSTANCE;
//    }
//
//    private ModelPowerFist() {
//    }
//}