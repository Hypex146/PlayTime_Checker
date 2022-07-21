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
			sender.sendMessage("� ��� ��� �� ��� ����!");
			return false;
		}
		if (args.length != 1) {
			sender.sendMessage("��������� ������� �����������!");
			return false;
		}
		if (args[0].equals("all")) {
			sender.sendMessage("�� ����� ������ �������� ��� ����������?");
			sender.sendMessage("������ �������� �������� ����� ������!");
			sender.sendMessage("����� ���������� ��������: /ptc confirm");
			sender.sendMessage("�� ��� �������� � ��� ���� 30 ������!");
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
			sender.sendMessage("���������� ���������");
			mainPlugin.saveConfig();
			return true;
		}
		if (args[0].equals("confirm")) {
			if (!mainPlugin.waitToReset) {
				sender.sendMessage("����� ���������� �� ���������!");
				return false;
			}
			mainPlugin.config.set("PlayTime", null);
			mainPlugin.waitToReset = false;
			mainPlugin.saveConfig();
			sender.sendMessage("��� ���������� ��������!");
			return true;
		}
		if (mainPlugin.config.isInt("PlayTime."+args[0])) {
			mainPlugin.config.set("PlayTime."+args[0], 0);
			mainPlugin.saveConfig();
			sender.sendMessage("���������� ������ "+args[0]+" ��������");
			return true;
		} else {
			sender.sendMessage("���������� "+args[0]+" �� �������");
			return false;
		}
	}
	
}
