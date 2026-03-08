import { useUser } from "@/auth/useUser";
import {
  BoatBookingDto,
  CompleteEventDto,
  completeQueryOptions,
  useMarkBoatBooking,
} from "@/inspectionevents/inspectionevent";
import Button from "@mui/material/Button";
import SendIcon from "@mui/icons-material/Send";
import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import Select from "@mui/material/Select";
import Typography from "@mui/material/Typography";
import { useSuspenseQuery } from "@tanstack/react-query";
import dayjs from "dayjs";
import { useState } from "react";
import Stack from "@mui/material/Stack";
import { boatsQueryOptions } from "@/boats/boat";
import { NewInspection , useCreateInspection  } from "./inspection";
import { useNavigate } from "@tanstack/react-router";
import { useIntlayer, useLocale } from "react-intlayer";
import { Locale } from "intlayer";

export default function DispatchPage() {
  const eventsQuery = useSuspenseQuery(completeQueryOptions);
  const events = eventsQuery.data
    .filter((c) => !dayjs(c.starts).isBefore(dayjs()))
    .sort((a, b) => (dayjs(a.starts).isAfter(dayjs(b.starts)) ? 1 : -1));
  const boatsQuery = useSuspenseQuery(boatsQueryOptions);
  const boats = boatsQuery.data;

  const { user } = useUser();
  const { mutateAsync: markBooking } = useMarkBoatBooking();
  const { mutateAsync: createInspection } = useCreateInspection();
  const [i9event, setI9event] = useState<CompleteEventDto>();
  const [booking, setBooking] = useState<BoatBookingDto>();

  const navigate = useNavigate();
  const content = useIntlayer("inspections");
  const { locale } = useLocale();
  const tlds: Locale = locale == "sv-FI" ? "fi-FI" : locale;

  const startInspection = async () => {
    if (i9event && booking?.boatId) {
      const boat = boats.find((b) => b.boatId === booking.boatId);
      if (boat) {
        const ni: NewInspection = {
          inspectorName: user.name,
          eventId: i9event.i9eventId,
          boatId: boat.boatId,
          inspectionClass: "0",
          inspectionType: booking.type,
        };
        const newInspection = await createInspection(ni);
        const inspectionStr = newInspection ? newInspection.inspectionId : '';

        const dto: BoatBookingDto = {
          boatId: boat.boatId,
          message: "Sent by " + user.name,
          type: booking?.type || "",
          time: booking?.time || "",
          taken: true,
        };
        if (booking) {
          booking.taken = true;
          setBooking(booking);
        }
        setI9event(i9event);
        await markBooking({ id: i9event.i9eventId, dto: dto });

        navigate({
            to: '/inspect/$inspectionId',
            params: { inspectionId: inspectionStr },
            replace: true,
        });
      }
    }
  };

  function decodeType(type: string) {
    switch (type) {
      case "A":
        return content.annual_insp;
      case "H":
        return content.hull_insp;
      case "B":
        return content.base_insp;
      default:
        return "";
    }
  }

  return (
    <div>
      <Typography variant="body1" gutterBottom sx={{ mb: 4 }}>
        {content.dispatch_text}
      </Typography>

      <Stack alignItems={"center"} direction={"row"} gap={4}>
        <FormControl variant="standard" sx={{ m: 1, minWidth: 250 }}>
          <InputLabel id="dispatch-event-label">{content.inspection_event}</InputLabel>
          <Select
            labelId="dispatch-event-label"
            id="dispatch-event"
            label={content.inspection_event.value}
            value={i9event}
          >
            {events.map((e, i) => (
              <MenuItem
                key={i}
                value={e.i9eventId}
                onClick={() => setI9event(e)}
              >
                {e.place} {dayjs(e.day).format("D.M.YYYY")}
              </MenuItem>
            ))}
          </Select>
        </FormControl>

        <FormControl variant="standard" sx={{ m: 1, minWidth: 200 }}>
          <InputLabel id="dispatch-boatname-label">{content.boat_name}</InputLabel>
          <Select
            labelId="dispatch-boatname-label"
            id="dispatch-boatname"
            label={content.boat_name.value}
            value={booking}
          >
            {i9event?.boats.sort((a, b) => a.time.localeCompare(b.time)).map((dto) => (
              <MenuItem
                key={dto.boatId}
                value={dto.boatId}
                /* onClick={() => setBoatId(dto.boatId)} */
                onClick={() => setBooking(dto)}
                disabled={dto.taken}
              >
                {dayjs(dto.time).format('HH:mm')} {boats.find((boat) => boat.boatId === dto.boatId)?.name} {decodeType(dto.type)}
              </MenuItem>
            ))}
          </Select>
        </FormControl>

        <Button
          variant="contained"
          onClick={startInspection}
          endIcon={<SendIcon />}
        >
          {content.start_inspection}
        </Button>
      </Stack>
    </div>
  );
}
