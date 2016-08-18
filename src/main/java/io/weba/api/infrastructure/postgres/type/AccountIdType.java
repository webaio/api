package io.weba.api.infrastructure.postgres.type;

import io.weba.api.domain.account.AccountId;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.PostgresUUIDType;


public class AccountIdType extends AbstractSingleColumnStandardBasicType<AccountId> {
    public static final AccountIdType INSTANCE = new AccountIdType();

    public AccountIdType() {
        super(PostgresUUIDType.PostgresUUIDSqlTypeDescriptor.INSTANCE, AccountIdTypeDescriptor.INSTANCE);
    }

    @Override
    protected boolean registerUnderJavaType() {
        return true;
    }

    @Override
    public String getName() {
        return "account_id";
    }
}
