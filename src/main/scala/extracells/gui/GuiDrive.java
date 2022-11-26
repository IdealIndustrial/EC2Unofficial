package extracells.gui;

import appeng.client.gui.widgets.GuiTabButton;
import appeng.core.localization.GuiText;
import extracells.container.ContainerDrive;
import extracells.network.packet.part.PacketFluidStorage;
import extracells.part.PartDrive;
import extracells.network.packet.other.PacketGuiSwitch;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiDrive extends ECGuiContainer {

	private EntityPlayer player;
	private ResourceLocation guiTexture = new ResourceLocation("extracells",
			"textures/gui/drive.png");
	private final PartDrive part;
	private GuiTabButton priority;

	public GuiDrive(PartDrive _part, EntityPlayer _player) {
		super(new ContainerDrive(_part, _player));
		this.player = _player;
		this.part = _part;
		this.xSize = 176;
		this.ySize = 163;
		new PacketFluidStorage(this.player).sendPacketToServer();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float alpha, int sizeX,
			int sizeY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(this.guiTexture);
		drawTexturedModalRect(this.guiLeft, this.guiTop - 18, 0, 0, this.xSize,
				this.ySize);
		for (Object s : this.inventorySlots.inventorySlots) {
			renderBackground((Slot) s);
		}
	}

	private void renderBackground(Slot slot) {
		if (slot.getStack() == null && slot.slotNumber < 6) {
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
			this.mc.getTextureManager().bindTexture(
					new ResourceLocation("appliedenergistics2",
							"textures/guis/states.png"));
			this.drawTexturedModalRect(this.guiLeft + slot.xDisplayPosition,
					this.guiTop + slot.yDisplayPosition, 240, 0, 16, 16);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_LIGHTING);

		}
	}

	@Override
	public void actionPerformed(GuiButton button) {
		super.actionPerformed(button);

		if (button == this.priority) {
			new PacketGuiSwitch(100 + part.getSide().ordinal(), part.getHostTile()).sendPacketToServer();
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		this.priority = new GuiTabButton(this.guiLeft + 154, this.guiTop - 18, 2 + 4 * 16, GuiText.Priority.getLocal(), itemRender);
		this.buttonList.add(priority);
	}
}
