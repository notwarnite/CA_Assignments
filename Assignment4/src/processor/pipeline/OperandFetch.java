package processor.pipeline;

import generic.Statistics;
import processor.Processor;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	int immx;
	int branchTarget;
	int instruction;
	int op1,op2;
	String opcode, rs1,rs2;
	boolean not_updatePC = false;

	public boolean checkUpdatePC(){
		return !not_updatePC;
	}

	private boolean checkSelectedConfict(int instructionB)
	{
		if (instructionB == 0) return false;
		int instructionA = instruction;

		String instructionBits_a = Integer.toBinaryString(instructionA);
		while(instructionBits_a.length() < 32)
			instructionBits_a = "0" + instructionBits_a;
		String instructA_bits =  instructionBits_a;

		String instructionBits_b = Integer.toBinaryString(instructionB);
		while(instructionBits_b.length() < 32)
			instructionBits_b = "0" + instructionBits_b;
		String instructB_bits =  instructionBits_b;

		String opcodeA = opcode;
		String opcodeB = instructB_bits.substring(0, 5);

		// for A is jmp or end : do nothing
		if (opcodeA.equals("11000") || opcodeA.equals("11101"))
			return false;

		// for B is store, jmp, branches or end : do nothing
		if (opcodeB.equals("10111") ||
				opcodeB.equals("11000") ||
				opcodeB.equals("11001") ||
				opcodeB.equals("11010") ||
				opcodeB.equals("11011") ||
				opcodeB.equals("11100") ||
				opcodeB.equals("11101"))
			return false;

		String rd_bin;
		if (opcodeB.charAt(4) == '1' || opcodeB.equals("10110"))
			rd_bin = instructB_bits.substring(10,15);
		else
			rd_bin = instructB_bits.substring(15,20);

		// checking for source 1;
		if (rs1.equals(rd_bin)) return true;

		// checking if we need to check the conflict for
		boolean isImm = false;
		// general checking for arithmatic types:
		isImm = opcodeA.charAt(4) == '1';

		// checking for load: store is covered in arithmatic type:
		if (opcodeA.equals("10110"))  isImm = true;

		// checking for conditional statement
		// setting false for beq, blt and store as these are wrongly flagged as true;
		if (opcodeA.equals("11001") || opcodeA.equals("11011") || opcodeA.equals("10111")) isImm = false;

		// checking for source 2 ;
		if (!isImm && rs2.equals(rd_bin)) return true;

		return false;

	}

	////////// checking instruction in OF stage's conflict with EX, MA and RW stages
	private boolean isConflict()
	{
		int ex_inst = containingProcessor.getEXUnit().getInstructionCheck();
		int ma_inst = containingProcessor.getMAUnit().getInstructionCheck();
		int rw_inst = containingProcessor.getRWUnit().getInstructionCheck();

		return (checkSelectedConfict(ex_inst) || checkSelectedConfict(ma_inst) || checkSelectedConfict(rw_inst));
//		return (dataConflictCheck(ex_inst) || dataConflictCheck(ma_inst) || dataConflictCheck(rw_inst));

	}

	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}

	public void performOF()
	{
		if(IF_OF_Latch.isOF_enable())
		{
			//obtaining values from IF-OF latch
			instruction = IF_OF_Latch.getInstruction();
			opcode = IF_OF_Latch.getOpcode();
			String imm = IF_OF_Latch.getImm();

			if (imm.charAt(0) == '1') {
				String final_line = imm;
				final_line = final_line.replace('0', ' ').replace('1', '0').replace(' ', '1');	
	        	int decimal = Integer.parseInt(final_line, 2);
	        	decimal = (decimal + 1) * -1;
	        	immx = decimal;
	    	} 
	    	else {
	        	immx = Integer.parseInt(imm, 2);
	   		 }

			
			int temp;
			String dsImm = IF_OF_Latch.getDsIm();
			int pc = containingProcessor.getRegisterFile().getProgramCounter();
			if (opcode.equals("11000")) {

				if (dsImm.charAt(0) == '1') {
					String final_line = dsImm;
					final_line = final_line.replace('0', ' ').replace('1', '0').replace(' ', '1');	
	        		int decimal = Integer.parseInt(final_line, 2);
	        		decimal = (decimal + 1) * -1;
	        		temp = decimal;
	    		} 
	    		else {
	        		temp = Integer.parseInt(dsImm, 2);
	   		 	}


				branchTarget = temp + pc;
			}
			else
				branchTarget = immx + pc;
			
			
			
			if(opcode.equals("10111")) {	//if it is a store operation
				rs1 = IF_OF_Latch.getRS1Store();
				rs2 = IF_OF_Latch.getRS2Store();		  
			}
			else {
				rs1 = IF_OF_Latch.getRS1Ld();
				rs2 = IF_OF_Latch.getRS2Ld();
			}
			
			if(!isConflict()) {
				not_updatePC = false;
				int rs1_val = Integer.parseInt(rs1, 2);
				if (rs1_val < 0) rs1_val = 0;

				int rs2_val = Integer.parseInt(rs2, 2);
				if (rs2_val < 0) rs2_val = 0;


				op1 = containingProcessor.getRegisterFile().getValue(rs1_val);
				op2 = containingProcessor.getRegisterFile().getValue(rs2_val);

				//setting values for next latch
				OF_EX_Latch.setInstruction(instruction);
				OF_EX_Latch.setImmx(immx);
				OF_EX_Latch.setBranchTarget(branchTarget);
				OF_EX_Latch.setOp1(op1);
				OF_EX_Latch.setOp2(op2);
				OF_EX_Latch.setOpcode(opcode);
				IF_OF_Latch.setOF_enable(false);
				OF_EX_Latch.setEX_enable(true);

			}
			else{	//////// data lock ///////////
				Statistics.updateDataStall();
				not_updatePC = true;
				OF_EX_Latch.setEX_enable(false);
			}

			if(containingProcessor.getEXUnit().getIsBranchTaken()){
				OF_EX_Latch.setEX_enable(false);
			}
		}
		else{
			instruction = 0;
			OF_EX_Latch.setEX_enable(false);
		}

	}

}
