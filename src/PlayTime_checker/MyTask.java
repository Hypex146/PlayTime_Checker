package PlayTime_checker;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class MyTask implements Runnable {
	private Main mainPlugin;
	
	public MyTask(Main mainPlugin) {
		this.mainPlugin = mainPlugin;
	}
	
	public void run() {
		return;
	}
	
	public void addTime() {
		FileConfiguration config = mainPlugin.config;
		for (Player player : mainPlugin.getServer().getOnlinePlayers()) {
			if (!config.isInt("PlayTime."+player.getName())){
				config.set("PlayTime."+player.getName(), 0);
			} else {
				config.set("PlayTime."+player.getName(), config.getInt("PlayTime."+player.getName())+1);
			}
		}
		return;
	}
	
	public void saveCfg() {
		mainPlugin.saveConfig();
		return;
	}
	
	public void waitToReset(CommandSender sender) {
		if (!mainPlugin.waitToReset) {return;}
		sender.sendMessage("Время на сброс статистики вышло!");
		mainPlugin.waitToReset = false;
	}
	
}
