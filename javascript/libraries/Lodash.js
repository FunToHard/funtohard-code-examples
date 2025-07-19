// Lodash.js - JavaScript Utility Library Examples
// Master utility functions for arrays, objects, and functional programming patterns with Lodash.

const _ = require('lodash');

// 1. Array Manipulation Examples
console.log('=== ARRAY MANIPULATION ===');

const numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
const users = [
  { name: 'John', age: 30, active: true, department: 'Engineering' },
  { name: 'Jane', age: 25, active: false, department: 'Marketing' },
  { name: 'Bob', age: 35, active: true, department: 'Engineering' },
  { name: 'Alice', age: 28, active: true, department: 'Design' },
  { name: 'Charlie', age: 32, active: false, department: 'Marketing' }
];

// Map, Filter, Reduce alternatives
const doubled = _.map(numbers, n => n * 2);
console.log('Doubled numbers:', doubled);

const evenNumbers = _.filter(numbers, n => n % 2 === 0);
console.log('Even numbers:', evenNumbers);

const sum = _.reduce(numbers, (acc, n) => acc + n, 0);
console.log('Sum of numbers:', sum);

// Advanced array operations
const chunked = _.chunk(numbers, 3);
console.log('Chunked array:', chunked);

const flattened = _.flatten([[1, 2], [3, 4], [5, 6]]);
console.log('Flattened array:', flattened);

const unique = _.uniq([1, 2, 2, 3, 3, 4, 5]);
console.log('Unique values:', unique);

// 2. Object Manipulation Examples
console.log('\n=== OBJECT MANIPULATION ===');

const user = {
  id: 1,
  name: 'John Doe',
  email: 'john@example.com',
  profile: {
    age: 30,
    address: {
      street: '123 Main St',
      city: 'New York',
      country: 'USA'
    }
  }
};

// Safe property access
const userName = _.get(user, 'name', 'Unknown');
const userAge = _.get(user, 'profile.age', 0);
const userStreet = _.get(user, 'profile.address.street', 'No address');
console.log('User info:', { userName, userAge, userStreet });

// Object manipulation
const userCopy = _.cloneDeep(user);
_.set(userCopy, 'profile.address.city', 'Los Angeles');
console.log('Modified copy city:', _.get(userCopy, 'profile.address.city'));
console.log('Original city:', _.get(user, 'profile.address.city'));

// Pick and omit properties
const publicUser = _.pick(user, ['id', 'name']);
const userWithoutEmail = _.omit(user, ['email']);
console.log('Public user:', publicUser);
console.log('User without email keys:', Object.keys(userWithoutEmail));

// 3. Collection Operations
console.log('\n=== COLLECTION OPERATIONS ===');

// Group by property
const usersByDepartment = _.groupBy(users, 'department');
console.log('Users by department:', usersByDepartment);

// Sort by multiple criteria
const sortedUsers = _.orderBy(users, ['age', 'name'], ['desc', 'asc']);
console.log('Sorted users:', sortedUsers.map(u => `${u.name} (${u.age})`));

// Find operations
const activeUser = _.find(users, { active: true });
const engineeringUsers = _.filter(users, { department: 'Engineering' });
console.log('First active user:', activeUser.name);
console.log('Engineering users:', engineeringUsers.map(u => u.name));

// Statistical operations
const ages = _.map(users, 'age');
console.log('Average age:', _.mean(ages));
console.log('Min age:', _.min(ages));
console.log('Max age:', _.max(ages));

// 4. String Manipulation
console.log('\n=== STRING MANIPULATION ===');

const text = '  hello world from lodash  ';
console.log('Capitalized:', _.capitalize(text));
console.log('Camel case:', _.camelCase(text));
console.log('Snake case:', _.snakeCase(text));
console.log('Kebab case:', _.kebabCase(text));
console.log('Start case:', _.startCase(text));

// String utilities
console.log('Trimmed:', _.trim(text));
console.log('Truncated:', _.truncate('This is a very long string that needs truncating', { length: 20 }));
console.log('Padded:', _.pad('hello', 10, '*'));

// 5. Function Utilities
console.log('\n=== FUNCTION UTILITIES ===');

// Debounce example (simulated)
let searchCount = 0;
const search = (query) => {
  searchCount++;
  console.log(`Searching for: ${query} (call #${searchCount})`);
};

const debouncedSearch = _.debounce(search, 300);

// Simulate rapid calls
console.log('Simulating rapid search calls...');
debouncedSearch('react');
debouncedSearch('react hooks');
debouncedSearch('react hooks tutorial');

