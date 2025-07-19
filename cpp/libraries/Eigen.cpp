// Eigen.cpp - Linear Algebra Library Examples
// High-performance C++ library for linear algebra, matrix operations, and numerical computations.

#include <Eigen/Dense>
#include <Eigen/Sparse>
#include <iostream>
#include <vector>

using namespace Eigen;
using namespace std;

int main() {
    cout << "=== EIGEN LINEAR ALGEBRA EXAMPLES ===" << endl;
    cout << "Eigen Version: " << EIGEN_WORLD_VERSION << "." << EIGEN_MAJOR_VERSION << "." << EIGEN_MINOR_VERSION << endl;
    
    // 1. Basic Matrix Operations
    basicMatrixOperations();
    
    // 2. Vector Operations
    vectorOperations();
    
    // 3. Matrix Decompositions
    matrixDecompositions();
    
    // 4. Solving Linear Systems
    solvingLinearSystems();
    
    // 5. Eigenvalues and Eigenvectors
    eigenvalueProblems();
    
    return 0;
}

void basicMatrixOperations() {
    cout << "\n1. Basic Matrix Operations:" << endl;
    
    // Create matrices
    Matrix3d A;
    A << 1, 2, 3,
         4, 5, 6,
         7, 8, 9;
    
    Matrix3d B = Matrix3d::Random();
    Matrix3d I = Matrix3d::Identity();
    Matrix3d Z = Matrix3d::Zero();
    
    cout << "Matrix A:" << endl << A << endl;
    cout << "Random Matrix B:" << endl << B << endl;
    cout << "Identity Matrix:" << endl << I << endl;
    
    // Basic operations
    Matrix3d C = A + B;
    Matrix3d D = A * B;
    Matrix3d E = A.transpose();
    
    cout << "A + B:" << endl << C << endl;
    cout << "A * B:" << endl << D << endl;
    cout << "A transpose:" << endl << E << endl;
    
    // Matrix properties
    cout << "Matrix A determinant: " << A.determinant() << endl;
    cout << "Matrix A trace: " << A.trace() << endl;
    cout << "Matrix A norm: " << A.norm() << endl;
    
    // Element access
    cout << "A(1,1) = " << A(1,1) << endl;
    cout << "A.row(0) = " << A.row(0) << endl;
    cout << "A.col(2) = " << A.col(2).transpose() << endl;
}

void vectorOperations() {
    cout << "\n2. Vector Operations:" << endl;
    
    // Create vectors
    Vector3d v1(1, 2, 3);
    Vector3d v2(4, 5, 6);
    VectorXd v3 = VectorXd::Random(5);
    
    cout << "Vector v1: " << v1.transpose() << endl;
    cout << "Vector v2: " << v2.transpose() << endl;
    cout << "Random vector v3: " << v3.transpose() << endl;
    
    // Vector operations
    Vector3d v_sum = v1 + v2;
    double dot_product = v1.dot(v2);
    Vector3d cross_product = v1.cross(v2);
    
    cout << "v1 + v2: " << v_sum.transpose() << endl;
    cout << "v1 · v2 (dot product): " << dot_product << endl;
    cout << "v1 × v2 (cross product): " << cross_product.transpose() << endl;
    
    // Vector norms
    cout << "||v1|| (L2 norm): " << v1.norm() << endl;
    cout << "||v1||₁ (L1 norm): " << v1.lpNorm<1>() << endl;
    cout << "||v1||∞ (infinity norm): " << v1.lpNorm<Infinity>() << endl;
    
    // Vector normalization
    Vector3d v1_normalized = v1.normalized();
    cout << "v1 normalized: " << v1_normalized.transpose() << endl;
    cout << "||v1_normalized||: " << v1_normalized.norm() << endl;
    
    // Vector angle
    double angle = acos(v1.dot(v2) / (v1.norm() * v2.norm()));
    cout << "Angle between v1 and v2: " << angle << " radians (" << angle * 180 / M_PI << " degrees)" << endl;
}

