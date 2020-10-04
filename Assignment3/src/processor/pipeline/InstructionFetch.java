package processor.pipeline;

import processor.Processor;

public class InstructionFetch {
	
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;

	String opcode, imm;
	
	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}
	
	public void performIF()
	{
		if(IF_EnableLatch.isIF_enable())
		{
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			
			// changing program counter:
			if(EX_IF_Latch.isIF_enable()) {
				if(EX_IF_Latch.getIsBranchTaken())
					containingProcessor.getRegisterFile().setProgramCounter(EX_IF_Latch.getBranchPC());
				else
					containingProcessor.getRegisterFile().setProgramCounter(currentPC+1);
			}
			EX_IF_Latch.setIsBRanchTaken(false);
			
			currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			
			int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);

			IF_OF_Latch.setInstruction(newInstruction);


			//extracting bits from instrucion to store in the latch for use in operand fetch stage
			String instructionBits = Integer.toBinaryString(newInstruction);
			while(instructionBits.length() < 32)
				instructionBits = "0" + instructionBits;
			
			opcode = instructionBits.substring(0,5);
			String imm = instructionBits.substring(15,32);
			String dsImmediate = instructionBits.substring(10,32);
			String rs1Store = instructionBits.substring(10,15);   
		    String rs2Store = instructionBits.substring(5,10);
		    String rs1Ld = instructionBits.substring(5,10);
			String rs2Ld = instructionBits.substring(10,15);	 

			IF_OF_Latch.setOpcode(opcode);
			IF_OF_Latch.setImm(imm);
			IF_OF_Latch.setDsIm(dsImmediate);
			IF_OF_Latch.setRS1Store(rs1Store);
			IF_OF_Latch.setRS2Store(rs2Store);
			IF_OF_Latch.setRS1Ld(rs1Ld);
			IF_OF_Latch.setRS2Ld(rs2Ld);


			IF_EnableLatch.setIF_enable(false);
			IF_OF_Latch.setOF_enable(true);
			EX_IF_Latch.setIF_enable(false);
		}
	}

}