// Throttle example
let scrollCount = 0;
const handleScroll = () => {
  scrollCount++;
  console.log(`Scroll event handled (call #${scrollCount})`);
};

const throttledScroll = _.throttle(handleScroll, 100);

// Memoization example
const expensiveCalculation = _.memoize((n) => {
  console.log(`Calculating factorial of ${n}...`);
  if (n <= 1) return 1;
  return n * expensiveCalculation(n - 1);
});

console.log('\nMemoized factorial calculations:');
console.log('5! =', expensiveCalculation(5));
console.log('5! =', expensiveCalculation(5)); // Cached result
console.log('6! =', expensiveCalculation(6));

// 6. Validation and Type Checking
console.log('\n=== VALIDATION & TYPE CHECKING ===');

const testValues = [
  42,
  'hello',
  [1, 2, 3],
  { key: 'value' },
  null,
  undefined,
  true,
  new Date()
];

testValues.forEach(value => {
  console.log(`Value: ${value}`);
  console.log(`  isNumber: ${_.isNumber(value)}`);
  console.log(`  isString: ${_.isString(value)}`);
  console.log(`  isArray: ${_.isArray(value)}`);
  console.log(`  isObject: ${_.isObject(value)}`);
  console.log(`  isNull: ${_.isNull(value)}`);
  console.log(`  isUndefined: ${_.isUndefined(value)}`);
  console.log(`  isEmpty: ${_.isEmpty(value)}`);
  console.log('---');
});

// 7. Advanced Functional Programming
console.log('\n=== FUNCTIONAL PROGRAMMING ===');

// Composition
const addOne = x => x + 1;
const multiplyByTwo = x => x * 2;
const square = x => x * x;

const composedFunction = _.flow([addOne, multiplyByTwo, square]);
console.log('Composed function (5):', composedFunction(5)); // ((5 + 1) * 2)^2 = 144

// Partial application
const greet = (greeting, name, punctuation) => `${greeting}, ${name}${punctuation}`;
const sayHello = _.partial(greet, 'Hello');
const sayHelloExcited = _.partial(greet, 'Hello', _, '!');

console.log('Partial application:', sayHello('World', '.'));
console.log('Partial with placeholder:', sayHelloExcited('Alice'));

// Currying
const curriedAdd = _.curry((a, b, c) => a + b + c);
const addFive = curriedAdd(5);
const addFiveAndThree = addFive(3);

console.log('Curried function:', addFiveAndThree(2)); // 5 + 3 + 2 = 10

// 8. Practical Real-World Examples
console.log('\n=== REAL-WORLD EXAMPLES ===');

// Data transformation pipeline
const rawData = [
  { id: 1, name: 'john doe', email: 'JOHN@EXAMPLE.COM', score: 85, tags: ['javascript', 'react'] },
  { id: 2, name: 'jane smith', email: 'jane@example.com', score: 92, tags: ['python', 'django'] },
  { id: 3, name: 'bob johnson', email: 'BOB@EXAMPLE.COM', score: 78, tags: ['java', 'spring'] }
];

const processedData = _.chain(rawData)
  .map(user => ({
    ...user,
    name: _.startCase(user.name),
    email: _.toLower(user.email),
    grade: user.score >= 90 ? 'A' : user.score >= 80 ? 'B' : 'C'
  }))
  .filter(user => user.score >= 80)
  .orderBy(['score'], ['desc'])
  .value();

console.log('Processed data:', processedData);

// Form validation helper
const validateForm = (formData, rules) => {
  const errors = {};
  
  _.forEach(rules, (rule, field) => {
    const value = _.get(formData, field);
    
    if (rule.required && _.isEmpty(value)) {
      _.set(errors, field, 'This field is required');
    } else if (rule.minLength && _.size(value) < rule.minLength) {
      _.set(errors, field, `Minimum length is ${rule.minLength}`);
    } else if (rule.pattern && !rule.pattern.test(value)) {
      _.set(errors, field, 'Invalid format');
    }
  });
  
  return {
    isValid: _.isEmpty(errors),
    errors
  };
};

const formData = {
  username: 'john',
  email: 'invalid-email',
  password: '123'
};

const validationRules = {
  username: { required: true, minLength: 3 },
  email: { required: true, pattern: /^[^\s@]+@[^\s@]+\.[^\s@]+$/ },
  password: { required: true, minLength: 6 }
};

const validation = validateForm(formData, validationRules);
console.log('Form validation:', validation);

console.log('\n=== LODASH EXAMPLES COMPLETE ===');
