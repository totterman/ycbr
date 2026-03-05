import dayjs from "dayjs";

export interface TimeSlot {
  time: dayjs.Dayjs;
  available: boolean;
}

/**
 * Generates all 20-minute time slots between _minTime_ and _maxTime_.
 * Determines availability based on existing bookings and the requested inspection type.
 *
 * Rules:
 * - Operating hours: minTime to maxTime
 * - Slots are every 20 minutes (e.g. 18:00, 18:20, 18:40)
 * - Annual inspection takes 20 mins, Complete takes 40 mins
 * - A slot is UNAVAILABLE if it overlaps with an existing booking,
 *   or if the duration would push the appointment past maxTime,
 *   or if the time is in the past.
 */
export function calculateSlots(
  minTime: dayjs.Dayjs,
  maxTime: dayjs.Dayjs,
  bookings: string[],
  itype: string,
): TimeSlot[] {
  const duration = itype === "Y" ? 20 : 40;
  const slots: TimeSlot[] = [];

  let current = minTime;

  while (current.isBefore(maxTime)) {
    const slotStart = current;
    const slotEnd = current.add(duration, 'minutes');
    let isAvailable = true;

    // 1. Check if the inspection duration pushes past closing time (21:00)
    // E.g., a hull or base (40 min) inspection starting at 20:40 ends at 21:20 -> NOT available
    if (!slotStart.add(duration - 1, 'minutes').isBefore(maxTime)) {
        isAvailable = false;
    }

    // 2. Check if the slot is in the past
    else if (slotStart.isBefore(dayjs())) {
        isAvailable = false;
    }

    // 3. Check for overlap with existing bookings
    else {
        for (const booking of bookings) {
            const bStart = dayjs(booking);
            const bEnd = bStart.add(20, 'minutes');
            if (slotStart.isBefore(bEnd) && slotEnd.isAfter(bStart)) {
                isAvailable = false;
                break;
            }
        }
    }
    slots.push({ time: slotStart, available: isAvailable });
    current = current.add(20, 'minutes');
  }
  return slots;
}
