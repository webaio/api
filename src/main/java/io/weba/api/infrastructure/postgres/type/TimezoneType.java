package io.weba.api.infrastructure.postgres.type;

import io.weba.api.domain.timezone.Timezone;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

public class TimezoneType extends AbstractSingleColumnStandardBasicType<Timezone> {

    public TimezoneType() {
        super(VarcharTypeDescriptor.INSTANCE, TimezoneTypeDescriptor.INSTANCE);
    }

    @Override
    protected boolean registerUnderJavaType() {
        return true;
    }

    @Override
    public String getName() {
        return "timezone";
    }
}
