# Synchronous Traverse implementation of the [Routes](https://github.com/MoodMinds/routes) SPI

This implementation of the [Routes SPI](https://github.com/MoodMinds/routes) offers support for synchronous execution traversal
and utilizes [Traverse Streams Traversable](https://github.com/MoodMinds/traverse-streams-traversable) for this. It serves
as an ideal extension-base implementation of the [Routes SPI](https://github.com/MoodMinds/routes) or as a client library
when certain logic cannot be adequately expressed using the Java 8 Streams API. It excels in scenarios where you need to allow
declaring checked exceptions in functions, implement exception handling, conditionals, and especially when you seek graceful,
reusable stream definitions.

## Understanding the Core

The materialized execution representation of this [Routes](https://github.com/MoodMinds/routes) implementation is `Traversable`.

```java
import org.moodminds.route.Stream;
import org.moodminds.route.Stream2;
import org.moodminds.route.traverse.Routes;

import java.io.IOException;
import java.util.logging.Logger;

import static java.lang.System.getenv;

class Sample {

    static Logger LOG = Logger.getLogger(Sample.class.getName());

    static final Stream<String, RuntimeException> INTEGERS = $ -> $.source("1", "2", "3");

    static final Stream2<Boolean, String, String, Exception> STREAMING = ($, bool, str) -> $
            .either(bool, () -> $
                .source("a", "b", "c"))
            .option(str, String::isEmpty, INTEGERS)
            .option(str, "mm"::equals, () -> $
                .supply(() -> getenv("VAR1")))
            .option(str, "WW"::equals, () -> $
                .supply(() -> {
                    throw new IOException("Something went wrong");
                }))
            .option(() -> $
                .expect(str));

    // refer to STREAMING emitting Exception inside
    static final Stream2<Boolean, String, String, RuntimeException> CATCHING = ($, bool, str) -> $
            .stream(STREAMING, bool, str)
                .caught(Exception.class, ex -> $
                    .supply(ex, Exception::getMessage))
                .caught(IOException.class, ioEx -> $
                    .supply(ioEx, RuntimeException::new, $::except))
                .caught(IllegalStateException.class, sEx -> $
                    .stream(INTEGERS))
                .caught(RuntimeException.class, $::except);


    // has to declare throws Exception because STREAMING declares it
    public void traverse1() throws Exception {
        new Routes().stream(STREAMING, false, "ww") // materialize to Traversable<String, Exception>
            .traverse(t -> t.each(s -> LOG.info(s)));
    }

    public void traverse2() {
        new Routes().stream(CATCHING, true, "WW") // materialize to Traversable<String, RuntimeException>
            .traverse(t -> t.each(s -> LOG.info(s)));
    }
}
```

## Maven configuration

Artifacts can be found on [Maven Central](https://search.maven.org/) after publication.

```xml
<dependency>
    <groupId>org.moodminds.routes</groupId>
    <artifactId>routes-traverse</artifactId>
    <version>${version}</version>
</dependency>
```

## Building from Source

You may need to build from source to use **Routes Traverse** (until it is in Maven Central) with Maven and JDK 9 at least.

## License
This project is going to be released under version 2.0 of the [Apache License][l].

[l]: https://www.apache.org/licenses/LICENSE-2.0