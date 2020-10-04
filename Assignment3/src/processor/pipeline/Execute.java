package processor.pipeline;

import processor.Processor;

public class Execute {
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	static int immx,branchTarget,op1,op2,aluResult;
	static String opcode;
	static boolean isBranchTaken = false;
	int instruction;
	

	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}

	private static boolean isImmediate()
	{
		boolean isImm = false;
		// general checking for arithmatic types:
		isImm = opcode.charAt(4) == '1';		//last char of opcode is 1 in case of immediate 
		
		// checking for load
		if (opcode.equals("10110"))  isImm = true;
		
		// checking for conditional statement
		// setting flase for beq and blt as these are wrongly flagged as true;
		if (opcode.equals("11001") || opcode.equals("11011")) isImm = false;
		
		return isImm;
	}
	
	private int aluOperation(int opr1, int opr2)
	{
		int aluResult = 0;
		int remainder = 0;
		switch (opcode) {
		case "00000":
			aluResult = opr1 + opr2;
			break;

		case "00001":
			aluResult = opr1 + opr2;
			break;

		case "00010":
			aluResult = opr1 - opr2;
			break;

		case "00011":
			aluResult = opr1 - opr2;
			break;

		case "00100":
			aluResult = opr1 * opr2;
			break;

		case "00101":
			aluResult = opr1 * opr2;
			break;

		case "00110":
			aluResult = opr1 / opr2;
			remainder = opr1 % opr2;
			containingProcessor.getRegisterFile().setValue(31,remainder); // remainder in stored in register 31
			break;

		case "00111":
			aluResult = opr1 / opr2;
			remainder = opr1 % opr2;
			containingProcessor.getRegisterFile().setValue(31,remainder); // remainder in stored in register 31
			break;

		case "01000":
			aluResult = opr1 & opr2;
			break;

		case "01001":
			aluResult = opr1 & opr2;
			break;

		case "01010":
			aluResult = opr1 | opr2;
			break;
		case "01011":
			aluResult = opr1 | opr2;
			break;

		case "01100":
			aluResult = opr1 ^ opr2;
			break;

		case "01101":
			aluResult = opr1 ^ opr2;
			break;

		case "10000":
			aluResult = opr1 << opr2;
			break;

		case "10001":
			aluResult = opr1 << opr2;
			break;

		case "10010":
			aluResult = opr1 >>> opr2;
			break;

		case "10011":
			aluResult = opr1 >>> opr2;
			break;

		case "10100":
			aluResult = opr1 >> opr2;
			break;

		case "10101":
			aluResult = opr1 >> opr2;
			break;

		case "10110":		//load
			aluResult = opr1 + opr2;
			break;

		case "10111":		//store
			aluResult = opr1 + opr2;
			break;
		
		}
		if (opcode.equals("01110") || opcode.equals("01111"))
			if(opr1 < opr2) 
				aluResult = 1;
			else 					
				aluResult = 0;
	return aluResult;
	}
	
	private static void checkBranch(int opr1,int opr2)
	{
		if ((opcode.equals("11001") && opr1 == opr2) ||
			(opcode.equals("11010") && opr1 != opr2) ||
			(opcode.equals("11011") && opr1 < opr2)  ||
			(opcode.equals("11100") && opr1 > opr2)  ||
			(opcode.equals("11000")))
				isBranchTaken = true;
	}
	
	public void performEX()
	{
		//TODO
		// getting the value of immx and others.
		if (OF_EX_Latch.isEX_enable())
		{
			//branch taken is set to false while performing execute satge
			isBranchTaken = false;

			//gettin values from OF-EX latch
			immx = OF_EX_Latch.getImmx();
			branchTarget = OF_EX_Latch.getBranchTarget();
			op1 = OF_EX_Latch.getOp1();
			op2 = OF_EX_Latch.getOp2();
			opcode = OF_EX_Latch.getOpcode();
			instruction = OF_EX_Latch.getInstruction();
			
			// performing the alu result:
			int operand2 = op2;
			if (isImmediate())		//checking if to set operand2 as immx or not
				operand2 = immx;
			
			aluResult = aluOperation(op1, operand2);
			checkBranch(op1,operand2);		//for branch target			

			
			// changing the latch enable values: 
			OF_EX_Latch.setEX_enable(false);		//previous latch
			EX_MA_Latch.setMA_enable(true);			//next latch
			EX_IF_Latch.setIF_enable(true);
			
			//setting values for the next latch
			EX_IF_Latch.setBranchPC(branchTarget);
			EX_IF_Latch.setIsBRanchTaken(isBranchTaken);
			EX_MA_Latch.setInstruction(instruction);
			EX_MA_Latch.setOp2(op2);
			EX_MA_Latch.setAluResult(aluResult);
			EX_MA_Latch.setOpcode(opcode);
			
		}

	}

}
