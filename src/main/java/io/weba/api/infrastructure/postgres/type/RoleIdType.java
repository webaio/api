package io.weba.api.infrastructure.postgres.type;

import io.weba.api.domain.role.RoleId;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.PostgresUUIDType;


public class RoleIdType extends AbstractSingleColumnStandardBasicType<RoleId> {
    public static final RoleIdType INSTANCE = new RoleIdType();

    public RoleIdType() {
        super(PostgresUUIDType.PostgresUUIDSqlTypeDescriptor.INSTANCE, RoleIdTypeDescriptor.INSTANCE);
    }

    @Override
    protected boolean registerUnderJavaType() {
        return true;
    }

    @Override
    public String getName() {
        return "role_id";
    }
}
