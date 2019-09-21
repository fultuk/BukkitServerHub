package de.panamo.server.hub.player;


import com.destroystokyo.paper.MaterialTags;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class JumpAndRun {

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private static final List<Material> TERRACOTTA_MATERIALS =
            new ArrayList<>(MaterialTags.TERRACOTTA.not(Material.TERRACOTTA).notEndsWith("GLAZED_TERRACOTTA").getValues());
    private static final List<Material> GLASS_MATERIALS
            = new ArrayList<>(MaterialTags.STAINED_GLASS.getValues());

    private static final int JUMP_AND_RUN_RANGE = 20;
    private static final int JUMP_AND_RUN_HEIGHT = 15;


    private boolean active;
    private Location currentBlockLocation;
    private Location nextBlockLocation;

    private Material reachedBlockMaterial;
    private Material nextBlockMaterial;

    public void start(Location entryLocation) {
        this.active = true;

        var materialIndex = RANDOM.nextInt(TERRACOTTA_MATERIALS.size() - 1);
        this.reachedBlockMaterial = TERRACOTTA_MATERIALS.get(materialIndex);
        this.nextBlockMaterial = GLASS_MATERIALS.get(materialIndex);

        this.currentBlockLocation = this.findStartBlock(entryLocation);
        this.currentBlockLocation.getBlock().setType(this.reachedBlockMaterial);

        this.nextBlockLocation = this.findNextBlock();
        this.nextBlockLocation.getBlock().setType(this.nextBlockMaterial);
    }

    public void nextBlockReached() {
        var lastBlockLocation = this.currentBlockLocation;
        var newCurrentBlockLocation = this.nextBlockLocation;

        lastBlockLocation.getBlock().setType(Material.AIR);
        newCurrentBlockLocation.getBlock().setType(this.reachedBlockMaterial);

        this.currentBlockLocation = newCurrentBlockLocation;

        this.nextBlockLocation = this.findNextBlock();
        this.nextBlockLocation.getBlock().setType(this.nextBlockMaterial);
    }

    /**
     * Markes this JumpAndRun as failed and removes the blocks
     */
    public void failed() {
        this.active = false;

        this.currentBlockLocation.getBlock().setType(Material.AIR);
        this.nextBlockLocation.getBlock().setType(Material.AIR);
    }

    /**
     * Finds an empty start block for the jumpAndRun within the given range
     *
     * @param entryLocation the location the player is on when starting the jumpAndRun
     * @return the location of the start block
     */
    private Location findStartBlock(Location entryLocation) {
        var x = RANDOM.nextInt(-JUMP_AND_RUN_RANGE, JUMP_AND_RUN_RANGE + 1);
        var z = RANDOM.nextInt(-JUMP_AND_RUN_RANGE, JUMP_AND_RUN_RANGE + 1);

        var startLocation = entryLocation.clone().add(x, JUMP_AND_RUN_HEIGHT, z);

        if (!startLocation.getBlock().getType().equals(Material.AIR)) {
            return this.findStartBlock(entryLocation);
        }

        return startLocation;
    }

    /**
     * Finds an empty next block for the jumpAndRun within the given range
     *
     * @return the location of the next block
     */
    private Location findNextBlock() {
        var y = RANDOM.nextInt(1);

        //TODO: find good block in range of the jump and run
        return this.currentBlockLocation.clone().add(3, y, 2);
    }


    public boolean isActive() {
        return active;
    }

    public Location getCurrentBlockLocation() {
        return currentBlockLocation;
    }

    public Location getNextBlockLocation() {
        return nextBlockLocation;
    }

}
