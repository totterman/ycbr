package fi.smartbass.ycbr;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.fasterxml.jackson.core.type.TypeReference;
import fi.smartbass.ycbr.i9event.BoatBookingDto;
import fi.smartbass.ycbr.i9event.I9EventDto;
import fi.smartbass.ycbr.i9event.InspectorRegistrationDto;
import fi.smartbass.ycbr.i9event.NewI9EventDto;
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

public class I9EventIntegrationTest extends BaseIntegrationTest {

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
    @DisplayName("Create and read events")
    @WithJwt("kalle.json")
    void findAndInsert() throws Exception {
        assert postgres.isRunning();

        MvcTestResult getAll = mvc.get()
                        .uri("/i9events")
                .exchange();
        assertThat(getAll)
                .hasStatus(HttpStatus.OK);
        List<I9EventDto> firstList = om.readValue(getAll.getResponse().getContentAsString(), new TypeReference<List<I9EventDto>>(){});
        System.out.println(" Empty JSON: " + getAll.getResponse().getContentAsString());

        I9EventDto newI9Event = new I9EventDto(null, "Blekholmen", "2026-05-18T00:00:00.000+03:00", "2026-05-18T18:00:00.000+03:00", "2026-05-18T20:00:00.000+03:00", null, 0, 0);
        String newI9EventJson = om.writeValueAsString(newI9Event);
        MvcTestResult addNew = mvc.post()
                .uri("/i9events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newI9EventJson)
                .exchange();
        assertThat(addNew)
                .hasStatus(HttpStatus.CREATED);
        System.out.println("Created JSON: " + addNew.getResponse().getContentAsString());

        MvcTestResult getAgain = mvc.get()
                .uri("/i9events")
                .exchange();
        assertThat(getAgain)
                .hasStatus(HttpStatus.OK);
        List<I9EventDto> secondList = om.readValue(getAgain.getResponse().getContentAsString(), new TypeReference<List<I9EventDto>>(){});
        assertThat(secondList.size()).isEqualTo(firstList.size() + 1);
        System.out.println("   Repo JSON: " + getAgain.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("Update events")
    @WithJwt("kalle.json")
    void findAndUpdate() throws Exception {
        assert postgres.isRunning();

        NewI9EventDto newI9Event = new NewI9EventDto("Björkholmen", "2026-05-19T00:00:00.000+03:00", "2026-05-19T18:00:00.000+03:00", "2026-05-19T20:00:00.000+03:00");
        String newI9EventJson = om.writeValueAsString(newI9Event);
        MvcTestResult addNew = mvc.post()
                .uri("/i9events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newI9EventJson)
                .exchange();
        assertThat(addNew)
                .hasStatus(HttpStatus.CREATED);
        System.out.println("Created JSON: " + addNew.getResponse().getContentAsString());

        MvcTestResult getAll = mvc.get()
                .uri("/i9events")
                .exchange();
        assertThat(getAll)
                .hasStatus(HttpStatus.OK);
        List<I9EventDto> firstList = om.readValue(getAll.getResponse().getContentAsString(), new TypeReference<List<I9EventDto>>(){});
        System.out.println("  Repo JSON: " + getAll.getResponse().getContentAsString());
        assertThat(firstList.size()).isGreaterThan(0);

        UUID i9eventId = firstList.getLast().i9eventId();
        MvcTestResult getOne = mvc.get()
                .uri("/i9events/" + i9eventId)
                .exchange();
        assertThat(getOne)
                .hasStatus(HttpStatus.OK);
        I9EventDto before = om.readValue(getOne.getResponse().getContentAsString(), I9EventDto.class);
        System.out.println(" Before JSON: " + getOne.getResponse().getContentAsString());

        I9EventDto updated = new I9EventDto(before.i9eventId(), before.place() + "x", before.day(), before.starts(), before.ends(), before.bookings(), before.boats(), before.inspectors());
        String updatedJson = om.writeValueAsString(updated);
        MvcTestResult replaceOld = mvc.put()
                .uri("/i9events/" + i9eventId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedJson)
                .exchange();
        assertThat(replaceOld)
                .hasStatus(HttpStatus.OK);
        System.out.println("Updated JSON: " + replaceOld.getResponse().getContentAsString());
        I9EventDto after = om.readValue(replaceOld.getResponse().getContentAsString(), I9EventDto.class);
        assertThat(after.i9eventId()).isEqualTo(before.i9eventId());
        assertThat(after.place()).isNotEqualTo(before.place());
        assertThat(after.starts()).isEqualTo(before.starts());
        assertThat(after.ends()).isEqualTo(before.ends());
    }

    @Test
    @DisplayName("Delete events")
    @WithJwt("kalle.json")
    void findAndDelete() throws Exception {
        assert postgres.isRunning();

        NewI9EventDto newI9Event = new NewI9EventDto("Kajholmen", "2026-05-21T00:00:00.000+03:00", "2026-05-21T18:00:00.000+03:00", "2026-05-21T20:00:00.000+03:00");
        String newI9EventJson = om.writeValueAsString(newI9Event);
        MvcTestResult addNew = mvc.post()
                .uri("/i9events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newI9EventJson)
                .exchange();
        assertThat(addNew)
                .hasStatus(HttpStatus.CREATED);
        System.out.println("Created JSON: " + addNew.getResponse().getContentAsString());

        MvcTestResult getBefore = mvc.get()
                .uri("/i9events")
                .exchange();
        assertThat(getBefore)
                .hasStatus(HttpStatus.OK);
        List<I9EventDto> beforeList = om.readValue(getBefore.getResponse().getContentAsString(), new TypeReference<List<I9EventDto>>() {
        });
        System.out.println("  Repo JSON: " + getBefore.getResponse().getContentAsString());
        assertThat(beforeList.size()).isGreaterThan(0);

        UUID i9eventId = beforeList.getLast().i9eventId();
        MvcTestResult deleteOne = mvc.delete()
                .uri("/i9events/" + i9eventId)
                .exchange();
        assertThat(deleteOne)
                .hasStatus(HttpStatus.OK);

        MvcTestResult getAfter = mvc.get()
                .uri("/i9events")
                .exchange();
        assertThat(getBefore)
                .hasStatus(HttpStatus.OK);
        List<I9EventDto> afterList = om.readValue(getAfter.getResponse().getContentAsString(), new TypeReference<List<I9EventDto>>() {
        });
        System.out.println("  Repo JSON: " + getAfter.getResponse().getContentAsString());
        assertThat(afterList.size()).isEqualTo(beforeList.size() - 1);

        Set<I9EventDto> afterSet = new HashSet<>(afterList);
        assertThat(!afterSet.contains(beforeList.getLast()));

        MvcTestResult deleteSame = mvc.delete()
                .uri("/i9events/" + i9eventId)
                .exchange();
        assertThat(deleteSame)
                .hasStatus(HttpStatus.OK);

        MvcTestResult getSame = mvc.get()
                .uri("/i9events")
                .exchange();
        assertThat(getSame)
                .hasStatus(HttpStatus.OK);
        List<I9EventDto> sameList = om.readValue(getSame.getResponse().getContentAsString(), new TypeReference<List<I9EventDto>>() {
        });
        System.out.println("  Repo JSON: " + getSame.getResponse().getContentAsString());
        assertThat(sameList.size()).isEqualTo(afterList.size());
    }

    @Test
    @DisplayName("Handle boatId bookings")
    @WithJwt("kalle.json")
    void boatBookings() throws Exception {
        assert postgres.isRunning();

        NewI9EventDto newI9Event = new NewI9EventDto("Björkholmen", "2026-05-19T00:00:00.000+03:00", "2026-05-19T18:00:00.000+03:00", "2026-05-19T20:00:00.000+03:00");
        String newI9EventJson = om.writeValueAsString(newI9Event);
        MvcTestResult addNew = mvc.post()
                .uri("/i9events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newI9EventJson)
                .exchange();
        assertThat(addNew)
                .hasStatus(HttpStatus.CREATED);
        System.out.println("Created JSON: " + addNew.getResponse().getContentAsString());

        MvcTestResult getAll = mvc.get()
                .uri("/i9events")
                .exchange();
        assertThat(getAll)
                .hasStatus(HttpStatus.OK);
        List<I9EventDto> firstList = om.readValue(getAll.getResponse().getContentAsString(), new TypeReference<List<I9EventDto>>(){});
        System.out.println("  Repo JSON: " + getAll.getResponse().getContentAsString());
        assertThat(firstList.size()).isGreaterThan(0);

        I9EventDto created = firstList.getLast();
        assertThat(created.boats()).isEqualTo(0);
        UUID boatId = UUID.randomUUID();
        BoatBookingDto bookingDTO = new BoatBookingDto(boatId, "BoatEntity Message","B","2026-05-19T01:00:00.000+03:00",  false);
        String bookingJson = om.writeValueAsString(bookingDTO);
        MvcTestResult updateOne = mvc.post()
                .uri("/i9events/" + created.i9eventId() + "/boats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookingJson)
                .exchange();
        assertThat(updateOne)
                .hasStatus(HttpStatus.CREATED);
        I9EventDto updated = om.readValue(updateOne.getResponse().getContentAsString(), I9EventDto.class);
        System.out.println("Updated JSON: " + updateOne.getResponse().getContentAsString());
        assertThat(updated.boats()).isEqualTo(1);

        BoatBookingDto secondDTO = new BoatBookingDto(boatId, "BoatEntity Message 2", "B", "2026-05-19T01:00:00.000+03:00", false);
        String secondJson = om.writeValueAsString(secondDTO);
        MvcTestResult updateTwo = mvc.post()
                .uri("/i9events/" + created.i9eventId() + "/boats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(secondJson)
                .exchange();
        assertThat(updateTwo)
                .hasStatus(HttpStatus.CONFLICT);
        System.out.println("Exception JSON: " + updateTwo.getResponse().getContentAsString());

        BoatBookingDto thirdDTO = new BoatBookingDto(null, null, null,null, false);
        String thirdJson = om.writeValueAsString(thirdDTO);
        MvcTestResult updateThree = mvc.post()
                .uri("/i9events/" + created.i9eventId() + "/boats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(thirdJson)
                .exchange();
        assertThat(updateThree)
                .hasStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        System.out.println("Exception JSON: " + updateThree.getResponse().getContentAsString());

        MvcTestResult deleteone = mvc.delete()
                .uri("/i9events/" + updated.i9eventId() + "/boats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookingJson)
                .exchange();
        assertThat(deleteone)
                .hasStatus(HttpStatus.OK);
        System.out.println("Updated JSON: " + deleteone.getResponse().getContentAsString());
        I9EventDto after = om.readValue(deleteone.getResponse().getContentAsString(), I9EventDto.class);
        assertThat(after.boats()).isEqualTo(0);
    }

    @Test
    @DisplayName("Handle inspectorName registrations")
    @WithJwt("kalle.json")
    void inspectorRegistrations() throws Exception {
        assert postgres.isRunning();

        I9EventDto newI9Event = new I9EventDto(null, "Björkholmen", "2026-05-19T00:00:00.000+03:00", "2026-05-19T18:00:00.000+03:00", "2026-05-19T20:00:00.000+03:00", null, 0, 0);
        String newI9EventJson = om.writeValueAsString(newI9Event);
        MvcTestResult addNew = mvc.post()
                .uri("/i9events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newI9EventJson)
                .exchange();
        assertThat(addNew)
                .hasStatus(HttpStatus.CREATED);
        System.out.println("Created JSON: " + addNew.getResponse().getContentAsString());

        MvcTestResult getAll = mvc.get()
                .uri("/i9events")
                .exchange();
        assertThat(getAll)
                .hasStatus(HttpStatus.OK);
        List<I9EventDto> firstList = om.readValue(getAll.getResponse().getContentAsString(), new TypeReference<List<I9EventDto>>(){});
        System.out.println("  Repo JSON: " + getAll.getResponse().getContentAsString());
        assertThat(firstList.size()).isGreaterThan(0);

        I9EventDto created = firstList.getLast();
        assertThat(created.inspectors()).isEqualTo(0);
        InspectorRegistrationDto registrationDTO = new InspectorRegistrationDto("Inspector Name", "Inspector Message");
        String bookingJson = om.writeValueAsString(registrationDTO);

        MvcTestResult updateFour = mvc.post()
                .uri("/i9events/" + created.i9eventId() + "/inspectors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookingJson)
                .exchange();
        assertThat(updateFour)
                .hasStatus(HttpStatus.CREATED);
        I9EventDto updated = om.readValue(updateFour.getResponse().getContentAsString(), I9EventDto.class);
        System.out.println("Updated JSON: " + updateFour.getResponse().getContentAsString());
        assertThat(updated.inspectors()).isEqualTo(1);

        InspectorRegistrationDto secondDTO = new InspectorRegistrationDto("Inspector Name", "Inspector Message 2");
        String secondJson = om.writeValueAsString(secondDTO);
        MvcTestResult updateFive = mvc.post()
                .uri("/i9events/" + created.i9eventId() + "/inspectors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(secondJson)
                .exchange();
        assertThat(updateFive)
                .hasStatus(HttpStatus.CONFLICT);
        System.out.println("Exception JSON: " + updateFive.getResponse().getContentAsString());

        InspectorRegistrationDto thirdDTO = new InspectorRegistrationDto(null, "Inspector Message 3");
        String thirdJson = om.writeValueAsString(thirdDTO);
        MvcTestResult updateSix = mvc.post()
                .uri("/i9events/" + created.i9eventId() + "/inspectors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(thirdJson)
                .exchange();
        assertThat(updateSix)
                .hasStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        System.out.println("Exception JSON: " + updateSix.getResponse().getContentAsString());

        MvcTestResult deleteTwo = mvc.delete()
                .uri("/i9events/" + updated.i9eventId() + "/inspectors?name=" + registrationDTO.inspectorName())
                .exchange();
        assertThat(deleteTwo)
                .hasStatus(HttpStatus.OK);
        System.out.println("Updated JSON: " + deleteTwo.getResponse().getContentAsString());
        I9EventDto after = om.readValue(deleteTwo.getResponse().getContentAsString(), I9EventDto.class);
        assertThat(after.inspectors()).isEqualTo(0);
    }

    @Test
    @DisplayName("Insert throws on invalid data")
    @WithJwt("kalle.json")
    void insertThrows() throws Exception {
        I9EventDto newI9Event = new I9EventDto(null, "All_too_long_name_breaks_50_characters_limit_and_validation_fails", "2026-05-18T00:00:00.000+03:00", "2026-05-18T18:00:00.000+03:00", "2026-05-18T20:00:00.000+03:00", null, 0, 0);
        String newI9EventJson = om.writeValueAsString(newI9Event);
        MvcTestResult addNew = mvc.post()
                .uri("/i9events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newI9EventJson)
                .exchange();
        assertThat(addNew)
                .hasStatus(HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
