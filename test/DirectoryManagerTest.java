import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DirectoryManagerTest {

    public final String simpleDirStr = "dir1\n dir11\ndir2\n dir21\n  dir211\n dir22";
    @Test
    public void testCreate_success(){
        assertEquals(simpleBuild().toString(), simpleDirStr);
    }

    public DirectoryManager simpleBuild(){
        DirectoryManager dm = new DirectoryManager();
        /**
         * dir1
         *  dir11
         * dir2
         *  dir21
         *   dir211
         *  dir22
         */
        dm.create("dir1");
        dm.create("dir2");
        dm.create("dir1/dir11");
        dm.create("dir2/dir21");
        dm.create("dir2/dir21/dir211");
        dm.create("dir2/dir22");
        return dm;
    }

    public DirectoryManager buildGivenExample(){
        DirectoryManager dm = new DirectoryManager();
        dm.create("fruits");
        dm.create("vegetables");
        dm.create("grains");
        dm.create("fruits/apples");
        dm.create("fruits/apples/fuji");
        dm.create("grains/squash");
        dm.create("foods");
        dm.move("grains", "foods");
        dm.move("fruits", "foods");
        dm.move("vegetables", "foods");

        return dm;
    }

    @Test
    public void testDelete_fail(){
        DirectoryManager dm = buildGivenExample();
        String ori = dm.toString();
        dm.delete("fruits/apples");
        assertEquals(ori, dm.toString());
    }

    @Test
    public void testCreate_fail(){
        DirectoryManager dm = simpleBuild();
        assertFalse(dm.create("dir1/dir21/dir3"));
        assertEquals(dm.toString(), simpleDirStr);

        //create duplicate
        dm.create("dir1");
        assertEquals(dm.toString(), simpleDirStr);
    }

    @Test
    public void testDelete_success(){
        DirectoryManager dm = simpleBuild();
        //delete leaf node
        dm.delete("dir2/dir21/dir211");
        assertEquals(dm.toString(), "dir1\n dir11\ndir2\n dir21\n dir22");

        //delete node that has child but no parent
        dm = simpleBuild();
        dm.delete("dir1");
        assertEquals(dm.toString(), "dir2\n" +
                " dir21\n" +
                "  dir211\n" +
                " dir22");

        //delete node that has parent and child
        dm = simpleBuild();
        dm.delete("dir2/dir21");
        assertEquals(dm.toString(), "dir1\n" +
                " dir11\n" +
                "dir2\n" +
                " dir22");
    }

    @Test
    public void testMove_success(){
        DirectoryManager dm = simpleBuild();
        /**
         * dir1             dir1
         *  dir11            dir11
         * dir2       ->    dir2
         *  dir21            dir21
         *   dir211          dir22
         *  dir22            dir23
         *                    dir211
         */
        // All but the last dir exists in the targetPath.
        // Create a new dir for the last targetPath, then move the source path dir over
        dm.move("dir2/dir21/dir211", "dir2/dir23");
        assertEquals(dm.toString(), "dir1\n" +
                " dir11\n" +
                "dir2\n" +
                " dir21\n" +
                " dir22\n" +
                " dir23\n" +
                "  dir211");


        /**
         * dir1             dir1
         *  *dir11*          dir2
         * dir2       ->     dir21
         *  dir21            dir22
         *   dir211         dir3
         *  dir22            *dir211*
         */
        // target path is a single level dir, and it doesn't exist yet.
        // create it first then move
        dm = simpleBuild();
        dm.move("dir1/dir11", "dir3");
        assertEquals(dm.toString(), "dir1\n" +
                "dir2\n" +
                " dir21\n" +
                "  dir211\n" +
                " dir22\n" +
                "dir3\n" +
                " dir11");

        dm = simpleBuild();
        //source node has child
        dm.move("dir1", "dir2/dir22");
        assertEquals(dm.toString(), "dir2\n" +
                " dir21\n" +
                "  dir211\n" +
                " dir22\n" +
                "  dir1\n" +
                "   dir11");

        dm = simpleBuild();
        String before = dm.toString();
        // source node doesn't exist
        dm.move("dir1/dir333", "dir2/dir22");
        assertEquals(before, dm.toString());

        dm = simpleBuild();
        //move leaf node, both path nodes exist
        dm.move("dir1/dir11", "dir2/dir22");
        assertEquals(dm.toString(), "dir1\n" +
                "dir2\n" +
                " dir21\n" +
                "  dir211\n" +
                " dir22\n" +
                "  dir11");
    }

    @Test
    public void testCreateMove_fail(){
        DirectoryManager dm = simpleBuild();
        //create a node that has the same name as its parent
        dm.create("dir1/dir11/dir11");
        assertEquals(dm.toString(), "dir1\n" +
                " dir11\n" +
                "  dir11\n" +
                "dir2\n" +
                " dir21\n" +
                "  dir211\n" +
                " dir22");
        //move this node to be ont the same level of the parent - should not success
        assertFalse(dm.move("dir1/dir11/dir11", "dir1/dir11"));
    }

    @Test
    public void testMove_fail(){
        DirectoryManager dm = simpleBuild();
        String original = dm.toString();
        // dir1/dir12 is a invalid path
        dm.move("dir1/dir12", "dir2/dir22");
        assertEquals(original, dm.toString());

        dm = simpleBuild();
        dm.move("dir1/dir11", "dir2/dir5/dir211");
        assertEquals(original, dm.toString());

    }
}
