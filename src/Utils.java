import java.io.Serializable;

public class Utils {
}

class FileActions implements Serializable {
    private String fileName;

    public FileActions(String fileName) {
        this.fileName = fileName;
    }

    String getFileName() {
        return fileName;
    }
}

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

    public String getMessage() {
        return message;
    }
}

class SuccessDeletingFile extends Success {
    SuccessDeletingFile(String fileName) {
        super("success deleting file " + fileName);
    }
}

class SuccessSendingFileSize extends Success {
    SuccessSendingFileSize(String size, String fileName) {
        super("size of file " + fileName + " is " + " size");
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
}

class CommandNotFound extends Failure {
    CommandNotFound() {
        super("Command not found");
    }
}