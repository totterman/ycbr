package fi.smartbass.ycbr;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.fasterxml.jackson.core.type.TypeReference;
import fi.smartbass.ycbr.register.BoatDto;
import fi.smartbass.ycbr.register.NewBoatDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class BoatIntegrationTest extends BaseIntegrationTest {

    @Test
    @DisplayName("Context loads")
    void contextLoads() {
        assert mvc != null;
        assert postgres != null;
    }

    @Test
    @DisplayName("PostgreSQL container is running")
    void testPostgreSQLModule() {
        assert postgres.isRunning();
        System.out.println("JDBC URL:          " + postgres.getJdbcUrl());
        System.out.println("Host:              " + postgres.getHost());
        System.out.println("Port:              " + postgres.getMappedPort(5432));
        System.out.println("Database:          " + postgres.getDatabaseName());
        System.out.println("Username:          " + postgres.getUsername());
        System.out.println("Password:          " + postgres.getPassword());
        System.out.println("Test query string: " + postgres.getTestQueryString());
    }

    @Test
    @DisplayName("Create and read boats")
    @WithJwt("ronja.json")
    void findAndInsert() throws Exception {
        assert postgres.isRunning();

        MvcTestResult getAll = mvc.get()
                        .uri("/boats")
                .exchange();
        assertThat(getAll)
                .hasStatus(HttpStatus.OK);
        List<BoatDto> firstList = om.readValue(getAll.getResponse().getContentAsString(), new TypeReference<List<BoatDto>>(){});
        System.out.println(" Empty JSON: " + getAll.getResponse().getContentAsString());

        BoatDto newBoat = new BoatDto(null, "owner11", "BoatName", "Reg11234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988");
        String newBoatJson = om.writeValueAsString(newBoat);
        MvcTestResult addNew = mvc.post()
                .uri("/boats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newBoatJson)
                .exchange();
        assertThat(addNew)
                .hasStatus(HttpStatus.CREATED);
        System.out.println("Created JSON: " + addNew.getResponse().getContentAsString());

        MvcTestResult getAgain = mvc.get()
                .uri("/boats")
                .exchange();
        assertThat(getAgain)
                .hasStatus(HttpStatus.OK);
        List<BoatDto> secondList = om.readValue(getAgain.getResponse().getContentAsString(), new TypeReference<List<BoatDto>>(){});
        assertThat(secondList.size()).isEqualTo(firstList.size() + 1);
        System.out.println("   Repo JSON: " + getAgain.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("Update boats")
    @WithJwt("ronja.json")
    void findAndUpdate() throws Exception {
        assert postgres.isRunning();

        NewBoatDto newBoat = new NewBoatDto("owner12", "BoatName2", "Reg112234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988");
        String newBoatJson = om.writeValueAsString(newBoat);
        MvcTestResult addNew = mvc.post()
                .uri("/boats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newBoatJson)
                .exchange();
        assertThat(addNew)
                .hasStatus(HttpStatus.CREATED);
        System.out.println("Created JSON: " + addNew.getResponse().getContentAsString());

        MvcTestResult getAll = mvc.get()
                .uri("/boats")
                .exchange();
        assertThat(getAll)
                .hasStatus(HttpStatus.OK);
        List<BoatDto> firstList = om.readValue(getAll.getResponse().getContentAsString(), new TypeReference<List<BoatDto>>(){});
        System.out.println("  Repo JSON: " + getAll.getResponse().getContentAsString());
        assertThat(firstList.size()).isGreaterThan(0);

        UUID boatId = firstList.getLast().boatId();
        MvcTestResult getOne = mvc.get()
                .uri("/boats/" + boatId)
                .exchange();
        assertThat(getOne)
                .hasStatus(HttpStatus.OK);
        BoatDto before = om.readValue(getOne.getResponse().getContentAsString(), BoatDto.class);
        System.out.println(" Before JSON: " + getOne.getResponse().getContentAsString());

        BoatDto updated = new BoatDto(before.boatId(), before.owner() + "x", before.name(), before.sign() + "X", before.make(), before.model(), before.loa(), before.draft(), before.beam(), before.deplacement(), before.engines(), before.year());
        String updatedJson = om.writeValueAsString(updated);
        MvcTestResult replaceOld = mvc.put()
                .uri("/boats/" + boatId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedJson)
                .exchange();
        assertThat(replaceOld)
                .hasStatus(HttpStatus.OK);
        System.out.println("Updated JSON: " + replaceOld.getResponse().getContentAsString());
        BoatDto after = om.readValue(replaceOld.getResponse().getContentAsString(), BoatDto.class);
        assertThat(after.boatId()).isEqualTo(before.boatId());
        assertThat(after.name()).isEqualTo(before.name());
        assertThat(after.owner()).isNotEqualTo(before.owner());
        assertThat(after.sign()).isNotEqualTo(before.sign());
    }

    @Test
    @DisplayName("Delete boats")
    @WithJwt("ronja.json")
    void findAndDelete() throws Exception {
        assert postgres.isRunning();

        NewBoatDto newBoat = new NewBoatDto("owner13", "BoatName3", "Reg13234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988");
        String newBoatJson = om.writeValueAsString(newBoat);
        MvcTestResult addNew = mvc.post()
                .uri("/boats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newBoatJson)
                .exchange();
        assertThat(addNew)
                .hasStatus(HttpStatus.CREATED);
        System.out.println("Created JSON: " + addNew.getResponse().getContentAsString());

        MvcTestResult getBefore = mvc.get()
                .uri("/boats")
                .exchange();
        assertThat(getBefore)
                .hasStatus(HttpStatus.OK);
        List<BoatDto> beforeList = om.readValue(getBefore.getResponse().getContentAsString(), new TypeReference<List<BoatDto>>() {
        });
        System.out.println("  Repo JSON: " + getBefore.getResponse().getContentAsString());
        assertThat(beforeList.size()).isGreaterThan(0);

        UUID boatId = beforeList.getLast().boatId();
        MvcTestResult deleteOne = mvc.delete()
                .uri("/boats/" + boatId)
                .exchange();
        assertThat(deleteOne)
                .hasStatus(HttpStatus.OK);

        MvcTestResult getAfter = mvc.get()
                .uri("/boats")
                .exchange();
        assertThat(getBefore)
                .hasStatus(HttpStatus.OK);
        List<BoatDto> afterList = om.readValue(getAfter.getResponse().getContentAsString(), new TypeReference<List<BoatDto>>() {
        });
        System.out.println("  Repo JSON: " + getAfter.getResponse().getContentAsString());
        assertThat(afterList.size()).isEqualTo(beforeList.size() - 1);

        Set<BoatDto> afterSet = new HashSet<>(afterList);
        assertThat(!afterSet.contains(beforeList.getLast()));

        MvcTestResult deleteSame = mvc.delete()
                .uri("/boats/" + boatId)
                .exchange();
        assertThat(deleteSame)
                .hasStatus(HttpStatus.OK);

        MvcTestResult getSame = mvc.get()
                .uri("/boats")
                .exchange();
        assertThat(getSame)
                .hasStatus(HttpStatus.OK);
        List<BoatDto> sameList = om.readValue(getSame.getResponse().getContentAsString(), new TypeReference<List<BoatDto>>() {
        });
        System.out.println("  Repo JSON: " + getSame.getResponse().getContentAsString());
        assertThat(sameList.size()).isEqualTo(afterList.size());
    }
}
