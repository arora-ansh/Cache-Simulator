//CO ENDSEM ASSIGNMENT
//Name: Ansh Arora
//Roll No: 2019022
//Section: A
//Group: 3

import java.util.*;
import java.lang.Math;
import java.lang.*;

public class AnshArora_2019022_FinalAssignment{

    public static boolean isPowerOfTwo(int n){ 
    if(n==0) 
        return false; 
    return (int)(Math.ceil((Math.log(n) / Math.log(2)))) ==  (int)(Math.floor(((Math.log(n) / Math.log(2))))); 
    } 

	public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Size of cache in KB: ");
        int S = in.nextInt()*1024; //Size is 16KB
        System.out.print("Enter the size of one block in Bytes: ");
        int B = in.nextInt(); //1 block size is 32 bytes
        int CL = S/B; //Number of blocks = cache lines = 2^14/2^5=2^9 = 512;
        int index_bits =(int)(Math.log(CL)/Math.log(2));//Number of buts required for index
        int block_bits =(int)(Math.log(B)/Math.log(2));//Number of bits required for block

        int cache_choice; //1=FA Cache 2=DM Cache 3=n-Way Set Associative Cache


    if(isPowerOfTwo(S) && isPowerOfTwo(B)){
        System.out.println();
        System.out.println("THE CACHE SIMULATOR");
        System.out.println();
        System.out.println("32-bit System Cache details -> Size = "+S/1024+"KB  Single Block Size = "+B+" Bytes  Number of Cache Lines = " + CL);
        System.out.println();
        System.out.println("1. Associative Memory Cache (Fully Associative Cache)");
        System.out.println("2. Direct Mapping Cache");
        System.out.println("3. n-Way Set Associative Memory Cache (8-way)");
        System.out.print("Choice: ");
        cache_choice = in.nextInt();//Takes in cache choice

        if(cache_choice==1){ //CODE FOR FULLY ASSOCIATIVE CACHE
            facache L1 = new facache(CL,block_bits); //Cache L1 is intitalized
            int choice = 1;
            System.out.println("Fully Associative Cache Simulator");
            while(choice==1){
                System.out.println("Enter Instruction(<read/write> <32 bit instruction> <data>): ");
                in.nextLine();
                String input = in.nextLine(); //Inputs data in the given format
                String[] input_data = input.split(" ");
                if(input_data[1].length()==32){
                int index = L1.lookup(input_data[1]); //Looks up for given tag in the L1 cache's blocks
                if(input_data[0].equals("read")){//In case of read command, simply reads out the prexisting block
                    if(index!=-1){
                        System.out.println(L1.read(index));
                    }
                }
                else if(input_data[0].equals("write")){//Two cases in write, one if cache hit one if cache miss
                    if(index>-1){//In case of cache hit, prexisting data is overwritten using rewrite function
                        L1.rewrite(index,input_data[2]);
                    }
                    else{
                        if(L1.occupied<CL){//If space empty and cache miss, new data written in
                            L1.new_write(input_data[1],input_data[2]);
                        }
                        else{//If space absent and cache miss, FIFO eviction followed by a new data writing
                            L1.evict();
                            L1.new_write(input_data[1],input_data[2]);
                        }
                    }
                }
                }
                else{
                    System.out.println("Invalid!");
                }
                int c = 0;
                System.out.print("Do you want to print out whole cache?(Yes=1 No=2): ");//Choice to print out the cache
                c = in.nextInt();
                if(c==1){
                    L1.printcache();
                }
                System.out.print("More instructions after this?(Yes=1 No=2): ");//Choice for more instructions
                choice = in.nextInt();
            }
        }
        //Basic skeletal system for instruction manipulation remains the same for all
        else if(cache_choice==2){
            dmcache L1 = new dmcache(CL,block_bits,index_bits); //Cache L1 is intitalized
            int choice = 1;
            System.out.println("Direct Mapping Cache Simulator");
            while(choice==1){
                System.out.println("Enter Instruction(<read/write> <32 bit instruction> <data>): ");
                in.nextLine();
                String input = in.nextLine(); //Inputs data in the given format
                String[] input_data = input.split(" ");
                if(input_data[1].length()==32){
                int index = L1.lookup(input_data[1]); //Looks up for given tag in the L1 cache's blocks
                if(input_data[0].equals("read")){
                    if(index!=-1){
                        System.out.println(L1.read(index));
                    }
                }
                else if(input_data[0].equals("write")){
                    if(index>-1){
                        L1.rewrite(index,input_data[2]);
                    }
                    else{
                        if(L1.occupied<CL){
                            L1.new_write(input_data[1],input_data[2]);
                        }
                        else{
                            L1.evict();
                            L1.new_write(input_data[1],input_data[2]);
                        }
                    }
                }
                }
                else{
                    System.out.println("Invalid!");
                }
                int c = 0;
                System.out.println("Do you want to print out whole cache?(Yes=1 No=2)");
                c = in.nextInt();
                if(c==1){
                    L1.printcache();
                }
                System.out.println("More instructions after this?(Yes=1 No=2)");
                choice = in.nextInt();
            }
        }
        else if(cache_choice==3){
            nwaycache L1 = new nwaycache(CL,block_bits,index_bits); //Cache L1 is intitalized
            int choice = 1;
            System.out.println("n-Way Set Associative Memory Cache Simulator(8-Way)");
            while(choice == 1){
                System.out.println("Enter Instruction(<read/write> <32 bit instruction> <data>): ");
                in.nextLine();
                String input = in.nextLine(); //Inputs data in the given format
                String[] input_data = input.split(" ");
                if(input_data[1].length()==32){
                int[] index = L1.lookup(input_data[1]); //Looks up for given tag in the L1 cache's blocks. Here since the data is stored in a 2dim setup, extensive array use
                if(input_data[0].equals("read")){
                    if(index[0]!=-1){
                        System.out.println(L1.read(index));
                    }
                }
                else if(input_data[0].equals("write")){
                    if(index[1]>-1){
                        L1.rewrite(index,input_data[2]);
                    }
                    else{
                        if(L1.occupied[index[0]]<8){
                            L1.new_write(input_data[1],input_data[2]);
                        }
                        else{
                            L1.evict(index[0]);
                            L1.new_write(input_data[1],input_data[2]);
                        }
                    }
                }
                }
                else{
                    System.out.println("Invalid!");
                }
                int c = 0;
                System.out.print("Do you want to print out whole cache?(Yes=1 No=2): ");
                c = in.nextInt();
                if(c==1){
                    L1.printcache();
                }
                System.out.print("More instructions after this?(Yes=1 No=2): ");
                choice = in.nextInt();

            }
        }
    }
    else{
        System.out.println("Invalid");
    }
}
}

