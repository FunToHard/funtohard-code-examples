# Requests.py - HTTP Library Examples
# Handle HTTP requests with authentication, error handling, and session management.

import requests
import json
import time
from requests.adapters import HTTPAdapter
from requests.packages.urllib3.util.retry import Retry
import base64

print("=== BASIC HTTP REQUESTS ===")

# 1. Basic GET Request
print("1. Basic GET Request:")

try:
    response = requests.get('https://jsonplaceholder.typicode.com/posts/1')
    print(f"Status Code: {response.status_code}")
    print(f"Content-Type: {response.headers.get('content-type')}")
    print(f"Response Data: {response.json()}")
except requests.exceptions.RequestException as e:
    print(f"Error: {e}")

# 2. GET with Parameters
print("\n2. GET with Parameters:")

params = {
    'userId': 1,
    '_limit': 3
}

try:
    response = requests.get('https://jsonplaceholder.typicode.com/posts', params=params)
    print(f"URL: {response.url}")
    print(f"Number of posts: {len(response.json())}")
    for post in response.json():
        print(f"  Post {post['id']}: {post['title'][:50]}...")
except requests.exceptions.RequestException as e:
    print(f"Error: {e}")

# 3. POST Request
print("\n3. POST Request:")

post_data = {
    'title': 'My New Post',
    'body': 'This is the content of my new post.',
    'userId': 1
}

try:
    response = requests.post('https://jsonplaceholder.typicode.com/posts', json=post_data)
    print(f"Status Code: {response.status_code}")
    print(f"Created Post: {response.json()}")
except requests.exceptions.RequestException as e:
    print(f"Error: {e}")

# 4. PUT Request (Update)
print("\n4. PUT Request:")

update_data = {
    'id': 1,
    'title': 'Updated Post Title',
    'body': 'This post has been updated.',
    'userId': 1
}

try:
    response = requests.put('https://jsonplaceholder.typicode.com/posts/1', json=update_data)
    print(f"Status Code: {response.status_code}")
    print(f"Updated Post: {response.json()}")
except requests.exceptions.RequestException as e:
    print(f"Error: {e}")

# 5. DELETE Request
print("\n5. DELETE Request:")

try:
    response = requests.delete('https://jsonplaceholder.typicode.com/posts/1')
    print(f"Status Code: {response.status_code}")
    print(f"Response: {response.text}")
except requests.exceptions.RequestException as e:
    print(f"Error: {e}")

print("\n=== HEADERS AND AUTHENTICATION ===")

# 6. Custom Headers
print("6. Custom Headers:")

headers = {
    'User-Agent': 'MyApp/1.0',
    'Accept': 'application/json',
    'Content-Type': 'application/json',
    'X-Custom-Header': 'custom-value'
}

try:
    response = requests.get('https://httpbin.org/headers', headers=headers)
    print(f"Request headers sent: {response.json()['headers']}")
except requests.exceptions.RequestException as e:
    print(f"Error: {e}")

# 7. Basic Authentication
print("\n7. Basic Authentication:")

try:
    # Using httpbin.org for testing authentication
    response = requests.get('https://httpbin.org/basic-auth/user/pass', 
                          auth=('user', 'pass'))
    print(f"Status Code: {response.status_code}")
    print(f"Authenticated Response: {response.json()}")
except requests.exceptions.RequestException as e:
    print(f"Error: {e}")

# 8. Bearer Token Authentication
print("\n8. Bearer Token Authentication:")

token = "your-api-token-here"
headers = {
    'Authorization': f'Bearer {token}',
    'Content-Type': 'application/json'
}

try:
    # Simulate API call with bearer token
    response = requests.get('https://httpbin.org/bearer', headers=headers)
    if response.status_code == 401:
        print("Authentication required (expected with dummy token)")
    else:
        print(f"Response: {response.json()}")
except requests.exceptions.RequestException as e:
    print(f"Error: {e}")

