package com.example.Api_Gateway.Controller;

import com.example.Api_Gateway.Model.PolynomialRequest;
import com.example.Api_Gateway.Model.PolynomialResponse;
import com.example.Api_Gateway.Service.PolynomialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/polynomial")
@CrossOrigin(
        origins = "http://localhost:4200",
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS}
)
public class GatewayController {

    private final PolynomialService polynomialService;
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public GatewayController(PolynomialService polynomialService, WebClient.Builder webClientBuilder) {
        this.polynomialService = polynomialService;
        this.webClientBuilder = webClientBuilder;
    }

    @PostMapping("/evaluate")
    public Mono<String> processPolynomial(@RequestBody PolynomialRequest request) {
        try {
            PolynomialResponse response = polynomialService.processPolynomial(request);

            if (response.getOrder() == 1 && "numerical".equalsIgnoreCase(request.getMethod())) {
                return routeToService("linearresolution", "/api/v1/linear/solve", buildPayload(request, response), "LinearResolution");
            } else if (response.getOrder() == 2 && "numerical".equalsIgnoreCase(request.getMethod())) {
                return routeToService("quadraticresolution", "/api/v1/quadratic/solve", buildPayload(request, response), "QuadraticResolution");
            } else if (response.getOrder() == 3 && "numerical".equalsIgnoreCase(request.getMethod())) {
                return routeToService("cardanosresolution", "/api/v1/cardanos/solve", buildPayload(request, response), "CardanosResolution");
            } else if (response.getOrder() == 4 && "numerical".equalsIgnoreCase(request.getMethod())) {
                return routeToService("ferrariresolution", "/api/v1/ferrari/solve", buildPayload(request, response), "FerrariResolution");
            } else if ("newton".equalsIgnoreCase(request.getMethod())) {
                return routeToService("newtonraphsonresolution", "/api/v1/newtonraphson/solve", buildPayload(request, response), "NewtonRaphsonResolution");
            } else if ("bisection".equalsIgnoreCase(request.getMethod())) {
                return routeToService("bisectionresolution", "/api/v1/bisection/solve", buildPayload(request, response), "BisectionResolution");
            } else if ("bairstow".equalsIgnoreCase(request.getMethod())) {
                return routeToService("BAIRSTOWRESOLUTION", "/api/v1/bairstow/solve", buildPayload(request, response), "BAIRSTOWRESOLUTION");
            } else if ("muller".equalsIgnoreCase(request.getMethod())) {
                return routeToService("mullerresolution", "/api/v1/muller/solve", buildPayload(request, response), "MullerResolution");
            } else if ("fixedpoint".equalsIgnoreCase(request.getMethod())) {
                return routeToService("fixedpointresolution", "/api/v1/fixedpoint/solve?initialGuess=1.0&domain=real", buildPayload(request, response), "FixedPointResolution");
            } else if ("detailed".equalsIgnoreCase(request.getMethod())) {
                return routeToService("GEMINIRESOLUTION", "/api/v1/gemini/solve", buildPayload(request, response), "GEMINIRESOLUTION");
            } else {
                return Mono.just("❌ Veuillez fournir un polynôme valide avec une méthode et un ordre supportés.");
            }

        } catch (Exception e) {
            return Mono.just("❌ Une erreur est survenue lors du traitement du polynôme : " + e.getMessage());
        }
    }

    /**
     * Envoie une requête générique vers un service donné.
     */
    private Mono<String> routeToService(String serviceId, String endpoint, Map<String, Object> payload, String serviceName) {
        try {
            String uri = "lb://" + serviceId + endpoint;
            System.out.println("🔄 Sending JSON to " + serviceName + ":\n" + payload);

            return webClientBuilder.build()
                    .post()
                    .uri(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(payload)
                    .retrieve()
                    .bodyToMono(String.class)
                    .onErrorResume(error -> {
                        System.err.println("❌ Erreur avec " + serviceName + ": " + error.getMessage());
                        return Mono.just("❌ Erreur avec " + serviceName + ": " + error.getMessage());
                    });
        } catch (Exception e) {
            return Mono.just("❌ Erreur critique avec " + serviceName + ": " + e.getMessage());
        }
    }

    /**
     * Construit le payload pour les services.
     */
    private Map<String, Object> buildPayload(PolynomialRequest request, PolynomialResponse response) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("coefficients", response.getCoefficients());
        payload.put("order", response.getOrder());
        payload.put("domain", response.getDomain());
        payload.put("method", request.getMethod());
        return payload;
    }
}
