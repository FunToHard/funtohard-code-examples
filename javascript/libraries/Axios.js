// Axios.js - HTTP Client Library Examples
// Handle HTTP requests with proper error handling, interceptors, and async/await patterns.

const axios = require('axios');

// 1. Basic HTTP Requests
console.log('=== BASIC HTTP REQUESTS ===');

// GET request with async/await
async function fetchUsers() {
  try {
    console.log('Fetching users...');
    const response = await axios.get('https://jsonplaceholder.typicode.com/users');
    
    console.log('Status:', response.status);
    console.log('Headers:', response.headers['content-type']);
    console.log('First user:', response.data[0]);
    console.log('Total users:', response.data.length);
    
    return response.data;
  } catch (error) {
    console.error('Error fetching users:', error.message);
    throw error;
  }
}

// POST request to create new resource
async function createUser(userData) {
  try {
    console.log('\nCreating new user...');
    const response = await axios.post('https://jsonplaceholder.typicode.com/users', userData);
    
    console.log('Created user:', response.data);
    console.log('Status:', response.status);
    
    return response.data;
  } catch (error) {
    console.error('Error creating user:', error.message);
    throw error;
  }
}

// PUT request to update resource
async function updateUser(userId, userData) {
  try {
    console.log(`\nUpdating user ${userId}...`);
    const response = await axios.put(`https://jsonplaceholder.typicode.com/users/${userId}`, userData);
    
    console.log('Updated user:', response.data);
    return response.data;
  } catch (error) {
    console.error('Error updating user:', error.message);
    throw error;
  }
}

// DELETE request
async function deleteUser(userId) {
  try {
    console.log(`\nDeleting user ${userId}...`);
    const response = await axios.delete(`https://jsonplaceholder.typicode.com/users/${userId}`);
    
    console.log('Delete status:', response.status);
    return response.status === 200;
  } catch (error) {
    console.error('Error deleting user:', error.message);
    throw error;
  }
}

// 2. Request Configuration and Headers
console.log('\n=== REQUEST CONFIGURATION ===');

// Custom headers and configuration
async function fetchWithAuth() {
  try {
    const config = {
      headers: {
        'Authorization': 'Bearer your-token-here',
        'Content-Type': 'application/json',
        'X-Custom-Header': 'custom-value'
      },
      timeout: 5000, // 5 second timeout
      params: {
        page: 1,
        limit: 10,
        sort: 'name'
      }
    };
    
    const response = await axios.get('https://jsonplaceholder.typicode.com/posts', config);
    console.log('Posts with config:', response.data.slice(0, 3));
    
  } catch (error) {
    if (error.code === 'ECONNABORTED') {
      console.error('Request timeout');
    } else {
      console.error('Request failed:', error.message);
    }
  }
}

// 3. Interceptors for Request/Response Processing
console.log('\n=== INTERCEPTORS ===');

// Request interceptor
axios.interceptors.request.use(
  (config) => {
    console.log(`Making ${config.method?.toUpperCase()} request to ${config.url}`);
    
    // Add timestamp to all requests
    config.metadata = { startTime: new Date() };
    
    // Add auth token if available
    const token = process.env.AUTH_TOKEN;
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    
    return config;
  },
  (error) => {
    console.error('Request interceptor error:', error);
    return Promise.reject(error);
  }
);

// Response interceptor
axios.interceptors.response.use(
  (response) => {
    const duration = new Date() - response.config.metadata.startTime;
    console.log(`Request completed in ${duration}ms`);
    
    return response;
  },
  (error) => {
    console.error('Response interceptor error:', error.response?.status, error.message);
    
    // Handle specific error cases
    if (error.response?.status === 401) {
      console.log('Unauthorized - redirecting to login...');
      // Handle authentication error
    } else if (error.response?.status === 429) {
      console.log('Rate limited - implementing retry logic...');
      // Handle rate limiting
    }
    
    return Promise.reject(error);
  }
);

// 4. Error Handling Patterns
console.log('\n=== ERROR HANDLING ===');

async function robustApiCall(url) {
  try {
    const response = await axios.get(url);
    return { success: true, data: response.data };
  } catch (error) {
    if (error.response) {
      // Server responded with error status
      console.error('Server Error:', {
        status: error.response.status,
        statusText: error.response.statusText,
        data: error.response.data
      });
      
      return {
        success: false,
        error: 'server_error',
        status: error.response.status,
        message: error.response.data?.message || error.response.statusText
      };
    } else if (error.request) {
      // Request was made but no response received
      console.error('Network Error:', error.message);
      return {
        success: false,
        error: 'network_error',
        message: 'No response from server'
      };
    } else {
      // Something else happened
      console.error('Request Setup Error:', error.message);
      return {
        success: false,
        error: 'setup_error',
        message: error.message
      };
    }
  }
}

// 5. Concurrent Requests
console.log('\n=== CONCURRENT REQUESTS ===');

