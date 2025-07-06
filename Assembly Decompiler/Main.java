import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * @author svyatoslavvarnitskyy
 */
public class Main {


    public static void main(String[] args) throws IOException {

        Path path = Paths.get(args[0]);
        byte[] data = Files.readAllBytes(path);



        int j = 0;
        for(int i = 0; i < data.length; i += 4){

            int instruction = (data[i] & 0xFF) << 24 | (data[i+1] & 0xFF) << 16 | (data[i+2] & 0xFF) << 8 | (data[i+3] & 0xFF);

            System.out.println("label"+ j + ":");
            if(((instruction >> 21) & 0x7ff) == 0b10001011000){       //ADD
                System.out.println("ADD" + getRegisterValuesRType(instruction));
            } else if (((instruction >> 22) & 0x3ff) == 0b1001000100) {  //ADDI
                System.out.println("ADDI" + getRegisterValuesIType(instruction));
            } else if (((instruction >> 21) & 0x7ff) == 0b10001010000) {  //AND
                System.out.println("AND" + getRegisterValuesRType(instruction));
            } else if (((instruction >> 22) & 0x3ff) == 0b1001001000) {  //ANDI
                System.out.println("ANDI" + getRegisterValuesIType(instruction));
            } else if (((instruction >> 26) & 0x3f) == 0b000101) {       //B
                System.out.println("B" + getRegisterValuesBType(instruction, j));
            } else if (((instruction >> 26) & 0x3f) == 0b100101) {         //BL
                System.out.println("BL" + getRegisterValuesBType(instruction, j));
            }else if (((instruction >> 21) & 0x7ff) == 0b11010110000) {  //BR
                String firstSource = getRegister((instruction >> 5) & 0x1f);
                System.out.println("BR " + firstSource);
            }else if (((instruction >> 24) & 0xff) == 0b10110101) {  //CBNZ
                System.out.println("CBNZ" + getRegisterValuesCBType(instruction, j));
            }else if (((instruction >> 24) & 0xff) == 0b10110100) {  //CBZ
                System.out.println("CBZ" + getRegisterValuesCBType(instruction, j));
            }else if (((instruction >> 21) & 0x7ff) == 0b11001010000) {  //EOR
                System.out.println("EOR" + getRegisterValuesRType(instruction));
            }else if (((instruction >> 22) & 0x3ff) == 0b1101001000) {  //EORI
                System.out.println("EORI" + getRegisterValuesIType(instruction));
            }else if (((instruction >> 21) & 0x7ff) == 0b11111000010) {  //LDUR
                System.out.println("LDUR" + getRegisterValuesDType(instruction));
            }else if (((instruction >> 21) & 0x7ff) == 0b11010011011) {  //LSL
                String destination = getRegister(instruction & 0x1f);
                String firstSource = getRegister((instruction >> 5) & 0x1f);
                int secondSource = (instruction >> 10) & 0x3f;
                System.out.println("LSL" + " " + destination + ", " + firstSource + ", #" + secondSource);
            }else if (((instruction >> 21) & 0x7ff) == 0b11010011010) {  //LSR
                String destination = getRegister(instruction & 0x1f);
                String firstSource = getRegister((instruction >> 5) & 0x1f);
                int secondSource = (instruction >> 10) & 0x3f;
                System.out.println("LSR" + " " + destination + ", " + firstSource + ", #" + secondSource);
            }else if (((instruction >> 21) & 0x7ff) == 0b10101010000) {  //ORR
                System.out.println("OOR" + getRegisterValuesRType(instruction));
            }else if (((instruction >> 22) & 0x3ff) == 0b1011001000) {  //OORI
                System.out.println("OORI" + getRegisterValuesIType(instruction));
            }else if (((instruction >> 21) & 0x7ff) == 0b11111000000) {  //STUR
                System.out.println("STUR" + getRegisterValuesDType(instruction));
            }else if (((instruction >> 21) & 0x7ff) == 0b11001011000) {  //SUB
                System.out.println("SUB" + getRegisterValuesRType(instruction));
            }else if (((instruction >> 22) & 0x3ff) == 0b1101000100) {  //SUBI
                System.out.println("SUBI" + getRegisterValuesIType(instruction));
            }else if (((instruction >> 22) & 0x3ff) == 0b1111000100) {  //SUBIS
                System.out.println("SUBIS" + getRegisterValuesIType(instruction));
            }else if (((instruction >> 21) & 0x7ff) == 0b11101011000) {  //SUBS
                System.out.println("SUBS" + getRegisterValuesRType(instruction));
            }else if (((instruction >> 21) & 0x7ff) == 0b10011011000) {  //MUL
                System.out.println("MUL" + getRegisterValuesRType(instruction));
            }else if (((instruction >> 21) & 0x7ff) == 0b11111111101) {  //PRNT
                int destination = instruction & 0x1f;
                System.out.println("PRNT" + " " + getRegister(destination));
            }else if (((instruction >> 21) & 0x7ff) == 0b11111111100) {  //PRNL
                System.out.println("PRNL");
            }else if (((instruction >> 21) & 0x7ff) == 0b11111111110) {  //DUMP
                System.out.println("DUMP");
            }else if (((instruction >> 21) & 0x7ff) == 0b11111111111) {  //HALT
                System.out.println("HALT");
            }else if (((instruction >> 24) & 0xff) == 0b01010100) {  //B.cond
                int condition = instruction & 0x1f;
                String returnString = "";
                if(condition == 0){
                    returnString = "B.EQ";
                } else if (condition == 1) {
                    returnString = "B.NE";
                } else if (condition == 2) {
                    returnString = "B.HS";
                }else if (condition == 3) {
                    returnString = "B.LO";
                }else if (condition == 4) {
                    returnString = "B.MI";
                }else if (condition == 5) {
                    returnString = "B.PL";
                }else if (condition == 6) {
                    returnString = "B.VS";
                }else if (condition == 7) {
                    returnString = "B.VC";
                }else if (condition == 8) {
                    returnString = "B.HI";
                }else if (condition == 9) {
                    returnString = "B.LS";
                }else if (condition == 10) {
                    returnString = "B.GE";
                }else if (condition == 11) {
                    returnString = "B.LT";
                }else if (condition == 12) {
                    returnString = "B.GT";
                }else if (condition == 13) {
                    returnString = "B.LE";
                }

                int address;
                if((instruction & 0x40000) == 0x40000){
                    address = (instruction >> 5) & 0x7ffff | 0xffff0000;
                }else{
                    address = (instruction >> 5) & 0x7ffff;
                }
                address += j;
                System.out.println(returnString + " label" + address);}

            j++;


        }
    }

