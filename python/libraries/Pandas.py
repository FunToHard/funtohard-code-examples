# Pandas.py - Data Analysis Library Examples
# Learn data manipulation, analysis, and visualization with DataFrames and Series.

import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from datetime import datetime, timedelta
import warnings
warnings.filterwarnings('ignore')

print("=== PANDAS BASICS ===")

# 1. Creating DataFrames and Series
print("1. Creating DataFrames and Series:")

# From dictionary
data = {
    'Name': ['Alice', 'Bob', 'Charlie', 'Diana', 'Eve'],
    'Age': [25, 30, 35, 28, 32],
    'City': ['New York', 'London', 'Tokyo', 'Paris', 'Sydney'],
    'Salary': [70000, 80000, 90000, 75000, 85000],
    'Department': ['Engineering', 'Marketing', 'Engineering', 'Design', 'Marketing']
}

df = pd.DataFrame(data)
print("DataFrame from dictionary:")
print(df)
print(f"\nDataFrame shape: {df.shape}")
print(f"DataFrame info:")
print(df.info())

# Creating Series
ages = pd.Series([25, 30, 35, 28, 32], name='Age')
print(f"\nSeries:\n{ages}")

# From lists with custom index
cities = pd.Series(['New York', 'London', 'Tokyo'], 
                  index=['Person1', 'Person2', 'Person3'])
print(f"\nSeries with custom index:\n{cities}")

# From CSV simulation (creating sample data)
sample_data = {
    'Date': pd.date_range('2023-01-01', periods=100, freq='D'),
    'Product': np.random.choice(['A', 'B', 'C'], 100),
    'Sales': np.random.randint(100, 1000, 100),
    'Region': np.random.choice(['North', 'South', 'East', 'West'], 100)
}
sales_df = pd.DataFrame(sample_data)
print(f"\nSample sales data (first 5 rows):")
print(sales_df.head())

print("\n=== DATA EXPLORATION ===")

# 2. Data Exploration
print("2. Data Exploration:")

# Basic info
print("Basic DataFrame information:")
print(f"Shape: {df.shape}")
print(f"Columns: {list(df.columns)}")
print(f"Data types:\n{df.dtypes}")
print(f"Index: {df.index}")

# Statistical summary
print(f"\nStatistical summary:")
print(df.describe())

# For non-numeric columns
print(f"\nNon-numeric summary:")
print(df.describe(include='object'))

# Unique values
print(f"\nUnique values in each column:")
for col in df.columns:
    print(f"{col}: {df[col].nunique()} unique values")

# Value counts
print(f"\nDepartment value counts:")
print(df['Department'].value_counts())

# Missing values check
print(f"\nMissing values:")
print(df.isnull().sum())

print("\n=== DATA SELECTION AND INDEXING ===")

# 3. Data Selection
print("3. Data Selection:")

# Column selection
print("Single column (Series):")
print(df['Name'])

print("\nMultiple columns (DataFrame):")
print(df[['Name', 'Age', 'Salary']])

# Row selection
print("\nFirst 3 rows:")
print(df.head(3))

print("\nLast 2 rows:")
print(df.tail(2))

# Specific rows by index
print("\nRows 1-3:")
print(df.iloc[1:4])

# Specific rows and columns
print("\nSpecific rows and columns:")
print(df.loc[1:3, ['Name', 'Salary']])

# Boolean indexing
print("\nEmployees with salary > 75000:")
high_salary = df[df['Salary'] > 75000]
print(high_salary)

print("\nEngineering department employees:")
engineering = df[df['Department'] == 'Engineering']
print(engineering[['Name', 'Age', 'Salary']])

# Multiple conditions
print("\nEngineering employees with salary > 80000:")
condition = (df['Department'] == 'Engineering') & (df['Salary'] > 80000)
print(df[condition])

print("\n=== DATA MANIPULATION ===")

