package rocks.inspectit.ocelot.core.instrumentation.actions.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class InspectitReflectionImplTest {

    private static final String fieldValue = "test";

    private static final Integer methodResult = 0;

    private InspectitReflectionImpl reflection;

    @BeforeEach
    void beforeEach() {
        reflection = new InspectitReflectionImpl();
    }

    @Test
    void shouldReturnHiddenFieldValue() throws Exception {
        DummyClass dummy = new DummyClass();

        Object result = reflection.getFieldValue(DummyClass.class, dummy, "field");

        assertThat(result).isEqualTo(fieldValue);
    }

    @Test
    void shouldReturnHiddenStaticFieldValue() throws Exception {
        Object result = reflection.getFieldValue(DummyClass.class, null, "staticField");

        assertThat(result).isEqualTo(fieldValue);
    }

    @Test
    void shouldThrowExceptionForMissingField() {
        DummyClass dummy = new DummyClass();

        assertThatThrownBy(() -> reflection.getFieldValue(DummyClass.class, dummy, "missing"))
                .isInstanceOf(NoSuchFieldException.class);
    }

    @Test
    void shouldReturnResultOfInvokedMethod() throws Exception {
        DummyClass dummy = new DummyClass();
        String argument = "hello";

        Object result = reflection.invokeMethod(DummyClass.class, dummy, "greet", argument);

        assertThat(result).isEqualTo(argument);
    }

    @Test
    void shouldReturnResultOfInvokedStaticMethod() throws Exception {
        Object result = reflection.invokeMethod(DummyClass.class, null, "zero");

        assertThat(result).isEqualTo(methodResult);
    }

    @Test
    void shouldReturnNullForInvokedVoidMethod() throws Exception {
        DummyClass dummy = new DummyClass();

        Object result = reflection.invokeMethod(DummyClass.class, dummy, "empty");

        assertThat(result).isNull();
    }

    @Test
    void shouldThrowExceptionForMissingMethod() {
        DummyClass dummy = new DummyClass();

        // Wrong name
        assertThatThrownBy(() -> reflection.invokeMethod(DummyClass.class, dummy, "missing"))
                .isInstanceOf(NoSuchMethodException.class);

        // Too many arguments
        assertThatThrownBy(() -> reflection.invokeMethod(DummyClass.class, dummy, "greet", "arg1", "arg2"))
                .isInstanceOf(NoSuchMethodException.class);

        // Wrong argument type
        assertThatThrownBy(() -> reflection.invokeMethod(DummyClass.class, dummy, "greet",0))
                .isInstanceOf(NoSuchMethodException.class);
    }

    static class DummyClass {

        private final String field = fieldValue;

        private final static String staticField = fieldValue;

        private String greet(String name) {
            return name;
        }

        private static int zero() {
            return methodResult;
        }

        private void empty() {}
    }
}
