# S3 Proxy
Simple Spring Boot application to act as an HTTP proxy to access S3 resources.

Intended usage is to run inside AWS to access S3 over HTTP without the need to make the bucket public.

S3 can be accessed at path /s3/{bucket}/{key}

## Running inside AWS
No further configuration is required provided EC2 or ECS Task IAM roles are configured.

## Running outside
Use the AWS_ACCESS_KEY_ID and AWS_SECRET_KEY environment variables.
