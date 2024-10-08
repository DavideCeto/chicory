package com.dylibso.chicory.aot;

import static java.lang.invoke.MethodHandles.lookup;

import com.dylibso.chicory.runtime.Instance;
import com.dylibso.chicory.wasm.types.Value;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

final class HostFunctionInvoker {
    private static final MethodHandle HANDLE;

    static {
        try {
            HANDLE =
                    lookup().unreflect(
                                    HostFunctionInvoker.class.getMethod(
                                            "invoke", Instance.class, int.class, Value[].class));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new LinkageError(e.getMessage(), e);
        }
    }

    private HostFunctionInvoker() {}

    public static MethodHandle handleFor(Instance instance, int funcId) {
        return MethodHandles.insertArguments(HANDLE, 0, instance, funcId);
    }

    public static Value[] invoke(Instance instance, int funcId, Value[] args) {
        return instance.callHostFunction(funcId, args);
    }
}
