package uk.co.jumperfly.aws.s3proxy.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.InputStream;
import java.io.OutputStream;

@RestController
public class S3ProxyController {
    @Autowired
    private AmazonS3 s3Client;

    @RequestMapping("/s3/{bucket}/{key:.+}")
    public StreamingResponseBody get(@PathVariable String bucket, @PathVariable String key) {
        S3Object object = s3Client.getObject(new GetObjectRequest(bucket, key));

        return out -> {
            try (InputStream in = object.getObjectContent()) {
                IOUtils.copy(in, out);
            }
        };
    }
}
