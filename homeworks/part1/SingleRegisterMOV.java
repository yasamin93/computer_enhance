package homeworks.part1;

import jdk.jshell.spi.ExecutionControl;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Task: Register to register on x8086
public class SingleRegisterMOV {
    private final static String MOV = "100010";
    private static HashMap<Character, HashMap<String, String>> REG_ENCODING = new HashMap<>();
    static {
        HashMap<String, String> W0 = new HashMap<>();
        W0.put("000", "AL");
        W0.put("001", "CL");
        W0.put("010", "DL");
        W0.put("011", "BL");
        W0.put("100", "AH");
        W0.put("101", "CH");
        W0.put("110", "DH");
        W0.put("111", "BH");
        REG_ENCODING.put('0', W0);

        HashMap<String, String> W1 = new HashMap<>();
        W1.put("000", "AX");
        W1.put("001", "CX");
        W1.put("010", "DX");
        W1.put("011", "BX");
        W1.put("100", "SP");
        W1.put("101", "BP");
        W1.put("110", "SI");
        W1.put("111", "DI");
        REG_ENCODING.put('1', W1);
    }
    // mov cx, bx
    public void run() {
        System.out.println("Running SingleRegisterMOV");
        String path = "./perfaware/part1/listing_0038_single_register_mov";
        int data;
        List<String> instructions = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(path)){
            while ((data = inputStream.read()) != -1) {
                instructions.add(Integer.toBinaryString(data));
            }
            System.out.println(instructions);
//        // bit 6: D - direction of operation: 0: second byte is the source, 1: second byte is the destination
//        // bit 7: W: 0: byte operation, 1: word operation
//        // first 6 bits of multibyte instructions contain an opcode to get the basic instruction: mov, add, xot, etc.
        var opcode = instructions.get(0).substring(0, 6);
        var D = instructions.get(0).charAt(6);
        var W = instructions.get(0).charAt(7);
        var MOD = instructions.get(1).substring(0, 2);
        var REG = instructions.get(1).substring(2, 5);
        var RM = instructions.get(1).substring(5, 8);
        if (opcode.equals(MOV) && MOD.equals("11")) {
            System.out.println("Opcode is MOV and we in register mod.");
            String regFieldContains = D == 0 ? "source" : "destination";
            if ( regFieldContains.equals("source"))
                System.out.println("MOV " + REG_ENCODING.get(W).get(REG) + ", " + REG_ENCODING.get(W).get(RM));
            else {
                System.out.println("MOV " + REG_ENCODING.get(W).get(RM) + ", " + REG_ENCODING.get(W).get(REG));
            }
        }
        else {
            throw new ExecutionControl.NotImplementedException("Opcode is not implemented.");
        }
        } catch ( FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException | ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
    }
}