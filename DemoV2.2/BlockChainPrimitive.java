package demo2.V2;


import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

public class BlockChainPrimitive{
		ArrayList<Block> _chain;
		int _size = 1;
		HashAlgorithm HA = new HashAlgorithm();
		
	public BlockChainPrimitive() throws NoSuchAlgorithmException{
		_chain = new ArrayList<Block>();
		createGenesisBlock();

	}
	
	public void createGenesisBlock() throws NoSuchAlgorithmException{
		Block GB = new Block(0, new Date(), new StringBuffer(""), new StringBuffer(""));
		_chain.add(GB);
	}
	
	public int size() {
		return _size;
	}
	
	public Block get(int i) {
		return _chain.get(i);
	}
	
	public Block getLatestBlock() {
		return _chain.get(_chain.size()-1);
	}
	
	public void addBlock(StringBuffer transaction) throws NoSuchAlgorithmException{
		
		
		StringBuffer PBHash = getLatestBlock()._blockHash;
		int NBIndex = getLatestBlock()._index+1;
		Date NBDate = new Date();
		StringBuffer[] NewHashArr = {transaction, PBHash};
		StringBuffer NewHash = HA.Hash(NewHashArr);
		
		Block NewBlock = new Block(NBIndex, NBDate, NewHash, PBHash);
		_chain.add(NewBlock);
		_size = _size + 1;
	}
	

	public String ToString() {
		ArrayList<String> stringRep = new ArrayList<String>();
		
		for(int i = 0; i< this.size(); i++) {
			stringRep.add(this.get(i).toString());
			stringRep.add("\n");
		}
		
		return stringRep.toString();
	}

}
