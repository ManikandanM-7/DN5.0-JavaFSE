# Deploying Spring Boot App to AWS - Practice Steps

## what i tried

### deploy spring boot to EC2

1. launch EC2 (Ubuntu 22.04, t2.micro)
2. SSH in and install java
```bash
sudo apt update
sudo apt install openjdk-17-jdk -y
java -version
```

3. copy the jar file
```bash
# from local machine
scp -i my-key.pem target/spring-rest-handson-1.0.0.jar ubuntu@<ec2-ip>:/home/ubuntu/
```

4. run the app
```bash
ssh into EC2
java -jar spring-rest-handson-1.0.0.jar
```

5. configure security group to allow port 8080 inbound
6. access from browser: http://<ec2-ip>:8080/api/hello

---

### S3 practice

```bash
# install aws cli
aws configure
# enter access key, secret key, region (ap-south-1 for India)

# create bucket
aws s3 mb s3://mani-test-bucket-mku

# upload file
aws s3 cp myfile.txt s3://mani-test-bucket-mku/

# list files
aws s3 ls s3://mani-test-bucket-mku/

# download
aws s3 cp s3://mani-test-bucket-mku/myfile.txt ./downloaded.txt
```

---

### RDS with Spring Boot

1. create RDS instance (MySQL, t3.micro free tier)
2. set master username and password
3. make it publicly accessible (for testing only)
4. update security group to allow port 3306 from my IP
5. update application.properties:

```properties
spring.datasource.url=jdbc:mysql://<rds-endpoint>:3306/cognizantdb
spring.datasource.username=admin
spring.datasource.password=Admin1234!
spring.jpa.hibernate.ddl-auto=update
```

6. test connection from IntelliJ database tool

---

### Lambda function test

created a simple Lambda in the AWS console (Java runtime)
- handler: `com.example.HelloHandler::handleRequest`
- trigger: API Gateway
- tested with test event in console

got the response: "Hello from Lambda!"

also tried connecting Lambda to DynamoDB to read/write items

---

## important aws concepts for the exam

- **Region** - physical location (ap-south-1 = Mumbai)
- **Availability Zone** - isolated data center within a region
- **IAM** - Identity and Access Management, controls who can do what
- **CloudWatch** - monitoring and logs for AWS services
- **Auto Scaling** - automatically add/remove EC2 instances based on load
