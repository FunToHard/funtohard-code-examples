// Moment.js - Date and Time Library Examples
// Work with dates and times using parsing, formatting, manipulation, and validation techniques.

const moment = require('moment');

// 1. Creating Moment Objects
console.log('=== CREATING MOMENT OBJECTS ===');

// Current date and time
const now = moment();
console.log('Current moment:', now.format());
console.log('Current date:', now.format('YYYY-MM-DD'));
console.log('Current time:', now.format('HH:mm:ss'));

// From string
const dateFromString = moment('2023-12-25');
const dateTimeFromString = moment('2023-12-25 15:30:00');
const customFormat = moment('25/12/2023', 'DD/MM/YYYY');

console.log('From string:', dateFromString.format('MMMM Do, YYYY'));
console.log('DateTime from string:', dateTimeFromString.format('LLLL'));
console.log('Custom format:', customFormat.format('dddd, MMMM Do YYYY'));

// From JavaScript Date
const jsDate = new Date();
const momentFromJS = moment(jsDate);
console.log('From JS Date:', momentFromJS.format());

// From array [year, month, day, hour, minute, second, millisecond]
const fromArray = moment([2023, 11, 25, 15, 30, 0]); // Note: month is 0-indexed
console.log('From array:', fromArray.format('YYYY-MM-DD HH:mm:ss'));

// From object
const fromObject = moment({
  year: 2023,
  month: 11, // 0-indexed
  day: 25,
  hour: 15,
  minute: 30,
  second: 0
});
console.log('From object:', fromObject.format());

// 2. Formatting Dates
console.log('\n=== FORMATTING DATES ===');

const sampleDate = moment('2023-12-25 15:30:45');

console.log('ISO 8601:', sampleDate.toISOString());
console.log('Default format:', sampleDate.format());
console.log('Custom formats:');
console.log('  YYYY-MM-DD:', sampleDate.format('YYYY-MM-DD'));
console.log('  MM/DD/YYYY:', sampleDate.format('MM/DD/YYYY'));
console.log('  DD/MM/YYYY:', sampleDate.format('DD/MM/YYYY'));
console.log('  MMMM Do, YYYY:', sampleDate.format('MMMM Do, YYYY'));
console.log('  dddd, MMMM Do YYYY:', sampleDate.format('dddd, MMMM Do YYYY'));
console.log('  HH:mm:ss:', sampleDate.format('HH:mm:ss'));
console.log('  h:mm A:', sampleDate.format('h:mm A'));

// Localized formats
console.log('Localized formats:');
console.log('  L (short date):', sampleDate.format('L'));
console.log('  LL (long date):', sampleDate.format('LL'));
console.log('  LLL (long date with time):', sampleDate.format('LLL'));
console.log('  LLLL (full date with time):', sampleDate.format('LLLL'));

// 3. Date Manipulation
console.log('\n=== DATE MANIPULATION ===');

const baseDate = moment('2023-06-15 12:00:00');
console.log('Base date:', baseDate.format('YYYY-MM-DD HH:mm:ss'));

// Adding time
console.log('Add operations:');
console.log('  +7 days:', baseDate.clone().add(7, 'days').format('YYYY-MM-DD'));
console.log('  +2 weeks:', baseDate.clone().add(2, 'weeks').format('YYYY-MM-DD'));
console.log('  +3 months:', baseDate.clone().add(3, 'months').format('YYYY-MM-DD'));
console.log('  +1 year:', baseDate.clone().add(1, 'year').format('YYYY-MM-DD'));
console.log('  +2 hours:', baseDate.clone().add(2, 'hours').format('YYYY-MM-DD HH:mm:ss'));
console.log('  +30 minutes:', baseDate.clone().add(30, 'minutes').format('HH:mm:ss'));

// Subtracting time
console.log('Subtract operations:');
console.log('  -5 days:', baseDate.clone().subtract(5, 'days').format('YYYY-MM-DD'));
console.log('  -1 month:', baseDate.clone().subtract(1, 'month').format('YYYY-MM-DD'));
console.log('  -6 hours:', baseDate.clone().subtract(6, 'hours').format('HH:mm:ss'));

// Start and end of periods
console.log('Start/End of periods:');
console.log('  Start of day:', baseDate.clone().startOf('day').format('YYYY-MM-DD HH:mm:ss'));
console.log('  End of day:', baseDate.clone().endOf('day').format('YYYY-MM-DD HH:mm:ss'));
console.log('  Start of month:', baseDate.clone().startOf('month').format('YYYY-MM-DD'));
console.log('  End of month:', baseDate.clone().endOf('month').format('YYYY-MM-DD'));
console.log('  Start of year:', baseDate.clone().startOf('year').format('YYYY-MM-DD'));

