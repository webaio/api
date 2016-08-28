package io.weba.api.infrastructure.postgres.type;

import io.weba.api.domain.role.RoleId;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;

import java.util.UUID;

public class RoleIdTypeDescriptor extends AbstractTypeDescriptor<RoleId> {
    public static final RoleIdTypeDescriptor INSTANCE = new RoleIdTypeDescriptor();

    public RoleIdTypeDescriptor() {
        super(RoleId.class);
    }

    @Override
    public String toString(RoleId RoleId) {
        return RoleId.toString();
    }

    @Override
    public RoleId fromString(String string) {
        return new RoleId(RoleId.fromString(string));
    }


    @SuppressWarnings({"unchecked"})
    public <X> X unwrap(RoleId value, Class<X> type, WrapperOptions options) {
        if (value == null) {
            return null;
        }

        if (UUID.class.isAssignableFrom(type)) {
            return (X) value.id;
        }

        throw unknownUnwrap(type);
    }

    public <X> RoleId wrap(X value, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (UUID.class.isInstance(value)) {
            return new RoleId((UUID) value);
        }
        throw unknownWrap(value.getClass());
    }
}