# 9. API Key Authentication
print("\n9. API Key Authentication:")

api_key = "your-api-key-here"
params = {'api_key': api_key}

# Alternative: API key in header
headers = {'X-API-Key': api_key}

try:
    response = requests.get('https://httpbin.org/get', params=params, headers=headers)
    print(f"Request with API key: {response.json()['args']}")
except requests.exceptions.RequestException as e:
    print(f"Error: {e}")

print("\n=== SESSION MANAGEMENT ===")

# 10. Using Sessions
print("10. Using Sessions:")

session = requests.Session()

# Set default headers for all requests in this session
session.headers.update({
    'User-Agent': 'MyApp/1.0',
    'Accept': 'application/json'
})

# Login simulation
login_data = {'username': 'testuser', 'password': 'testpass'}

try:
    # Login request
    login_response = session.post('https://httpbin.org/post', json=login_data)
    print(f"Login Status: {login_response.status_code}")
    
    # Subsequent requests will maintain session state
    profile_response = session.get('https://httpbin.org/get')
    print(f"Session maintained: {profile_response.status_code}")
    
    # Session cookies are automatically handled
    print(f"Session cookies: {session.cookies}")
    
except requests.exceptions.RequestException as e:
    print(f"Error: {e}")
finally:
    session.close()

print("\n=== ERROR HANDLING ===")

# 11. Comprehensive Error Handling
print("11. Error Handling:")

def make_request_with_error_handling(url, **kwargs):
    """Make HTTP request with comprehensive error handling"""
    try:
        response = requests.get(url, timeout=5, **kwargs)
        
        # Check if request was successful
        response.raise_for_status()
        
        return {
            'success': True,
            'status_code': response.status_code,
            'data': response.json() if response.headers.get('content-type', '').startswith('application/json') else response.text,
            'headers': dict(response.headers)
        }
        
    except requests.exceptions.HTTPError as e:
        return {
            'success': False,
            'error_type': 'HTTP Error',
            'status_code': e.response.status_code if e.response else None,
            'message': str(e)
        }
    except requests.exceptions.ConnectionError as e:
        return {
            'success': False,
            'error_type': 'Connection Error',
            'message': 'Failed to connect to the server'
        }
    except requests.exceptions.Timeout as e:
        return {
            'success': False,
            'error_type': 'Timeout Error',
            'message': 'Request timed out'
        }
    except requests.exceptions.RequestException as e:
        return {
            'success': False,
            'error_type': 'Request Error',
            'message': str(e)
        }
    except json.JSONDecodeError as e:
        return {
            'success': False,
            'error_type': 'JSON Decode Error',
            'message': 'Invalid JSON response'
        }

# Test error handling
test_urls = [
    'https://jsonplaceholder.typicode.com/posts/1',  # Valid
    'https://httpbin.org/status/404',  # 404 Error
    'https://httpbin.org/delay/10',  # Timeout (with 5s timeout)
    'https://invalid-domain-that-does-not-exist.com'  # Connection Error
]

for url in test_urls:
    print(f"\nTesting: {url}")
    result = make_request_with_error_handling(url)
    if result['success']:
        print(f"  Success: Status {result['status_code']}")
    else:
        print(f"  Error: {result['error_type']} - {result['message']}")

print("\n=== RETRY LOGIC ===")

# 12. Retry Logic with Exponential Backoff
print("12. Retry Logic:")

def create_session_with_retries():
    """Create session with automatic retry logic"""
    session = requests.Session()
    
    # Define retry strategy
    retry_strategy = Retry(
        total=3,  # Total number of retries
        status_forcelist=[429, 500, 502, 503, 504],  # HTTP status codes to retry
        method_whitelist=["HEAD", "GET", "OPTIONS"],  # HTTP methods to retry
        backoff_factor=1  # Backoff factor for exponential backoff
    )
    
    # Mount adapter with retry strategy
    adapter = HTTPAdapter(max_retries=retry_strategy)
    session.mount("http://", adapter)
    session.mount("https://", adapter)
    
    return session