# 4. Data Manipulation
print("4. Data Manipulation:")

# Adding new columns
df_copy = df.copy()
df_copy['Salary_K'] = df_copy['Salary'] / 1000
df_copy['Age_Group'] = pd.cut(df_copy['Age'], bins=[0, 30, 40, 100], 
                             labels=['Young', 'Middle', 'Senior'])

print("DataFrame with new columns:")
print(df_copy[['Name', 'Age', 'Age_Group', 'Salary', 'Salary_K']])

# Modifying existing data
df_copy.loc[df_copy['City'] == 'New York', 'City'] = 'NYC'
print(f"\nAfter modifying New York to NYC:")
print(df_copy[['Name', 'City']])

# Dropping columns and rows
df_dropped = df_copy.drop(['Salary_K'], axis=1)  # Drop column
df_dropped = df_dropped.drop([0], axis=0)  # Drop row
print(f"\nAfter dropping column and row:")
print(df_dropped)

# Renaming columns
df_renamed = df.rename(columns={'Name': 'Employee_Name', 'Age': 'Employee_Age'})
print(f"\nRenamed columns:")
print(df_renamed.columns.tolist())

print("\n=== GROUPBY OPERATIONS ===")

# 5. GroupBy Operations
print("5. GroupBy Operations:")

# Group by single column
dept_groups = df.groupby('Department')
print("Average salary by department:")
print(dept_groups['Salary'].mean())

print("\nMultiple aggregations:")
print(dept_groups['Salary'].agg(['mean', 'min', 'max', 'count']))

# Group by multiple columns (using sales data)
print("\nSales analysis by Product and Region:")
sales_summary = sales_df.groupby(['Product', 'Region'])['Sales'].agg({
    'Total_Sales': 'sum',
    'Avg_Sales': 'mean',
    'Count': 'count'
}).round(2)
print(sales_summary.head(10))

# Custom aggregation functions
def salary_range(series):
    return series.max() - series.min()

print("\nCustom aggregation (salary range by department):")
print(dept_groups['Salary'].agg(salary_range))

# Transform operations
print("\nSalary relative to department average:")
df_with_relative = df.copy()
df_with_relative['Dept_Avg_Salary'] = df_with_relative.groupby('Department')['Salary'].transform('mean')
df_with_relative['Salary_vs_Avg'] = df_with_relative['Salary'] - df_with_relative['Dept_Avg_Salary']
print(df_with_relative[['Name', 'Department', 'Salary', 'Dept_Avg_Salary', 'Salary_vs_Avg']])

print("\n=== DATA CLEANING ===")

# 6. Data Cleaning
print("6. Data Cleaning:")

# Create data with missing values and duplicates
dirty_data = {
    'ID': [1, 2, 3, 4, 5, 5, 6, 7],  # Duplicate ID
    'Name': ['Alice', 'Bob', None, 'Diana', 'Eve', 'Eve', 'Frank', 'Grace'],  # Missing name
    'Score': [85, 90, 78, None, 92, 92, 88, 95],  # Missing score
    'Grade': ['A', 'A', 'B', 'B', 'A', 'A', 'B', 'A']
}

dirty_df = pd.DataFrame(dirty_data)
print("Dirty data:")
print(dirty_df)

# Check for missing values
print(f"\nMissing values:")
print(dirty_df.isnull().sum())

# Handle missing values
print("\nFilling missing values:")
cleaned_df = dirty_df.copy()

# Fill missing names with 'Unknown'
cleaned_df['Name'].fillna('Unknown', inplace=True)

# Fill missing scores with mean
mean_score = cleaned_df['Score'].mean()
cleaned_df['Score'].fillna(mean_score, inplace=True)

print(cleaned_df)

# Remove duplicates
print(f"\nBefore removing duplicates: {len(cleaned_df)} rows")
cleaned_df = cleaned_df.drop_duplicates()
print(f"After removing duplicates: {len(cleaned_df)} rows")
print(cleaned_df)

