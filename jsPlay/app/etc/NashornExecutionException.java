package etc;

import java.util.Arrays;
import java.util.List;

import play.exceptions.PlayException;
import play.exceptions.SourceAttachment;
import play.vfs.VirtualFile;

/**
 * A java compilation error
 */
public class NashornExecutionException extends PlayException implements SourceAttachment {

    private String problem;
    private VirtualFile source;
    private Integer line;
    private Integer start;
    private Integer end;

    public NashornExecutionException(String problem) {
        super(problem);
        this.problem = problem;
    }

    public NashornExecutionException(VirtualFile source, String problem, int line, int start, int end) {
        super(problem);
        this.problem = problem;
        this.line = line;
        this.source = source;
        this.start = start;
        this.end = end;
    }

    @Override
    public String getErrorTitle() {
        return String.format("JavaScript Execution Error");
    }

    @Override
    public String getErrorDescription() {
        return String.format("A problem occured while running the file <strong>%s</strong>.\nError raised is : <strong>%s</strong>", isSourceAvailable() ? source.relativePath() : "", problem.toString().replace("<", "&lt;"));
    }
    
    @Override
    public String getMessage() {
        return problem;
    }

    @Override
    public List<String> getSource() {
        String sourceCode = source.contentAsString();
        if(start != -1 && end != -1) {
            if(start.equals(end)) {
                sourceCode = sourceCode.substring(0, start + 1) + "↓" + sourceCode.substring(end + 1);
            } else {
                sourceCode = sourceCode.substring(0, start) + "\000" + sourceCode.substring(start, end + 1) + "\001" + sourceCode.substring(end + 1);
            }
        }
        return Arrays.asList(sourceCode.split("\n"));
    }

    @Override
    public Integer getLineNumber() {
        return line;
    }

    @Override
    public String getSourceFile() {
        return source.relativePath();
    }

    public Integer getSourceStart() {
        return start;
    }

    public Integer getSourceEnd() {
        return end;
    }

    @Override
    public boolean isSourceAvailable() {
        return source != null && line != null;
    }
    
    /**
     * @author bran
     * @return
     */
    public VirtualFile getSourceVirtualFile() {
    	return source;
    }
}
