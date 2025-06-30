package gof.command;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

public interface command2 {
    class Config {
        boolean showHidden = false;
        boolean longForm = false;
        boolean showInode = false;
        boolean showHelp = false;

        @Override
        public String toString() {
            return "Config[showHidden: %s, longForm: %s, showInode: %s, showHelp: %s]"
                    .formatted(showHidden, longForm, showInode, showHelp);
        }
    }

    record Command(String name, Consumer<Config> action) {
    }

    class CommandRegistry {
        private final HashMap<String, Command> map = new HashMap<>();
        private final StringBuilder help = new StringBuilder();

        public void registerOptions(List<String> options, String description, Consumer<Config> action) {
            var command = new Command(options.get(0), action);
            options.forEach(option -> map.put(option, command));
            help.append(String.join(", ", options)).append(": ").append(description).append("\n");
        }

        public Command command(String option) {
            return map.get(option);
        }

        public String help() {
            return help.toString();
        }
    }

    static Config config(CommandRegistry registry, List<String> args) {
        var config = new Config();
        var commandSet = new HashSet<String>();
        for (var arg : args) {
            var command = registry.command(arg);
            if (command == null) {
                continue;  // ignore
            }
            if (!commandSet.add(command.name)) {
                throw new IllegalStateException(command.name + " specified twice");
            }
            command.action.accept(config);
        }
        return config;
    }

    static CommandRegistry commandRegistry() {
        var registry = new CommandRegistry();
        registry.registerOptions(List.of("--all", "-a"), "show hidden files", c -> c.showHidden = true);
        registry.registerOptions(List.of("--long", "-l"), "long form", c -> c.longForm = true);
        registry.registerOptions(List.of("--inode", "-i"), "show inodes", c -> c.showInode = true);
        registry.registerOptions(List.of("--help", "-h"), "show this help", c -> c.showHelp = true);
        return registry;
    }

    static void main(String[] args) {
        args = new String[]{"--all", "foo", "-i", "--help"}; // DEBUG
        var registry = commandRegistry();
        var config = config(registry, List.of(args));
        System.out.println(config);
        if (config.showHelp) {
            System.out.println(registry.help());
        }
    }
}
