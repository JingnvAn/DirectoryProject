import java.util.Map;
import java.util.TreeMap;

public class TreeNode {
    private String name;
    private TreeNode parent;
    private final Map<String, TreeNode> subDirMap;
    public TreeNode(String name){
        this.name = name;
        this.parent = null;
        this.subDirMap = new TreeMap<>();
    }

    public String getName(){ return this.name; }
    public Map<String, TreeNode> getSubDirMap() { return this.subDirMap; }

    public void setName(String name){
        this.name = name;
    }

    public TreeNode getParent(){ return this.parent; }

    public void setParent(TreeNode parent) { this.parent = parent; }

    public void removeSubDir(TreeNode parent, TreeNode sub){
        if(parent.findSubDirByName(sub.getName()) != null){
            parent.getSubDirMap().remove(sub.getName());
            sub.parent = null;
        }
    }

    public TreeNode addSubDir(TreeNode parent, TreeNode cur){
        if(this.subDirMap.containsKey(cur.getName())){
            return this.subDirMap.get(cur.getName());
        }

        this.subDirMap.put(cur.getName(), cur);
        cur.setParent(parent);
        return cur;
    }
    public TreeNode addSubDir(TreeNode parent, String sub){
        if(this.subDirMap.containsKey(sub)){
            return this.subDirMap.get(sub);
        }

        TreeNode newDir = new TreeNode(sub);
        newDir.setParent(parent);
        this.subDirMap.put(sub, newDir);
        return newDir;
    }

    public TreeNode findSubDirByName(String name){
        return this.subDirMap.getOrDefault(name, null);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        printHelper(this, 0, sb);
        if(sb.charAt(sb.length()-1) == '\n')
            sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    private void printHelper(TreeNode cur, int depth, StringBuilder path){
        path.append(" ".repeat(Math.max(0, depth)));

        path.append(cur.name).append("\n");
        for(String next : cur.subDirMap.keySet()){
            printHelper(cur.subDirMap.get(next), depth+1, path);
        }
    }
}
