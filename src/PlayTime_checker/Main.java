package PlayTime_checker;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	private boolean enable;
	public boolean waitToReset = false;
	public FileConfiguration config;
	
	public void check_config() {
		config = getConfig();
		if (!config.isBoolean("enable")) {
			config.set("enable", true);
		}
		enable = config.getBoolean("enable");
		saveConfig();
		return;
	}
	
	private void printHelloInConsole() {
		ConsoleCommandSender console = getServer().getConsoleSender();
		console.sendMessage("===========================");
		console.sendMessage("|   |     ___   ___        ");
		console.sendMessage("|   |\\   /|  |  |     \\  / ");
		console.sendMessage("|===| \\ / |__|  |__    \\/  ");
		console.sendMessage("|   |  |  |     |      /\\  ");
		console.sendMessage("|   |  |  |     |__   /  \\ ");
		console.sendMessage("===========================");
	}
	
	@Override
	public void onEnable() {
		check_config();
		if (!enable) {
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		printHelloInConsole();
		getServer().getScheduler().scheduleSyncRepeatingTask(
				this, new MyTask(this){
					@Override
					public void run() {
						addTime();
						return;
					}
				}, 0, 1200);
		getServer().getScheduler().scheduleSyncRepeatingTask(
				this, new MyTask(this){
					@Override
					public void run() {
						saveCfg();
						return;
					}
				}, 0, 12000);
		this.getCommand("ptc").setExecutor(new CommandReset(this));
		return;
	}
	
	@Override
	public void onDisable() {
		saveConfig();
		return;
	}

}
