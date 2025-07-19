# NumPy.py - Scientific Computing Library Examples
# Master array operations, mathematical functions, and scientific computing with NumPy.

import numpy as np
import matplotlib.pyplot as plt
from scipy import stats

print("=== NUMPY ARRAY CREATION ===")

# 1. Creating Arrays
print("1. Creating Arrays:")

# From lists
arr1d = np.array([1, 2, 3, 4, 5])
arr2d = np.array([[1, 2, 3], [4, 5, 6], [7, 8, 9]])
arr3d = np.array([[[1, 2], [3, 4]], [[5, 6], [7, 8]]])

print(f"1D array: {arr1d}")
print(f"2D array:\n{arr2d}")
print(f"3D array shape: {arr3d.shape}")

# Using built-in functions
zeros = np.zeros((3, 4))
ones = np.ones((2, 3))
empty = np.empty((2, 2))
eye = np.eye(3)  # Identity matrix
full = np.full((2, 3), 7)

print(f"Zeros array:\n{zeros}")
print(f"Ones array:\n{ones}")
print(f"Identity matrix:\n{eye}")
print(f"Full array (filled with 7):\n{full}")

# Range arrays
arange_arr = np.arange(0, 10, 2)  # start, stop, step
linspace_arr = np.linspace(0, 1, 5)  # start, stop, num_points
logspace_arr = np.logspace(0, 2, 3)  # 10^0 to 10^2, 3 points

print(f"Arange (0 to 10, step 2): {arange_arr}")
print(f"Linspace (0 to 1, 5 points): {linspace_arr}")
print(f"Logspace (10^0 to 10^2, 3 points): {logspace_arr}")

# Random arrays
np.random.seed(42)  # For reproducibility
random_uniform = np.random.random((2, 3))
random_normal = np.random.normal(0, 1, (2, 3))
random_int = np.random.randint(1, 10, (2, 3))

print(f"Random uniform [0,1):\n{random_uniform}")
print(f"Random normal (mean=0, std=1):\n{random_normal}")
print(f"Random integers [1,10):\n{random_int}")

print("\n=== ARRAY PROPERTIES AND INDEXING ===")

# 2. Array Properties
sample_array = np.array([[1, 2, 3, 4], [5, 6, 7, 8], [9, 10, 11, 12]])
print(f"Sample array:\n{sample_array}")
print(f"Shape: {sample_array.shape}")
print(f"Size: {sample_array.size}")
print(f"Dimensions: {sample_array.ndim}")
print(f"Data type: {sample_array.dtype}")
print(f"Item size: {sample_array.itemsize} bytes")
print(f"Total bytes: {sample_array.nbytes}")

# 3. Indexing and Slicing
print("\n3. Indexing and Slicing:")
print(f"Element at [1,2]: {sample_array[1, 2]}")
print(f"First row: {sample_array[0, :]}")
print(f"Last column: {sample_array[:, -1]}")
print(f"Subarray [0:2, 1:3]:\n{sample_array[0:2, 1:3]}")

# Boolean indexing
mask = sample_array > 6
print(f"Boolean mask (>6):\n{mask}")
print(f"Elements > 6: {sample_array[mask]}")

# Fancy indexing
indices = np.array([0, 2])
print(f"Rows 0 and 2:\n{sample_array[indices]}")

print("\n=== ARRAY OPERATIONS ===")

# 4. Mathematical Operations
a = np.array([1, 2, 3, 4])
b = np.array([5, 6, 7, 8])

print(f"Array a: {a}")
print(f"Array b: {b}")

# Element-wise operations
print(f"a + b = {a + b}")
print(f"a - b = {a - b}")
print(f"a * b = {a * b}")
print(f"a / b = {a / b}")
print(f"a ** 2 = {a ** 2}")
print(f"sqrt(a) = {np.sqrt(a)}")

# Scalar operations
print(f"a + 10 = {a + 10}")
print(f"a * 2 = {a * 2}")

# Universal functions (ufuncs)
angles = np.array([0, np.pi/4, np.pi/2, np.pi])
print(f"Angles: {angles}")
print(f"sin(angles): {np.sin(angles)}")
print(f"cos(angles): {np.cos(angles)}")
print(f"exp(a): {np.exp(a)}")
print(f"log(a): {np.log(a)}")

print("\n=== MATRIX OPERATIONS ===")

# 5. Linear Algebra
matrix_a = np.array([[1, 2], [3, 4]])
matrix_b = np.array([[5, 6], [7, 8]])
vector = np.array([1, 2])

print(f"Matrix A:\n{matrix_a}")
print(f"Matrix B:\n{matrix_b}")
print(f"Vector: {vector}")

# Matrix multiplication
print(f"A @ B (matrix multiplication):\n{matrix_a @ matrix_b}")
print(f"A * B (element-wise):\n{matrix_a * matrix_b}")

# Matrix-vector multiplication
print(f"A @ vector: {matrix_a @ vector}")

