package org.example.bairstowresolution.Services;

import org.example.bairstowresolution.Models.PolynomialEntry;
import org.example.bairstowresolution.Models.PolynomialResponse;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BairstowService {

    private static final double TOLERANCE = 1e-6;
    private static final int MAX_ITERATIONS = 1000;

    public PolynomialResponse solvePolynomial(PolynomialEntry entry) {
        List<String> roots = new ArrayList<>();
        double[] coefficients = entry.getCoefficients();
        int order = entry.getOrder();

        if (coefficients == null || coefficients.length != order + 1) {
            throw new IllegalArgumentException("The number of coefficients must match the polynomial order + 1.");
        }

        double r = -1.0;
        double s = -1.0;

        while (order >= 2) {
            if (order == 2) {
                roots.addAll(solveQuadratic(coefficients[0], coefficients[1], coefficients[2]));
                break;
            }

            double[] b = new double[order + 1];
            double[] c = new double[order + 1];
            int iterations = 0;

            while (iterations < MAX_ITERATIONS) {
                b[order] = coefficients[order];
                b[order - 1] = coefficients[order - 1] + r * b[order];
                for (int i = order - 2; i >= 0; i--) {
                    b[i] = coefficients[i] + r * b[i + 1] + s * b[i + 2];
                }

                c[order] = b[order];
                c[order - 1] = b[order - 1] + r * c[order];
                for (int i = order - 2; i >= 0; i--) {
                    c[i] = b[i] + r * c[i + 1] + s * c[i + 2];
                }

                double determinant = c[2] * c[2] - c[3] * c[1];
                if (Math.abs(determinant) < TOLERANCE) {
                    break;
                }

                double dr = (-b[1] * c[2] + b[0] * c[3]) / determinant;
                double ds = (-b[0] * c[2] + b[1] * c[1]) / determinant;

                r += dr;
                s += ds;

                if (Math.abs(dr) < TOLERANCE && Math.abs(ds) < TOLERANCE) {
                    break;
                }
                iterations++;
            }

            double discriminant = r * r + 4 * s;
            if (discriminant >= 0) {
                double root1 = (r + Math.sqrt(discriminant)) / 2;
                double root2 = (r - Math.sqrt(discriminant)) / 2;
                roots.add(String.format("%.6f", root1));
                roots.add(String.format("%.6f", root2));
            } else {
                double realPart = r / 2;
                double imaginaryPart = Math.sqrt(-discriminant) / 2;
                roots.add(String.format("%.6f + %.6fi", realPart, imaginaryPart));
                roots.add(String.format("%.6f - %.6fi", realPart, imaginaryPart));
            }

            coefficients = deflatePolynomial(coefficients, r, s);
            order -= 2;
        }

        if (order == 1) {
            roots.add(String.format("%.6f", -coefficients[1] / coefficients[0]));
        }

        // Factorize into exponential notation
        String factorizedForm = formatFactorization(roots);

        return new PolynomialResponse(roots, "Factorization completed: " + factorizedForm);
    }

    /**
     * Solve a quadratic polynomial ax^2 + bx + c = 0
     */
    private List<String> solveQuadratic(double a, double b, double c) {
        List<String> roots = new ArrayList<>();
        double discriminant = b * b - 4 * a * c;

        if (discriminant >= 0) {
            double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            roots.add(String.format("%.6f", root1));
            roots.add(String.format("%.6f", root2));
        } else {
            double realPart = -b / (2 * a);
            double imaginaryPart = Math.sqrt(-discriminant) / (2 * a);
            roots.add(String.format("%.6f + %.6fi", realPart, imaginaryPart));
            roots.add(String.format("%.6f - %.6fi", realPart, imaginaryPart));
        }

        return roots;
    }

    /**
     * Deflate a polynomial by dividing it by (x^2 - r*x - s)
     */
    private double[] deflatePolynomial(double[] coefficients, double r, double s) {
        int degree = coefficients.length - 1;
        double[] newCoefficients = new double[degree - 1];

        newCoefficients[degree - 2] = coefficients[degree];
        newCoefficients[degree - 3] = coefficients[degree - 1] + r * newCoefficients[degree - 2];

        for (int i = degree - 4; i >= 0; i--) {
            newCoefficients[i] = coefficients[i + 2] + r * newCoefficients[i + 1] + s * newCoefficients[i + 2];
        }

        return newCoefficients;
    }

    /**
     * Format roots into factorized exponential form
     */
    private String formatFactorization(List<String> roots) {
        Map<String, Long> rootCounts = roots.stream()
                .collect(Collectors.groupingBy(root -> root, LinkedHashMap::new, Collectors.counting()));

        StringBuilder factorized = new StringBuilder();
        for (Map.Entry<String, Long> entry : rootCounts.entrySet()) {
            String root = entry.getKey();
            Long count = entry.getValue();

            if (root.contains("+") || root.contains("-")) {
                factorized.append(String.format("(x - (%s))", root));
            } else {
                factorized.append(String.format("(x - %s)", root));
            }

            if (count > 1) {
                factorized.append("^").append(count);
            }
        }

        return factorized.toString();
    }
}
