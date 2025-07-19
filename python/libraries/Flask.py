# Flask.py - Web Framework Examples
# Build web applications with routing, templates, and RESTful API development.

from flask import Flask, request, jsonify, render_template_string, session, redirect, url_for, flash
from flask import abort, make_response, send_file
from werkzeug.security import generate_password_hash, check_password_hash
from functools import wraps
import sqlite3
import json
import os
from datetime import datetime, timedelta

# Create Flask application
app = Flask(__name__)
app.secret_key = 'your-secret-key-here'  # Change this in production!

print("=== BASIC FLASK APPLICATION ===")

# 1. Basic Routes
print("1. Basic Routes:")

@app.route('/')
def home():
    """Home page route"""
    return '''
    <h1>Welcome to Flask Examples</h1>
    <p>This is a comprehensive Flask application demonstrating various features.</p>
    <ul>
        <li><a href="/hello">Hello World</a></li>
        <li><a href="/user/john">User Profile</a></li>
        <li><a href="/api/users">API Users</a></li>
        <li><a href="/form">Form Example</a></li>
        <li><a href="/login">Login</a></li>
    </ul>
    '''

@app.route('/hello')
def hello():
    """Simple hello route"""
    return '<h1>Hello, World!</h1>'

@app.route('/hello/<name>')
def hello_name(name):
    """Route with URL parameter"""
    return f'<h1>Hello, {name}!</h1>'

@app.route('/user/<username>')
def user_profile(username):
    """User profile route"""
    return f'''
    <h1>User Profile</h1>
    <p>Username: {username}</p>
    <p>Profile created at: {datetime.now().strftime("%Y-%m-%d %H:%M:%S")}</p>
    '''

print("Basic routes defined")

# 2. HTTP Methods
print("\n2. HTTP Methods:")

@app.route('/api/data', methods=['GET', 'POST', 'PUT', 'DELETE'])
def handle_data():
    """Handle different HTTP methods"""
    if request.method == 'GET':
        return jsonify({
            'message': 'GET request received',
            'data': ['item1', 'item2', 'item3']
        })
    
    elif request.method == 'POST':
        data = request.get_json()
        return jsonify({
            'message': 'POST request received',
            'received_data': data,
            'timestamp': datetime.now().isoformat()
        }), 201
    
    elif request.method == 'PUT':
        data = request.get_json()
        return jsonify({
            'message': 'PUT request received',
            'updated_data': data
        })
    
    elif request.method == 'DELETE':
        return jsonify({
            'message': 'DELETE request received',
            'status': 'deleted'
        })

# 3. Query Parameters
@app.route('/search')
def search():
    """Handle query parameters"""
    query = request.args.get('q', '')
    page = request.args.get('page', 1, type=int)
    per_page = request.args.get('per_page', 10, type=int)
    
    # Simulate search results
    results = [f"Result {i} for '{query}'" for i in range(1, per_page + 1)]
    
    return jsonify({
        'query': query,
        'page': page,
        'per_page': per_page,
        'results': results,
        'total': 100  # Simulated total
    })

print("HTTP methods and query parameters configured")

print("\n=== FORMS AND REQUEST HANDLING ===")

# 4. Form Handling
@app.route('/form', methods=['GET', 'POST'])
def form_example():
    """Form handling example"""
    if request.method == 'GET':
        return '''
        <h1>Form Example</h1>
        <form method="POST">
            <p>
                <label>Name:</label><br>
                <input type="text" name="name" required>
            </p>
            <p>
                <label>Email:</label><br>
                <input type="email" name="email" required>
            </p>
            <p>
                <label>Age:</label><br>
                <input type="number" name="age" min="1" max="120">
            </p>
            <p>
                <label>Message:</label><br>
                <textarea name="message" rows="4" cols="50"></textarea>
            </p>
            <p>
                <input type="submit" value="Submit">
            </p>
        </form>
        '''
    
    elif request.method == 'POST':
        name = request.form.get('name')
        email = request.form.get('email')
        age = request.form.get('age', type=int)
        message = request.form.get('message')
        
        return f'''
        <h1>Form Submitted Successfully!</h1>
        <p><strong>Name:</strong> {name}</p>
        <p><strong>Email:</strong> {email}</p>
        <p><strong>Age:</strong> {age}</p>
        <p><strong>Message:</strong> {message}</p>
        <p><a href="/form">Submit Another</a></p>
        '''