# Remove duplicates based on specific columns
cleaned_df2 = dirty_df.drop_duplicates(subset=['Name'], keep='first')
print(f"\nRemoving duplicates based on Name column:")
print(cleaned_df2)

print("\n=== TIME SERIES DATA ===")

# 7. Time Series Operations
print("7. Time Series Operations:")

# Create time series data
dates = pd.date_range('2023-01-01', periods=365, freq='D')
ts_data = pd.DataFrame({
    'Date': dates,
    'Temperature': 20 + 10 * np.sin(2 * np.pi * np.arange(365) / 365) + np.random.normal(0, 2, 365),
    'Humidity': 50 + 20 * np.cos(2 * np.pi * np.arange(365) / 365) + np.random.normal(0, 5, 365),
    'Rainfall': np.random.exponential(2, 365)
})

# Set date as index
ts_data.set_index('Date', inplace=True)
print("Time series data (first 5 rows):")
print(ts_data.head())

# Time-based selection
print("\nJanuary 2023 data:")
january_data = ts_data['2023-01']
print(f"January average temperature: {january_data['Temperature'].mean():.2f}°C")

# Resampling
print("\nMonthly averages:")
monthly_avg = ts_data.resample('M').mean()
print(monthly_avg.head())

# Rolling statistics
print("\n7-day rolling average temperature:")
ts_data['Temp_7day_avg'] = ts_data['Temperature'].rolling(window=7).mean()
print(ts_data[['Temperature', 'Temp_7day_avg']].head(10))

# Seasonal decomposition simulation
print("\nSeasonal statistics:")
ts_data['Month'] = ts_data.index.month
seasonal_stats = ts_data.groupby('Month')[['Temperature', 'Humidity']].mean()
print(seasonal_stats)

print("\n=== MERGING AND JOINING ===")

# 8. Merging and Joining
print("8. Merging and Joining:")

# Create sample datasets
employees = pd.DataFrame({
    'emp_id': [1, 2, 3, 4, 5],
    'name': ['Alice', 'Bob', 'Charlie', 'Diana', 'Eve'],
    'dept_id': [1, 2, 1, 3, 2]
})

departments = pd.DataFrame({
    'dept_id': [1, 2, 3, 4],
    'dept_name': ['Engineering', 'Marketing', 'Design', 'Sales'],
    'budget': [100000, 80000, 60000, 90000]
})

salaries = pd.DataFrame({
    'emp_id': [1, 2, 3, 4, 6],  # Note: emp_id 6 doesn't exist in employees
    'salary': [70000, 80000, 75000, 65000, 85000]
})

print("Employees:")
print(employees)
print("\nDepartments:")
print(departments)
print("\nSalaries:")
print(salaries)

# Inner join
inner_join = pd.merge(employees, departments, on='dept_id', how='inner')
print(f"\nInner join (employees + departments):")
print(inner_join)

# Left join
left_join = pd.merge(employees, salaries, on='emp_id', how='left')
print(f"\nLeft join (employees + salaries):")
print(left_join)

# Outer join
outer_join = pd.merge(employees, salaries, on='emp_id', how='outer')
print(f"\nOuter join (employees + salaries):")
print(outer_join)

# Multiple merges
complete_data = employees.merge(departments, on='dept_id').merge(salaries, on='emp_id', how='left')
print(f"\nComplete employee data:")
print(complete_data)

print("\n=== PIVOT TABLES ===")

# 9. Pivot Tables
print("9. Pivot Tables:")

# Create sample sales data
sales_data = pd.DataFrame({
    'Date': pd.date_range('2023-01-01', periods=100, freq='D'),
    'Salesperson': np.random.choice(['Alice', 'Bob', 'Charlie'], 100),
    'Product': np.random.choice(['Widget A', 'Widget B', 'Widget C'], 100),
    'Region': np.random.choice(['North', 'South', 'East', 'West'], 100),
    'Sales_Amount': np.random.randint(1000, 5000, 100),
    'Quantity': np.random.randint(1, 20, 100)
})

