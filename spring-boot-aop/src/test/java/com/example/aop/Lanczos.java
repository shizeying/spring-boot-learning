package com.example.aop;

import java.util.Random;

public class Lanczos {
	
	// 定义一个函数来生成给定行数的随机int方阵
	public static int[][] generateMatrix(int n) {
		int[][] matrix = new int[n][n];
		Random  rand   = new Random();
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] = rand.nextInt(100); // 随机生成0-99之间的整数填充矩阵
			}
		}
		
		return matrix;
	}
	
	// 定义一个函数来计算向量的点积
	public static double dotProduct(double[] v1, double[] v2) {
		double sum = 0;
		
		for (int i = 0; i < v1.length; i++) {
			sum += v1[i] * v2[i]; // 计算两个向量对应位置的乘积，并累加
		}
		
		return sum;
	}
	
	// 定义一个函数来对给定的向量进行标准化
	public static void normalize(double[] v) {
		double norm = 0;
		
		for (double val : v) {
			norm += val * val; // 计算向量的范数（平方和）
		}
		
		norm = Math.sqrt(norm); // 求范数的平方根
		
		for (int i = 0; i < v.length; i++) {
			v[i] /= norm; // 将向量的每个元素除以范数，实现标准化
		}
	}
	
	// 定义一个函数来实现矩阵和向量的乘法
	public static double[] matrixVectorMultiplication(int[][] matrix, double[] vector) {
		int      n      = matrix.length;
		double[] result = new double[n];
		
		for (int i = 0; i < n; i++) {
			result[i] = 0;
			for (int j = 0; j < n; j++) {
				result[i] += matrix[i][j] * vector[j]; // 计算矩阵行和向量的点积，并累加
			}
		}
		
		return result;
	}
	
	// 定义Lanczos迭代的实现
	
	// 定义Lanczos迭代的实现
	public static double[][] lanczos(int[][] matrix, int maxIter) {
		int        n           = matrix.length;
		double[][] tridiagonal = new double[maxIter][3]; // 三对角矩阵的结果
		double[]   v           = new double[n];
		double[]   vOld        = new double[n];
		double[]   w           = new double[n];
		
		// 初始化迭代过程的第一个向量
		Random rand = new Random();
		for (int i = 0; i < n; i++) {
			v[i] = rand.nextDouble(); // 随机生成0-1之间的实数填充向量
		}
		normalize(v); // 对向量v进行标准化
		
		for (int iter = 0; iter < maxIter; iter++) {
			if (iter > 0) {
				// 除第一次迭代外，将上次迭代结果的w除以beta，并赋值给v
				for (int i = 0; i < n; i++) {
					v[i] = w[i] / tridiagonal[iter - 1][2];
				}
			}
			
			// 计算矩阵与向量v的乘积
			w = matrixVectorMultiplication(matrix, v);
			
			// 计算alpha
			tridiagonal[iter][1] = dotProduct(v, w);
			
			if (iter > 0) {
				// 更新向量w，减去上一次迭代的beta * vOld
				for (int i = 0; i < n; i++) {
					w[i] -= tridiagonal[iter - 1][2] * vOld[i];
				}
			}
			
			// 将当前v的值存储在vOld中，以备下一次迭代使用
			System.arraycopy(v, 0, vOld, 0, n);
			
			// 更新向量w，减去alpha * v
			for (int i = 0; i < n; i++) {
				w[i] -= tridiagonal[iter][1] * v[i];
			}
			
			// 计算beta
			double sum = 0;
			for (double val : w) {
				sum += val * val;
			}
			tridiagonal[iter][2] = Math.sqrt(sum);
			
			// 更新迭代次数
			tridiagonal[iter][0] = iter + 1;
		}
		
		return tridiagonal;
	}
	
	/**
	 * Lanczos 三对角矩阵奇异值分解的方法。在这里，我们将使用一个名为 svdTridiagonal 的方法，它将利用雅可比方法（Jacobi Method）计算给定三对角矩阵的奇异值分解。雅可比方法是一种迭代算法，用于计算实对称矩阵的特征值和特征向量。
	 */
	// ...
	public static double[][][] svdTridiagonal(double[][] tridiagonal, int maxIter) {
		int        n              = tridiagonal.length;
		double[][] matrixU        = new double[n][n];
		double[][] matrixV        = new double[n][n];
		double[][] singularValues = new double[n][n];
		
		// 初始化 U 和 V 为单位矩阵
		for (int i = 0; i < n; i++) {
			matrixU[i][i] = 1;
			matrixV[i][i] = 1;
		}
		
		// 对角线元素复制到 singularValues 对角线
		for (int i = 0; i < n; i++) {
			singularValues[i][i] = tridiagonal[i][1];
		}
		
		// 超对角线元素复制到 singularValues 超对角线
		for (int i = 0; i < n - 1; i++) {
			singularValues[i][i + 1] = tridiagonal[i][2];
			singularValues[i + 1][i] = tridiagonal[i][2];
		}
		
		// 迭代雅可比旋转
		for (int k = 0; k < maxIter; k++) {
			for (int p = 0; p < n - 1; p++) {
				for (int q = p + 1; q < n; q++) {
					// 计算雅可比旋转矩阵
					double[][] j = jacobiRotation(singularValues, p, q);
					
					// 用雅可比旋转矩阵更新 U 和 V
					matrixU = matrixMultiplication(matrixU, j);
					matrixV = matrixMultiplication(matrixV, j);
					
					// 用雅可比旋转矩阵更新 singularValues
					singularValues = matrixMultiplication(matrixMultiplication(transpose(j), singularValues), j);
				}
			}
		}
		
		// 返回奇异值分解结果
		return new double[][][]{matrixU, singularValues, matrixV};
	}
	
	public static double[][] matrixMultiplication(double[][] A, double[][] B) {
		int aRows    = A.length;
		int aColumns = A[0].length;
		int bColumns = B[0].length;
		
		if (aColumns != B.length) {
			throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + B.length + ".");
		}
		
		double[][] result = new double[aRows][bColumns];
		
		for (int i = 0; i < aRows; i++) {
			for (int j = 0; j < bColumns; j++) {
				result[i][j] = 0.0;
				for (int k = 0; k < aColumns; k++) {
					result[i][j] += A[i][k] * B[k][j];
				}
			}
		}
		
		return result;
	}
	
	public static double[][] jacobiRotation(double[][] matrix, int p, int q) {
		int        n = matrix.length;
		double[][] j = new double[n][n];
		
		// 初始化 J 为单位矩阵
		for (int i = 0; i < n; i++) {
			j[i][i] = 1;
		}
		
		double tau = (matrix[q][q] - matrix[p][p]) / (2 * matrix[p][q]);
		double t   = Math.signum(tau) / (Math.abs(tau) + Math.sqrt(1 + tau * tau));
		double c   = 1 / Math.sqrt(1 + t * t);
		double s   = t * c;
		
		j[p][p] = c;
		j[q][q] = c;
		j[p][q] = s;
		j[q][p] = -s;
		
		return j;
	}
	
	public static double[][] transpose(double[][] matrix) {
		int        rows       = matrix.length;
		int        cols       = matrix[0].length;
		double[][] transposed = new double[cols][rows];
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				transposed[j][i] = matrix[i][j];
			}
		}
		
		return transposed;
	}
	
	public static void printTridiagonalMatrix(double[][] tridiagonal) {
		System.out.println("Tridiagonal matrix:");
		for (int i = 0; i < tridiagonal.length; i++) {
			System.out.printf("%d: Alpha = %.4f, Beta = %.4f%n", i + 1, tridiagonal[i][1], tridiagonal[i][2]);
		}
	}
	
	public static void printMatrix(double[][] matrix) {
		for (double[] row : matrix) {
			for (double element : row) {
				System.out.printf("%.4f\t", element);
			}
			System.out.println();
		}
	}

