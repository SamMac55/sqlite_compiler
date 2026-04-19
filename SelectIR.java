import java.util.List;
import java.util.ArrayList;
public class SelectIR {
    //basic select
    String primaryTableSource;
    List<String> selectList = new ArrayList<>();
    //limit clause
    int limitInt=-1;//sentinel value placeholder
    //order clause
    String order; //either null, desc or asc
    List<AttributeRef> orderAttributes = new ArrayList<>();
    //group clause
    List<AttributeRef> groupAttributes = new ArrayList<>();
    //join clause
    String joinedTable;
    boolean hasJoin;
    List<String> joinSelectList = new ArrayList<>();
    String joinAttr;
    //where having (similar logic)
    ConjoinedComparison whereCondition;
    ConjoinedComparison havingCondition;
    
}
