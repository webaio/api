package io.weba.api.infrastructure.postgres.type;

import io.weba.api.domain.user.UserId;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.PostgresUUIDType;


public class UserIdType extends AbstractSingleColumnStandardBasicType<UserId> {
    public static final UserIdType INSTANCE = new UserIdType();

    public UserIdType() {
        super(PostgresUUIDType.PostgresUUIDSqlTypeDescriptor.INSTANCE, UserIdTypeDescriptor.INSTANCE);
    }

    @Override
    protected boolean registerUnderJavaType() {
        return true;
    }

    @Override
    public String getName() {
        return "user_id";
    }
}
