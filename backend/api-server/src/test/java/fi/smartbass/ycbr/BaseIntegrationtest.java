package fi.smartbass.ycbr;

import com.c4_soft.springaddons.security.oauth2.test.webmvc.AddonsWebmvcTestConf;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.testcontainers.postgresql.PostgreSQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ImportAutoConfiguration(AddonsWebmvcTestConf.class)
@Sql("/db.sql")
public abstract class BaseIntegrationtest {

    @Autowired
    public MockMvcTester mvc;

    @ServiceConnection
    public static final PostgreSQLContainer postgres;

    public ObjectMapper om = new ObjectMapper();

    static  {
        postgres = new PostgreSQLContainer("postgres:18-alpine");
        postgres.start();
    }


}
