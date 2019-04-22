import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystemException;

public class LocalFile implements PCDFile{
	public static final int NUM_READERS=3;

	@Override
	public String read() throws FileSystemException {
		try {
			return new String(read(0, length()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void write(String dataToWrite) throws FileSystemException {
		write(dataToWrite.getBytes(), 0);
	}
	
	public byte[] read(int offset, int length) throws FileSystemException {
		ByteBuffer buffer = null;
		RandomAccessFile aFile = null;
		try {
			aFile = new RandomAccessFile(getFullPath(), "r");
			FileChannel channel = aFile.getChannel();
			if (offset > aFile.length()) {
				return null;
			}
			if (offset + length > aFile.length()) {
				length = (int) aFile.length() - offset;
			}
			channel.position(offset);
			buffer = ByteBuffer.allocate(length);
			channel.read(buffer);
		} catch (IOException e) {
			throw new FileSystemException(e.getMessage());
		} finally {
			if (aFile != null) {
				try {
						aFile.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return buffer.array();
	}
	
	private String getFullPath() {
		// TODO Auto-generated method stub
		return null;
	}

	public void write(byte[] dataToWrite, int offset) throws FileSystemException {
		RandomAccessFile aFile = null;
		try {
			aFile = new RandomAccessFile(getFullPath(), "rw");
			FileChannel channel = aFile.getChannel();
			if (offset > aFile.length()) {
				offset = (int) aFile.length();
				ByteBuffer buf = ByteBuffer.wrap(dataToWrite);
				channel.position(offset);
				while (buf.hasRemaining()) {
					channel.write(buf);
				}
			}
		} catch (IOException e) {
			throw new FileSystemException(e.getMessage());
		} finally {
			if (aFile != null) {
				try {
					aFile.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean readLock() throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean writeLock() throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void readUnlock() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeUnlock() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exists() throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int length() throws FileSystemException, IOException {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
