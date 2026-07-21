# AWS Cloud Fundamentals - My Notes

## cloud basics

traditional IT vs cloud:
- traditional: buy server, set up in office, maintain it yourself
- cloud: rent servers on demand, pay only what you use, no maintenance

**service models:**
- IaaS (Infrastructure as a Service) - you manage OS and above, aws manages hardware. eg: EC2
- PaaS (Platform as a Service) - you manage only app and data, aws manages everything else. eg: Elastic Beanstalk
- SaaS (Software as a Service) - just use the software, aws manages everything. eg: Gmail

**deployment models:**
- Public cloud - shared infrastructure, anyone can use (aws, azure, gcp)
- Private cloud - dedicated for one org
- Hybrid - mix of both

**major cloud providers:** AWS, Azure (Microsoft), GCP (Google)

---

## EC2 - Elastic Compute Cloud

basically a virtual machine in the cloud

### what i learned
- instance types: t2.micro (free tier), t3.medium, etc
- AMI (Amazon Machine Image) - template to launch EC2
- security groups - like a firewall, control inbound/outbound traffic
- key pairs - .pem file to SSH into the instance

### how to launch EC2
1. go to EC2 dashboard → Launch Instance
2. choose AMI (Amazon Linux 2 or Ubuntu)
3. choose instance type (t2.micro for free tier)
4. configure security group - add rule for SSH (port 22) and HTTP (port 80)
5. create or select key pair
6. launch

### connect via SSH
```bash
chmod 400 my-key.pem
ssh -i my-key.pem ec2-user@<public-ip>
```

---

## S3 - Simple Storage Service

object storage - store any file (images, videos, backups, static websites)

### key concepts
- Bucket - container for objects (like a folder)
- Object - the actual file stored
- bucket names must be globally unique

### storage classes
- Standard - frequently accessed data
- Intelligent-Tiering - automatically moves between tiers
- Standard-IA - infrequently accessed but needs fast retrieval
- Glacier - archival, cheap but slow retrieval

### what i did
```
created a bucket
uploaded a file
made it public
accessed via URL: https://<bucket>.s3.amazonaws.com/<filename>
```

---

## VPC - Virtual Private Cloud

your own private network inside AWS

### components
- Subnet - divide VPC into smaller ranges
  - Public subnet - has internet access
  - Private subnet - no direct internet access
- Internet Gateway - connects VPC to internet
- NAT Gateway - lets private subnet access internet (outbound only)
- Route Table - rules for where traffic goes
- Security Groups - instance level firewall

---

## RDS - Relational Database Service

managed database service - aws handles backups, patching, failover

### supported engines
MySQL, PostgreSQL, MariaDB, SQL Server, Oracle, Aurora

### why use RDS instead of installing MySQL on EC2
- automatic backups
- Multi-AZ for high availability (auto failover)
- read replicas for performance
- no need to manage OS or DB patches

### connecting spring boot to RDS
```properties
spring.datasource.url=jdbc:mysql://<rds-endpoint>:3306/mydb
spring.datasource.username=admin
spring.datasource.password=yourpassword
```

---

## Lambda - Serverless Functions

run code without managing servers, pay only per execution

### how it works
1. write a function (Java, Python, Node.js etc)
2. upload to Lambda
3. configure a trigger (API Gateway, S3 event, schedule etc)
4. Lambda runs on demand, scales automatically

### example - Lambda triggered by API Gateway
```java
public class HelloHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context ctx) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        response.setStatusCode(200);
        response.setBody("Hello from Lambda!");
        return response;
    }
}
```

---

## API Gateway

managed service to create and publish REST APIs

### what i understood
- create REST API
- define resources and methods (GET /users, POST /users etc)
- connect each method to a backend (Lambda, EC2, etc)
- deploy to a stage (dev, staging, prod)
- throttling and rate limiting built in

### stages
```
dev   → https://xxx.execute-api.region.amazonaws.com/dev/users
prod  → https://xxx.execute-api.region.amazonaws.com/prod/users
```

---

## DynamoDB - NoSQL Database

key-value and document database, fully managed

### when to use DynamoDB vs RDS
- DynamoDB: high scale, low latency, flexible schema, gaming/IoT/carts
- RDS: complex queries, joins, transactions, financial data

### key concepts
- Table, Item (row), Attribute (column)
- Primary Key:
  - Partition Key only (simple)
  - Partition Key + Sort Key (composite) - allows multiple items per partition

---

## ECS - Elastic Container Service

run Docker containers on AWS

### ECS vs EC2
- EC2: manage VMs yourself
- ECS: just give it a Docker image, AWS manages where/how to run it

### worked example
```bash
# push your docker image to ECR (Elastic Container Registry)
aws ecr create-repository --repository-name my-spring-app
docker tag my-spring-app:latest <ecr-url>/my-spring-app:latest
docker push <ecr-url>/my-spring-app:latest
# then create ECS task definition pointing to this image
```

---

## Elastic Load Balancer

distributes incoming traffic across multiple EC2 instances

- ALB (Application Load Balancer) - Layer 7, HTTP/HTTPS, path based routing
- NLB (Network Load Balancer) - Layer 4, TCP/UDP, ultra high performance

### target groups and health checks
- target group = group of EC2 instances
- ALB checks health of each instance, only sends traffic to healthy ones
