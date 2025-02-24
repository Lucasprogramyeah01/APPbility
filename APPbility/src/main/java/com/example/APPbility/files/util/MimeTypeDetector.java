package com.example.APPbility.files.util;

import org.springframework.core.io.Resource;

public interface MimeTypeDetector {

    String getMimeType(Resource resource);

}
