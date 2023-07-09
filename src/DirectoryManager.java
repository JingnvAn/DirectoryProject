import java.util.Map;
import java.util.TreeMap;

public class DirectoryManager {
    private Map<String, TreeNode> dirs;
    public DirectoryManager(){
        this.dirs = new TreeMap<>();
    }

    public boolean create(String path){
        String[] names = path.split("/");

        if(names.length == 1){
            if(!this.dirs.containsKey(names[0])){
                this.dirs.put(names[0], new TreeNode(names[0]));
                return true;
            }
            System.out.printf("Cannot create %s - %s already exists\n", path, path);
            return false;
        }

        TreeNode root = this.dirs.getOrDefault(names[0], new TreeNode(names[0]));
        boolean created = false;
        for(int i=1; i< names.length; i++){
            String curDirName = names[i];
            TreeNode subDirNode = root.findSubDirByName(curDirName);
            if(subDirNode == null && i != names.length-1){
                System.out.printf("Cannot create %s - %s does not exist\n", path, curDirName);
                return false;
            }else if(subDirNode == null){ // allow the last dir to not exist
                root.addSubDir(root, curDirName);
                created = true;
            }else{
                root = subDirNode;
            }
        }
        if(!created)
            System.out.printf("Cannot create %s - %s already exists\n", path, path);
        return true;
    }

    public String list(){
        return toString();
    }

    public boolean move(String sourcePath, String targetPath){
        TreeNode sourceNode = findTargetDirByName(sourcePath.split("/"));
        // targetNode is actually initialized as the second to last node
        // for example, if targetPath = d1/d2/d3, then targetNode points to d2 initially
        TreeNode targetNode = findTargetDirByName(removeLastDirectory(targetPath).split("/"));
        String lastTargetDirName = getLasDirName(targetPath);

        if(sourceNode == null){
            System.out.printf("Cannot move %s - path doesn't exist\n", sourcePath);
            return false;
        }

        // when targetPath has only one dir name
        if(targetPath.equals(lastTargetDirName)){
            targetNode  = this.dirs.computeIfAbsent(targetPath, k -> new TreeNode(targetPath));
        }else if(targetNode == null){ // second to the last target node doesn't exist is not allowed
            System.out.printf("Cannot move %s - %s doesn't exist\n", targetPath, lastTargetDirName);
            return false;
        }else { // after making sure that the target path is valid, move targetNode to be the last node in the path
            if(targetNode.findSubDirByName(lastTargetDirName) == null){
                targetNode.addSubDir(targetNode, lastTargetDirName);
            }
            targetNode = targetNode.findSubDirByName(lastTargetDirName);
        }

        TreeNode originalParent = sourceNode.getParent();
        // cannot move d1/d11/d11 to d1/d11
        if(targetNode.findSubDirByName(sourceNode.getName()) != null){
            System.out.printf("Cannot move %s - %s already exists in the target directory\n", sourcePath, sourceNode.getName());
            return false;
        }

        if(originalParent == null){
            this.dirs.remove(sourceNode.getName());
        }else{
            originalParent.removeSubDir(originalParent, sourceNode);
        }

        targetNode.addSubDir(targetNode, sourceNode);

        return true;
    }

    public boolean delete(String path){
        String[] names = path.split("/");
        String firstDirName = names[0].strip();
        if(names.length == 1){
            if(this.dirs.get(firstDirName) == null){
                System.out.printf("Cannot delete %s - %s does not exist\n", path, firstDirName);
                return false;
            }
            this.dirs.remove(firstDirName);
            return true;
        }

        TreeNode root = this.dirs.getOrDefault(firstDirName, null);
        if(root == null){
            System.out.printf("Cannot delete %s - %s does not exist\n", path, firstDirName);
            return false;
        }

        for(int i=1; i<names.length; i++){
            String curDirName = names[i].strip();
            TreeNode subDirNode = root.findSubDirByName(curDirName);
            if(subDirNode == null){
                System.out.printf("Cannot delete %s - %s does not exist\n", path, curDirName);
                return false;
            }else if(i == names.length-1){
                root.getSubDirMap().remove(curDirName);
            }else{
                root = subDirNode;
            }
        }
        return true;
    }

    private String getLasDirName(String path){
        if(removeLastDirectory(path).equals(path))
            return path;
        else{
            return path.substring(path.lastIndexOf("/")+1);
        }
    }

    private String removeLastDirectory(String path) {
        int lastSlashIndex = path.lastIndexOf("/");
        if (lastSlashIndex != -1) {
            return path.substring(0, lastSlashIndex);
        } else {
            return path;
        }
    }

    private TreeNode findTargetDirByName(String[] path){
        TreeNode targetNode = this.dirs.getOrDefault(path[0].strip(), null);
        for (int i=1; i<path.length; i++) {
            if (targetNode == null)
                return null;
            targetNode = targetNode.findSubDirByName(path[i].strip());
        }
        return targetNode;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(String dir : this.dirs.keySet()){
            sb.append(dirs.get(dir).toString()).append("\n");
        }
        if(sb.length() > 0)
            sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

}
