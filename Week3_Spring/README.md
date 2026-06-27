# Week 3 - Spring Core and Spring Data JPA

## spring core

**exercise 1 - basic spring app**
- ApplicationContext loading beans from applicationContext.xml
- BeanFactory vs ApplicationContext

**exercise 2 - dependency injection**
- constructor injection
- setter injection
- field injection with @Autowired
- @Configuration and @Bean
- @Qualifier to pick between two beans of same type

**additional - spring aop**
- @Aspect with @Before, @AfterReturning, @AfterThrowing
- @Around for performance timing

## spring data jpa

**entities**
- Country with @Entity, @Table, @Column, @Id, @GeneratedValue
- @CreatedDate and @LastModifiedDate auditing
- Department and Employee with @OneToMany and @ManyToOne

**repository**
- extends JpaRepository - get save delete findAll for free
- derived query methods - findByCode, findByContinent etc
- @Query for custom JPQL and native SQL
- @Modifying for update/delete queries

**service**
- pagination with PageRequest.of(page, size, sort)
- sorting with Sort.by().ascending()
- @Transactional(readOnly = true)

## how to run
```bash
# spring core
cd SpringCore_Maven
mvn clean compile exec:java -Dexec.mainClass="com.cognizant.spring.SpringCoreApp"

# spring data jpa
cd SpringDataJPA
mvn clean spring-boot:run
# h2 console: http://localhost:8080/h2-console
```