void matrixDecompositions() {
    cout << "\n3. Matrix Decompositions:" << endl;
    
    // Create a symmetric positive definite matrix
    Matrix3d A;
    A << 4, 2, 1,
         2, 5, 3,
         1, 3, 6;
    
    cout << "Matrix A:" << endl << A << endl;
    
    // LU Decomposition
    PartialPivLU<Matrix3d> lu(A);
    Matrix3d L = Matrix3d::Identity();
    L.triangularView<StrictlyLower>() = lu.matrixLU().triangularView<StrictlyLower>();
    Matrix3d U = lu.matrixLU().triangularView<Upper>();
    Matrix3d P = lu.permutationP();
    
    cout << "LU Decomposition (PA = LU):" << endl;
    cout << "P:" << endl << P << endl;
    cout << "L:" << endl << L << endl;
    cout << "U:" << endl << U << endl;
    cout << "Verification P*A - L*U:" << endl << P*A - L*U << endl;
    
    // QR Decomposition
    HouseholderQR<Matrix3d> qr(A);
    Matrix3d Q = qr.householderQ();
    Matrix3d R = qr.matrixQR().triangularView<Upper>();
    
    cout << "QR Decomposition (A = QR):" << endl;
    cout << "Q:" << endl << Q << endl;
    cout << "R:" << endl << R << endl;
    cout << "Verification A - Q*R:" << endl << A - Q*R << endl;
    
    // Cholesky Decomposition (for symmetric positive definite matrices)
    LLT<Matrix3d> chol(A);
    if (chol.info() == Success) {
        Matrix3d L_chol = chol.matrixL();
        cout << "Cholesky Decomposition (A = LL^T):" << endl;
        cout << "L:" << endl << L_chol << endl;
        cout << "Verification A - L*L^T:" << endl << A - L_chol * L_chol.transpose() << endl;
    }
    
    // SVD (Singular Value Decomposition)
    JacobiSVD<Matrix3d> svd(A, ComputeFullU | ComputeFullV);
    Matrix3d U_svd = svd.matrixU();
    Vector3d S = svd.singularValues();
    Matrix3d V = svd.matrixV();
    
    cout << "SVD Decomposition (A = USV^T):" << endl;
    cout << "U:" << endl << U_svd << endl;
    cout << "S (singular values): " << S.transpose() << endl;
    cout << "V:" << endl << V << endl;
    cout << "Verification A - U*S*V^T:" << endl << A - U_svd * S.asDiagonal() * V.transpose() << endl;
}

void solvingLinearSystems() {
    cout << "\n4. Solving Linear Systems:" << endl;
    
    // System: Ax = b
    Matrix3d A;
    A << 3, 2, 1,
         2, 3, 2,
         1, 2, 3;
    
    Vector3d b(1, 2, 3);
    
    cout << "System Ax = b:" << endl;
    cout << "A:" << endl << A << endl;
    cout << "b: " << b.transpose() << endl;
    
    // Direct methods
    Vector3d x1 = A.lu().solve(b);
    Vector3d x2 = A.llt().solve(b);  // For symmetric positive definite
    Vector3d x3 = A.ldlt().solve(b); // For symmetric
    Vector3d x4 = A.householderQr().solve(b);
    
    cout << "Solution using LU: " << x1.transpose() << endl;
    cout << "Solution using Cholesky: " << x2.transpose() << endl;
    cout << "Solution using LDLT: " << x3.transpose() << endl;
    cout << "Solution using QR: " << x4.transpose() << endl;
    
    // Verification
    cout << "Verification Ax1 - b: " << (A * x1 - b).transpose() << endl;
    
    // Overdetermined system (least squares)
    MatrixXd A_over(4, 3);
    A_over << 1, 2, 1,
              2, 1, 3,
              1, 3, 2,
              3, 1, 1;
    
    VectorXd b_over(4);
    b_over << 1, 2, 3, 4;
    
    cout << "\nOverdetermined system (least squares):" << endl;
    cout << "A (4x3):" << endl << A_over << endl;
    cout << "b: " << b_over.transpose() << endl;
    
    VectorXd x_ls = A_over.colPivHouseholderQr().solve(b_over);
    cout << "Least squares solution: " << x_ls.transpose() << endl;
    cout << "Residual norm: " << (A_over * x_ls - b_over).norm() << endl;
}

