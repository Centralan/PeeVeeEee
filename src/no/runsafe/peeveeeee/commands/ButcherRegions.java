import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

public class ButcherRegions extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        getLogger().info("ButcherRegions enabled.");
        getCommand("butchermobs").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("butchermobs")) {
            if (args.length != 3) {
                sender.sendMessage("Usage: /butchermobs <world> <region>");
                return true;
            }

            String worldName = args[0];
            String regionName = args[1];

            // Get the WorldGuard plugin instance
            WorldGuardPlugin worldGuard = getWorldGuard();

            if (worldGuard == null) {
                sender.sendMessage("WorldGuard is not installed!");
                return true;
            }

            // Get the specified world
            World world = Bukkit.getWorld(worldName);

            if (world == null) {
                sender.sendMessage("World '" + worldName + "' not found!");
                return true;
            }

            // Check if the specified region exists
            ApplicableRegionSet regions = worldGuard.getRegionManager(world).getApplicableRegions(Bukkit.getPlayer(sender.getName()).getLocation());

            if (!regions.allows(DefaultFlag.BUTCHER)) {
                sender.sendMessage("Region '" + regionName + "' does not allow butchering mobs!");
                return true;
            }

            // Butcher all mobs in the specified region
            int mobCount = 0;
            for (Entity entity : world.getEntities()) {
                if (regions.testState(null, DefaultFlag.BUTCHER, State.DENY)) {
                    if (entity.getType().name().endsWith("MINECART")) {
                        // Exclude minecarts from being butchered
                        continue;
                    }
                    entity.remove();
                    mobCount++;
                }
            }

            sender.sendMessage("Butchered " + mobCount + " mobs in region '" + regionName + "' of world '" + worldName + "'.");
            return true;
        }
        return false;
    }

    private WorldGuardPlugin getWorldGuard() {
        // Check if the WorldGuard plugin is installed and return it
        return (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
    }
}

