package processor.pipeline;

import processor.Processor;

public class MemoryAccess {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	int op2;
	int ldResult = 0;
	int aluResult;
	int instruction;
	String opcode;
	
	
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
	}

	public void performMA()
	{
		//TODO
		if (EX_MA_Latch.isMA_enable())
		{
			// getting values from EX_MA latch;
			opcode = EX_MA_Latch.getOpcode();
			op2 = EX_MA_Latch.getOp2();
			aluResult = EX_MA_Latch.getAluResult();
			instruction = EX_MA_Latch.getInstruction();
			
			if (opcode.equals("10110"))		//if load operation
				ldResult = containingProcessor.getMainMemory().getWord(aluResult);
					
			if (opcode.equals("10111"))		//if store operation
				containingProcessor.getMainMemory().setWord(aluResult,op2);  //address,value

			//setting values for MA-RW latch
			EX_MA_Latch.setMA_enable(false);
			MA_RW_Latch.setRW_enable(true);
			MA_RW_Latch.setInstruction(instruction);
			MA_RW_Latch.setOpcode(opcode);
			MA_RW_Latch.setAluResult(aluResult);
			MA_RW_Latch.setLdResult(ldResult);
			
		}
	}

}
