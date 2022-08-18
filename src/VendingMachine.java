import java.io.*;
import java.util.*;

public class VendingMachine {

    private static ArrayList<String> partName = new ArrayList<>(); //to keep the part names in order
    private static Map<String,Part> allParts = new HashMap<>();   //each part has it's own items
    private static Queue<Token> tokenBox = new Queue<>();

    public static void Command(String[] args) {

        String fileParts = args[0];
        String fileItems = args[1];
        String fileTokens = args[2];
        String fileTasks = args[3];
        String fileOutput = args[4];

        //read parts file and create appropriate parts
        createParts(fileParts);

        //read items file and put them into their parts stack
        createItems(fileItems);

        //read tokens file and put them into their correct order
        createTokens(fileTokens);

        //perform the tasks
        doTask(fileTasks);

        //create output file and write it
        writeOutput(fileOutput);


    }
    private static void doTask(String fileTasks){

        ArrayList<String> tasks= read(fileTasks);


        for (String currentLine: tasks){

            String task = currentLine.split("\\s")[0];//split the line for any space length

            String[] commandLine = currentLine.split("\\s");


            if (task.equals("BUY")){//if the command is "buy".

                for (int i = 1; i < commandLine.length; i++) {//all parts of command

                    String part = commandLine[i].split(",")[0];
                    int howMany = Integer.parseInt(commandLine[i].split(",")[1]);

                    buyItem(part,howMany);//remove the items from stack of the part according to "howMany"

                    useToken(part,howMany);//use tokens according to "howMany"

                }

            }else if (task.equals("PUT")){

                for (int i = 1; i < commandLine.length ; i++) {

                    String[] command = commandLine[i].split(",");

                    String part = command[0];

                    for (int j = 1; j < command.length; j++) {

                        String id = command[j];

                        Item newItem = new Item(id, part);
                        allParts.get(part).getItems().push(newItem);

                    }
                }
            }
        }
    }

    private static void createParts(String fileParts){

        ArrayList<String> parts = read(fileParts);

        for (String part : parts){

            Part newPart = new Part(part);
            allParts.put(part,newPart);
            partName.add(part);

        }
    }

    private static void createItems(String fileItems){

        ArrayList<String> items = read(fileItems);

        for (String currentLine: items){

            String id = currentLine.split(" ")[0];
            String part = currentLine.split(" ")[1];

            Item newItem = new Item(id,part);

            allParts.get(part).getItems().push(newItem);

        }
    }

    private static void createTokens(String fileTokens){

        ArrayList<String> tokens = read(fileTokens);

        for (String currentLine: tokens){

            String id = currentLine.split(" ")[0];
            String part = currentLine.split(" ")[1];
            int credit = Integer.parseInt(currentLine.split(" ")[2]);

            Token newToken = new Token(id,part,credit);

            tokenBox.enqueue(newToken);
            Collections.sort(tokenBox); // resort the queue


        }
    }

    private static void buyItem(String part, int howMany){

        for (int count = howMany ; count>0 ;count--){

            allParts.get(part).getItems().pop();

        }


    }

    private static void useToken(String part,int howMany){

        ArrayList<Token> removeToken = new ArrayList<>();


        for (Token token: tokenBox){

            if(token.getPart().equals(part)){//if tokens part and our part is matching start using token

                howMany=token.use(tokenBox,part,howMany);


                if (howMany==0){//if no more token needed, break.

                    removeToken.add(token);

                    tokenBox.dequeueAll(removeToken);


                    if (token.getCredit()!=0){

                        tokenBox.enqueue(token);

                    }
                    Collections.sort(tokenBox);
                    return;

                }else {
                    removeToken.add(token);
                }

            }

        }

    }

    private static void writeOutput(String fileOutput){

        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(fileOutput));
            String partStr="";
            String tokenStr="Token Box:\n";


            for (String part: partName){

                partStr+=allParts.get(part).toString()+"\n";

            }
            for (int i = tokenBox.size()-1; i >-1 ; i--) {

                tokenStr+= tokenBox.get(i).toString()+"\n";

            }
            br.write(partStr+tokenStr.trim());
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }








    }

    public static ArrayList<String> read(String fileName){//read any existing file and convert its consistence to string array format

        ArrayList<String> fileStr = new ArrayList<>();

        try {

            BufferedReader br = new BufferedReader(new FileReader(fileName));

            String currentLine;
            while ((currentLine = br.readLine())!= null){

                fileStr.add(currentLine);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileStr;

    }


}
