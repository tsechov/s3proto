package sun.net.www.protocol.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class S3UrlConnection extends URLConnection {

    private final AmazonS3 s3;
    private final GetObjectRequest request;

    public S3UrlConnection(URL url, AmazonS3 s3, GetObjectRequest request) {
        super(url);
        this.s3 = s3;
        this.request = request;
    }

    @Override
    public InputStream getInputStream() throws IOException {

        S3Object s3obj = s3.getObject(request);

        return s3obj.getObjectContent();

    }


    @Override
    public void connect() throws IOException {

    }


}
