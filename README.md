# elastic-authserach

spring data查询elasticsearch貌似不支持认证的方式

将org.springframework.data.elasticsearch.client包下的TransportClientFactoryBean
拉出来，修改一下

将Settings.builder().put(...)改成自己需要的环境  

pom包也需要对应的调整