    public static String getRegisterValuesRType(int instruction){
        String destination = getRegister(instruction & 0x1f);
        String firstSource = getRegister((instruction >> 5) & 0x1f);
        String secondSource = getRegister((instruction >> 16) & 0x1f);


        return  " " + destination + ", " + firstSource + ", " + secondSource;
    }

    public static String getRegisterValuesIType(int instruction){
        String destination = getRegister(instruction & 0x1f);
        String firstSource = getRegister((instruction >> 5) & 0x1f);
        int immediate = (instruction >> 10) & 0xfff;


        return  " " + destination + ", " + firstSource + ", #" + immediate;
    }

    public static String getRegisterValuesBType(int instruction, int j){
        int destination;
        if((instruction & 0x2000000) == 0x2000000){
            destination = (instruction & 0x1ffffff) | 0xff000000;
        }else{
            destination = (instruction & 0x3ffffff);
        }
        destination += j;
        return  " label" + destination;
    }

    public static String getRegisterValuesCBType(int instruction, int j){
        String register = getRegister(instruction & 0x1f);
        int address = ((instruction >> 5) & 0x7ffff) + j;
        return  " " + register + ", label" + address;
    }
    public static String getRegisterValuesDType(int instruction){
        String destination = getRegister(instruction & 0x1f);
        String firstSource = getRegister((instruction >> 5) & 0x1f);
        int offset = ((instruction >> 12) & 0x1ff);

        return  " " + destination + ", [" + firstSource + ", #" + offset + "]";
    }

    private static String getRegister(int register){
        if(register == 31) return "XZR";
        else if (register == 30) return "LR";
        else if (register == 29) return "FP";
        else if(register == 28) return "SP";
        else return "X" + Integer.toString(register);
    }

}