void eigenvalueProblems() {
    cout << "\n5. Eigenvalues and Eigenvectors:" << endl;
    
    // Symmetric matrix (real eigenvalues)
    Matrix3d A;
    A << 4, 2, 1,
         2, 5, 3,
         1, 3, 6;
    
    cout << "Symmetric Matrix A:" << endl << A << endl;
    
    // Eigenvalue decomposition
    SelfAdjointEigenSolver<Matrix3d> eigensolver(A);
    if (eigensolver.info() != Success) {
        cout << "Eigenvalue computation failed!" << endl;
        return;
    }
    
    Vector3d eigenvalues = eigensolver.eigenvalues();
    Matrix3d eigenvectors = eigensolver.eigenvectors();
    
    cout << "Eigenvalues: " << eigenvalues.transpose() << endl;
    cout << "Eigenvectors:" << endl << eigenvectors << endl;
    
    // Verification: A * v = λ * v
    for (int i = 0; i < 3; i++) {
        Vector3d v = eigenvectors.col(i);
        double lambda = eigenvalues(i);
        Vector3d Av = A * v;
        Vector3d lambda_v = lambda * v;
        
        cout << "Verification for eigenvalue " << lambda << ":" << endl;
        cout << "  A*v: " << Av.transpose() << endl;
        cout << "  λ*v: " << lambda_v.transpose() << endl;
        cout << "  Error: " << (Av - lambda_v).norm() << endl;
    }
    
    // Matrix powers using eigendecomposition
    Matrix3d A_squared = eigenvectors * eigenvalues.asDiagonal().toDenseMatrix().array().square().matrix().asDiagonal() * eigenvectors.transpose();
    cout << "A² (using eigendecomposition):" << endl << A_squared << endl;
    cout << "A² (direct computation):" << endl << A * A << endl;
    cout << "Difference: " << (A_squared - A*A).norm() << endl;
    
    // General matrix (complex eigenvalues possible)
    Matrix3d B;
    B << 0, 1, 0,
         0, 0, 1,
         1, 0, 0;
    
    cout << "\nGeneral Matrix B:" << endl << B << endl;
    
    EigenSolver<Matrix3d> gen_eigensolver(B);
    if (gen_eigensolver.info() != Success) {
        cout << "General eigenvalue computation failed!" << endl;
        return;
    }
    
    cout << "Complex eigenvalues:" << endl;
    for (int i = 0; i < 3; i++) {
        complex<double> lambda = gen_eigensolver.eigenvalues()[i];
        cout << "  λ" << i << " = " << lambda << endl;
    }
    
    // Generalized eigenvalue problem: A*x = λ*B*x
    Matrix3d C = Matrix3d::Identity() + 0.1 * Matrix3d::Random();
    GeneralizedSelfAdjointEigenSolver<Matrix3d> gen_solver(A, C);
    
    if (gen_solver.info() == Success) {
        cout << "\nGeneralized eigenvalue problem Ax = λCx:" << endl;
        cout << "Generalized eigenvalues: " << gen_solver.eigenvalues().transpose() << endl;
    }
}

/*
=== EIGEN FEATURES DEMONSTRATED ===

1. Basic Matrix Operations:
   - Matrix creation and initialization
   - Arithmetic operations (+, -, *, transpose)
   - Matrix properties (determinant, trace, norm)
   - Element and block access

2. Vector Operations:
   - Vector creation and manipulation
   - Dot and cross products
   - Vector norms (L1, L2, infinity)
   - Normalization and angles

3. Matrix Decompositions:
   - LU decomposition with partial pivoting
   - QR decomposition (Householder)
   - Cholesky decomposition (for SPD matrices)
   - Singular Value Decomposition (SVD)

4. Solving Linear Systems:
   - Direct methods (LU, Cholesky, QR)
   - Overdetermined systems (least squares)
   - System verification and error analysis

5. Eigenvalue Problems:
   - Symmetric eigenvalue problems
   - General eigenvalue problems (complex eigenvalues)
   - Generalized eigenvalue problems
   - Matrix functions using eigendecomposition

=== COMPILATION ===

# Basic compilation
g++ -std=c++11 Eigen.cpp -o eigen_example -I/path/to/eigen3

# With optimization
g++ -std=c++11 -O3 -DNDEBUG Eigen.cpp -o eigen_example -I/path/to/eigen3

# Using pkg-config (if available)
g++ -std=c++11 Eigen.cpp -o eigen_example `pkg-config --cflags eigen3`

# CMake approach
cmake_minimum_required(VERSION 3.12)
project(EigenExample)
find_package(Eigen3 REQUIRED)
add_executable(eigen_example Eigen.cpp)
target_link_libraries(eigen_example Eigen3::Eigen)

=== EIGEN MODULES ===

- Eigen/Dense: Dense matrices and vectors, basic linear algebra
- Eigen/Sparse: Sparse matrices and related algorithms
- Eigen/Eigenvalues: Eigenvalue and eigenvector computations
- Eigen/Geometry: Geometric transformations (rotations, translations)
- Eigen/LU: LU decomposition
- Eigen/QR: QR decomposition
- Eigen/Cholesky: Cholesky decomposition
- Eigen/SVD: Singular value decomposition

=== PERFORMANCE TIPS ===

1. Use fixed-size matrices when possible (Matrix3d vs MatrixXd)
2. Enable compiler optimizations (-O3 -DNDEBUG)
3. Use .noalias() for matrix products when safe
4. Prefer in-place operations when possible
5. Use appropriate decompositions for your problem type

=== COMMON APPLICATIONS ===

1. Computer Graphics:
   - 3D transformations
   - Mesh processing
   - Animation systems

2. Robotics:
   - Kinematics and dynamics
   - Control systems
   - State estimation

3. Machine Learning:
   - Principal Component Analysis (PCA)
   - Linear regression
   - Neural network computations

4. Scientific Computing:
   - Finite element methods
   - Signal processing
   - Optimization problems

5. Computer Vision:
   - Camera calibration
   - 3D reconstruction
   - Image transformations

*/
