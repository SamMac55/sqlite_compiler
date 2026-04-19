import java.util.ArrayList;
import java.util.List;
public class SelectEmitter {
    public String emit(SelectIR ir) {
        StringBuilder sb = new StringBuilder();
        sb.append("-- SELECT STMT --\n");
        sb.append("SELECT ").append(emitSelectList(ir));
        sb.append("\nFROM ").append(ir.primaryTableSource);

        if (ir.hasJoin)
            sb.append("\nJOIN ").append(ir.joinedTable)
              .append(" ON ").append(ir.primaryTableSource + "." + ir.joinAttr).append(" = " + ir.joinedTable + "." + ir.joinAttr);

        if (ir.whereCondition != null)
            sb.append("\nWHERE ").append(emitComparison(ir.whereCondition));

        if (!ir.groupAttributes.isEmpty())
            sb.append("\nGROUP BY ").append(emitAttributeRefs(ir.groupAttributes));

        if (ir.havingCondition != null)
            sb.append("\nHAVING ").append(emitComparison(ir.havingCondition));

        if (ir.order != null)
            sb.append("\nORDER BY ").append(emitAttributeRefs(ir.orderAttributes))
              .append("desc".equals(ir.order) ? " DESC" : "");

        if (ir.limitInt != -1)
            sb.append("\nLIMIT ").append(ir.limitInt);

        sb.append(";");
        return sb.toString();
    }

    private String emitSelectList(SelectIR ir) {
        //no join condution
        if (!ir.hasJoin)
            return ir.selectList.contains("all") ? "*" : String.join(", ", ir.selectList);

        // join case add aliases
        List<String> cols = new ArrayList<>();
        if (ir.selectList.get(0).equals("all")) {
            cols.add(ir.primaryTableSource + ".*");
        } else {
            for (String col : ir.selectList)
                cols.add(ir.primaryTableSource + "." + col);
        }
        if (ir.joinSelectList.get(0).equals("all")) {
            cols.add(ir.joinedTable + ".*");
        } else {
            for (String col : ir.joinSelectList)
                cols.add(ir.joinedTable + "." + col);
        }
        return String.join(", ", cols);
    }

    //put the attributes in a list to pretty print
    private String emitAttributeRefs(List<AttributeRef> refs) {
         String result = "";
        for (int i = 0; i < refs.size(); i++) {
            result += refs.get(i).toSQL();
            if (i < refs.size() - 1) {
                result += ", ";
            }
        }
        return result;
    }
    //how to write comparisons
    private String emitComparison(ConjoinedComparison node) {
        if (node.isLeaf()) {
            AttrComparison c = node.leaf;
            String rhs = (c.val != null) ? c.val.toSQL() : c.value;
            return c.attribute.toSQL() + " " + c.operator + " " + rhs;
        }
        return emitComparison(node.left) + " " + node.conjunction + " " + emitComparison(node.right);
    }
}