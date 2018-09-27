package com.nemo.log;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;

@Getter
@Setter
public class ColumnDesc {
    private Method readMethod;
    private String name;
    private String type;
    private int size;
    private boolean allowNull;
    private boolean autoIncrement;
    private String commit;

    ColumnDesc() {
    }

    public String toDDL() {
        String ddl = "`" + this.name + "`\t" + this.getFieldType() + this.getNullable() + this.getAutoIncrementable() + this.getCommitStr();
        return ddl;
    }

    private String getFieldType() {
        return !this.type.equalsIgnoreCase("text") && !this.type.equalsIgnoreCase("longtext") && !this.type.equalsIgnoreCase("blob") ? this.type + "(" + this.size + ")" : this.type;
    }

    private String getNullable() {
        return this.allowNull ? "" : "\tnot null";
    }

    private String getAutoIncrementable() {
        return this.autoIncrement ? "\tAUTO_INCREMENT" : "";
    }

    private String getCommitStr() {
        return this.commit.equals("") ? "" : "\tCOMMENT '" + this.commit + "'";
    }
}