# 5. File Upload
@app.route('/upload', methods=['GET', 'POST'])
def upload_file():
    """File upload example"""
    if request.method == 'GET':
        return '''
        <h1>File Upload</h1>
        <form method="POST" enctype="multipart/form-data">
            <p>
                <label>Select file:</label><br>
                <input type="file" name="file" required>
            </p>
            <p>
                <label>Description:</label><br>
                <input type="text" name="description">
            </p>
            <p>
                <input type="submit" value="Upload">
            </p>
        </form>
        '''
    
    elif request.method == 'POST':
        if 'file' not in request.files:
            return 'No file selected', 400
        
        file = request.files['file']
        description = request.form.get('description', '')
        
        if file.filename == '':
            return 'No file selected', 400
        
        # In a real application, you would save the file
        return f'''
        <h1>File Upload Successful!</h1>
        <p><strong>Filename:</strong> {file.filename}</p>
        <p><strong>Content Type:</strong> {file.content_type}</p>
        <p><strong>Description:</strong> {description}</p>
        <p><a href="/upload">Upload Another</a></p>
        '''

print("Form and file upload handlers configured")

print("\n=== SESSION MANAGEMENT ===")

# 6. Session Management
@app.route('/login', methods=['GET', 'POST'])
def login():
    """Login with session management"""
    if request.method == 'GET':
        if 'username' in session:
            return f'''
            <h1>Already Logged In</h1>
            <p>Welcome back, {session['username']}!</p>
            <p><a href="/profile">View Profile</a></p>
            <p><a href="/logout">Logout</a></p>
            '''
        
        return '''
        <h1>Login</h1>
        <form method="POST">
            <p>
                <label>Username:</label><br>
                <input type="text" name="username" required>
            </p>
            <p>
                <label>Password:</label><br>
                <input type="password" name="password" required>
            </p>
            <p>
                <input type="submit" value="Login">
            </p>
        </form>
        <p><em>Use any username and password for demo</em></p>
        '''
    
    elif request.method == 'POST':
        username = request.form.get('username')
        password = request.form.get('password')
        
        # Simple authentication (in production, use proper password hashing)
        if username and password:
            session['username'] = username
            session['login_time'] = datetime.now().isoformat()
            flash('Login successful!', 'success')
            return redirect(url_for('profile'))
        else:
            flash('Invalid credentials!', 'error')
            return redirect(url_for('login'))

@app.route('/profile')
def profile():
    """User profile (requires login)"""
    if 'username' not in session:
        flash('Please log in first!', 'error')
        return redirect(url_for('login'))
    
    return f'''
    <h1>User Profile</h1>
    <p><strong>Username:</strong> {session['username']}</p>
    <p><strong>Login Time:</strong> {session.get('login_time', 'Unknown')}</p>
    <p><a href="/logout">Logout</a></p>
    '''

@app.route('/logout')
def logout():
    """Logout and clear session"""
    session.pop('username', None)
    session.pop('login_time', None)
    flash('You have been logged out!', 'info')
    return redirect(url_for('home'))

print("Session management configured")

print("\n=== REST API ENDPOINTS ===")

# 7. RESTful API
# Simulate a simple database
users_db = [
    {'id': 1, 'name': 'Alice Johnson', 'email': 'alice@example.com', 'age': 28},
    {'id': 2, 'name': 'Bob Smith', 'email': 'bob@example.com', 'age': 32},
    {'id': 3, 'name': 'Charlie Brown', 'email': 'charlie@example.com', 'age': 25}
]

@app.route('/api/users', methods=['GET'])
def get_users():
    """Get all users"""
    return jsonify({
        'users': users_db,
        'total': len(users_db)
    })

@app.route('/api/users/<int:user_id>', methods=['GET'])
def get_user(user_id):
    """Get specific user"""
    user = next((u for u in users_db if u['id'] == user_id), None)
    if user:
        return jsonify(user)
    else:
        return jsonify({'error': 'User not found'}), 404

@app.route('/api/users', methods=['POST'])
def create_user():
    """Create new user"""
    data = request.get_json()
    
    if not data or 'name' not in data or 'email' not in data:
        return jsonify({'error': 'Name and email are required'}), 400
    
    new_user = {
        'id': max([u['id'] for u in users_db]) + 1 if users_db else 1,
        'name': data['name'],
        'email': data['email'],
        'age': data.get('age', 0)
    }
    
    users_db.append(new_user)
    return jsonify(new_user), 201

@app.route('/api/users/<int:user_id>', methods=['PUT'])
def update_user(user_id):
    """Update existing user"""
    user = next((u for u in users_db if u['id'] == user_id), None)
    if not user:
        return jsonify({'error': 'User not found'}), 404
    
    data = request.get_json()
    if not data:
        return jsonify({'error': 'No data provided'}), 400
    
    user.update({k: v for k, v in data.items() if k in ['name', 'email', 'age']})
    return jsonify(user)

