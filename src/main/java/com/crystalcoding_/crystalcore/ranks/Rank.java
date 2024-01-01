package com.crystalcoding_.crystalcore.ranks;

import java.util.ArrayList;
import java.util.List;

public class Rank {
    private String name;
    private String prefix;
    private List<String> permissions;
    private List<String> parents;

    public Rank() {
        this.permissions = new ArrayList<>();
        this.parents = new ArrayList<>();
    }

    public Rank(String name) {
        this();
        this.name = name;
        this.prefix = name;
    }

    public Rank(String name, String prefix) {
        this();
        this.name = name;
        this.prefix = prefix;
    }

    public Rank(String name, String prefix, List<String> permissions) {
        this();
        this.name = name;
        this.prefix = prefix;
        this.permissions = permissions;
    }

    public Rank(String name, String prefix, List<String> permissions, List<String> parents) {
        this();
        this.name = name;
        this.prefix = prefix;
        this.permissions = permissions;
        this.parents = parents;
    }

    // Getters and setters for name, prefix, permissions, and parents

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public List<String> getParents() {
        return parents;
    }

    public void setParents(List<String> parents) {
        this.parents = parents;
    }
}
