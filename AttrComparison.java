public class AttrComparison {
    //how to represent attribute comparisons
    public AttributeRef attribute;
    public String operator;  
    public String value;     
    public AttributeRef val;
    public AttrComparison(AttributeRef attribute, String operator, String value) {
        this.attribute = attribute;
        this.operator = operator;
        this.value = value;
        this.val=null;
    }
    public AttrComparison(AttributeRef attribute, String operator, AttributeRef value) {
        this.attribute= attribute;
        this.operator= operator;
        this.val= value;
        this.value=null;
    }
}