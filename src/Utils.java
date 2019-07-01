import java.io.Serializable;


class FileActions implements Serializable {
    private String fileName;

    public FileActions(String fileName) {
        this.fileName = fileName;
    }

    String getFileName() {
        return fileName;
    }
}
class AllFiles implements Serializable{}

class EditFile extends FileActions {
    EditFile(String fileName) {
        super(fileName);
    }
}

class ShowFile extends FileActions{
    ShowFile(String fileName) {
        super(fileName);
    }
}

class DeleteFile extends FileActions {
    DeleteFile(String fileName) {
        super(fileName);
    }
}

class SizeOfFile extends FileActions {
    SizeOfFile(String fileName) {
        super(fileName);
    }
}

class NewFile extends FileActions {
    NewFile(String fileName) {
        super(fileName);
    }
}

class Success implements Serializable{
    private String message;

    Success(String message) {
        this.message = message;
    }

    String getMessage() {
        return message;
    }
}

class SuccessDeletingFile extends Success {
    SuccessDeletingFile(String fileName) {
        super("success deleting file " + fileName);
    }
}

class SuccessSendingFileSize extends Success {
    private String size;
    SuccessSendingFileSize(String fileName, String size) {
        super("size of file " + fileName + " is " + size + "B");
        this.size = size;
    }

    public String getSizeFile(){
       return size;
    }
}

class SuccessCreatingFile extends Success {
    SuccessCreatingFile(String fileName) {
        super("Success creating file: " + fileName);
    }
}

class Failure implements Serializable{
    private String message;

    Failure(String message) {
        this.message = message;
    }

    String getMessage() {
        return message;
    }
}

class CommandNotFound extends Failure {
    CommandNotFound() {
        super("Command not found");
    }
}

