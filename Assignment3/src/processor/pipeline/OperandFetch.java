package processor.pipeline;

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
			
			
			//binary to int,  rafix = 2
			int rs1_val = Integer.parseInt(rs1, 2);  
			if(rs1_val<0) rs1_val = 0;

			int rs2_val = Integer.parseInt(rs2, 2);
			if(rs2_val<0) rs2_val = 0;
			
			
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
	}

}
