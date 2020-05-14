# HHH-13761

## Steps to Reproduce

To reproduce the error reported at [HHH-13671](https://hibernate.atlassian.net/browse/HHH-13671), follow the steps below.

 1. Setup an Oracle database (e.g., Oracle Express) and create the following sequences:
    ```sql
    CREATE SEQUENCE test_inc; -- Incrementing (default)
    CREATE SEQUENCE test_dec INCREMENT BY -1; -- Decrementing
    ```
 2. Provide the database configuration in `application.yml`.
 3. Start this application, for instance by running the following commands:
    ```bash
    mvn clean package
    java -jar target/HHH-13761.jar
    ```
    
The output should then report the following stack trace:

```
java.sql.SQLException: Numeric Overflow
        at oracle.jdbc.driver.NumberCommonAccessor.throwOverflow(NumberCommonAccessor.java:4139) ~[ojdbc8-19.3.0.0.jar!/:19.3.0.0.0]
        at oracle.jdbc.driver.NumberCommonAccessor.getLong(NumberCommonAccessor.java:636) ~[ojdbc8-19.3.0.0.jar!/:19.3.0.0.0]
        at oracle.jdbc.driver.GeneratedStatement.getLong(GeneratedStatement.java:208) ~[ojdbc8-19.3.0.0.jar!/:19.3.0.0.0]
        at oracle.jdbc.driver.GeneratedScrollableResultSet.getLong(GeneratedScrollableResultSet.java:261) ~[ojdbc8-19.3.0.0.jar!/:19.3.0.0.0]
        at oracle.jdbc.driver.GeneratedResultSet.getLong(GeneratedResultSet.java:560) ~[ojdbc8-19.3.0.0.jar!/:19.3.0.0.0]
        at com.zaxxer.hikari.pool.HikariProxyResultSet.getLong(HikariProxyResultSet.java) ~[HikariCP-3.4.3.jar!/:na]
        at org.hibernate.tool.schema.extract.internal.SequenceInformationExtractorLegacyImpl.resultSetMinValue(SequenceInformationExtractorLegacyImpl.java:134) ~[hibernate-core-5.4.15.Final.jar!/:5.4.15.Final]
        at org.hibernate.tool.schema.extract.internal.SequenceInformationExtractorLegacyImpl.extractMetadata(SequenceInformationExtractorLegacyImpl.java:60) ~[hibernate-core-5.4.15.Final.jar!/:5.4.15.Final]
        at org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentImpl.sequenceInformationList(JdbcEnvironmentImpl.java:403) ~[hibernate-core-5.4.15.Final.jar!/:5.4.15.Final]
        at org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentImpl.<init>(JdbcEnvironmentImpl.java:268) ~[hibernate-core-5.4.15.Final.jar!/:5.4.15.Final]
        at org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator.initiateService(JdbcEnvironmentInitiator.java:114) ~[hibernate-core-5.4.15.Final.jar!/:5.4.15.Final]
        at org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator.initiateService(JdbcEnvironmentInitiator.java:35) ~[hibernate-core-5.4.15.Final.jar!/:5.4.15.Final]
        at org.hibernate.boot.registry.internal.StandardServiceRegistryImpl.initiateService(StandardServiceRegistryImpl.java:101) ~[hibernate-core-5.4.15.Final.jar!/:5.4.15.Final]
        at org.hibernate.service.internal.AbstractServiceRegistryImpl.createService(AbstractServiceRegistryImpl.java:263) ~[hibernate-core-5.4.15.Final.jar!/:5.4.15.Final]
        at org.hibernate.service.internal.AbstractServiceRegistryImpl.initializeService(AbstractServiceRegistryImpl.java:237) ~[hibernate-core-5.4.15.Final.jar!/:5.4.15.Final]
        at org.hibernate.service.internal.AbstractServiceRegistryImpl.getService(AbstractServiceRegistryImpl.java:214) ~[hibernate-core-5.4.15.Final.jar!/:5.4.15.Final]
        at org.hibernate.id.factory.internal.DefaultIdentifierGeneratorFactory.injectServices(DefaultIdentifierGeneratorFactory.java:152) ~[hibernate-core-5.4.15.Final.jar!/:5.4.15.Final]
        at org.hibernate.service.internal.AbstractServiceRegistryImpl.injectDependencies(AbstractServiceRegistryImpl.java:286) ~[hibernate-core-5.4.15.Final.jar!/:5.4.15.Final]
        at org.hibernate.service.internal.AbstractServiceRegistryImpl.initializeService(AbstractServiceRegistryImpl.java:243) ~[hibernate-core-5.4.15.Final.jar!/:5.4.15.Final]
        at org.hibernate.service.internal.AbstractServiceRegistryImpl.getService(AbstractServiceRegistryImpl.java:214) ~[hibernate-core-5.4.15.Final.jar!/:5.4.15.Final]
        at org.hibernate.boot.internal.InFlightMetadataCollectorImpl.<init>(InFlightMetadataCollectorImpl.java:176) ~[hibernate-core-5.4.15.Final.jar!/:5.4.15.Final]
        at org.hibernate.boot.model.process.spi.MetadataBuildingProcess.complete(MetadataBuildingProcess.java:118) ~[hibernate-core-5.4.15.Final.jar!/:5.4.15.Final]
        at org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl.metadata(EntityManagerFactoryBuilderImpl.java:1214) ~[hibernate-core-5.4.15.Final.jar!/:5.4.15.Final]
        at org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl.build(EntityManagerFactoryBuilderImpl.java:1245) ~[hibernate-core-5.4.15.Final.jar!/:5.4.15.Final]
        at org.springframework.orm.jpa.vendor.SpringHibernateJpaPersistenceProvider.createContainerEntityManagerFactory(SpringHibernateJpaPersistenceProvider.java:58) ~[spring-orm-5.2.6.RELEASE.jar!/:5.2.6.RELEASE]
        at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.createNativeEntityManagerFactory(LocalContainerEntityManagerFactoryBean.java:365) ~[spring-orm-5.2.6.RELEASE.jar!/:5.2.6.RELEASE]
        at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.buildNativeEntityManagerFactory(AbstractEntityManagerFactoryBean.java:391) ~[spring-orm-5.2.6.RELEASE.jar!/:5.2.6.RELEASE]
        at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.afterPropertiesSet(AbstractEntityManagerFactoryBean.java:378) ~[spring-orm-5.2.6.RELEASE.jar!/:5.2.6.RELEASE]
        at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.afterPropertiesSet(LocalContainerEntityManagerFactoryBean.java:341) ~[spring-orm-5.2.6.RELEASE.jar!/:5.2.6.RELEASE]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1855) ~[spring-beans-5.2.6.RELEASE.jar!/:5.2.6.RELEASE]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1792) ~[spring-beans-5.2.6.RELEASE.jar!/:5.2.6.RELEASE]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:595) ~[spring-beans-5.2.6.RELEASE.jar!/:5.2.6.RELEASE]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:517) ~[spring-beans-5.2.6.RELEASE.jar!/:5.2.6.RELEASE]
        at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:323) ~[spring-beans-5.2.6.RELEASE.jar!/:5.2.6.RELEASE]
        at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:226) ~[spring-beans-5.2.6.RELEASE.jar!/:5.2.6.RELEASE]
        at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:321) ~[spring-beans-5.2.6.RELEASE.jar!/:5.2.6.RELEASE]
        at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:202) ~[spring-beans-5.2.6.RELEASE.jar!/:5.2.6.RELEASE]
        at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1108) ~[spring-context-5.2.6.RELEASE.jar!/:5.2.6.RELEASE]
        at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:868) ~[spring-context-5.2.6.RELEASE.jar!/:5.2.6.RELEASE]
        at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:550) ~[spring-context-5.2.6.RELEASE.jar!/:5.2.6.RELEASE]
        at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:747) ~[spring-boot-2.2.7.RELEASE.jar!/:2.2.7.RELEASE]
        at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:397) ~[spring-boot-2.2.7.RELEASE.jar!/:2.2.7.RELEASE]
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:315) ~[spring-boot-2.2.7.RELEASE.jar!/:2.2.7.RELEASE]
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:1226) ~[spring-boot-2.2.7.RELEASE.jar!/:2.2.7.RELEASE]
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:1215) ~[spring-boot-2.2.7.RELEASE.jar!/:2.2.7.RELEASE]
        at org.hibernate.hhh13761.Application.main(Application.java:22) ~[classes!/:0.0.1-SNAPSHOT]
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:na]
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
        at java.base/java.lang.reflect.Method.invoke(Method.java:566) ~[na:na]
        at org.springframework.boot.loader.MainMethodRunner.run(MainMethodRunner.java:48) ~[HHH-13761.jar:0.0.1-SNAPSHOT]
        at org.springframework.boot.loader.Launcher.launch(Launcher.java:87) ~[HHH-13761.jar:0.0.1-SNAPSHOT]
        at org.springframework.boot.loader.Launcher.launch(Launcher.java:51) ~[HHH-13761.jar:0.0.1-SNAPSHOT]
        at org.springframework.boot.loader.JarLauncher.main(JarLauncher.java:52) ~[HHH-13761.jar:0.0.1-SNAPSHOT]
```

## Thoughts about SequenceInformation

### Specific to Oracle
 
In addition, the application shows some indications on the conversion of the range of 
[Oracle's sequences](https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-SEQUENCE.html):

```
Oracle min value: -999999999999999999999999999
Oracle max value: 9999999999999999999999999999
Oracle min value digits: 27
Oracle max value digits: 28
Oracle min value as Long: 6930898827444486145
Oracle max value as Long: 4477988020393345023
Oracle min value as BigInteger: -999999999999999999999999999
Oracle max value as BigInteger: 9999999999999999999999999999
```

In consequence, the sequence's ranges cannot be accurately represented in `java.lang.Long`,
which might cause problems depending on how the extracted inforamtion is used.

### General

Based on the above insights, to be able to support Oracle's default sequence settings,
in `org.hibernate.tool.schema.extract.spi.SequenceInformation` would require a change of the return type of
`getStartValue`, `getMinValue`, `getMaxValue`, and `getIncrementValue`.

Regarding `getIncrementValue`, I would furthermore suggest changing the behaviour 
in that a negative number should *not* be used to indicate a non-extractable increment value.
At least H2, Postgres and Oracle all support negative increment values, which are now not representable.
Therefore, I would use `null`` instead, which also conforms to the behaviour of the other methods.

Furthermore, I observed that in our setting the error did not occur with Hibernate 5.4.13.Final and above,
which seems to be caused by [a fix for HHH-13322](https://github.com/hibernate/hibernate-orm/pull/3270).
However, I'm not sure if this goes into the right direction,
since we use a dedicated application user different from the table owner for increased security, 
and I suspect this can result in no sequence information at all.
