package io.siuolplex.wood_you_dye.forge;

import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.resource.PathPackResources;
import org.antlr.v4.runtime.misc.NotNull;

import java.nio.file.Path;

// Shamelessly stolen from C+ please forgive me its found here https://github.com/ConsistencyPlus/ConsistencyPlus/blob/1.20/forge/src/main/java/io/github/consistencyplus/consistency_plus/forge/ModFilePackResources.java
// Supposedly based on what Create did.
public class ModFilePackResources extends PathPackResources {
    protected final IModFile modFile;
    protected final String sourcePath;

    public ModFilePackResources(String name, IModFile modFile, String sourcePath) {
        super(name, false, modFile.findResource(sourcePath));
        this.modFile = modFile;
        this.sourcePath = sourcePath;
    }

    @Override
    @NotNull
    protected Path resolve(String... paths) {
        String[] allPaths = new String[paths.length + 1];
        allPaths[0] = sourcePath;
        System.arraycopy(paths, 0, allPaths, 1, paths.length);
        return modFile.findResource(allPaths);
    }
}