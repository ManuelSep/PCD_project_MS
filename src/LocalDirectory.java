import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class LocalDirectory implements PCDDirectory {
	private LinkedList<LocalFile> files = new LinkedList<>();
	private String rootName;
	
	public LocalDirectory(String rootName) {
		this.rootName = rootName;
		File directory = new File(rootName);
		
		if(!directory.isDirectory()) {
			throw new IllegalArgumentException("It needs a directory! It's wrong or empty");
		}
		System.out.println(directory.toString());
		System.out.println(directory.listFiles());
		for (File f : directory.listFiles()){
			files.add(new LocalFile(f));
		}
	}
	public LocalDirectory() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean fileExists(String name) throws IOException {
		return !files.stream().filter(p -> p.getName() == name).collect(Collectors.toList()).isEmpty();
	}

	@Override
	public PCDFile newFile(String name) throws FileSystemException, IOException {
		LocalFile newFile = new LocalFile(new File(rootName + "/" + name));
		files.add(newFile);
		return newFile;
				
	}

	@Override
	public void delete(String name) throws FileSystemException, IOException {
		files.removeIf(p -> p.getName() == name);
	}

	@Override
	public String[] getDirectoryListing() throws FileSystemException, IOException {
		String[] names = new String[files.size()];
		int i = 0;
		for (LocalFile file : files) {
			names[i] = file.getName();
			i++;
		}
		return names;
	}

	@Override
	public PCDFile getFile(String name) throws FileSystemException, IOException {
		if(fileExists(name)) 
			return files.stream().filter(p -> p.getName() == name).collect(Collectors.toList()).get(0);
		else
			throw new FileSystemException("The file named " + name + " does not exist");
	}	
	
	public static void main(String[] args) {
		LocalDirectory test = new LocalDirectory("/Users/manelsepulveda/eclipse-workspace/PCD_project_MS/src/text_files");
		try {
			for (String name : test.getDirectoryListing()) {
				System.out.println(name);
			}
		} catch (FileSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void testeMessage() {
		LocalDirectory test = new LocalDirectory("/Users/manelsepulveda/eclipse-workspace/PCD_project_MS/src/text_files");
		try {
			for (String name : test.getDirectoryListing()) {
				System.out.println(name);
			}
		} catch (FileSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
