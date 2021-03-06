package generic;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

import generic.Operand.OperandType;

import static java.lang.Integer.parseInt;


public class Simulator {
		
	static FileInputStream inputcodeStream = null;

	public static void setupSimulation(String assemblyProgramFile)
	{
		int firstCodeAddress = ParsedProgram.parseDataSection(assemblyProgramFile);
		ParsedProgram.parseCodeSection(assemblyProgramFile, firstCodeAddress);
		ParsedProgram.printState();
	}

	public static String getOpcode(Instruction.OperationType opc)
	{
		String opcode = "";
		switch(opc)
		{
			case add:
				opcode="00000";
				break;
			case addi:
				opcode="00001";
				break;
			case sub:
				opcode="00010";
				break;
			case subi:
				opcode="00011";
				break;
			case mul:
				opcode="00100";
				break;
			case muli:
				opcode="00101";
				break;
			case div:
				opcode="00110";
				break;
			case divi:
				opcode="00111";
				break;
			case and:
				opcode="01000";
				break;
			case andi:
				opcode="01001";
				break;
			case or:
				opcode="01010";
				break;
			case ori:
				opcode="01011";
				break;
			case xor:
				opcode="01100";
				break;
			case xori:
				opcode="01101";
				break;
			case slt:
				opcode="01110";
				break;
			case slti:
				opcode="01111";
				break;
			case sll:
				opcode="10000";
				break;
			case slli:
				opcode="10001";
				break;
			case srl:
				opcode="10010";
				break;
			case srli:
				opcode="10011";
				break;
			case sra:
				opcode="10100";
				break;
			case srai:
				opcode="10101";
				break;
			case jmp:
				opcode="11000";
				break;
			case beq:
				opcode="11001";
				break;
			case bne:
				opcode="11010";
				break;
			case blt:
				opcode="11011";
				break;
			case bgt:
				opcode="11100";
				break;
			case load:
				opcode="10110";
				break;
			case store:
				opcode="10111";
				break;
			case end:
				opcode="11101";
				break;
				default:
					System.out.println("instruction not found");

		}
		return opcode;
	}

	public static void writeToFile(String objectProgramFile,int writeValue)
	{
		try {
			DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(objectProgramFile, true));
			dataOut.writeInt(writeValue);
		}
		catch(Exception e) {
			System.out.println("error occured");
		}
	}

	public static String intToBinary(int intValue,int nbits)
	{
		String tempBinary = Integer.toBinaryString(intValue);
		if(intValue >= 0)
			while(tempBinary.length() < nbits)
				tempBinary = "0"+tempBinary;
		else {
			tempBinary = tempBinary.substring(32 - nbits);
		}
		return tempBinary;
	}


	public static void assemble(String objectProgramFile, String assemblyProgramFile)
	{
		int pc = ParsedProgram.firstCodeAddress; // program counter
		writeToFile(objectProgramFile,pc);
		FileInputStream inp = null;
		try
		{
			inp = new FileInputStream(assemblyProgramFile);
		}
		catch(FileNotFoundException e){
			Misc.printErrorAndExit(e.toString());
		}

		Scanner scan = new Scanner(inp);
		String line = scan.nextLine();

		if(line.contains(".data"))
			line= scan.next();
		int temp = 0;
		while(!line.contains(".text"))
		{
			if(line.matches(".*\\d+.*"))
			{
				temp = parseInt(line);
				writeToFile(objectProgramFile, temp);
			}
			line = scan.next();
		}
		scan.close();


		for(int l=0;l<ParsedProgram.code.size();l++) {
			Instruction newInstruction = ParsedProgram.getInstructionAt(pc);

			Operand src1 = newInstruction.getSourceOperand1();		//object for source 1
			Operand src2 = newInstruction.getSourceOperand2();		//object for source 2
			Operand dstOp = newInstruction.getDestinationOperand();		//object for destination

			Instruction.OperationType op = newInstruction.getOperationType();
			String opcode= getOpcode(op);

			String final_line =opcode;

			if(src1 == null & dstOp == null){
				final_line += "000000000000000000000000000";
			}
			else if(src1 == null & dstOp.getOperandType() == Operand.OperandType.Label){
				int imm_val = ParsedProgram.symtab.get(dstOp.getLabelValue());
				imm_val = imm_val-pc;
				String imm_bin = intToBinary(imm_val,22); // integer to  extended binary conversion
				final_line += "00000" + imm_bin;	//starting 5 bits plus the imm_bin
			}
			else
			{
				String rs1 = intToBinary(src1.getValue(),5);		//binary rs1 value

				if(dstOp.getOperandType() == Operand.OperandType.Register)		//destination operand type register
				{
					String rd = intToBinary(dstOp.getValue(),5);		//binary destination value

					if(src2.getOperandType() == Operand.OperandType.Register)
					{
						String rs2 = intToBinary(src2.getValue(),5);		//binary rs2 value
						final_line += rs1+rs2+rd+"000000000000";
					}
					else
					{
						int imm_val=0;
						if(src2.getOperandType() == Operand.OperandType.Label)		//src2 is of type label
							imm_val = ParsedProgram.symtab.get(src2.getLabelValue());
						else														//if not of type label
							imm_val = src2.getValue();
						String imm = intToBinary(imm_val,17);
						final_line += rs1+rd+imm;
					}
				}
				else if(dstOp.getOperandType() == Operand.OperandType.Label)		//operand type label
				{
					String rs2 = intToBinary(src2.getValue(),5);
					int imm_val = ParsedProgram.symtab.get(dstOp.getLabelValue());
					imm_val = imm_val - pc;
					String imm_bin = intToBinary(imm_val,17);
					final_line += rs1+rs2+imm_bin;
				}

			}

			// CONVERSION TO Decimal
			int instructLine;
			if(final_line.charAt(0) == '1'){		// handling 2's complement, checking the first bit
				final_line = final_line.replace('0', ' ').replace('1', '0').replace(' ', '1');		//inversion step
				int decimal = Integer.parseInt(final_line, 2);
				decimal = (decimal + 1) * -1;
				instructLine = decimal;
			}
			else{
				instructLine = Integer.parseInt(final_line, 2);
			}

			writeToFile(objectProgramFile,instructLine);
			pc += 1;

		}
	}
}