// 4. Relative Time (Time from now)
console.log('\n=== RELATIVE TIME ===');

const pastDates = [
  moment().subtract(1, 'minute'),
  moment().subtract(30, 'minutes'),
  moment().subtract(2, 'hours'),
  moment().subtract(1, 'day'),
  moment().subtract(3, 'days'),
  moment().subtract(1, 'week'),
  moment().subtract(2, 'months'),
  moment().subtract(1, 'year')
];

const futureDates = [
  moment().add(1, 'minute'),
  moment().add(45, 'minutes'),
  moment().add(3, 'hours'),
  moment().add(2, 'days'),
  moment().add(1, 'week'),
  moment().add(1, 'month'),
  moment().add(6, 'months'),
  moment().add(2, 'years')
];

console.log('Past dates (from now):');
pastDates.forEach(date => {
  console.log(`  ${date.format('YYYY-MM-DD HH:mm')} -> ${date.fromNow()}`);
});

console.log('Future dates (from now):');
futureDates.forEach(date => {
  console.log(`  ${date.format('YYYY-MM-DD HH:mm')} -> ${date.fromNow()}`);
});

// Relative to specific date
const referenceDate = moment('2023-06-15');
console.log(`\nRelative to ${referenceDate.format('YYYY-MM-DD')}:`);
console.log('  2023-06-10 ->', moment('2023-06-10').from(referenceDate));
console.log('  2023-06-20 ->', moment('2023-06-20').from(referenceDate));

// 5. Date Comparison
console.log('\n=== DATE COMPARISON ===');

const date1 = moment('2023-06-15');
const date2 = moment('2023-06-20');
const date3 = moment('2023-06-15');

console.log('Comparison methods:');
console.log(`  ${date1.format('YYYY-MM-DD')} is before ${date2.format('YYYY-MM-DD')}:`, date1.isBefore(date2));
console.log(`  ${date2.format('YYYY-MM-DD')} is after ${date1.format('YYYY-MM-DD')}:`, date2.isAfter(date1));
console.log(`  ${date1.format('YYYY-MM-DD')} is same as ${date3.format('YYYY-MM-DD')}:`, date1.isSame(date3));
console.log(`  ${date1.format('YYYY-MM-DD')} is same or before ${date2.format('YYYY-MM-DD')}:`, date1.isSameOrBefore(date2));
console.log(`  ${date2.format('YYYY-MM-DD')} is same or after ${date1.format('YYYY-MM-DD')}:`, date2.isSameOrAfter(date1));

// Between dates
const startDate = moment('2023-06-01');
const endDate = moment('2023-06-30');
const testDate = moment('2023-06-15');

console.log(`  ${testDate.format('YYYY-MM-DD')} is between ${startDate.format('YYYY-MM-DD')} and ${endDate.format('YYYY-MM-DD')}:`, 
  testDate.isBetween(startDate, endDate));

// 6. Date Validation
console.log('\n=== DATE VALIDATION ===');

const validDates = [
  '2023-06-15',
  '2023-02-29', // Invalid leap year
  '2024-02-29', // Valid leap year
  '2023-13-01', // Invalid month
  '2023-06-32', // Invalid day
  'invalid-date',
  '2023/06/15',
  '15-06-2023'
];

console.log('Date validation:');
validDates.forEach(dateStr => {
  const momentDate = moment(dateStr);
  console.log(`  "${dateStr}" -> Valid: ${momentDate.isValid()}`);
});

// Strict parsing
console.log('\nStrict parsing:');
const strictTests = [
  { date: '2023-06-15', format: 'YYYY-MM-DD' },
  { date: '15/06/2023', format: 'DD/MM/YYYY' },
  { date: '2023-06-15', format: 'DD/MM/YYYY' }, // Wrong format
  { date: '15-06-2023', format: 'DD-MM-YYYY' }
];

strictTests.forEach(test => {
  const momentDate = moment(test.date, test.format, true); // true for strict mode
  console.log(`  "${test.date}" with format "${test.format}" -> Valid: ${momentDate.isValid()}`);
});

// 7. Duration and Differences
console.log('\n=== DURATION AND DIFFERENCES ===');

const startTime = moment('2023-06-15 09:00:00');
const endTime = moment('2023-06-15 17:30:00');

console.log('Time difference:');
console.log('  In milliseconds:', endTime.diff(startTime));
console.log('  In seconds:', endTime.diff(startTime, 'seconds'));
console.log('  In minutes:', endTime.diff(startTime, 'minutes'));
console.log('  In hours:', endTime.diff(startTime, 'hours'));
console.log('  In hours (precise):', endTime.diff(startTime, 'hours', true));

