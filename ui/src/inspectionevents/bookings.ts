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
  capacity: number,
): TimeSlot[] {
  const duration = itype === "A" ? 20 : 40;
  const slots: TimeSlot[] = [];

  let current = minTime;

  while (current.isBefore(maxTime)) {
    const slotStart = current;
    const slotEnd = current.add(duration, 'minutes');
    let isAvailable = true;

    // 1. Check if the inspection duration pushes past closing time (21:00)
    //    E.g., a hull or base (40 min) inspection starting at 20:40 ends at 21:20 -> NOT available
    if (!slotStart.add(duration - 1, 'minutes').isBefore(maxTime)) {
        isAvailable = false;
    }

    // 2. Check if the slot is in the past
    else if (slotStart.isBefore(dayjs())) {
        isAvailable = false;
    }

    // 3. Check for capacity
    else if (capacity < 1) {
        isAvailable = false;
    }

    // 4. Check for overlap with existing bookings considering capacity
    //    Bookings are ordered - this is crucial!
    //    Overlapping bookings = equal booking values so we check for similarity with previous to count vacants
    //    Builtin weakness: no way to tell if the SAME inspector is available for consequent timeslots.
    //    We assume that 80% of inspections are annuals = 1 slot, thus problems will be infrequent.
    else {
        let vacant = capacity;
        let bPrev = minTime.subtract(1, 'minutes'); // an initial value that can not exist in bookings
    
        for (const booking of bookings) {
            const bStart = dayjs(booking);
            const bEnd = bStart.add(20, 'minutes');
            if (!bStart.isSame(bPrev)) {
                vacant = capacity;
            }
            if (slotStart.isBefore(bEnd) && slotEnd.isAfter(bStart)) {
                vacant--;
                // console.log('Slot:', slotStart.format('HH:mm'), '-', slotEnd.format('HH:mm'), 'Booking:', bStart.format('HH:mm'), '-', bEnd.format('HH:mm'), 'vacant:', vacant, 'bPrev:', bPrev.format('HH:mm'));                
                if (vacant < 1) {
                    isAvailable = false;
                    break;
                }
            }
            bPrev = bStart;
        }
    }
    slots.push({ time: slotStart, available: isAvailable });
    current = current.add(20, 'minutes');
  }
  return slots;
}