print("Sample sales data:")
print(sales_data.head())

# Simple pivot table
pivot1 = sales_data.pivot_table(
    values='Sales_Amount', 
    index='Salesperson', 
    columns='Product', 
    aggfunc='sum',
    fill_value=0
)
print(f"\nSales by Salesperson and Product:")
print(pivot1)

# Multiple aggregations
pivot2 = sales_data.pivot_table(
    values=['Sales_Amount', 'Quantity'],
    index='Region',
    columns='Product',
    aggfunc={'Sales_Amount': 'sum', 'Quantity': 'mean'},
    fill_value=0
)
print(f"\nSales and average quantity by Region and Product:")
print(pivot2)

# Cross-tabulation
crosstab = pd.crosstab(sales_data['Region'], sales_data['Product'], 
                      values=sales_data['Sales_Amount'], aggfunc='sum')
print(f"\nCross-tabulation (Region vs Product):")
print(crosstab)

print("\n=== STRING OPERATIONS ===")

# 10. String Operations
print("10. String Operations:")

# Sample text data
text_data = pd.DataFrame({
    'Name': ['  Alice Johnson  ', 'bob smith', 'CHARLIE BROWN', 'diana.wilson@email.com'],
    'Email': ['alice@company.com', 'bob@company.com', 'charlie@company.com', 'diana@company.com'],
    'Phone': ['123-456-7890', '(555) 123-4567', '555.987.6543', '123 456 7890']
})

print("Original text data:")
print(text_data)

# String cleaning and manipulation
text_cleaned = text_data.copy()

# Clean names
text_cleaned['Name_Clean'] = (text_data['Name']
                             .str.strip()  # Remove whitespace
                             .str.title()  # Title case
                             .str.replace('.', ' ')  # Replace dots with spaces
                             .str.replace('@.*', '', regex=True))  # Remove email parts

# Extract domain from email
text_cleaned['Email_Domain'] = text_data['Email'].str.extract(r'@(.+)')

# Standardize phone numbers
text_cleaned['Phone_Clean'] = (text_data['Phone']
                              .str.replace(r'[^\d]', '', regex=True)  # Keep only digits
                              .str.replace(r'(\d{3})(\d{3})(\d{4})', r'\1-\2-\3', regex=True))  # Format

print(f"\nCleaned text data:")
print(text_cleaned)

# String analysis
print(f"\nString analysis:")
print(f"Name lengths: {text_data['Name'].str.len()}")
print(f"Names containing 'o': {text_data['Name'].str.contains('o', case=False)}")
print(f"Email domains: {text_data['Email'].str.split('@').str[1].unique()}")

print("\n=== PRACTICAL EXAMPLES ===")

# 11. Real-world Data Analysis Example
print("11. Real-world Data Analysis:")

# Simulate e-commerce data
np.random.seed(42)
n_orders = 1000

ecommerce_data = pd.DataFrame({
    'order_id': range(1, n_orders + 1),
    'customer_id': np.random.randint(1, 200, n_orders),
    'order_date': pd.date_range('2023-01-01', periods=n_orders, freq='H'),
    'product_category': np.random.choice(['Electronics', 'Clothing', 'Books', 'Home'], n_orders),
    'order_value': np.random.lognormal(4, 1, n_orders),
    'shipping_cost': np.random.uniform(5, 25, n_orders),
    'customer_age': np.random.randint(18, 70, n_orders),
    'customer_city': np.random.choice(['New York', 'Los Angeles', 'Chicago', 'Houston', 'Phoenix'], n_orders)
})

# Round monetary values
ecommerce_data['order_value'] = ecommerce_data['order_value'].round(2)
ecommerce_data['shipping_cost'] = ecommerce_data['shipping_cost'].round(2)

