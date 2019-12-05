package com.unipi.talepis;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;



public class Main {

    public static List<Block> blockchain = new ArrayList<Block>();
    public static int userChoice;
    public static int prefix = 3;

    public static void main(String[] args) {
        boolean quit = false;
        int counter=0;
        new InitialDB();
        DatabaseMng db = new DatabaseMng();







        do {
            Scanner input = new Scanner(System.in);

            /***************************************************/

            System.out.println("Please Choose from these choices");
            System.out.println("-------------------------\n");
            System.out.println("1 - View all Products");
            System.out.println("2 - Add a Product");
            System.out.println("3 - Add multiple products");
            System.out.println("4 - Search for a product");
            System.out.println("5 - Viw statistics of a Product");
            System.out.println("6 - Exit");

            userChoice = input.nextInt();


            switch (userChoice) {
                case 1:
                    System.out.println("\nYou've chosen item #1 - View all Products");
                    break;

                case 2:
                    System.out.println("\nYou've chosen item #2 - Add a Product");
                    Scanner myObj = new Scanner(System.in);  // Create a Scanner object

                    System.out.println("\nPlease Enter the A/A of the Product");
                    int aa = myObj.nextInt();  // Read user input

                    System.out.println("\nPlease Enter the code of the Product");
                    int code = myObj.nextInt();  // Read user input

                    System.out.println("\nPlease Enter the tile of the Product");
                    String title = myObj.next();  // Read user input

                    System.out.println("\nPlease Enter the cost of the Product");
                    int cost = myObj.nextInt();  // Read user input

                    System.out.println("\nPlease Enter the description of the Product");
                    String desc = myObj.next();  // Read user input

                    System.out.println("\nPlease Enter the category of the Product");
                    String categ = myObj.next();  // Read user input

                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    String dtime=dtf.format(now);

                    String data= aa+"#"+code+"#"+title+"#"+dtime+cost+"#"+desc+"#"+categ;


                    long startTime = System.nanoTime();
                    System.out.println("Process Started");

                    newBlockchainNode(data, "0", new Date().getTime());


                    //Transform BlockChain into Json and print it
                    String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
                    System.out.println("\nThe block chain: ");
                    System.out.println(blockchainJson);





                    //Μετατρέπω απο balockchainJson απο συλλογη από Objects Block σε πινακα από objects Block
                    Gson gson = new Gson();
                    Block[] founderArray = gson.fromJson(blockchainJson, Block[].class);

                    for(int i = 0; i< founderArray.length; i++){
                        System.out.println("---------------------------------------------------------");
                        System.out.println("hash: "+founderArray[i].getHash());
                        System.out.println("previousHash: "+founderArray[i].getPreviousHash());
                        System.out.println("data: "+data);
                        System.out.println("timeStamp: "+founderArray[i].timeStamp());
                        System.out.println("nonce: "+founderArray[i].nonce());
                        System.out.println("---------------------------------------------------------");
                    }

                    String hash=founderArray[counter].getHash();
                    String prvhash=founderArray[counter].getPreviousHash();
                    long timeStamp=founderArray[counter].timeStamp();
                    int nonce=founderArray[counter].nonce();




                    // Check for validity
                    System.out.println("\nBlockchain is Valid: " + isChainValid());

                    long endTime = System.nanoTime();
                    long duration = endTime - startTime;
                    System.out.println("Total time ellapsed: " + (float) duration / 1000000000 + " seconds");

                    db.insert(aa,code,title,dtime,cost,desc,categ,hash,prvhash,data,timeStamp,nonce);
                    counter++;
                    break;


                case 3:
                    System.out.println("\nYou've chosen item #3 - Add multiple products");
                    Scanner myObj1 = new Scanner(System.in);  // Create a Scanner object
                    System.out.println("\nPlease Enter how many Products you want to add ");
                    int loop = myObj1.nextInt();  // Read user input
                    if (loop<=0){
                        System.out.println("Illegal value");
                        break;
                    }

                    for (int i=1;i<=loop;i++) {
                        System.out.println("Please Enter the A/A of the Product"+ i);
                        String aa1 = myObj1.next();  // Read user input

                        System.out.println("Please Enter the code of the Product"+i);
                        String code1 = myObj1.next();  // Read user input

                        System.out.println("Please Enter the tile of the Product"+i);
                        String title1 = myObj1.next();  // Read user input

                        System.out.println("Please Enter the cost of the Product"+i);
                        String cost1 = myObj1.next();  // Read user input

                        System.out.println("Please Enter the description of the Product"+i);
                        String desc1 = myObj1.next();  // Read user input

                        System.out.println("Please Enter the category of the Product"+i);
                        String categ1 = myObj1.next();  // Read user input

                        System.out.println("The previous A/A of the previous  Product is " + blockchain.size());


                        String data1 = aa1 + "#" + code1 + "#" + title1 + "#" + cost1 + "#" + desc1 + "#" + categ1;


                        long startTime1 = System.nanoTime();
                        System.out.println("Process Started");

                        newBlockchainNode(data1, "0", new Date().getTime());


                        //Transform BlockChain into Json and print it
                        String blockchainJson2 = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
                        System.out.println("\nThe block chain: ");
                        System.out.println(blockchainJson2);


                        // Check for validity
                        System.out.println("\nBlockchain is Valid: " + isChainValid());

                        long endTime1 = System.nanoTime();
                        long duration1 = endTime1 - startTime1;
                        System.out.println("Total time ellapsed: " + (float) duration1 / 1000000000 + " seconds");
                    }
                    break;

                case 4:
                    System.out.println("\nYou've chosen item #4 - Search for a product");
                    // do something...
                    break;

                case 5:
                    System.out.println("\nYou've chosen item #5 - Viw statistics of a Product");
                    // do something...
                    break;
                case 6:
                    quit = true;
                    break;

                default:
                    System.out.println("\nInvalid choice.");
            }
        } while (!quit);


        System.out.println("Bye-bye!");


    }

    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[prefix]).replace('\0', '0');

        //loop through blockchain to check hashes:
        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);
            //compare registered hash and calculated hash:
            if (!currentBlock.getHash().equals(currentBlock.calculateBlockHash())) {
                System.out.println("Current Hashes not equal");
                return false;
            }
            //compare previous hash and registered previous hash
            if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
            //check if hash is solved
            if (!currentBlock.getHash().substring(0, prefix).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }

    public static void newBlockchainNode(String data, String hash, long date) {
        if (blockchain.isEmpty()) {
            //data=data+"#"+"0";
            Block blockNode = new Block(data, "0", new Date().getTime());
            blockNode.mineBlock(prefix);
            blockchain.add(blockNode);
            System.out.println("Node " + (blockchain.size() - 1) + " created...");
        } else {
            // data = data+"#"+blockchain.size();
            Block blockNode = new Block(data, blockchain.get(blockchain.size() - 1).getHash(), new Date().getTime());
            blockNode.mineBlock(prefix);
            blockchain.add(blockNode);
            System.out.println("Node " + (blockchain.size() - 1) + " created...");
        }
    }
}