# Test retry logic
retry_session = create_session_with_retries()

try:
    # This will trigger retries due to 500 status
    print("Testing retry logic with 500 error:")
    response = retry_session.get('https://httpbin.org/status/500', timeout=10)
except requests.exceptions.RequestException as e:
    print(f"Request failed after retries: {e}")
finally:
    retry_session.close()

print("\n=== FILE OPERATIONS ===")

# 13. File Upload
print("13. File Upload:")

# Create a sample file
sample_content = "This is a sample file for upload testing."
with open('sample_upload.txt', 'w') as f:
    f.write(sample_content)

try:
    # Upload file
    with open('sample_upload.txt', 'rb') as f:
        files = {'file': ('sample.txt', f, 'text/plain')}
        data = {'description': 'Sample file upload'}
        
        response = requests.post('https://httpbin.org/post', files=files, data=data)
        print(f"Upload Status: {response.status_code}")
        print(f"Files uploaded: {response.json()['files']}")
        
except requests.exceptions.RequestException as e:
    print(f"Upload Error: {e}")

# 14. File Download
print("\n14. File Download:")

def download_file(url, filename):
    """Download file with progress tracking"""
    try:
        response = requests.get(url, stream=True)
        response.raise_for_status()
        
        total_size = int(response.headers.get('content-length', 0))
        downloaded_size = 0
        
        with open(filename, 'wb') as f:
            for chunk in response.iter_content(chunk_size=8192):
                if chunk:
                    f.write(chunk)
                    downloaded_size += len(chunk)
                    
                    if total_size > 0:
                        progress = (downloaded_size / total_size) * 100
                        print(f"\rDownload progress: {progress:.1f}%", end='')
        
        print(f"\nDownload completed: {filename}")
        return True
        
    except requests.exceptions.RequestException as e:
        print(f"Download Error: {e}")
        return False

# Download a small test file
download_success = download_file('https://httpbin.org/json', 'downloaded_data.json')

print("\n=== ADVANCED FEATURES ===")

# 15. Streaming Responses
print("15. Streaming Responses:")

def stream_large_response(url):
    """Stream large response to avoid memory issues"""
    try:
        response = requests.get(url, stream=True)
        response.raise_for_status()
        
        chunk_count = 0
        for chunk in response.iter_content(chunk_size=1024):
            if chunk:
                chunk_count += 1
                if chunk_count <= 5:  # Show first 5 chunks
                    print(f"Chunk {chunk_count}: {len(chunk)} bytes")
                elif chunk_count == 6:
                    print("... (more chunks)")
        
        print(f"Total chunks processed: {chunk_count}")
        
    except requests.exceptions.RequestException as e:
        print(f"Streaming Error: {e}")

# Test streaming (using a JSON endpoint)
stream_large_response('https://jsonplaceholder.typicode.com/posts')

# 16. Custom Timeout Configuration
print("\n16. Timeout Configuration:")

timeout_configs = [
    (3.05, 27),  # (connect timeout, read timeout)
    5,  # Single timeout for both
    None  # No timeout (not recommended)
]

for i, timeout in enumerate(timeout_configs[:2]):  # Skip None timeout for safety
    try:
        print(f"Testing timeout config {i+1}: {timeout}")
        response = requests.get('https://httpbin.org/delay/2', timeout=timeout)
        print(f"  Success: {response.status_code}")
    except requests.exceptions.Timeout:
        print(f"  Timeout occurred")
    except requests.exceptions.RequestException as e:
        print(f"  Error: {e}")

print("\n=== REAL-WORLD API EXAMPLES ===")

# 17. REST API Client Class
print("17. REST API Client:")

