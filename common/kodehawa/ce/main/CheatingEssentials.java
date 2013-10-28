package common.kodehawa.ce.main;

import java.util.Arrays;
import java.util.logging.Level;

import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;

import common.kodehawa.ce.commands.*;
import common.kodehawa.ce.logger.DynamicLogger;
import common.kodehawa.ce.module.man.ModuleManager;
import common.kodehawa.ce.tick.TickHandler;
import common.kodehawa.ce.util.CEConnectionHandler;
import common.kodehawa.ce.util.CEInitializationError;
import common.kodehawa.ce.util.ConfigManager;
import common.kodehawa.ce.util.CrashManager;
import common.kodehawa.ce.util.ForgeEvents;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

/**
 * Note from author:
 * You have the RIGHT to say that my code is horrible.
 * You have the RIGHT to say that this is like a Hacked Client.
 * You have the RIGHT to say that this code is skidded.
 * But it's my code, it's not skidded and the HC GUI and the Module System is the thing that makes this looks like a HC, but this is
 * NOT. ReesZRB, one of the persons that's working in CE since 3.1, is a HC coder, and he obviously makes the GUI looks like that, 
 * but I like how it looks. A lot of people likes how it looks. We have the RIGHT to do anything that we want with our code.
 * If this is Open Source IT'S for let's people to LEARN, not for see you making criticism to it with VERY BAD arguments.
 * Thanks.-
 * @author Kodehawa
 */

@SupportedSourceVersion(SourceVersion.RELEASE_6)
@Mod(modid="Cheating-Essentials", name="Cheating Essentials Reloaded", version="4.0.0B1")
public class CheatingEssentials {

	@Instance("Cheating-Essentials")
	public static CheatingEssentials main;
	TickHandler tickhandler = new TickHandler();
	
	@EventHandler
	public void preInitialization(FMLPreInitializationEvent e){
		DynamicLogger.instance().writeLog("Loading Cheating Essentials "+modVersion+" in " + MinecraftForge.getBrandingVersion(), Level.INFO);
		MinecraftForge.EVENT_BUS.register(new ForgeEvents());

		Minecraft.getMinecraft().mcProfiler.startSection("Cheating Essentials Starting");
		
		/********* Cheating Essentials METADATA DECLARATION START *********/
		
		ModMetadata mMetadata = e.getModMetadata();
		mMetadata.credits = "Kodehawa";
		mMetadata.description = "The most complete Forge cheating mod, with a lot of options and configurable cheats!";
		mMetadata.autogenerated = false;
		mMetadata.version = this.modVersion;
		mMetadata.authorList = Arrays.asList(new String[] { "Kodehawa" });
		mMetadata.url = "http://www.minecraftforum.net/topic/1846289-";
		mMetadata.logoFile = "ce.png";
		
		/********* Cheating Essentials METADATA DECLARATION FINISH *********/
	}
	
	@EventHandler
	public void initialization(FMLInitializationEvent e){
		TickRegistry.registerScheduledTickHandler(tickhandler, Side.CLIENT);
		try{
			load();
		}
		catch(CEInitializationError error){
			CrashManager.instance().propagate("Error on Cheating Essentials Instance Loading", error);
		}
	}
	
	@EventHandler
	public void postInitialization(FMLPostInitializationEvent e){
		Minecraft.getMinecraft().mcProfiler.startSection("Cheating Essentials Start");
		DynamicLogger.instance().writeLog("Cheating Essentials v4 succefully started in Minecraft 1.6.4", Level.INFO);
	}
	
	@EventHandler
	public void serverStarting(FMLServerStartingEvent ev){
		/* COMMAND REGISTERER START */
		
		MinecraftServer server = MinecraftServer.getServer();
		ICommandManager icommand = server.getCommandManager();
		ServerCommandManager command = ((ServerCommandManager) icommand);
		
		/* 1 */ command.registerCommand(new CommandModuleList());
		/* 2 */ command.registerCommand(new CommandModuleToggle());
		/* 3 */ command.registerCommand(new CommandSMKeybind());
		/* 4 */ command.registerCommand(new CommandAddFriend());
		/* 5 */ command.registerCommand(new CommandAddEnemy());
		/* 6 */ command.registerCommand(new CommandFlySpeed());
		/* 7 */ command.registerCommand(new CommandStepHeight());
		/* 8 */ command.registerCommand(new CommandBreadcrumbClear());
		/* 9 */ command.registerCommand(new CommandBlockESP());
		/* 10 */ command.registerCommand(new CommandModuleHelp());
		
		/* COMMAND REGISTERER FINISH */
		
	}
	
	private void load() throws CEInitializationError {
		NetworkRegistry.instance().registerConnectionHandler(new CEConnectionHandler());
		ModuleManager.instance();
		ConfigManager.instance();
	}
	
	public static CheatingEssentials mainInstance(){
		return main;
	}
	
	private String majorVersion = "4", minorVersion = "0", revision = "0", status = "B1";
	public final String modVersion = majorVersion+"."+minorVersion+"."+revision+status;
}