# Matrix operations
print(f"Transpose of A:\n{matrix_a.T}")
print(f"Determinant of A: {np.linalg.det(matrix_a)}")
print(f"Inverse of A:\n{np.linalg.inv(matrix_a)}")

# Eigenvalues and eigenvectors
eigenvals, eigenvecs = np.linalg.eig(matrix_a)
print(f"Eigenvalues: {eigenvals}")
print(f"Eigenvectors:\n{eigenvecs}")

# Solving linear systems Ax = b
b_vec = np.array([5, 11])
x_solution = np.linalg.solve(matrix_a, b_vec)
print(f"Solution to Ax = b: {x_solution}")
print(f"Verification A @ x: {matrix_a @ x_solution}")

print("\n=== STATISTICAL OPERATIONS ===")

# 6. Statistics
data = np.random.normal(100, 15, 1000)  # mean=100, std=15, 1000 samples

print(f"Data sample (first 10): {data[:10]}")
print(f"Mean: {np.mean(data):.2f}")
print(f"Median: {np.median(data):.2f}")
print(f"Standard deviation: {np.std(data):.2f}")
print(f"Variance: {np.var(data):.2f}")
print(f"Min: {np.min(data):.2f}")
print(f"Max: {np.max(data):.2f}")
print(f"25th percentile: {np.percentile(data, 25):.2f}")
print(f"75th percentile: {np.percentile(data, 75):.2f}")

# Multi-dimensional statistics
matrix_data = np.random.random((3, 4))
print(f"\nMatrix data:\n{matrix_data}")
print(f"Mean along axis 0 (columns): {np.mean(matrix_data, axis=0)}")
print(f"Mean along axis 1 (rows): {np.mean(matrix_data, axis=1)}")
print(f"Overall mean: {np.mean(matrix_data)}")

print("\n=== ARRAY MANIPULATION ===")

# 7. Reshaping and Manipulation
original = np.arange(12)
print(f"Original array: {original}")

# Reshaping
reshaped = original.reshape(3, 4)
print(f"Reshaped to 3x4:\n{reshaped}")

reshaped_3d = original.reshape(2, 2, 3)
print(f"Reshaped to 2x2x3:\n{reshaped_3d}")

# Flattening
flattened = reshaped.flatten()
print(f"Flattened: {flattened}")

# Concatenation
arr1 = np.array([[1, 2], [3, 4]])
arr2 = np.array([[5, 6], [7, 8]])

concat_vertical = np.concatenate([arr1, arr2], axis=0)
concat_horizontal = np.concatenate([arr1, arr2], axis=1)

print(f"Vertical concatenation:\n{concat_vertical}")
print(f"Horizontal concatenation:\n{concat_horizontal}")

# Stacking
vstacked = np.vstack([arr1, arr2])
hstacked = np.hstack([arr1, arr2])

print(f"Vertical stack:\n{vstacked}")
print(f"Horizontal stack:\n{hstacked}")

# Splitting
split_arrays = np.split(np.arange(8), 4)
print(f"Split array into 4 parts: {split_arrays}")

print("\n=== BROADCASTING ===")

# 8. Broadcasting
print("Broadcasting examples:")

# Scalar with array
arr = np.array([[1, 2, 3], [4, 5, 6]])
scalar = 10
print(f"Array:\n{arr}")
print(f"Array + scalar:\n{arr + scalar}")

# Different shaped arrays
row_vector = np.array([1, 2, 3])
col_vector = np.array([[1], [2]])

print(f"Row vector: {row_vector}")
print(f"Column vector:\n{col_vector}")
print(f"Broadcasting addition:\n{row_vector + col_vector}")

# Practical example: normalize each row
data_matrix = np.array([[1, 2, 3], [4, 5, 6], [7, 8, 9]])
row_means = np.mean(data_matrix, axis=1, keepdims=True)
normalized = data_matrix - row_means

print(f"Original matrix:\n{data_matrix}")
print(f"Row means:\n{row_means}")
print(f"Normalized matrix:\n{normalized}")

print("\n=== ADVANCED OPERATIONS ===")

# 9. Advanced Indexing and Filtering
data_2d = np.random.randint(1, 20, (5, 4))
print(f"Random 5x4 matrix:\n{data_2d}")

# Conditional operations
condition = data_2d > 10
print(f"Elements > 10:\n{condition}")
print(f"Values > 10: {data_2d[condition]}")

# Where function
result = np.where(data_2d > 10, data_2d, 0)
print(f"Replace values <= 10 with 0:\n{result}")

# Argmax and argmin
print(f"Index of maximum value: {np.argmax(data_2d)}")
print(f"Index of maximum in each row: {np.argmax(data_2d, axis=1)}")
print(f"Index of maximum in each column: {np.argmax(data_2d, axis=0)}")

# Sorting
sorted_data = np.sort(data_2d, axis=1)
print(f"Sorted along rows:\n{sorted_data}")

