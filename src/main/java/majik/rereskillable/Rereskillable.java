package majik.rereskillable;

import majik.rereskillable.client.Keybind;
import majik.rereskillable.client.Overlay;
import majik.rereskillable.client.screen.InventoryTabs;
import majik.rereskillable.client.Tooltip;
import majik.rereskillable.common.Curios;
import majik.rereskillable.common.Events;
import majik.rereskillable.common.capabilities.SkillModel;
import majik.rereskillable.common.capabilities.SkillStorage;
import majik.rereskillable.common.network.NotifyWarning;
import majik.rereskillable.common.network.RequestLevelUp;
import majik.rereskillable.common.network.SyncToClient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Optional;

@Mod("rereskillable")
public class Rereskillable
{
    public static SimpleChannel NETWORK;
    
    public Rereskillable()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configuration.getConfig());
    }
    
    // Common Setup
    
    private void commonSetup(final FMLCommonSetupEvent event)
    {
        CapabilityManager.INSTANCE.register(SkillModel.class, new SkillStorage(), () -> { throw new UnsupportedOperationException("No Implementation!"); });
        NETWORK = NetworkRegistry.newSimpleChannel(new ResourceLocation("rereskillable", "main_channel"), () -> "1.0", s -> true, s -> true);
        NETWORK.registerMessage(1, SyncToClient.class, SyncToClient::encode, SyncToClient::new, SyncToClient::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        NETWORK.registerMessage(2, RequestLevelUp.class, RequestLevelUp::encode, RequestLevelUp::new, RequestLevelUp::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        NETWORK.registerMessage(3, NotifyWarning.class, NotifyWarning::encode, NotifyWarning::new, NotifyWarning::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    
        Configuration.load();
        
        MinecraftForge.EVENT_BUS.register(new Events());
    }
    
    // Client Setup
    
    private void clientSetup(final FMLClientSetupEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new InventoryTabs());
        MinecraftForge.EVENT_BUS.register(new Tooltip());
        MinecraftForge.EVENT_BUS.register(new Keybind());
        MinecraftForge.EVENT_BUS.register(new Overlay());
        
        // Curios Compatibility
        
        if (ModList.get().isLoaded("curios"))
        {
            MinecraftForge.EVENT_BUS.register(new Curios());
        }
    }
}