print("E-commerce data sample:")
print(ecommerce_data.head())

# Analysis 1: Monthly sales trends
ecommerce_data['order_month'] = ecommerce_data['order_date'].dt.to_period('M')
monthly_sales = ecommerce_data.groupby('order_month')['order_value'].agg(['sum', 'count', 'mean'])
monthly_sales.columns = ['Total_Sales', 'Order_Count', 'Avg_Order_Value']
print(f"\nMonthly sales summary:")
print(monthly_sales)

# Analysis 2: Customer segmentation
customer_summary = ecommerce_data.groupby('customer_id').agg({
    'order_value': ['sum', 'count', 'mean'],
    'order_date': ['min', 'max']
}).round(2)

customer_summary.columns = ['Total_Spent', 'Order_Count', 'Avg_Order_Value', 'First_Order', 'Last_Order']
customer_summary['Days_Active'] = (customer_summary['Last_Order'] - customer_summary['First_Order']).dt.days

# Customer segments
customer_summary['Segment'] = pd.cut(customer_summary['Total_Spent'], 
                                   bins=[0, 100, 500, 1000, float('inf')],
                                   labels=['Low', 'Medium', 'High', 'VIP'])

print(f"\nCustomer segmentation:")
print(customer_summary['Segment'].value_counts())

# Analysis 3: Product performance by city
city_product_analysis = ecommerce_data.pivot_table(
    values='order_value',
    index='customer_city',
    columns='product_category',
    aggfunc=['sum', 'count'],
    fill_value=0
)

print(f"\nProduct performance by city (total sales):")
print(city_product_analysis['sum'])

# Analysis 4: Cohort analysis simulation
def create_cohort_table(df):
    """Create a simple cohort analysis"""
    df['order_period'] = df['order_date'].dt.to_period('M')
    df['cohort_group'] = df.groupby('customer_id')['order_date'].transform('min').dt.to_period('M')
    
    df['period_number'] = (df['order_period'] - df['cohort_group']).apply(attrgetter('n'))
    
    cohort_data = df.groupby(['cohort_group', 'period_number'])['customer_id'].nunique().reset_index()
    cohort_table = cohort_data.pivot(index='cohort_group', columns='period_number', values='customer_id')
    
    return cohort_table

from operator import attrgetter
cohort_table = create_cohort_table(ecommerce_data)
print(f"\nCohort analysis (customer retention):")
print(cohort_table.fillna(0).astype(int))

print("\n=== PERFORMANCE TIPS ===")

# 12. Performance optimization tips
print("12. Performance Tips:")

# Vectorized operations vs loops
large_df = pd.DataFrame({
    'A': np.random.randn(100000),
    'B': np.random.randn(100000)
})

import time

# Vectorized operation (fast)
start = time.time()
large_df['C_vectorized'] = large_df['A'] + large_df['B']
vectorized_time = time.time() - start

# Using apply (slower)
start = time.time()
large_df['C_apply'] = large_df.apply(lambda row: row['A'] + row['B'], axis=1)
apply_time = time.time() - start

print(f"Vectorized operation time: {vectorized_time:.4f} seconds")
print(f"Apply operation time: {apply_time:.4f} seconds")
print(f"Speedup: {apply_time/vectorized_time:.1f}x")

# Memory usage optimization
print(f"\nMemory usage optimization:")
print(f"Original memory usage: {large_df.memory_usage(deep=True).sum() / 1024**2:.2f} MB")

# Convert to more efficient data types
large_df_optimized = large_df.copy()
for col in ['A', 'B']:
    large_df_optimized[col] = pd.to_numeric(large_df_optimized[col], downcast='float')

print(f"Optimized memory usage: {large_df_optimized.memory_usage(deep=True).sum() / 1024**2:.2f} MB")

print("\n=== PANDAS EXAMPLES COMPLETE ===")
