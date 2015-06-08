package com.charlie.settpscommands;

import java.io.File;
import java.util.Timer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.charlie.earth2me.essentials.EssentialsTimer;

public final class SetTpsCommands extends JavaPlugin {
	private Timer t;
	public static boolean enabled = true;
	public EssentialsTimer et = new EssentialsTimer();
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if(command.getName().equalsIgnoreCase("stc")){
			if(args.length < 1 || args.length > 1){
				sender.sendMessage(command.getUsage());
				return false;
			}
			switch(args[0]){
			case "reload":
				this.reloadConfig();
				//t.cancel();
				//t = new Timer();
				//t.schedule(new TpsChecker(this),0,getConfig().getLong("interval"));
				sender.sendMessage("重新加载成功");
				return true;
			default:
				sender.sendMessage(command.getUsage());
				return false;
			}
		}
		return false;
	}
	@Override
	public void onEnable() {
		getLogger().info("SetTpsCommands 0.2");
		if(!(new File(this.getDataFolder(),"config.yml").exists())){
			getLogger().info("Config not found. Generating default config...");
			this.saveDefaultConfig();
			this.reloadConfig();
		}
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, et, 1000,50);
		enabled = getConfig().getBoolean("enabled");
		if(enabled)
			getLogger().info("开始检测，时间间隔："+getConfig().getInt("interval",2000)+"ms, TPS下限："+getConfig().getInt("tpslimit")+"命令："+getConfig().getStringList("commands")+" 提示信息："+getConfig().getInt("message"));
		t = new Timer();
		t.schedule(new TpsChecker(this),0,getConfig().getLong("interval"));
	}
	@Override
	public void onDisable() {
		t.cancel();
		getLogger().info("SetTpsCommands 0.2 已卸载");
	}
}
