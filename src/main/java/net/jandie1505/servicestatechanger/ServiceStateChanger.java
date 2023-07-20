package net.jandie1505.servicestatechanger;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.BooleanArgument;
import dev.jorel.commandapi.arguments.TextArgument;
import eu.cloudnetservice.driver.inject.InjectionLayer;
import eu.cloudnetservice.modules.bridge.BridgeServiceHelper;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ServiceStateChanger extends JavaPlugin {
    private static ServiceStateChanger plugin;

    @Override
    public void onLoad() {
        plugin = this;

        CommandAPI.onLoad(new CommandAPIBukkitConfig(this));

        new CommandAPICommand("servicestate")
                .withPermission("servicestate.get")
                .executesPlayer((player, args) -> {
                    player.sendMessage("§aThe current service state is §e" + this.getState() + "§a");
                    return this.getStateId();
                })
                .executesConsole((console, args) -> {
                    console.sendMessage("Current service state: " + this.getState());
                    return this.getStateId();
                })
                .executesEntity((entity, args) -> {
                    return this.getStateId();
                })
                .executesCommandBlock((block, args) -> {
                    return this.getStateId();
                })
                .executesProxy((proxy, args) -> {
                    return this.getStateId();
                })
                .withSubcommand(
                        new CommandAPICommand("LOBBY")
                                .withPermission("servicestate.lobby")
                                .executesPlayer((player, args) -> {
                                    this.setState("LOBBY");
                                    player.sendMessage("§aService state was changed to §eLOBBY§a");
                                })
                                .executesConsole((console, args) -> {
                                    this.setState("LOBBY");
                                    console.sendMessage("Service state changed to LOBBY");
                                })
                                .executesEntity((entity, args) -> {
                                    this.setState("LOBBY");
                                })
                                .executesCommandBlock((block, args) -> {
                                    this.setState("LOBBY");
                                })
                                .executesProxy((proxy, args) -> {
                                    this.setState("LOBBY");
                                })
                )
                .withSubcommand(
                        new CommandAPICommand("INGAME")
                                .withPermission("servicestate.ingame")
                                .withArguments(new BooleanArgument("startNewService (default=true)"))
                                .executesPlayer((player, args) -> {
                                    boolean startNewService = (boolean) args.args()[0];
                                    this.setIngame(startNewService);
                                    player.sendMessage("§aService state was changed to §eINGAME§a (Start new service: " + startNewService + "true)");
                                })
                                .executesConsole((console, args) -> {
                                    boolean startNewService = (boolean) args.args()[0];
                                    this.setIngame(startNewService);
                                    console.sendMessage("Service state changed to INGAME" + startNewService + "true)");
                                })
                                .executesEntity((entity, args) -> {
                                    boolean startNewService = (boolean) args.args()[0];
                                    this.setIngame(startNewService);
                                })
                                .executesCommandBlock((block, args) -> {
                                    boolean startNewService = (boolean) args.args()[0];
                                    this.setIngame(startNewService);
                                })
                                .executesProxy((proxy, args) -> {
                                    boolean startNewService = (boolean) args.args()[0];
                                    this.setIngame(startNewService);
                                })
                )
                .withSubcommand(
                        new CommandAPICommand("INGAME")
                                .withPermission("servicestate.ingame")
                                .executesPlayer((player, args) -> {
                                    this.setIngame(true);
                                    player.sendMessage("§aService state was changed to §eINGAME§a");
                                })
                                .executesConsole((console, args) -> {
                                    this.setIngame(true);
                                    console.sendMessage("Service state changed to INGAME");
                                })
                                .executesEntity((entity, args) -> {
                                    this.setIngame(true);
                                })
                                .executesCommandBlock((block, args) -> {
                                    this.setIngame(true);
                                })
                                .executesProxy((proxy, args) -> {
                                    this.setIngame(true);
                                })
                )
                .withSubcommand(
                        new CommandAPICommand("CUSTOM")
                                .withPermission("servicestate.custom")
                                .withArguments(new TextArgument("customServiceState"))
                                .executesPlayer((player, args) -> {
                                    String customServiceState = (String) args.args()[0];
                                    this.setState(customServiceState);
                                    player.sendMessage("§aService state was changed to §e+ " + customServiceState + "§a (=LOBBY)");
                                })
                                .executesConsole((console, args) -> {
                                    String customServiceState = (String) args.args()[0];
                                    this.setState(customServiceState);
                                    console.sendMessage("Service state changed to " + customServiceState + " (=LOBBY)");
                                })
                                .executesEntity((entity, args) -> {
                                    String customServiceState = (String) args.args()[0];
                                    this.setState(customServiceState);
                                })
                                .executesCommandBlock((block, args) -> {
                                    String customServiceState = (String) args.args()[0];
                                    this.setState(customServiceState);
                                })
                                .executesProxy((proxy, args) -> {
                                    String customServiceState = (String) args.args()[0];
                                    this.setState(customServiceState);
                                })
                )
                .register();
    }

    @Override
    public void onEnable() {
        plugin = this;
        CommandAPI.onEnable();

        Bukkit.getLogger().info("ServiceStateChanger v2 by jandie1505 was successfully enabled");
    }

    private BridgeServiceHelper bridgeServiceHelper() {
        return InjectionLayer.ext().instance(BridgeServiceHelper.class);
    }

    public void setState(String serviceState) {
        this.bridgeServiceHelper().state().set(serviceState);
        Bukkit.getLogger().info("Service state updated to: " + serviceState + " (=LOBBY)");
    }

    public void setIngame(boolean startNewService) {
        this.bridgeServiceHelper().changeToIngame(startNewService);
        Bukkit.getLogger().info("Service state updated to INGAME (=INGAME), Autostart new service: " + startNewService);
    }

    public String getState() {
        return this.bridgeServiceHelper().state().get();
    }

    public int getStateId() {
        String state = this.getState();

        if(state.equals("INGAME")) {
            return 1;
        } else {
            return 0;
        }
    }
}
