package com.dylibso.chicory.fuzz;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.dylibso.chicory.runtime.Instance;
import com.dylibso.chicory.wasm.Parser;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;

public class SingleReproTest extends TestModule {
    private static final String CHICORY_FUZZ_SEED_KEY = "CHICORY_FUZZ_SEED";
    private static final String CHICORY_FUZZ_TYPES_KEY = "CHICORY_FUZZ_TYPES";

    WasmSmithWrapper smith = new WasmSmithWrapper();

    boolean enableSingleReproducer() {
        return System.getenv(CHICORY_FUZZ_SEED_KEY) != null
                && System.getenv(CHICORY_FUZZ_TYPES_KEY) != null;
    }

    @Test
    @EnabledIf("enableSingleReproducer")
    void singleReproducer() throws Exception {
        var seed = Files.readString(Paths.get(System.getenv(CHICORY_FUZZ_SEED_KEY)));
        var types = InstructionTypes.fromString(System.getenv(CHICORY_FUZZ_TYPES_KEY));
        var targetWasm =
                smith.run(seed.substring(0, Math.min(seed.length(), 32)), "test.wasm", types);

        var module = Parser.parse(targetWasm);
        var instance = Instance.builder(module).withInitialize(true).withStart(false).build();

        testModule(targetWasm, module, instance);
        // Sanity check that the starting function doesn't break
        assertDoesNotThrow(() -> Instance.builder(module).build());
    }
}
