package processor.pipeline;

import generic.Simulator;
import processor.Processor;

public class RegisterWrite {
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	int instruction = 0;
	int ldResult;
	int aluResult;
	String opcode;
	int rd;
	
	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}

	public int getInstructionCheck(){
		return instruction;
	}

	private boolean isWriteNeeded()
	{
		if (opcode.equals("11000") || opcode.equals("11001") || opcode.equals("11010") || opcode.equals("11011") || opcode.equals("11100") || opcode.equals("10111")) 
			return false;
		return true;
	}

	public void performRW()
	{
		if(MA_RW_Latch.isRW_enable())
		{
			//TODO
			//getting values from previous latch
			opcode = MA_RW_Latch.getOpcode();
			instruction = MA_RW_Latch.getInstruction();
			aluResult = MA_RW_Latch.getAluResult();
			ldResult = MA_RW_Latch.getLdResult();

			if(isWriteNeeded())
			{
				String instructionBits = Integer.toBinaryString(instruction);
				while(instructionBits.length() < 32)
					instructionBits = "0" + instructionBits;
				
				if (opcode.charAt(4) == '1' || opcode.equals("10110"))		//immediate or load 
				{
					String rd_bin = instructionBits.substring(10,15);
					rd = Integer.parseInt(rd_bin,2); 						
					if (opcode.equals("10110"))
						containingProcessor.getRegisterFile().setValue(rd, ldResult);
					else
						containingProcessor.getRegisterFile().setValue(rd, aluResult);
				}
				else
				{
					String rd_bin = instructionBits.substring(15,20);
					rd = Integer.parseInt(rd_bin,2);
					containingProcessor.getRegisterFile().setValue(rd, aluResult);
				}
			}

			// checking for the end instruction:
			if(opcode.equals("11101"))		//opcode for end = 11101
			{
				Simulator.setSimulationComplete(true);		//once simulation complete you set SimulationCOmplete as 'true'
				int currPC = containingProcessor.getRegisterFile().getProgramCounter();		//obtaining current PC
				containingProcessor.getRegisterFile().setProgramCounter(currPC+1);		//updating the PC: increment by 1
			}
			else{
				instruction = 0;

			}

		}
	}

}


