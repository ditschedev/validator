package dev.ditsche.validator.error;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ErrorBagTest {

    @Test
    public void errorBagShouldBeEmptyAfterCreation() {
        ErrorBag bag = new ErrorBag();
        assertThat(bag.isEmpty()).isTrue();
    }

    @Test
    public void errorBagShouldBeEmptyAfterClear() {
        ErrorBag bag = new ErrorBag();
        bag.clear();
        assertThat(bag.isEmpty()).isTrue();

        bag.add("test", "Testmessage");
        bag.clear();
        assertThat(bag.isEmpty()).isTrue();
    }

    @Test
    public void errorBagShouldNotBeEmptyAfterAddingAnError() {
        ErrorBag bag = new ErrorBag();
        bag.add("test", "Testmessage");
        assertThat(bag.isEmpty()).isFalse();
    }

    @Test
    public void errorBagShouldReturnNoErrorsWhenEmpty() {
        ErrorBag bag = new ErrorBag();
        assertThat(bag.getErrors()).isEmpty();
    }

    @Test
    public void errorBagShouldReturnAllErrorsWhenNotEmpty() {
        ErrorBag bag = new ErrorBag();
        bag.add("test", "Testmessage");
        bag.add("test2", "Testmessage 2");
        assertThat(bag.getErrors().size()).isEqualTo(2);
    }

    @Test
    public void errorBagShouldBundleErrorsAfterKey() {
        ErrorBag bag = new ErrorBag();
        bag.add("test", "Testmessage");
        assertThat(bag.getErrors().size()).isEqualTo(1);
        bag.add("test", "Testmessage 2");
        assertThat(bag.getErrors().size()).isEqualTo(1);
        bag.add("test2", "Testmessage 3");
        assertThat(bag.getErrors().size()).isEqualTo(2);
        bag.add("test2", "Testmessage 4");
        assertThat(bag.getErrors().size()).isEqualTo(2);
    }

}