class facache{

    int CL; //Number of blocks in cache
    int bb; //Block bits
    String[] tag_array; //tag array part of cache
    String[] data_array; //data array part of cache
    int occupied; //Gives the number of occupied blocks right now

    facache(int x,int y){
        CL = x;
        bb = y;
        tag_array = new String[CL];
        data_array = new String[CL];
        occupied = 0;
    }

    public int lookup(String instr){
        String tag = instr.substring(0,(32-bb));
        int index = -1;
        for(int i=0; i<occupied; i++){
            if(tag.equals(tag_array[i])){
                index = i;
                System.out.println("Cache HIT");
            }
        }
        if(index == -1){
            System.out.println("Cache MISS");
        }
        return index;
    }

    public String read(int index){
        return data_array[index];
    }

    public void rewrite(int index, String new_data){
        data_array[index] = new_data; //rewrites data in the given address cache
        System.out.println("Pre existing data rewritten");
    }

    public void new_write(String new_tag,String new_data){ //Insertion operation for new data in empty space in cache
        data_array[occupied] = new_data;
        tag_array[occupied] = new_tag.substring(0,(32-bb));
        occupied = occupied + 1;
        System.out.println("New Data Written at CL #"+(occupied-1));
    }

    public void evict(){ //FIFO eviction policy is followed
        for(int i=0;i<(occupied-1);i++){
            tag_array[i] = tag_array[i+1]; 
            data_array[i] = data_array[i+1];
        }
        occupied = occupied-1;
    }

    public void printcache(){
        for(int i=0;i<occupied;i++){
            System.out.println(tag_array[i]+" "+data_array[i]);
        }
    }
}
class dmcache{

    int CL; //Number of blocks in cache
    int bb; //Block bits
    int ib; //Index bits
    String[] tag_array; //tag array part of cache
    String[] data_array; //data array part of cache
    int occupied; //Gives the number of occupied blocks right now

    dmcache(int x,int y, int z){
        CL = x;
        bb = y;
        ib = z;
        tag_array = new String[CL];
        data_array = new String[CL];
        occupied = 0;
    }

    public int lookup(String instr){
        String ixblock = instr.substring(32-bb-ib,32-bb); //We take out the index part from the instruction
        String tag = instr.substring(0,32-bb-ib); //We take out the tag part
        int index = -1;
        //Code to convert binary to decimal
        int ix = Integer.valueOf(ixblock); //Converted index part from string into integer so that it can be converted into decimal index
        int ixf = 0; //Final decimal stored value
        int base = 1; 
        int temp = ix; 
        while (temp > 0) { 
            int last_digit = temp % 10; 
            temp = temp / 10; 
            ixf = ixf + last_digit * base; 
            base = base * 2; 
        } 
        //ixf is the coverted value in decimal form which can be searched for in the cache lines
        if(tag_array[ixf]!=null && tag_array[ixf].equals(tag)){
            index = ixf;
            System.out.println("Cache HIT");
        }
        else{
            System.out.println("Cache MISS");
        }
        return index;
    }

    public String read(int index){
        return data_array[index];
    }

    public void rewrite(int index, String new_data){ //rewrites data in the given address cache
        data_array[index] = new_data; 
    }