// ...
	
	
	public static void main(String[] args) {
		int     n       = 5; // 自定义矩阵的行数
		int     maxIter = 10; // 自定义迭代次数
		int[][] matrix  = generateMatrix(n); // 生成随机矩阵
		
		double[][] tridiagonal = lanczos(matrix, maxIter); // 计算三对角矩阵
		
		// 输出结果
		System.out.println("Tridiagonal matrix:");
		for (int i = 0; i < maxIter; i++) {
			System.out.printf("%d: Alpha = %.4f, Beta = %.4f%n", (int) tridiagonal[i][0], tridiagonal[i][1], tridiagonal[i][2]);
		}
		/**
		 *奇异值分解
		 */
		int[][] inputMatrix = {
				{5, 1, 0, 0, 0},
				{1, 4, 2, 0, 0},
				{0, 2, 3, 3, 0},
				{0, 0, 3, 1, 1},
				{0, 0, 0, 1, 2}
		};
		System.out.println("奇异值分解.......");
		
		double[][] tridiagonalMatrix = lanczos(inputMatrix, maxIter);
		
		printTridiagonalMatrix(tridiagonalMatrix);
		
		double[][][] svdResult = svdTridiagonal(tridiagonalMatrix, maxIter);
		
		System.out.println("\nMatrix U:");
		printMatrix(svdResult[0]);
		
		System.out.println("\nSingular Values (diagonal):");
		printMatrix(svdResult[1]);
		
		System.out.println("\nMatrix V:");
		printMatrix(svdResult[2]);
		
		
	}
	
	
}