public class ConjoinedComparison {
        // Leaf node
        public AttrComparison leaf;

        // Interior node
        public ConjoinedComparison left;
        public ConjoinedComparison right;
        public String conjunction; // "AND" or "OR"

        //make leaf
        public ConjoinedComparison(AttrComparison leaf) {
            this.leaf = leaf;
        }

        // make int node
        public ConjoinedComparison(ConjoinedComparison left, String conjunction, ConjoinedComparison right) {
            this.left = left;
            this.conjunction = conjunction;
            this.right = right;
        }

        public boolean isLeaf() { return leaf != null; }
    }