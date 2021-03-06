package common.kodehawa.ce.apicommands;

import net.minecraft.client.Minecraft;

import common.kodehawa.api.console.BaseCommand;
import common.kodehawa.ce.config.ModuleStateConfiguration;
import common.kodehawa.ce.module.core.AbstractModule;
import common.kodehawa.ce.module.man.ModuleManager;

public class ACommandModuleToggle extends BaseCommand
{

	public ACommandModuleToggle() 
	{
		super("module");
	}

	@Override
	public void runCommand(String s, String[] subcommands)
	{
		for(AbstractModule module : ModuleManager.instance().avModules){
			if(module.getModuleName().replace(" ", "").equalsIgnoreCase(subcommands[0])){
				module.toggle(); 
				Minecraft.getMinecraft().thePlayer.addChatMessage("Toggled Module: "+module.getModuleName()+" -- Module State: "+ (module.isActive() ? ("Active") : ("Disabled") ));
				break;
			}
		}
	}

	@Override
	public String getDescription()
	{
		return "Toggles a module";
	}

	@Override
	public String getSyntax() 
	{
		return this.getCommand()+" <module name>";
	}

}
