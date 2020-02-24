package be.cloudway.gramba.nativeimage.builder;

import java.util.function.Supplier;

public class CommandStringBuilder {
    private StringBuilder stringBuilder = new StringBuilder();

    private static final Supplier<Boolean> TRUE_SUPPLIER = () -> true;

    public CommandStringBuilder space () {
        stringBuilder.append(" ");
        return this;
    }

    public CommandStringBuilder and () {
        return append("&&").space();
    }

    public CommandStringBuilder or () {
        return append("||").space();
    }

    public CommandStringBuilder append(String part) {
        return appendNoSpace(part).space();
    }

    public CommandStringBuilder appendNoSpace(String part) {
        return appendOptional(part, TRUE_SUPPLIER);
    }

    public CommandStringBuilder appendOptional(String part, Supplier<Boolean> supplier) {
        if (supplier.get()) {
            stringBuilder.append(part);
        }

        return this;
    }

    public CommandStringBuilder appendOptional(Supplier<String> part, Supplier<Boolean> supplier) {
        if (supplier.get()) {
            stringBuilder.append(part.get());
        }

        return this;
    }

    public CommandStringBuilder close () {
        return closeOptional(TRUE_SUPPLIER).space();
    }

    public CommandStringBuilder closeOptional(Supplier<Boolean> supplier) {
        if (supplier.get()) {
            stringBuilder.append("; ");
        }

        return this;
    }

    public String build () {
        return this.stringBuilder.toString();
    }
}
