package com.charlie.settpscommands;

import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;

public class TpsChecker extends TimerTask {
	private final SetTpsCommands plugin;
	/*private long mills = System.currentTimeMillis();
	private int time;*/
	private double TPS = 20.0D;
	//private List<String> commands;
	//private String message;
	//private double tpslimit;
	//private List<Integer> dc;
	public TpsChecker(SetTpsCommands p){
		plugin = p;
		//commands = plugin.getConfig().getStringList("commands");
		//message = plugin.getConfig().getString("message");
		//tpslimit = plugin.getConfig().getDouble("tpslimit");
		//dc = plugin.getConfig().getIntegerList("downcount");
	}
	@Override
	public void run() {
		try{
			this.TPS = plugin.et.getAverageTPS();
			/*this.time += 1;
			if (this.time == 200) {
				long outmils = System.currentTimeMillis();
				double r = ((outmils - this.mills) / 50.0D - 200.0D) / 10.0D;
				double tick = 20.0D - r;
				BigDecimal bd = new BigDecimal(tick).setScale(2, RoundingMode.HALF_EVEN);
				tick = Double.valueOf(bd.doubleValue()).doubleValue();
				if (tick >= 21.0D)
					tick = 20.0D - (tick - 20.0D);
				else if (tick >= 19.899999999999999D)
					tick = 20.0D;
				this.TPS = tick;
				this.time = 0;
				this.mills = System.currentTimeMillis();
			}*/
			if(this.TPS < plugin.getConfig().getDouble("tpslimit")){
				for(int i = 0;i < plugin.getConfig().getIntegerList("downcount").size() - 1;i++){
				/*for(int i = 0;i < dc.size();i++){*/
					//if(dc.get(i) < 1) break;
					plugin.getServer().broadcastMessage(String.format(plugin.getConfig().getString("message"), plugin.getConfig().getIntegerList("downcount").get(i)));
					TimeUnit.SECONDS.sleep(plugin.getConfig().getIntegerList("downcount").get(i) - plugin.getConfig().getIntegerList("downcount").get(i + 1));
					/*if(i == dc.size() - 1)
						TimeUnit.SECONDS.sleep(dc.get(i));
					else
						TimeUnit.SECONDS.sleep(dc.get(i) - dc.get(i + 1));*/
					for(String c : plugin.getConfig().getStringList("commands")){
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c);
					}
				}
			}
		}catch(Exception e){return;}
	}
}
