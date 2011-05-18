
beans = {
  dataSource(com.mchange.v2.c3p0.ComboPooledDataSource){ bean ->
    bean.destroyMethod = "close"
    driverClass = "org.postgresql.Driver"
    jdbcUrl = "jdbc:postgresql://localhost:5432/sampledb"
    user = "postgres"
    password = "postgres"
    initialPoolSize = 3
    maxPoolSize = 15
    maxIdleTime = 120
    minPoolSize = 0
  }
}

/*
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.0.xsd">

  <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource" destroy-method="close">
    <property name="driverClass"><value>org.postgresql.Driver</value></property>
    <property name="jdbcUrl"><value>jdbc:postgresql://localhost:5432/sampledb</value></property>
    <property name="user"><value>postgres</value></property>
    <property name="password"><value>postgres</value></property>
    <!--//optional Tuning-->
    <property name="maxPoolSize"><value>15</value></property>
    <property name="initialPoolSize"><value>3</value></property>
    <property name="maxIdleTime"><value>120</value></property>
    <property name="minPoolSize"><value>0</value></property>
  </bean>

</beans>
*/
