package PlayTime_checker;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandReset implements CommandExecutor{
	private Main mainPlugin;
	
	public CommandReset(Main mainPlugin) {
		this.mainPlugin = mainPlugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("ptc.reset")) {
			sender.sendMessage("У вас нет на это прав!");
			return false;
		}
		if (args.length != 1) {
			sender.sendMessage("Аргументы указаны неправильно!");
			return false;
		}
		if (args[0].equals("all")) {
			sender.sendMessage("Вы точно хотите сбросить ВСЮ статистику?");
			sender.sendMessage("Данное действие отменить будет нельзя!");
			sender.sendMessage("Чтобы продолжить напишите: /ptc confirm");
			sender.sendMessage("На это действие у вас есть 30 секунд!");
			mainPlugin.getServer().getScheduler().runTaskLater(
					mainPlugin, new MyTask(mainPlugin) {
						@Override
						public void run() {
							waitToReset(sender);
						}
					}, 600);
			mainPlugin.waitToReset = true;
			return true;
		}
		
		if (args[0].equals("save")) {
			sender.sendMessage("Статистика сохранена");
			mainPlugin.saveConfig();
			return true;
		}
		if (args[0].equals("confirm")) {
			if (!mainPlugin.waitToReset) {
				sender.sendMessage("Сброс статистики не ожидается!");
				return false;
			}
			mainPlugin.config.set("PlayTime", null);
			mainPlugin.waitToReset = false;
			mainPlugin.saveConfig();
			sender.sendMessage("Вся статистика сброшена!");
			return true;
		}
		if (mainPlugin.config.isInt("PlayTime."+args[0])) {
			mainPlugin.config.set("PlayTime."+args[0], 0);
			mainPlugin.saveConfig();
			sender.sendMessage("Статистика игрока "+args[0]+" сброшена");
			return true;
		} else {
			sender.sendMessage("Статистика "+args[0]+" не найдена");
			return false;
		}
	}
	
}
