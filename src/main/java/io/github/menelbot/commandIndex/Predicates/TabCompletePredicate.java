package io.github.menelbot.commandIndex.Predicates;

import java.util.List;

@FunctionalInterface
public interface TabCompletePredicate<CS, C, S, SA> {
    List<String> test(CS sender, C command, S label, SA args);
}
