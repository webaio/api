package io.weba.api.infrastructure.postgres.type;

import io.weba.api.domain.timezone.Timezone;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;

public class TimezoneTypeDescriptor extends AbstractTypeDescriptor<Timezone> {
    public static final TimezoneTypeDescriptor INSTANCE = new TimezoneTypeDescriptor();

    public TimezoneTypeDescriptor() {
        super(Timezone.class);
    }

    @Override
    public String toString(Timezone value) {
        return value.value;
    }

    @Override
    public Timezone fromString(String string) {
        return new Timezone(string);
    }

    @Override
    public <X> X unwrap(Timezone value, Class<X> type, WrapperOptions options) {
        if (value == null) {
            return null;
        }

        if (String.class.isAssignableFrom(type)) {
            return (X) value.value;
        }

        throw unknownUnwrap(type);
    }

    @Override
    public <X> Timezone wrap(X value, WrapperOptions options) {
        if (value == null) {
            return null;
        }

        if (String.class.isInstance(value)) {
            return new Timezone((String) value);
        }
        throw unknownWrap(value.getClass());
    }
}