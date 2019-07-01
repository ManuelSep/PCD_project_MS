import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystemException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class LocalFile extends Thread implements PCDFile{
	public int currentReaders = 0;
	public static final int MAX_NUM_READERS = 3;
	private File file; 
	
	public LocalFile(File file){
		this.file = file;
		try {
			file.createNewFile();
		}catch(IOException exc) {
			exc.getStackTrace();
		}
	}

	public File getFile() throws IOException{
		return file;
	}

	@Override
	public String read() throws FileSystemException , IOException, InterruptedException {
		String result = "";
		synchronized (this) {
			if(currentReaders ++ <= MAX_NUM_READERS) {
				System.out.println("Reading the file");
				Scanner sc = new Scanner(file);
				while (sc.hasNextLine()) {
					result = sc.nextLine();
				}
				currentReaders--;
				notifyAll();
			}
			else {
				System.out.println("Waiting for available reading space");
				wait();
				read();
			}
		}
		return result;
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
	public String getFileName() {
		return file.getName();
	}

	@Override
	public boolean readLock() throws IOException {
		return currentReaders ++ < MAX_NUM_READERS;
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
		return (int)file.length();
	}
	
}
