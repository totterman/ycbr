import dayjs from "dayjs";
import { I9EventDto } from "./inspectionevent";

export const events: Array<I9EventDto> = [
  {
    i9eventId: '1',
    place: 'Björkholmen',
    day: '2026-05-10',
    starts: '2026-05-10 18:00:00.000+02:00',
    ends: '2026-05-10 21:00:00.000+02:00',
    bookings: [],
    inspectors: 2,
    boats: 12,
  },
  {
    i9eventId: '2',
    place: 'Blekholmen',
    day: '2026-05-12',
    starts: '2026-05-12 18:00:00.000+02:00',
    ends: '2026-05-12 21:00:00.000+02:00',
    bookings: [],
    inspectors: 3,
    boats: 14,
  },
  {
    i9eventId: '3',
    place: 'Björkholmen',
    day: '2026-05-17',
    starts: '2026-05-17 18:00:00.000+02:00',
    ends: '2026-05-17 21:00:00.000+02:00',
    bookings: [],
    inspectors: 2,
    boats: 7,
  },
  {
    i9eventId: '4',
    place: 'Blekholmen',
    day: '2026-05-22',
    starts: '2026-05-22 18:00:00.000+02:00',
    ends: '2026-05-22 21:00:00.000+02:00',
    bookings: [],
    inspectors: 1,
    boats: 6,
  },
  {
    i9eventId: '5',
    place: 'Barösund',
    day: '2026-06-09',
    starts: '2026-06-09 10:00:00.000+02:00',
    ends: '2026-06-09 14:00:00.000+02:00',
    bookings: [],
    inspectors: 1,
    boats: 8,
  },
  {
    i9eventId: '6',
    place: 'Björkholmen',
    day: '2026-06-23',
    starts: '2026-06-23 18:00:00.000+02:00',
    ends: '2026-06-23 21:00:00.000+02:00',
    bookings: [],
    inspectors: 3,
    boats: 19,
  },
];

export const places = ["Björkholmen", "Blekholmen", "Barösund", "Gumbostrand"];
