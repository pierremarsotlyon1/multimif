package viewModel;

/**
 * Created by pierremarsot on 24/11/2016.
 */
public class DeleteFile
{
    private boolean success;
    private String message;

    public DeleteFile()
    {

    }

    public DeleteFile(boolean success, String message)
    {
        this();
        this.success = success;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
