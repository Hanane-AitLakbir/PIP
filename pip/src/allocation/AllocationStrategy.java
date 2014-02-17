package allocation;


import coding.Coder;


import utilities.Cloud;

public interface AllocationStrategy {

	public boolean upLoad(String fileName, int nbrOfPackets, String[] clouds, Coder coder);
}
