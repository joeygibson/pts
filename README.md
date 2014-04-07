# pts

A simple implementation of the Weight Watchers Points Plus algorithm, as described at
[http://en.wikipedia.org/wiki/Weight_Watchers#PointsPlus_.28US.3B_Nov_2010-.29](http://en.wikipedia.org/wiki/Weight_Watchers#PointsPlus_.28US.3B_Nov_2010-.29)

This code is not affiliated with Weight Watchers International in any way.

## Usage
        Usage: pts [options]

        Options:
            -f, --fat FAT          0  Fat grams
            -c, --carbs CARBS      0  Carbohydrate grams
            -b, --fiber FIBER      0  Fiber grams
            -p, --protein PROTEIN  0  Protein grams
            -v                        Be verbose
            -h, --help

## Building
You can just run `lein uberjar` to produce an executable jar file, containing everything necessary. You can then run it with `java -jar`.
If you install the [lein-bin](https://github.com/Raynes/lein-bin) plugin, then running `lein bin` will produce a truly executable jar,
under the name `pts`, that can be run without specifying the Java executable.

## License

Copyright © 2014 Joey Gibson <joey@joeygibson.com>

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
