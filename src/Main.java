import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DirectoryManager dm = new DirectoryManager();
        ActionManager am = new ActionManager();
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            input = scanner.nextLine().strip();
            if (input.equalsIgnoreCase("q")) {
                break;  // Exit the loop when user enters 'q' or 'Q'
            }

            ActionManager.ActionType action = am.getActionTypeFromCmd(input);

            if(!am.shallowValidate(input) || action == null)
                System.out.println("Invalid cmd");
            else{
                String processedInput = input.replaceAll("\\s+", " ").trim();
                System.out.println(processedInput);
                String sourcePath = am.extractSourceOrTargetPath(input, true);
                String targetPath = am.extractSourceOrTargetPath(input, false);

                switch (action){
                    case CREATE -> dm.create(sourcePath);
                    case LIST -> System.out.println(dm.list());
                    case MOVE -> dm.move(sourcePath, targetPath);
                    case DELETE -> dm.delete(sourcePath);
                    default -> System.out.println("Invalid cmd");
                }
            }
        }

        scanner.close();
    }
}