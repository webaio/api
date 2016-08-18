package io.weba.api.infrastructure.postgres.type;

import io.weba.api.domain.account.AccountId;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;

import java.util.UUID;

public class AccountIdTypeDescriptor extends AbstractTypeDescriptor<AccountId> {
    public static final AccountIdTypeDescriptor INSTANCE = new AccountIdTypeDescriptor();

    public AccountIdTypeDescriptor() {
        super(AccountId.class);
    }

    @Override
    public String toString(AccountId accountId) {
        return accountId.toString();
    }

    @Override
    public AccountId fromString(String string) {
        return new AccountId(AccountId.fromString(string));
    }


    @SuppressWarnings({"unchecked"})
    public <X> X unwrap(AccountId value, Class<X> type, WrapperOptions options) {
        if (value == null) {
            return null;
        }

        if (UUID.class.isAssignableFrom(type)) {
            return (X) value.id;
        }

        throw unknownUnwrap(type);
    }

    public <X> AccountId wrap(X value, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (UUID.class.isInstance(value)) {
            return new AccountId((UUID) value);
        }
        throw unknownWrap(value.getClass());
    }
}
