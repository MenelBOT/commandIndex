package io.github.menelbot.commandIndex.Predicates;

@FunctionalInterface
public interface CommandPredicate<CS, C, S, SA> {
    boolean test(CS sender, C command, S label, SA args);
}
