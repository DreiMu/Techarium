package software.bernie.techarium.integration.mysticalagriculture;

import com.blakebr0.mysticalagriculture.item.MysticalSeedsItem;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.techarium.Techarium;
import software.bernie.techarium.integration.Integration;
import software.bernie.techarium.integration.ModIntegrations;
import software.bernie.techarium.recipe.recipe.BotariumRecipe;
import software.bernie.techarium.registry.TagRegistry;
import software.bernie.techarium.util.ChancedItemStackList;

import java.util.function.Consumer;

public class MysticalAgricultureIntegration extends Integration {
    public MysticalAgricultureIntegration(String modID) {
        super(modID);
    }

    @Override
    public void generateRecipes(Consumer<IFinishedRecipe> consumer) {
        for (Item item : ForgeRegistries.ITEMS) {
            if (item instanceof MysticalSeedsItem) {
                MysticalSeedsItem mysticalSeed = (MysticalSeedsItem) item;
                BotariumRecipe.builder()
                        .cropType(Ingredient.of(new ItemStack(mysticalSeed)))
                        .soilIn(Ingredient.of(TagRegistry.DIRT))
                        .fluidIn(new FluidStack(Fluids.WATER, 1000))
                        .maxProgress(2000*mysticalSeed.getCrop().getTier().getValue())
                        .rfPerTick(30*mysticalSeed.getCrop().getTier().getValue())
                        .renderSoil((BlockItem) Items.FARMLAND)
                        .progressPerTick(1)
                        .output(ChancedItemStackList.of(mysticalSeed.getCrop().getEssence()))
                        .construct()
                        .addCondition(new ModLoadedCondition(ModIntegrations.getMystical().orElseThrow(NullPointerException::new).getModID()))
                        .build(consumer,
                                new ResourceLocation(Techarium.MOD_ID, "botarium/mystical/" + mysticalSeed.getRegistryName().getPath()));
            }
        }
    }
}
