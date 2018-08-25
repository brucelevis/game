package com.nemo.common.persist;

public interface PersistFactory {
    String name();

    int dataType();

    String createInsertSql();

    String createUpdateSql();

    String createDeleteSql();

    Object[] createInsertParameters(Persistable var1);

    Object[] createUpdateParameters(Persistable var1);

    Object[] createDeleteParameters(Persistable var1);

    long taskPeriod();
}
