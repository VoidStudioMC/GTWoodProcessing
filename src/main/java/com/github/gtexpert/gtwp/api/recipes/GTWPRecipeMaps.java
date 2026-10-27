package com.github.gtexpert.gtwp.api.recipes;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMapBuilder;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import gregtech.core.sound.GTSoundEvents;

import java.lang.reflect.Field;
import java.util.Map;

import crafttweaker.annotations.OnRegister;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.zenscript.GlobalRegistry;
import stanhebben.zenscript.TypeExpansion;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;
import stanhebben.zenscript.type.expand.ZenExpandMember;
import stanhebben.zenscript.type.natives.ZenFieldMethod;

@ZenExpansion("mods.gregtech.recipe.RecipeMaps")
@ZenRegister
public class GTWPRecipeMaps {

    private static final String RECIPE_MAPS_ZEN_CLASS = "mods.gregtech.recipe.RecipeMaps";
    private static final String SAWMILL_RECIPES_PROPERTY = "SAWMILL_RECIPES";

    public static final RecipeMap<SimpleRecipeBuilder> SAWMILL_RECIPES = new RecipeMapBuilder<>(
            "sawmill", new SimpleRecipeBuilder())
                    .itemInputs(2)
                    .itemOutputs(2)
                    .fluidInputs(1)
                    .fluidOutputs(0)
                    .itemSlotOverlay(GuiTextures.SAWBLADE_OVERLAY, false, false)
                    .itemSlotOverlay(GuiTextures.CUTTER_OVERLAY, true, false)
                    .itemSlotOverlay(GuiTextures.DUST_OVERLAY, true, true)
                    .progressBar(GuiTextures.PROGRESS_BAR_SLICE, ProgressWidget.MoveType.HORIZONTAL)
                    .sound(GTSoundEvents.CHAINSAW_TOOL)
                    .build();

    @ZenMethodStatic("SAWMILL_RECIPES")
    public static RecipeMap<SimpleRecipeBuilder> getSawmillRecipes() {
        return SAWMILL_RECIPES;
    }

    /*
     * Manually registering Sawmill recipe map extension because CraftTweaker is cooked
     */
    @OnRegister
    @SuppressWarnings("unchecked")
    public static void registerSawmillRecipesProperty() {
        try {
            TypeExpansion expansion = GlobalRegistry.getExpansions().get(RECIPE_MAPS_ZEN_CLASS);
            if (expansion == null) {
                throw new IllegalStateException("RecipeMaps ZenScript expansion was not registered");
            }

            Field staticMembersField = TypeExpansion.class.getDeclaredField("staticMembers");
            staticMembersField.setAccessible(true);

            Map<String, ZenExpandMember> staticMembers =
                    (Map<String, ZenExpandMember>) staticMembersField.get(expansion);
            ZenExpandMember sawmillRecipes = staticMembers.computeIfAbsent(SAWMILL_RECIPES_PROPERTY,
                    name -> new ZenExpandMember(RECIPE_MAPS_ZEN_CLASS, name));
            sawmillRecipes.setGetter(new ZenFieldMethod(
                    GTWPRecipeMaps.class.getField(SAWMILL_RECIPES_PROPERTY), GlobalRegistry.getTypes(), false));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to register RecipeMaps.SAWMILL_RECIPES as a ZenScript property", e);
        }
    }
}