// Duration object
const duration = moment.duration(endTime.diff(startTime));
console.log('Duration object:');
console.log('  Hours:', duration.hours());
console.log('  Minutes:', duration.minutes());
console.log('  Total hours:', duration.asHours());
console.log('  Humanized:', duration.humanize());

// Date differences
const birthday = moment('1990-05-15');
const today = moment();

console.log(`\nAge calculation (from ${birthday.format('YYYY-MM-DD')} to today):`);
console.log('  Years:', today.diff(birthday, 'years'));
console.log('  Months:', today.diff(birthday, 'months'));
console.log('  Days:', today.diff(birthday, 'days'));

// 8. Working with Timezones (requires moment-timezone)
console.log('\n=== TIMEZONE EXAMPLES (Basic) ===');

// UTC operations
const utcNow = moment.utc();
const localNow = moment();

console.log('UTC vs Local:');
console.log('  UTC now:', utcNow.format('YYYY-MM-DD HH:mm:ss'));
console.log('  Local now:', localNow.format('YYYY-MM-DD HH:mm:ss'));
console.log('  UTC offset:', localNow.utcOffset(), 'minutes');

// Convert to UTC
const localTime = moment('2023-06-15 15:30:00');
const utcTime = localTime.clone().utc();
console.log('Local to UTC conversion:');
console.log('  Local:', localTime.format('YYYY-MM-DD HH:mm:ss'));
console.log('  UTC:', utcTime.format('YYYY-MM-DD HH:mm:ss'));

// 9. Practical Examples
console.log('\n=== PRACTICAL EXAMPLES ===');

// Business days calculation
function addBusinessDays(startDate, days) {
  const result = moment(startDate);
  let addedDays = 0;
  
  while (addedDays < days) {
    result.add(1, 'day');
    if (result.day() !== 0 && result.day() !== 6) { // Not Sunday (0) or Saturday (6)
      addedDays++;
    }
  }
  
  return result;
}

const businessStart = moment('2023-06-15'); // Thursday
const businessEnd = addBusinessDays(businessStart, 5);
console.log('Business days calculation:');
console.log(`  Start: ${businessStart.format('dddd, YYYY-MM-DD')}`);
console.log(`  +5 business days: ${businessEnd.format('dddd, YYYY-MM-DD')}`);

// Age calculator
function calculateAge(birthDate) {
  const birth = moment(birthDate);
  const now = moment();
  
  const years = now.diff(birth, 'years');
  const months = now.diff(birth.clone().add(years, 'years'), 'months');
  const days = now.diff(birth.clone().add(years, 'years').add(months, 'months'), 'days');
  
  return { years, months, days };
}

const age = calculateAge('1990-05-15');
console.log('Detailed age calculation:');
console.log(`  ${age.years} years, ${age.months} months, ${age.days} days old`);

// Meeting scheduler
function scheduleRecurringMeeting(startDate, frequency, count) {
  const meetings = [];
  let currentDate = moment(startDate);
  
  for (let i = 0; i < count; i++) {
    meetings.push(currentDate.clone());
    
    switch (frequency) {
      case 'daily':
        currentDate.add(1, 'day');
        break;
      case 'weekly':
        currentDate.add(1, 'week');
        break;
      case 'monthly':
        currentDate.add(1, 'month');
        break;
    }
  }
  
  return meetings;
}

const weeklyMeetings = scheduleRecurringMeeting('2023-06-15 10:00:00', 'weekly', 4);
console.log('Weekly meetings schedule:');
weeklyMeetings.forEach((meeting, index) => {
  console.log(`  Meeting ${index + 1}: ${meeting.format('dddd, MMMM Do YYYY [at] h:mm A')}`);
});

// Time until deadline
function timeUntilDeadline(deadlineDate) {
  const deadline = moment(deadlineDate);
  const now = moment();
  
  if (deadline.isBefore(now)) {
    return `Deadline passed ${now.diff(deadline, 'days')} days ago`;
  }
  
  const duration = moment.duration(deadline.diff(now));
  const days = Math.floor(duration.asDays());
  const hours = duration.hours();
  const minutes = duration.minutes();
  
  return `${days} days, ${hours} hours, ${minutes} minutes remaining`;
}

console.log('Deadline countdown:');
console.log('  Project deadline:', timeUntilDeadline('2023-12-31 23:59:59'));

console.log('\n=== MOMENT.JS EXAMPLES COMPLETE ===');
