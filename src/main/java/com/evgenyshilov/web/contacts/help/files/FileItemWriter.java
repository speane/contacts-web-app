package com.evgenyshilov.web.contacts.help.files;

import com.evgenyshilov.web.contacts.exceptions.CustomException;
import org.apache.commons.fileupload.FileItem;

import java.io.File;

/**
 * Created by Evgeny Shilov on 01.10.2016.
 */
public class FileItemWriter {
    public void writeFileItem(FileItem item, String path) throws CustomException {
        try {
            File file = new File(path);
            item.write(file);
        } catch (Exception e) {
            throw new CustomException("Can't write file item: ", e);
        }
    }
}