sort_indices = np.argsort(data_2d, axis=1)
print(f"Sort indices:\n{sort_indices}")

print("\n=== PRACTICAL EXAMPLES ===")

# 10. Real-world Applications

# Image processing simulation
def create_sample_image():
    """Create a sample 'image' (2D array)"""
    x = np.linspace(-2, 2, 100)
    y = np.linspace(-2, 2, 100)
    X, Y = np.meshgrid(x, y)
    Z = np.exp(-(X**2 + Y**2))
    return Z

image = create_sample_image()
print(f"Sample image shape: {image.shape}")
print(f"Image min/max: {image.min():.3f}/{image.max():.3f}")

# Apply filters
blurred = np.convolve(image.flatten(), np.ones(5)/5, mode='same').reshape(image.shape)
print(f"Blurred image min/max: {blurred.min():.3f}/{blurred.max():.3f}")

# Signal processing
def generate_signal():
    """Generate a noisy sine wave"""
    t = np.linspace(0, 2*np.pi, 1000)
    signal = np.sin(5*t) + 0.5*np.sin(10*t)
    noise = 0.2 * np.random.random(len(t))
    return t, signal + noise

time, noisy_signal = generate_signal()
print(f"Signal length: {len(noisy_signal)}")
print(f"Signal mean: {np.mean(noisy_signal):.3f}")
print(f"Signal std: {np.std(noisy_signal):.3f}")

# Moving average filter
window_size = 50
moving_avg = np.convolve(noisy_signal, np.ones(window_size)/window_size, mode='valid')
print(f"Filtered signal length: {len(moving_avg)}")

# Financial data analysis simulation
def simulate_stock_prices(days=252, initial_price=100):
    """Simulate stock prices using random walk"""
    returns = np.random.normal(0.001, 0.02, days)  # Daily returns
    prices = initial_price * np.cumprod(1 + returns)
    return prices

stock_prices = simulate_stock_prices()
print(f"\nStock price simulation:")
print(f"Initial price: ${stock_prices[0]:.2f}")
print(f"Final price: ${stock_prices[-1]:.2f}")
print(f"Max price: ${np.max(stock_prices):.2f}")
print(f"Min price: ${np.min(stock_prices):.2f}")

# Calculate moving averages
ma_20 = np.convolve(stock_prices, np.ones(20)/20, mode='valid')
ma_50 = np.convolve(stock_prices, np.ones(50)/50, mode='valid')

print(f"20-day MA (last value): ${ma_20[-1]:.2f}")
print(f"50-day MA (last value): ${ma_50[-1]:.2f}")

# Volatility calculation
returns = np.diff(stock_prices) / stock_prices[:-1]
volatility = np.std(returns) * np.sqrt(252)  # Annualized volatility
print(f"Annualized volatility: {volatility:.2%}")

# Scientific computation example
def solve_heat_equation():
    """Solve 1D heat equation using finite differences"""
    # Parameters
    L = 1.0  # Length
    T = 0.1  # Time
    nx = 50  # Spatial points
    nt = 1000  # Time steps
    
    dx = L / (nx - 1)
    dt = T / nt
    alpha = 0.01  # Thermal diffusivity
    
    # Stability condition
    r = alpha * dt / dx**2
    print(f"Stability parameter r = {r:.3f} (should be <= 0.5)")
    
    # Initial condition: step function
    x = np.linspace(0, L, nx)
    u = np.where(x < 0.5, 1.0, 0.0)
    
    # Time evolution
    for n in range(nt):
        u_new = u.copy()
        for i in range(1, nx-1):
            u_new[i] = u[i] + r * (u[i+1] - 2*u[i] + u[i-1])
        u = u_new
    
    return x, u

x_heat, u_final = solve_heat_equation()
print(f"\nHeat equation solution:")
print(f"Final temperature at center: {u_final[len(u_final)//2]:.3f}")
print(f"Final temperature range: {u_final.min():.3f} to {u_final.max():.3f}")

print("\n=== PERFORMANCE TIPS ===")

# Performance comparison
def compare_performance():
    """Compare NumPy vs pure Python performance"""
    import time
    
    # Large arrays
    size = 1000000
    a = np.random.random(size)
    b = np.random.random(size)
    
    # NumPy operation
    start = time.time()
    c_numpy = a + b
    numpy_time = time.time() - start
    
    # Pure Python operation
    a_list = a.tolist()
    b_list = b.tolist()
    
    start = time.time()
    c_python = [a_list[i] + b_list[i] for i in range(size)]
    python_time = time.time() - start
    
    print(f"Array size: {size:,}")
    print(f"NumPy time: {numpy_time:.4f} seconds")
    print(f"Python time: {python_time:.4f} seconds")
    print(f"Speedup: {python_time/numpy_time:.1f}x")

compare_performance()

print("\n=== NUMPY EXAMPLES COMPLETE ===")
