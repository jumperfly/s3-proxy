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
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.InputStream;
import java.io.OutputStream;

@RestController
public class S3ProxyController {
    @Autowired
    private AmazonS3 s3Client;

    @RequestMapping("/s3/{bucket}/**")
    public StreamingResponseBody get(@PathVariable String bucket, WebRequest request) {
        String key = request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE,
                RequestAttributes.SCOPE_REQUEST).toString().substring(bucket.length() + 5);
        S3Object object = s3Client.getObject(new GetObjectRequest(bucket, key));

        return out -> {
            try (InputStream in = object.getObjectContent()) {
                IOUtils.copy(in, out);
            }
        };
    }
}
