import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TreeNodeTest {
    @Test
    public void testTreeNodePrint(){
        TreeNode root = buildSimpleTree();
        assertEquals("foods\n fruits\n  apple\n grains", root.toString());
    }

    private TreeNode buildSimpleTree(){
        /**
         * -foods
         *  -fruits
         *   -apple
         *  -grains
         */
        TreeNode root =  new TreeNode("foods");

        root.addSubDir(root,"grains");
        TreeNode fruits = root.addSubDir(root,"fruits");
        fruits.addSubDir(fruits, "apple");

        return root;
    }
}