    public void new_write(String new_tag,String new_data){ //Insertion operation for new data in empty space in cache
        String ixblock = new_tag.substring(32-bb-ib,32-bb);
        //Code to convert binary to decimal
        int ix = Integer.valueOf(ixblock); //Converted index part from string into integer so that it can be converted into decimal index
        int ixf = 0; //Final decimal stored value
        int base = 1; 
        int temp = ix; 
        while (temp > 0) { 
            int last_digit = temp % 10; 
            temp = temp / 10; 
            ixf = ixf + last_digit * base; 
            base = base * 2; 
        }
        //ixf is the coverted value in decimal form which can be used as index to store data in the cache line requested
        data_array[ixf] = new_data;
        tag_array[ixf] = new_tag.substring(0,32-bb-ib);
        occupied = occupied + 1;
        System.out.println("New Data Written at CL #"+ixf);
    }

    public void evict(){ //FIFO eviction policy is followed
        for(int i=0;i<(occupied-1);i++){
            tag_array[i] = tag_array[i+1]; 
            data_array[i] = data_array[i+1];
        }
        occupied = occupied-1;
    }

    public void printcache(){
        for(int i=0;i<CL;i++){
            System.out.println(tag_array[i]+" "+data_array[i]);
        }
    }
}

class nwaycache{

    int sets; //Number of blocks in cache
    int bb; //Block bits
    int ib; //Index bits
    String[][] tag_array; //tag array part of cache
    String[][] data_array; //data array part of cache
    int[] occupied; //Gives the number of occupied blocks right now

    nwaycache(int x,int y,int z){
        sets = x/8;//Since each set houses 8 blocks in this simulation(8-ways)
        bb = y;
        ib = z;
        tag_array = new String[sets][8];//created the arrays as 2-dim ones, the first dim the set #, and the 2nd dim the block # within it
        data_array = new String[sets][8];
        occupied = new int[sets];//Gives the number of occupied blocks in each set
    }

    public int[] lookup(String instr){
        String index_set = instr.substring(32-bb-ib+3,32-bb);
        String tag_way = instr.substring(0,32-bb-ib+3);
        int[] index = new int[2];
        index[0] = -1;
        index[1] = -1;
        //Code to convert binary to decimal
        int ix = Integer.valueOf(index_set); //Converted index part from string into integer so that it can be converted into decimal index
        int ixf = 0; //Final decimal stored value
        int base = 1; 
        int temp = ix; 
        while (temp > 0) { 
            int last_digit = temp % 10; 
            temp = temp / 10; 
            ixf = ixf + last_digit * base; 
            base = base * 2; 
        } 
        //ixf is the coverted value in decimal form which can be searched for in the cache lines
        //The following method is like FA, where we check the tag of each element to see if its the same as the element in q
        for(int i=0;i<occupied[ixf];i++){
            if(tag_way.equals(tag_array[ixf][i])){
                System.out.println("Cache HIT");
                index[0] = ixf;
                index[1] = i;
            }
        }
        if(index[0]==-1 && index[1]==-1){
            index[0] = ixf;
            System.out.println("Cache MISS");
        }
        return index;
    }

    public String read(int[] index){
        return data_array[index[0]][index[1]];
    }

    public void rewrite(int[] index, String new_data){ //rewrites data in the given address cache
        data_array[index[0]][index[1]] = new_data; 
    }

    public void new_write(String new_tag, String new_data){
        String index_set = new_tag.substring(32-bb-ib+3,32-bb);
        String tag_way = new_tag.substring(0,32-bb-ib+3);
        int ix = Integer.valueOf(index_set); //Converted index part from string into integer so that it can be converted into decimal index
        int ixf = 0; //Final decimal stored value
        int base = 1; 
        int temp = ix; 
        while (temp > 0) { 
            int last_digit = temp % 10; 
            temp = temp / 10; 
            ixf = ixf + last_digit * base; 
            base = base * 2; 
        } 
        //ixf is the coverted value in decimal form which can be searched for in the cache lines
        tag_array[ixf][occupied[ixf]] = tag_way;//the first empty spot in the given set is filled up with tag and data info
        data_array[ixf][occupied[ixf]] = new_data;
        System.out.println("New data written at set #"+ixf+" way #"+occupied[ixf]);
        occupied[ixf] = occupied[ixf] + 1;
    }

    public void evict(int index){//LRU Policy followed within the set being operated on
        for(int i=0;i<(occupied[index]-1);i++){
            tag_array[index][i] = tag_array[index][i+1]; 
            data_array[index][i] = data_array[index][i+1];
        }
        occupied[index] = occupied[index]-1;
    }
    public void printcache(){
        for(int i=0;i<sets;i++){
            System.out.print("{");
            for(int j=0;j<occupied[i];j++){
                System.out.print(tag_array[i][j]+" "+data_array[i][j]+" , ");
            }
            System.out.println("}");
        }
    }
}