@app.route('/api/users/<int:user_id>', methods=['DELETE'])
def delete_user(user_id):
    """Delete user"""
    global users_db
    user = next((u for u in users_db if u['id'] == user_id), None)
    if not user:
        return jsonify({'error': 'User not found'}), 404
    
    users_db = [u for u in users_db if u['id'] != user_id]
    return jsonify({'message': 'User deleted successfully'})

print("REST API endpoints configured")

print("\n=== MIDDLEWARE AND DECORATORS ===")

# 8. Custom Decorators
def require_auth(f):
    """Decorator to require authentication"""
    @wraps(f)
    def decorated_function(*args, **kwargs):
        if 'username' not in session:
            return jsonify({'error': 'Authentication required'}), 401
        return f(*args, **kwargs)
    return decorated_function

def require_json(f):
    """Decorator to require JSON content type"""
    @wraps(f)
    def decorated_function(*args, **kwargs):
        if not request.is_json:
            return jsonify({'error': 'Content-Type must be application/json'}), 400
        return f(*args, **kwargs)
    return decorated_function

@app.route('/api/protected')
@require_auth
def protected_endpoint():
    """Protected API endpoint"""
    return jsonify({
        'message': 'This is a protected endpoint',
        'user': session['username'],
        'timestamp': datetime.now().isoformat()
    })

@app.route('/api/json-only', methods=['POST'])
@require_json
def json_only_endpoint():
    """Endpoint that only accepts JSON"""
    data = request.get_json()
    return jsonify({
        'message': 'JSON data received',
        'data': data
    })

# 9. Error Handlers
@app.errorhandler(404)
def not_found(error):
    """Custom 404 error handler"""
    if request.path.startswith('/api/'):
        return jsonify({'error': 'Endpoint not found'}), 404
    return '''
    <h1>Page Not Found</h1>
    <p>The page you're looking for doesn't exist.</p>
    <p><a href="/">Go Home</a></p>
    ''', 404

@app.errorhandler(500)
def internal_error(error):
    """Custom 500 error handler"""
    if request.path.startswith('/api/'):
        return jsonify({'error': 'Internal server error'}), 500
    return '''
    <h1>Internal Server Error</h1>
    <p>Something went wrong on our end.</p>
    <p><a href="/">Go Home</a></p>
    ''', 500

print("Middleware and error handlers configured")

print("\n=== ADVANCED FEATURES ===")

# 10. Request Hooks
@app.before_request
def before_request():
    """Execute before each request"""
    # Log request information
    print(f"Request: {request.method} {request.path} from {request.remote_addr}")
    
    # Add request timestamp
    request.start_time = datetime.now()

@app.after_request
def after_request(response):
    """Execute after each request"""
    # Calculate request duration
    if hasattr(request, 'start_time'):
        duration = datetime.now() - request.start_time
        response.headers['X-Response-Time'] = str(duration.total_seconds())
    
    # Add CORS headers for API endpoints
    if request.path.startswith('/api/'):
        response.headers['Access-Control-Allow-Origin'] = '*'
        response.headers['Access-Control-Allow-Methods'] = 'GET, POST, PUT, DELETE, OPTIONS'
        response.headers['Access-Control-Allow-Headers'] = 'Content-Type, Authorization'
    
    return response

# 11. Custom Response Types
@app.route('/api/download')
def download_data():
    """Download data as file"""
    data = {
        'users': users_db,
        'generated_at': datetime.now().isoformat(),
        'total_users': len(users_db)
    }
    
    # Create temporary file
    filename = 'users_data.json'
    with open(filename, 'w') as f:
        json.dump(data, f, indent=2)
    
    return send_file(filename, as_attachment=True, download_name='users_data.json')

@app.route('/api/custom-response')
def custom_response():
    """Custom response with headers"""
    response = make_response(jsonify({
        'message': 'Custom response',
        'timestamp': datetime.now().isoformat()
    }))
    
    response.headers['X-Custom-Header'] = 'CustomValue'
    response.headers['Cache-Control'] = 'no-cache'
    
    return response

# 12. Template Rendering (using string templates for simplicity)
@app.route('/template-example')
def template_example():
    """Template rendering example"""
    template = '''
    <!DOCTYPE html>
    <html>
    <head>
        <title>{{ title }}</title>
        <style>
            body { font-family: Arial, sans-serif; margin: 40px; }
            .user { border: 1px solid #ccc; padding: 10px; margin: 10px 0; }
        </style>
    </head>
    <body>
        <h1>{{ title }}</h1>
        <p>Generated at: {{ timestamp }}</p>
        
        <h2>Users ({{ user_count }})</h2>
        {% for user in users %}
        <div class="user">
            <h3>{{ user.name }}</h3>
            <p>Email: {{ user.email }}</p>
            <p>Age: {{ user.age }}</p>
        </div>
        {% endfor %}
        
        <p><a href="/">Back to Home</a></p>
    </body>
    </html>
    '''
    
    return render_template_string(template,
                                title="User Directory",
                                timestamp=datetime.now().strftime("%Y-%m-%d %H:%M:%S"),
                                users=users_db,
                                user_count=len(users_db))

