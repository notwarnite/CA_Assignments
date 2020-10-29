package processor.pipeline;

public class IF_OF_LatchType {
	
	boolean OF_enable;
	int instruction;
	String opcode, imm, ds, rs1Store, rs2Store, rs1Ld, rs2Ld;
	
	public IF_OF_LatchType()
	{
		OF_enable = false;
	}

	public boolean isOF_enable() {
		return OF_enable;
	}

	public void setOF_enable(boolean oF_enable) {
		OF_enable = oF_enable;
	}

	public int getInstruction() {
		return instruction;
	}

	public void setInstruction(int instruction) {
		this.instruction = instruction;
	}

	public String getOpcode() {
		return opcode;
	}

	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}

	public String getImm() {
		return imm;
	}

	public void setImm(String imm) {
		this.imm = imm;
	}

	public String getDsIm() {
		return ds;
	}

	public void setDsIm(String ds) {
		this.ds = ds;
	}

	public String getRS1Store() {
		return rs1Store;
	}

	public void setRS1Store(String rs1Store) {
		this.rs1Store = rs1Store;
	}

	public String getRS2Store() {
		return rs2Store;
	}

	public void setRS2Store(String rs2Store) {
		this.rs2Store = rs2Store;
	}

	public String getRS1Ld() {
		return rs1Ld;
	}

	public void setRS1Ld(String rs1Ld) {
		this.rs1Ld = rs1Ld;
	}

	public String getRS2Ld() {
		return rs2Ld;
	}

	public void setRS2Ld(String rs2Ld) {
		this.rs2Ld = rs2Ld;
	}


}
