#!/bin/bash
echo "=========== Initializing LocalStack S3 Bucket ==========="

awslocal s3api head-bucket --bucket research-connect-publications --region ap-south-1 2>/dev/null || \
awslocal s3api create-bucket \
  --bucket research-connect-publications \
  --region ap-south-1 \
  --create-bucket-configuration LocationConstraint=ap-south-1

echo "=========== Bucket Initialization Complete ==========="