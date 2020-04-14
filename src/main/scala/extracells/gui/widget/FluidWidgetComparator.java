package extracells.gui.widget;

import extracells.gui.widget.fluid.AbstractFluidWidget;
import extracells.gui.widget.fluid.WidgetFluidSelector;
import net.minecraftforge.fluids.FluidStack;

import java.util.Comparator;

public class FluidWidgetComparator implements Comparator<AbstractFluidWidget> {
	private int sortingOrder;
	
	public FluidWidgetComparator() {
		super();
		this.sortingOrder = 0;
	}
	
	public FluidWidgetComparator(int _sortingOrder) {
		super();
		this.sortingOrder = _sortingOrder;
	}
	
	@Override
	public int compare(AbstractFluidWidget o1, AbstractFluidWidget o2) {
		switch(this.sortingOrder) {
		case 0:
			return ((Long)(((WidgetFluidSelector)o2).getAmount())).compareTo(((WidgetFluidSelector)o1).getAmount());
		case 1:
			return ((Long)(((WidgetFluidSelector)o1).getAmount())).compareTo(((WidgetFluidSelector)o2).getAmount());
		case 2:
			return o1.getFluid().getLocalizedName(new FluidStack(o1.getFluid(), 0))
					.compareTo(o2.getFluid().getLocalizedName(new FluidStack(o2.getFluid(), 0)));
		case 3:
			return o2.getFluid().getLocalizedName(new FluidStack(o2.getFluid(), 0))
					.compareTo(o1.getFluid().getLocalizedName(new FluidStack(o1.getFluid(), 0)));
		}
		return 0;
	}
}
