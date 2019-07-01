import java.io.File;
import java.io.FileNotFoundException;
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
		System.out.println("Rechaed File exists");
		return !files.stream().filter(p -> p.getFileName().equals(name)).collect(Collectors.toList()).isEmpty();
	}

	@Override
	public PCDFile newFile(String name) throws FileSystemException, IOException {
		System.out.println("Reached newFile");
		LocalFile newFile = new LocalFile(new File(rootName + "/" + name));
		System.out.println("new file:" + newFile.getFile());
		files.add(newFile);
		return newFile;
				
	}

	@Override
	public void delete(String name) throws FileSystemException, IOException {
		if (getFile(name).getFile().delete()) {
			files.removeIf(p -> p.getFileName().equals(rootName + "/" + name));
		} else {
			throw new FileNotFoundException("File " + name + " not found.");
		}
	}

	@Override
	public String[] getDirectoryListing() throws FileSystemException, IOException {
		System.out.println("Getting all filenames from LocaLDirectory");
		String[] names = new String[files.size()];
		int i = 0;
		for (LocalFile file : files) {
			names[i] = file.getFileName();
			i++;
		}
		return names;
	}

	@Override
	public PCDFile getFile(String name) throws FileSystemException, IOException {
		if(fileExists(name)){
			System.out.println("EXISTS");
			return files.stream().filter(p -> p.getFileName().equals(name)).collect(Collectors.toList()).get(0);
		}else
			throw new FileSystemException("The file named " + name + " does not exist");
	}	
	
	public static void main(String[] args) {
		LocalDirectory test = new LocalDirectory("/Users/franciscoazevedo/code/java/PCD_project_MS/src/text_files");

		try {
			test.delete("testFile.txt");
			// System.out.println(test.files.size());
		} catch (FileSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getRootName() {
		return rootName;
	}
}