print("Advanced features configured")

print("\n=== CONFIGURATION AND UTILITIES ===")

# 13. Configuration
class Config:
    """Application configuration"""
    SECRET_KEY = os.environ.get('SECRET_KEY') or 'dev-secret-key'
    DATABASE_URL = os.environ.get('DATABASE_URL') or 'sqlite:///app.db'
    DEBUG = os.environ.get('FLASK_DEBUG', 'False').lower() == 'true'
    MAX_CONTENT_LENGTH = 16 * 1024 * 1024  # 16MB max file size

app.config.from_object(Config)

# 14. Health Check Endpoint
@app.route('/health')
def health_check():
    """Health check endpoint"""
    return jsonify({
        'status': 'healthy',
        'timestamp': datetime.now().isoformat(),
        'version': '1.0.0',
        'uptime': 'N/A',  # In a real app, calculate actual uptime
        'database': 'connected',  # In a real app, check database connection
        'memory_usage': 'N/A'  # In a real app, get actual memory usage
    })

# 15. API Documentation Endpoint
@app.route('/api/docs')
def api_docs():
    """API documentation"""
    docs = {
        'title': 'Flask Example API',
        'version': '1.0.0',
        'description': 'Comprehensive Flask application demonstrating various features',
        'endpoints': [
            {
                'path': '/api/users',
                'methods': ['GET', 'POST'],
                'description': 'Get all users or create new user'
            },
            {
                'path': '/api/users/<id>',
                'methods': ['GET', 'PUT', 'DELETE'],
                'description': 'Get, update, or delete specific user'
            },
            {
                'path': '/api/protected',
                'methods': ['GET'],
                'description': 'Protected endpoint requiring authentication'
            },
            {
                'path': '/health',
                'methods': ['GET'],
                'description': 'Health check endpoint'
            }
        ]
    }
    
    return jsonify(docs)

print("Configuration and utilities configured")

print("\n=== APPLICATION FACTORY PATTERN ===")

def create_app(config_name='default'):
    """Application factory function"""
    app = Flask(__name__)
    
    # Load configuration
    if config_name == 'testing':
        app.config['TESTING'] = True
        app.config['SECRET_KEY'] = 'testing-secret'
    elif config_name == 'production':
        app.config['DEBUG'] = False
        app.config['SECRET_KEY'] = os.environ.get('SECRET_KEY')
    else:
        app.config.from_object(Config)
    
    # Register blueprints (in a real app, you'd have separate blueprint files)
    # from .auth import auth_bp
    # app.register_blueprint(auth_bp, url_prefix='/auth')
    
    return app

print("Application factory pattern implemented")

# Main execution
if __name__ == '__main__':
    print("\n=== FLASK APPLICATION READY ===")
    print("Available endpoints:")
    print("  GET  /                    - Home page")
    print("  GET  /hello               - Hello world")
    print("  GET  /hello/<name>        - Personalized hello")
    print("  GET  /user/<username>     - User profile")
    print("  GET  /search              - Search with query parameters")
    print("  GET  /form                - Form example")
    print("  POST /form                - Form submission")
    print("  GET  /upload              - File upload form")
    print("  POST /upload              - File upload handler")
    print("  GET  /login               - Login form")
    print("  POST /login               - Login handler")
    print("  GET  /profile             - User profile (requires login)")
    print("  GET  /logout              - Logout")
    print("  GET  /template-example    - Template rendering example")
    print("  GET  /health              - Health check")
    print("")
    print("API endpoints:")
    print("  GET    /api/users         - Get all users")
    print("  POST   /api/users         - Create user")
    print("  GET    /api/users/<id>    - Get specific user")
    print("  PUT    /api/users/<id>    - Update user")
    print("  DELETE /api/users/<id>    - Delete user")
    print("  GET    /api/protected     - Protected endpoint")
    print("  POST   /api/json-only     - JSON-only endpoint")
    print("  GET    /api/download      - Download data file")
    print("  GET    /api/custom-response - Custom response headers")
    print("  GET    /api/docs          - API documentation")
    print("")
    print("To run the application:")
    print("  python Flask.py")
    print("  or")
    print("  flask run")
    print("")
    print("Environment variables:")
    print("  FLASK_DEBUG=true          - Enable debug mode")
    print("  SECRET_KEY=your-key       - Set secret key")
    
    # Run the application
    app.run(debug=True, host='0.0.0.0', port=5000)

print("\n=== FLASK EXAMPLES COMPLETE ===")
