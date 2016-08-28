package io.weba.api.infrastructure.postgres.type;

import io.weba.api.domain.user.UserId;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;

import java.util.UUID;

public class UserIdTypeDescriptor extends AbstractTypeDescriptor<UserId> {
    public static final UserIdTypeDescriptor INSTANCE = new UserIdTypeDescriptor();

    public UserIdTypeDescriptor() {
        super(UserId.class);
    }

    @Override
    public String toString(UserId userId) {
        return userId.toString();
    }

    @Override
    public UserId fromString(String string) {
        return new UserId(UserId.fromString(string));
    }


    @SuppressWarnings({"unchecked"})
    public <X> X unwrap(UserId value, Class<X> type, WrapperOptions options) {
        if (value == null) {
            return null;
        }

        if (UUID.class.isAssignableFrom(type)) {
            return (X) value.id;
        }

        throw unknownUnwrap(type);
    }

    public <X> UserId wrap(X value, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (UUID.class.isInstance(value)) {
            return new UserId((UUID) value);
        }
        throw unknownWrap(value.getClass());
    }
}
