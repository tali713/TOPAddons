package io.github.drmanganese.topaddons.addons;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import io.github.drmanganese.topaddons.TOPRegistrar;
import io.github.drmanganese.topaddons.api.TOPAddon;
import io.github.drmanganese.topaddons.elements.tconstruct.ElementSmelteryTank;

import java.util.List;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import slimeknights.tconstruct.gadgets.tileentity.TileDryingRack;
import slimeknights.tconstruct.library.smeltery.SmelteryTank;
import slimeknights.tconstruct.smeltery.tileentity.TileSmeltery;

@TOPAddon(dependency = "tconstruct")
public class AddonTinkersConstruct extends AddonBlank {

    public static int ELEMENT_SMELTERY_TANK;

    @Override
    public void registerElements() {
        ELEMENT_SMELTERY_TANK = TOPRegistrar.GetTheOneProbe.probe.registerElementFactory(ElementSmelteryTank::new);
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        TileEntity tile = world.getTileEntity(data.getPos());
        if (tile instanceof TileSmeltery) {
            SmelteryTank tank = ((TileSmeltery) world.getTileEntity(data.getPos())).getTank();
            addSmelteryTankElement(probeInfo, tank.getFluids(), Math.max(tank.getFluidAmount(), tank.getCapacity()), mode);
        }

        if (tile instanceof TileDryingRack) {
            TileDryingRack tileDrying = (TileDryingRack) tile;
            textPrefixed(probeInfo, "Progress", (Math.round(tileDrying.getProgress()  * 100)) + "%");
        }
    }

    private void addSmelteryTankElement(IProbeInfo probeInfo, List<FluidStack> fluids, int capacity, ProbeMode mode) {
        probeInfo.element(new ElementSmelteryTank(fluids, capacity, mode == ProbeMode.EXTENDED));
    }
}
