package extracells.tileentity;

import appeng.api.AEApi;
import appeng.api.definitions.IBlocks;
import appeng.tile.crafting.TileCraftingStorageTile;
import appeng.tile.crafting.TileCraftingTile;
import net.minecraft.item.ItemStack;

import static extracells.registries.BlockEnum.CRAFTINGSTORAGE;

public class TileEntityCraftingStorage extends TileCraftingStorageTile {

    private static final int KILO_SCALAR = 1024;

    @Override
    protected ItemStack getItemFromTile( final Object obj ){
        final int storage = ((TileCraftingTile) obj).getStorageBytes()/1000;

        switch(storage){
            case 256:  return new ItemStack(CRAFTINGSTORAGE.getBlock(), 1, 0);
            case 1024: return new ItemStack(CRAFTINGSTORAGE.getBlock(), 1, 1);
            case 4096: return new ItemStack(CRAFTINGSTORAGE.getBlock(), 1, 2);
            case 16384: return new ItemStack(CRAFTINGSTORAGE.getBlock(), 1, 3);
        }
        return super.getItemFromTile(obj);
    }

    public boolean isAccelerator() {
        return false;
    }

    public boolean isStorage() {
        return true;
    }

    @Override
    public int getStorageBytes(){
        if( this.worldObj == null || this.notLoaded() )
        {
            return 0;
        }

        switch( this.worldObj.getBlockMetadata( this.xCoord, this.yCoord, this.zCoord ) & 3 )
        {
            default:
            case 0:return 256 * 1000;
            case 1:return 1024 * 1000;
            case 2:return 4096 * 1000;
            case 3:return 16384 * 1000;
        }
    }
}
