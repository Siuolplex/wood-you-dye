package io.siuolplex.gremdle

import org.gradle.api.Plugin
import org.gradle.api.Project

class GremdlePlugin : Plugin<Project> {
    override fun apply(project: Project) {

    }
}

// Rough outline of the extention and stuff.
/* build.gradle.kts:
    gremdle {
        loaders = [NEOFORGE, FABRIC] // Probably no support for Lexforge unless I really wanna make something in <1.20
        minecraftVersion = 26.1.2 // Not gonna do multi-version, I feel thats a bit too much for the mods made.
        modID = "gremlib"
        metadata { // Yes this is based on the Cloche metadata. I like what I was seeing to be fair.
            modName = "Gremlib"
            version = 0.1.0 // The loader and mc versions would be appended on.
            description = "The library used for various mods."
            license = "MIT"
            author = person.name("Siuol")
            contributor.add(person.name("Siuol"))
        }

        useGremlib = false // Since Gremlib would be a common library, my thought is that you can include a line to add that.
    }
 */

/* common/build.gradle.kts:
    gremdle {
        common {
            classTweaker = "${modID}.gct" // Most likely would want a custom class tweaking format. My idea is that we could convert it to the other two formats before they consume it.
            mixin = "${modID}.mixin.json"
        }
    }
 */

/* fabric/build.gradle.kts
    gremdle {
        fabric {
            mixin = "${modID}.fabric.mixin.json" // So my idea is that you can define a classtweaker and mixin json for your loader
        }
    }
 */

/*
    gremdle {
        neoforge {
            mixin = "${modID}.neoforge.mixin.json"
        }
    }
 */
