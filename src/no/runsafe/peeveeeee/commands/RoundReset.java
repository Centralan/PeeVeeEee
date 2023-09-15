import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ResetRound extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        getLogger().info("ResetRound enabled.");
        getCommand("resetround").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.getWorld().getName().equalsIgnoreCase("world_mobarena")) {
            sender.sendMessage("You are not in the MobArena world.");
            return true;
        }

        // Check if the player is in the MobArena region, replace 'mobarena_region' with the actual region name
        if (!isInMobArenaRegion(player)) {
            sender.sendMessage("You are not in the MobArena region.");
            return true;
        }

        // Implement your round reset logic here
        // For example, you might clear entities, reset player positions, and more

        sender.sendMessage("Round reset!");
        return true;
    }

    private boolean isInMobArenaRegion(Player player) {
        // Implement the logic to check if the player is in the MobArena region
        // You may need to use a plugin like WorldGuard or another region-checking method
        return false; // Return true if the player is in the MobArena region, otherwise false
    }
}