async function fetchMultipleResources() {
  try {
    console.log('Fetching multiple resources concurrently...');
    
    const [usersResponse, postsResponse, commentsResponse] = await Promise.all([
      axios.get('https://jsonplaceholder.typicode.com/users'),
      axios.get('https://jsonplaceholder.typicode.com/posts'),
      axios.get('https://jsonplaceholder.typicode.com/comments')
    ]);
    
    console.log('All requests completed:');
    console.log(`- Users: ${usersResponse.data.length}`);
    console.log(`- Posts: ${postsResponse.data.length}`);
    console.log(`- Comments: ${commentsResponse.data.length}`);
    
    return {
      users: usersResponse.data,
      posts: postsResponse.data,
      comments: commentsResponse.data
    };
  } catch (error) {
    console.error('One or more requests failed:', error.message);
    throw error;
  }
}

// 6. Retry Logic with Exponential Backoff
console.log('\n=== RETRY LOGIC ===');

async function retryRequest(url, maxRetries = 3) {
  for (let attempt = 1; attempt <= maxRetries; attempt++) {
    try {
      console.log(`Attempt ${attempt} for ${url}`);
      const response = await axios.get(url, { timeout: 2000 });
      console.log('Request successful on attempt', attempt);
      return response.data;
    } catch (error) {
      console.log(`Attempt ${attempt} failed:`, error.message);
      
      if (attempt === maxRetries) {
        console.error('All retry attempts failed');
        throw error;
      }
      
      // Exponential backoff: wait 2^attempt seconds
      const delay = Math.pow(2, attempt) * 1000;
      console.log(`Waiting ${delay}ms before retry...`);
      await new Promise(resolve => setTimeout(resolve, delay));
    }
  }
}

// 7. File Upload Example
console.log('\n=== FILE UPLOAD ===');

async function uploadFile(fileData, filename) {
  try {
    const formData = new FormData();
    formData.append('file', fileData, filename);
    formData.append('description', 'Uploaded via Axios');
    
    const response = await axios.post('https://httpbin.org/post', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      },
      onUploadProgress: (progressEvent) => {
        const percentCompleted = Math.round((progressEvent.loaded * 100) / progressEvent.total);
        console.log(`Upload progress: ${percentCompleted}%`);
      }
    });
    
    console.log('File uploaded successfully:', response.data.files);
    return response.data;
  } catch (error) {
    console.error('File upload failed:', error.message);
    throw error;
  }
}

// 8. API Client Class
console.log('\n=== API CLIENT CLASS ===');

class ApiClient {
  constructor(baseURL, defaultHeaders = {}) {
    this.client = axios.create({
      baseURL,
      timeout: 10000,
      headers: {
        'Content-Type': 'application/json',
        ...defaultHeaders
      }
    });
    
    this.setupInterceptors();
  }
  
  setupInterceptors() {
    // Request interceptor
    this.client.interceptors.request.use(
      (config) => {
        console.log(`[API] ${config.method?.toUpperCase()} ${config.url}`);
        return config;
      },
      (error) => Promise.reject(error)
    );
    
    // Response interceptor
    this.client.interceptors.response.use(
      (response) => response,
      (error) => {
        console.error(`[API Error] ${error.response?.status}: ${error.message}`);
        return Promise.reject(error);
      }
    );
  }
  
  async get(endpoint, params = {}) {
    const response = await this.client.get(endpoint, { params });
    return response.data;
  }
  
  async post(endpoint, data) {
    const response = await this.client.post(endpoint, data);
    return response.data;
  }
  
  async put(endpoint, data) {
    const response = await this.client.put(endpoint, data);
    return response.data;
  }
  
  async delete(endpoint) {
    const response = await this.client.delete(endpoint);
    return response.data;
  }
  
  setAuthToken(token) {
    this.client.defaults.headers.common['Authorization'] = `Bearer ${token}`;
  }
  
  removeAuthToken() {
    delete this.client.defaults.headers.common['Authorization'];
  }
}

// 9. Usage Examples
async function runExamples() {
  try {
    // Basic requests
    await fetchUsers();
    
    const newUser = {
      name: 'John Doe',
      username: 'johndoe',
      email: 'john@example.com'
    };
    
    await createUser(newUser);
    await updateUser(1, { ...newUser, name: 'John Updated' });
    await deleteUser(1);
    
    // Advanced features
    await fetchWithAuth();
    await fetchMultipleResources();
    
    // Error handling
    const result = await robustApiCall('https://jsonplaceholder.typicode.com/posts/1');
    console.log('Robust API call result:', result.success);
    
    // API Client usage
    const api = new ApiClient('https://jsonplaceholder.typicode.com');
    const posts = await api.get('/posts', { _limit: 5 });
    console.log('API Client posts:', posts.length);
    
  } catch (error) {
    console.error('Example execution failed:', error.message);
  }
}

// Run examples if this file is executed directly
if (require.main === module) {
  runExamples();
}

module.exports = {
  fetchUsers,
  createUser,
  updateUser,
  deleteUser,
  robustApiCall,
  ApiClient
};
