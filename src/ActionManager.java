import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ActionManager {
    public enum ActionType {
        CREATE, LIST, MOVE, DELETE
    }
    public ActionManager(){}

    public boolean shallowValidate(String cmd){
        if(cmd == null || cmd.isEmpty())
            return false;

        ActionType type = getActionTypeFromCmd(cmd);
        if(type == null)
            return false;

        String[] split = cmd.replaceAll("\\s+", " ").trim().split(" ");
        if(type.equals(ActionType.LIST)){
            return split.length == 1;
        }else if(type.equals(ActionType.CREATE)){
            if(split.length != 2)
                return false;
            return validatePath(split[1]);
        }else if(type.equals(ActionType.MOVE)){
            if(split.length != 3)
                return false;
            return validatePath(split[1]) && validatePath(split[2]);
        }else if(type.equals(ActionType.DELETE)){
            if(split.length != 2)
                return false;
            return validatePath(split[1]);
        }
        return false;
    }

    public String extractSourceOrTargetPath(String input, boolean isSource) {
        String[] parts = input.split("\\s+");
        int index = isSource ? 1 : 2;

        if (parts.length >= index + 1) {
            return parts[index].strip();
        } else {
            return "";
        }
    }


    public boolean validatePath(String path){
        Set<Character> badChars = new HashSet<>(Arrays.asList(':', '*','<','>','|','"','\\'));
        String[] split =  path.split("/");
        for(String s : split){
            for(char c : s.toCharArray()){
                if(badChars.contains(c))
                    return false;
            }
        }
        return true;
    }

    public ActionType getActionTypeFromCmd(String cmd){
        String actionType =  cmd.strip().split(" ")[0];
        return switch (actionType) {
            case "CREATE" -> ActionType.CREATE;
            case "LIST" -> ActionType.LIST;
            case "MOVE" -> ActionType.MOVE;
            case "DELETE" -> ActionType.DELETE;
            default -> null;
        };
    }

}
