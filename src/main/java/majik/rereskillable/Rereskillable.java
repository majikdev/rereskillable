package majik.rereskillable;

import majik.rereskillable.client.Keybind;
import majik.rereskillable.client.Overlay;
import majik.rereskillable.client.Tooltip;
import majik.rereskillable.client.screen.InventoryTabs;
import majik.rereskillable.common.CuriosCompat;
import majik.rereskillable.common.EventHandler;
import majik.rereskillable.common.capabilities.SkillModel;
import majik.rereskillable.common.commands.Commands;
import majik.rereskillable.common.network.NotifyWarning;
import majik.rereskillable.common.network.RequestLevelUp;
import majik.rereskillable.common.network.SyncToClient;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

@Mod("rereskillable")
public class Rereskillable
{
    public static SimpleChannel NETWORK;
    
    public Rereskillable()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::initCaps);
        
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configuration.getConfig());
    }
    
    // Common Setup
    
    private void commonSetup(final FMLCommonSetupEvent event)
    {
        Configuration.load();
        
        NETWORK = NetworkRegistry.newSimpleChannel(new ResourceLocation("rereskillable", "main_channel"), () -> "1.0", s -> true, s -> true);
        NETWORK.registerMessage(1, SyncToClient.class, SyncToClient::encode, SyncToClient::new, SyncToClient::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        NETWORK.registerMessage(2, RequestLevelUp.class, RequestLevelUp::encode, RequestLevelUp::new, RequestLevelUp::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        NETWORK.registerMessage(3, NotifyWarning.class, NotifyWarning::encode, NotifyWarning::new, NotifyWarning::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        MinecraftForge.EVENT_BUS.register(new Commands());
        
        // Curios Compatibility
    
        if (ModList.get().isLoaded("curios"))
        {
            MinecraftForge.EVENT_BUS.register(new CuriosCompat());
        }
    }

    public void initCaps(RegisterCapabilitiesEvent event) {
        event.register(SkillModel.class);
    }

    // Client Setup
    
    private void clientSetup(final FMLClientSetupEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new InventoryTabs());
        MinecraftForge.EVENT_BUS.register(new Tooltip());
        MinecraftForge.EVENT_BUS.register(new Keybind());
        MinecraftForge.EVENT_BUS.register(new Overlay());
    }
}