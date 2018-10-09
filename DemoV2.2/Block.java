package demo2.V2;

import java.util.Date;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

public class Block {

	HashAlgorithm HA = new HashAlgorithm();
	
	public StringBuffer _previousHash;
	public StringBuffer _transaction;
	public StringBuffer _blockHash;
	public int _index;
	public Date _date;
	public Timestamp _timeStamp;

	public Block(int index, Date date, StringBuffer transaction, StringBuffer prevHash)
			throws NoSuchAlgorithmException {

		_previousHash = prevHash;
		_transaction = transaction;
		_date = date;
		_index = index;
		_timeStamp = new Timestamp(_date.getTime());
		_blockHash = HA.Hash(transaction);
		
	}
	
	public int getIndex() {
		return _index;
	}

	public StringBuffer getPreviousHash() {
		return _previousHash;
	}

	public StringBuffer getTransaction() {
		return _transaction;
	}

	public StringBuffer getBlockHash() {
		return _blockHash;
	}

	public Timestamp getTimeStamp() {
		return _timeStamp;
	}

	public String toString() {
		return String.format("Block Number: %d, Hashed to: %s on %s", _index, _blockHash.toString(),
				_timeStamp.toString());
	}
}
