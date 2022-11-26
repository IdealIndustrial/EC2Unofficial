package extracells.gui;

import appeng.client.gui.widgets.ITooltip;
import net.minecraft.inventory.Container;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

abstract class ECGuiContainer extends GuiContainer {

    ECGuiContainer(Container container) {
        super(container);
    }

    @Override
	public void drawScreen( final int mouseX, final int mouseY, final float btn )
	{
		super.drawScreen( mouseX, mouseY, btn );
		for( final Object c : this.buttonList )
		{
			if( c instanceof ITooltip )
			{
				handleTooltip(mouseX, mouseY, (ITooltip) c);
			}
		}
	}

    protected void handleTooltip(int mouseX, int mouseY, ITooltip c) {
		final int x = c.xPos();
		int y = c.yPos();

		if( x < mouseX && x + c.getWidth() > mouseX && c.isVisible() )
		{
			if( y < mouseY && y + c.getHeight() > mouseY)
			{
				if( y < 15 )
				{
					y = 15;
				}

				final String msg = c.getMessage();
				if( msg != null )
				{
					this.drawTooltip( x + 11, y + 4, 0, msg );
				}
			}
		}
	}

    public void drawTooltip( final int par2, final int par3, final int forceWidth, final String message )
	{
		GL11.glPushAttrib( GL11.GL_ALL_ATTRIB_BITS );
		GL11.glDisable( GL12.GL_RESCALE_NORMAL );
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable( GL11.GL_LIGHTING );
		GL11.glDisable( GL11.GL_DEPTH_TEST );
		final String[] var4 = message.split( "\n" );

		if( var4.length > 0 )
		{
			int var5 = 0;
			int var6;
			int var7;

			for( var6 = 0; var6 < var4.length; ++var6 )
			{
				var7 = this.fontRendererObj.getStringWidth( var4[var6] );

				if( var7 > var5 )
				{
					var5 = var7;
				}
			}

			var6 = par2 + 12;
			var7 = par3 - 12;
			int var9 = 8;

			if( var4.length > 1 )
			{
				var9 += 2 + ( var4.length - 1 ) * 10;
			}

			if( this.guiTop + var7 + var9 + 6 > this.height )
			{
				var7 = this.height - var9 - this.guiTop - 6;
			}

			if( forceWidth > 0 )
			{
				var5 = forceWidth;
			}

			this.zLevel = 300.0F;
			itemRender.zLevel = 300.0F;
			final int var10 = -267386864;
			this.drawGradientRect( var6 - 3, var7 - 4, var6 + var5 + 3, var7 - 3, var10, var10 );
			this.drawGradientRect( var6 - 3, var7 + var9 + 3, var6 + var5 + 3, var7 + var9 + 4, var10, var10 );
			this.drawGradientRect( var6 - 3, var7 - 3, var6 + var5 + 3, var7 + var9 + 3, var10, var10 );
			this.drawGradientRect( var6 - 4, var7 - 3, var6 - 3, var7 + var9 + 3, var10, var10 );
			this.drawGradientRect( var6 + var5 + 3, var7 - 3, var6 + var5 + 4, var7 + var9 + 3, var10, var10 );
			final int var11 = 1347420415;
			final int var12 = ( var11 & 16711422 ) >> 1 | var11 & -16777216;
			this.drawGradientRect( var6 - 3, var7 - 3 + 1, var6 - 3 + 1, var7 + var9 + 3 - 1, var11, var12 );
			this.drawGradientRect( var6 + var5 + 2, var7 - 3 + 1, var6 + var5 + 3, var7 + var9 + 3 - 1, var11, var12 );
			this.drawGradientRect( var6 - 3, var7 - 3, var6 + var5 + 3, var7 - 3 + 1, var11, var11 );
			this.drawGradientRect( var6 - 3, var7 + var9 + 2, var6 + var5 + 3, var7 + var9 + 3, var12, var12 );

			for( int var13 = 0; var13 < var4.length; ++var13 )
			{
				String var14 = var4[var13];

				if( var13 == 0 )
				{
					var14 = '\u00a7' + Integer.toHexString( 15 ) + var14;
				}
				else
				{
					var14 = "\u00a77" + var14;
				}

				this.fontRendererObj.drawStringWithShadow( var14, var6, var7, -1 );

				if( var13 == 0 )
				{
					var7 += 2;
				}

				var7 += 10;
			}

			this.zLevel = 0.0F;
			itemRender.zLevel = 0.0F;
		}
		GL11.glPopAttrib();
	}
}
