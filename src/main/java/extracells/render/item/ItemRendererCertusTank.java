package extracells.render.item;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import extracells.render.model.ModelCertusTank;

public class ItemRendererCertusTank implements IItemRenderer
{

	private ModelCertusTank model = new ModelCertusTank();

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return true;
	}

	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("extracells", "textures/blocks/texmap_tank.png"));
		GL11.glPushMatrix();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		GL11.glScalef(1.0F, -1F, -1F);
		model.render(0.0625f);
		GL11.glScalef(1.0F, -1F, 1.0F);
		model.render(0.0625f);

		if (item != null && item.hasTagCompound())
		{
			FluidStack storedFluid = FluidStack.loadFluidStackFromNBT(item.getTagCompound().getCompoundTag("tileEntity"));
			int tankCapacity = 32000;

			if (storedFluid != null && storedFluid.getFluid() != null)
			{
				Icon fluidIcon = storedFluid.getFluid().getIcon();

				Tessellator tessellator = Tessellator.instance;
				RenderBlocks renderer = new RenderBlocks();

				GL11.glScalef(1.0F, 1.0F, -1.0F);
				renderer.setRenderBounds(0.08F, 0.001F, 0.08F, 0.92, (float) storedFluid.amount / (float) tankCapacity * 0.999F, 0.92F);
				Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
				GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

				GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
				GL11.glEnable(GL11.GL_CULL_FACE);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, -1F, 0.0F);
				renderer.renderFaceYNeg(Block.blocksList[FluidRegistry.WATER.getBlockID()], 0.0D, 0.0D, 0.0D, fluidIcon);
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 1.0F, 0.0F);
				renderer.renderFaceYPos(Block.blocksList[FluidRegistry.WATER.getBlockID()], 0.0D, 0.0D, 0.0D, fluidIcon);
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 0.0F, -1F);
				renderer.renderFaceZNeg(Block.blocksList[FluidRegistry.WATER.getBlockID()], 0.0D, 0.0D, 0.0D, fluidIcon);
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 0.0F, 1.0F);
				renderer.renderFaceZPos(Block.blocksList[FluidRegistry.WATER.getBlockID()], 0.0D, 0.0D, 0.0D, fluidIcon);
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(-1F, 0.0F, 0.0F);
				renderer.renderFaceXNeg(Block.blocksList[FluidRegistry.WATER.getBlockID()], 0.0D, 0.0D, 0.0D, fluidIcon);
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(1.0F, 0.0F, 0.0F);
				renderer.renderFaceXPos(Block.blocksList[FluidRegistry.WATER.getBlockID()], 0.0D, 0.0D, 0.0D, fluidIcon);
				tessellator.draw();

				GL11.glPopAttrib();
			}
		}

		GL11.glPopMatrix();
	}
}