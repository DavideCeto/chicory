import com.dylibso.chicory.runtime.HostFunction;
import com.dylibso.chicory.runtime.Instance;
import com.dylibso.chicory.wasm.types.Value;
import com.dylibso.chicory.wasm.types.ValueType;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("com.dylibso.chicory.function.processor.FunctionProcessor")
public final class NoPackage_ModuleFactory {

    private Nested_ModuleFactory() {}

    public static HostFunction[] toHostFunctions(NoPackage functions) {
        return new HostFunction[] {
            new HostFunction(
                    (Instance instance, Value... args) -> {
                        functions.print(instance.memory(), args[0].asInt(), args[1].asInt());
                        return null;
                    },
                    "nopackage",
                    "print",
                    List.of(ValueType.I32, ValueType.I32),
                    List.of()
            ),
            new HostFunction(
                    (Instance instance, Value... args) -> {
                        functions.exit();
                        return null;
                    },
                    "nopackage",
                    "exit",
                    List.of(),
                    List.of()
            ),
        };
    }
}
