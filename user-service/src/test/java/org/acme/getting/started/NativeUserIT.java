package org.acme.getting.started;

import io.quarkus.test.junit.NativeImageTest;

@NativeImageTest
public class NativeUserIT extends UserResourceTest {

    // Execute the same tests but in native mode.
}