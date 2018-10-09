package demo2.V2;

public class BlockChainPrimitiveTEST {

	public static void main(String[] args) throws Exception {
		
		BlockChainPrimitive LIFEChain = new BlockChainPrimitive();
		HashAlgorithm HA = new HashAlgorithm();
		
		String file1 = FileReadIn.readInFile("C:/Users/mburg/Desktop/Capstone Project/SampleMedicalTransaction.xml");
		String file2 = FileReadIn.readInFile("C:/Users/mburg/Desktop/Capstone Project/SampleMedicalTransaction2.xml");
		
		StringBuffer transaction1 = HA.Hash(file1);
		StringBuffer transaction2 = HA.Hash(file2);
		
		LIFEChain.addBlock(transaction1);
		LIFEChain.addBlock(transaction2);
			

		System.out.println(LIFEChain.ToString());
	}

}
