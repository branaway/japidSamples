package etc;

import java.io.File;
import java.util.List;

import play.data.Upload;
import play.data.binding.TypeBinder;
import play.mvc.Http.Request;

/**
 * locate the File object created during multipart/form-data parser. Modeled after the FileBinder.
 * 
 * @author ran
 *
 */
public class JavaUtils {
	
	public static File bindFile(String fieldName) {
		Request req = Request.current();
		if (req != null && req.args != null) {
			List<Upload> uploads = (List<Upload>) req.args.get("__UPLOADS");
			if (uploads != null) {
				for (Upload upload : uploads) {
					if (fieldName.equals(upload.getFieldName())) {
						if (upload.getFileName().trim().length() > 0) {
							File file = upload.asFile();
							return file;
						}
					}
				}
			}
		}
		return null;
	}
}
