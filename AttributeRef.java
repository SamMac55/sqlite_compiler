public class AttributeRef {
    //how to repreesnt attribute references (aliasing when necessary)
    public String tableName;   // null if no alias necessary
    public String columnName;

    public AttributeRef(String columnName) {
        this.columnName = columnName;
    }

    public AttributeRef(String tableName, String columnName) {
        this.tableName  = tableName;
        this.columnName = columnName;
    }

    public String toSQL() {
        return tableName != null ? tableName + "." + columnName : columnName;
    }

    @Override public String toString() { return toSQL(); }
}
