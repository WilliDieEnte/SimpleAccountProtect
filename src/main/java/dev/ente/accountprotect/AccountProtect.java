package dev.ente.accountprotect;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

import java.io.File;
import java.io.IOException;

public class AccountProtect extends Plugin implements Listener {
    private String allowedIPAddress;

    @Override
    public void onEnable() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdir();
            }
            File configFile = new File(getDataFolder(), "config.yml");
            Configuration config;
            if (!configFile.exists()) {
                configFile.createNewFile();
                config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
                config.set("allowed-ip-address", "127.0.0.1");
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, configFile);
            } else {
                config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
            }
            allowedIPAddress = config.getString("allowed-ip-address");
        } catch (IOException e) {
            getLogger().severe(ChatColor.RED + "Failed to load configuration file!");
            e.printStackTrace();
        }
        getProxy().getPluginManager().registerListener(this, this);
        getLogger().info("WilliDieEnte or mcprotection are not liable for any bugs or issues with this plugin.");
        getLogger().info("Use at your own risk!");
        getLogger().info("AccountProtect has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("AccountProtect has been disabled.");
        getLogger().info("Made in 5 Minutes by WilliDieEnte#0308");
        getLogger().info("https://ente.dev");
    }

    @EventHandler
    public void onPlayerLogin(LoginEvent event) {
        String username = event.getConnection().getName();
        String ipAddress = event.getConnection().getAddress().getAddress().getHostAddress();

        if (!username.equalsIgnoreCase("_Gr1ZzLy_") || !ipAddress.equalsIgnoreCase(allowedIPAddress)) {
            event.setCancelled(true);
            event.setCancelReason(new TextComponent(ChatColor.RED + "You are not allowed to join from this IP address."));
        }
    }
}