class APIClient:
    """Generic REST API client with common functionality"""
    
    def __init__(self, base_url, api_key=None, timeout=30):
        self.base_url = base_url.rstrip('/')
        self.session = requests.Session()
        self.timeout = timeout
        
        # Set default headers
        self.session.headers.update({
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'User-Agent': 'APIClient/1.0'
        })
        
        # Set API key if provided
        if api_key:
            self.session.headers.update({'Authorization': f'Bearer {api_key}'})
    
    def _make_request(self, method, endpoint, **kwargs):
        """Make HTTP request with error handling"""
        url = f"{self.base_url}/{endpoint.lstrip('/')}"
        
        try:
            response = self.session.request(method, url, timeout=self.timeout, **kwargs)
            response.raise_for_status()
            
            # Try to parse JSON, fallback to text
            try:
                return response.json()
            except json.JSONDecodeError:
                return response.text
                
        except requests.exceptions.RequestException as e:
            print(f"API Error: {e}")
            raise
    
    def get(self, endpoint, params=None):
        """GET request"""
        return self._make_request('GET', endpoint, params=params)
    
    def post(self, endpoint, data=None, json_data=None):
        """POST request"""
        return self._make_request('POST', endpoint, data=data, json=json_data)
    
    def put(self, endpoint, data=None, json_data=None):
        """PUT request"""
        return self._make_request('PUT', endpoint, data=data, json=json_data)
    
    def delete(self, endpoint):
        """DELETE request"""
        return self._make_request('DELETE', endpoint)
    
    def close(self):
        """Close session"""
        self.session.close()

# Usage example
api = APIClient('https://jsonplaceholder.typicode.com')

try:
    # Get all posts
    posts = api.get('/posts', params={'_limit': 3})
    print(f"Retrieved {len(posts)} posts")
    
    # Create new post
    new_post = api.post('/posts', json_data={
        'title': 'API Client Test',
        'body': 'Testing the API client',
        'userId': 1
    })
    print(f"Created post with ID: {new_post['id']}")
    
    # Update post
    updated_post = api.put(f"/posts/{new_post['id']}", json_data={
        'title': 'Updated API Client Test',
        'body': 'Updated content',
        'userId': 1
    })
    print(f"Updated post title: {updated_post['title']}")
    
except Exception as e:
    print(f"API Client Error: {e}")
finally:
    api.close()

# 18. Rate Limiting
print("\n18. Rate Limiting:")

class RateLimitedClient:
    """API client with rate limiting"""
    
    def __init__(self, requests_per_second=1):
        self.min_interval = 1.0 / requests_per_second
        self.last_request_time = 0
    
    def make_request(self, url, **kwargs):
        """Make rate-limited request"""
        # Calculate time since last request
        current_time = time.time()
        time_since_last = current_time - self.last_request_time
        
        # Wait if necessary
        if time_since_last < self.min_interval:
            sleep_time = self.min_interval - time_since_last
            print(f"Rate limiting: sleeping for {sleep_time:.2f} seconds")
            time.sleep(sleep_time)
        
        # Make request
        response = requests.get(url, **kwargs)
        self.last_request_time = time.time()
        
        return response

# Test rate limiting
rate_limited_client = RateLimitedClient(requests_per_second=0.5)  # 1 request per 2 seconds

urls = [
    'https://jsonplaceholder.typicode.com/posts/1',
    'https://jsonplaceholder.typicode.com/posts/2',
    'https://jsonplaceholder.typicode.com/posts/3'
]

print("Making rate-limited requests:")
for i, url in enumerate(urls):
    start_time = time.time()
    response = rate_limited_client.make_request(url)
    elapsed = time.time() - start_time
    print(f"Request {i+1}: Status {response.status_code}, Time: {elapsed:.2f}s")

print("\n=== CLEANUP ===")

# Clean up created files
import os
try:
    os.remove('sample_upload.txt')
    os.remove('downloaded_data.json')
    print("Cleanup completed")
except FileNotFoundError:
    print("Files already cleaned up")

print("\n=== REQUESTS EXAMPLES COMPLETE ===")
