import java.util.ArrayList;
import java.util.List;

public class PrettyPrintVisitor extends liteQLBaseVisitor<String> {
    @Override
    public String visitProgram(liteQLParser.ProgramContext ctx){
        StringBuilder sb = new StringBuilder();
        for (liteQLParser.StmtContext stmt : ctx.stmt()) {
            sb.append(visit(stmt)).append("\n\n");
        }
        return sb.toString();
    }

    @Override public String visitDeleteTable(liteQLParser.DeleteTableContext ctx) { 
        //REMOVE TABLE tablename=ID ';';
        return "DROP TABLE IF EXISTS " + ctx.tablename.getText() + ";"; 
    }
	
	@Override public String visitDeleteRow(liteQLParser.DeleteRowContext ctx) { 
        //REMOVE tableSource whereClause ';';
        return "DELETE FROM " + ctx.tableSource().getText() + " " + visit(ctx.whereClause()) + ";";
     }

	@Override public String visitInsert(liteQLParser.InsertContext ctx) { 
        //insert: ADD tableSource assignList  ';';
        AssignListExtractor ir = splitAssignList(ctx.assignList().getText());
        return "INSERT INTO " + ctx.tableSource().getText() + "(" + String.join(",", ir.labels) 
        + ") VALUES (" + String.join(",",ir.values) +  ");";
     }

	@Override public String visitUpdateRow(liteQLParser.UpdateRowContext ctx) { 
        return "UPDATE " + ctx.tableSource().getText() + " SET " + ctx.assignList().getText() + " " + visit(ctx.whereClause()) + ";"; 
     }

	@Override public String visitSelect(liteQLParser.SelectContext ctx) { return visitChildren(ctx); }

	@Override public String visitCreateTable(liteQLParser.CreateTableContext ctx) { return visitChildren(ctx); }
    
    @Override public String visitWhereClause(liteQLParser.WhereClauseContext ctx) { 
        //WITH conjoinedAttrComparison;
        return "WHERE " + visit(ctx.conjoinedAttrComparison());
     }
    @Override public String visitConjoinedAttrComparison(liteQLParser.ConjoinedAttrComparisonContext ctx) {
        //recursively create this by getting left and right
        if (ctx.conjoinedAttrComparison() != null) {
            String left = visit(ctx.attrComparison());
            String right = visit(ctx.conjoinedAttrComparison());
            String op = ctx.conjunction().getText().toUpperCase();

            return left + " " + op + " " + right;
        }
        return visit(ctx.attrComparison());
    }
    @Override public String visitAttrComparison(liteQLParser.AttrComparisonContext ctx) {
        String comparison = getComparisonSymbol(ctx.comparison().getText());
        return ctx.attribute().getText() + " " + comparison + " " + ctx.value().getText();
    }
    public AssignListExtractor splitAssignList(String assignList){
        /*
        assignList: assignmentStmt ',' assignList
        |   assignmentStmt         
        ;
        assignmentStmt: attr=attribute '=' val=value; 
        */
       String[] splitList = assignList.split(",");
       AssignListExtractor ir = new AssignListExtractor();
       for(int i = 0; i< splitList.length; i++){
            String[] splitExpr = splitList[i].split("=");
            ir.labels.add(splitExpr[0]);
            ir.values.add(splitExpr[1]);
       }
       return ir;
    }
    public String getComparisonSymbol(String comparison){
        switch(comparison){
            case "less than":
                return "<";
            case "greater than":
                return ">";
            case "at least":
                return ">=";
            case "at most":
                return "<=";
            case "is":
                return "=";
            case "is not":
                return "!=";
        }
        return comparison;
    }
}