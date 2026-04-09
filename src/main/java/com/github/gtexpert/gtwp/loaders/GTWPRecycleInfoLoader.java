package com.github.gtexpert.gtwp.loaders;

import java.util.Collections;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.RecyclingData;
import gregtech.common.ConfigHolder;

import com.github.gtexpert.gtwp.common.blocks.GTWPBlockMetalCasing;
import com.github.gtexpert.gtwp.common.blocks.GTWPMetaBlocks;

public class GTWPRecycleInfoLoader {

    public static void init() {
        GregTechAPI.RECYCLING_MANAGER.registerRecyclingData(
                GTWPMetaBlocks.GTWP_METAL_CASING.getItemVariant(GTWPBlockMetalCasing.MetalCasingType.SAWMill),
                new RecyclingData(Collections.singletonList(
                        new MaterialStack(Materials.TreatedWood,
                                (GTValues.M * 8L) / ConfigHolder.recipes.casingsPerCraft))));
    }
}
