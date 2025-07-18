# Assembly-Decompiler-Cycle-Sort
## Project Overview
A Java-based decompiler that converts LEGv8 machine code (binary) back into human-readable assembly instructions. This project also features a custom implementation of the Cycle Sort algorithm written directly in LEGv8 assembly.

## Features

  * **LEGv8 Machine Code Decompiler**: Converts binary `.machine` files into LEGv8 assembly instructions.
  * **Instruction Decoding**:
      * Identifies and decodes various LEGv8 instruction types, including R-type (e.g., ADD, AND, EOR, MUL, ORR, SUB, SUBS), I-type (e.g., ADDI, ANDI, EORI, OORI, SUBI, SUBIS), B-type (B, BL), BR-type, CB-type (CBNZ, CBZ), and D-type (LDUR, STUR).
      * Handles conditional branch instructions (e.g., B.EQ, B.NE, B.HS, B.LO, B.MI, B.PL, B.VS, B.VC, B.HI, B.LS, B.GE, B.LT, B.GT, B.LE).
      * Decodes shift operations (LSL, LSR).
      * Recognizes and displays system calls like PRNT, PRNL, DUMP, and HALT.
  * **Register Mapping**: Translates numerical register values into their conventional LEGv8 names (e.g., XZR, LR, FP, SP, X0-X27).
  * **Cycle Sort Implementation in Assembly**: The Cycle Sort algorithm was custom coded in LEGv8 assembly, and its machine code is included in `project12.legv8asm.machine`. This allows for direct examination of low-level sorting logic.

## How It Works

The decompiler, implemented in Java, reads the binary `.machine` file byte by byte, interpreting every four bytes as a single 32-bit LEGv8 instruction. It then uses bitwise operations and masks to extract the opcode and operand fields from each instruction. Based on the opcode, the decompiler determines the instruction type and its corresponding assembly mnemonic. Further bitwise operations are used to extract register numbers, immediate values, and memory offsets, which are then translated into human-readable format. Branch targets are calculated relative to the current instruction's address. The output is a sequence of labeled assembly instructions.

The Cycle Sort assembly code, `project12.legv8asm.machine`, is a pre-compiled LEGv8 program that can be processed by this decompiler to show its original assembly instructions.

## Getting Started

### Prerequisites

  * Java Development Kit (JDK) 8 or higher

### Building the Decompiler

1.  Navigate to the project root directory in your terminal.
2.  Run the `build.sh` script:
    ```bash
    ./build.sh
    ```
    This will compile `Main.java` into `Main.class`.

### Running the Decompiler

1.  After building, run the `run.sh` script, providing the path to your LEGv8 machine code file as an argument:
    ```bash
    ./run.sh <path_to_machine_file.machine>
    ```
    For example, to decompile the included Cycle Sort assembly:
    ```bash
    ./run.sh project12.legv8asm.machine
    ```
    Or to decompile the included test instructions:
    ```bash
    ./run.sh testInstruct.legv8asm.machine
    ```
    The decompiler will print the disassembled LEGv8 instructions to the console.

## Files in This Repository

  * `Main.java`: The main Java source file containing the decompiler logic.
  * `Main.class`: The compiled Java bytecode.
  * `build.sh`: A simple shell script to compile `Main.java`.
  * `run.sh`: A simple shell script to run the compiled `Main.class` with a given machine code file.
  * `project12.legv8asm.machine`: The binary machine code for the Cycle Sort algorithm implemented in LEGv8 assembly.
  * `testInstruct.legv8asm.machine`: A binary machine code file containing test instructions.
