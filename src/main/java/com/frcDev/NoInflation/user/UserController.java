package com.frcDev.NoInflation.user;

import com.frcDev.NoInflation.dto.LoginResponseDto;
import com.frcDev.NoInflation.dto.UserLoginDto;
import com.frcDev.NoInflation.shop.Shop;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UserController {

    private static final String SECRET = "your-very-secure-and-long-secret-key";

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserController(UserService userService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/test-connection")
    public ResponseEntity<String> testConnection() {
        return ResponseEntity.ok("Backend connection successful");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            // Agregar logs detallados
            System.out.println("Received registration request");
            System.out.println("Email: " + user.getEmail());
            System.out.println("Name: " + user.getName());
            System.out.println("Role: " + user.getRole());
            System.out.println("Password present: " + (user.getPassword() != null));

            // Validaciones mejoradas
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                System.out.println("Password validation failed");
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "La contraseña es requerida"));
            }

            // Validar otros campos requeridos
            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "El email es requerido"));
            }

            if (user.getName() == null || user.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "El nombre es requerido"));
            }

            // Normalizar y validar datos
            user.setEmail(user.getEmail().toLowerCase().trim());
            user.setName(user.getName().trim());

            // Log antes de encriptar la contraseña
            System.out.println("About to encode password");

            // Encriptar contraseña
            String rawPassword = user.getPassword();
            String encodedPassword = passwordEncoder.encode(rawPassword);
            user.setPassword(encodedPassword);

            System.out.println("Password encoded successfully");

            // Registrar usuario
            User registeredUser = userService.registerUser(user);
            System.out.println("User registered successfully with ID: " + registeredUser.getUserId());

            // Preparar respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("userId", registeredUser.getUserId());
            response.put("email", registeredUser.getEmail());
            response.put("name", registeredUser.getName());
            response.put("role", registeredUser.getRole());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Registration error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al registrar usuario: " + e.getMessage()));
        }
    }

    private void validateUserData(User user) {
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es requerida");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email es requerido");
        }
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es requerido");
        }
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            throw new IllegalArgumentException("El rol es requerido");
        }
        if (!user.getRole().equals("BUYER") && !user.getRole().equals("COMMERCE")) {
            throw new IllegalArgumentException("Rol inválido");
        }
    }

    private void validateShopData(Shop shop) {
        if (shop == null) {
            throw new IllegalArgumentException("Información de tienda requerida para comercios");
        }
        if (shop.getShopName() == null || shop.getShopName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la tienda es requerido");
        }
        if (shop.getLocation() == null || shop.getLocation().trim().isEmpty()) {
            throw new IllegalArgumentException("La ubicación de la tienda es requerida");
        }
    }




// Codigo comentado de Login para agregar uno alternativo
    /*

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDto loginDto) {
        User authenticatedUser = userService.loginUser(loginDto);
        try {
            if (authenticatedUser != null) {
                String token = generateToken(authenticatedUser);
                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("user", authenticatedUser);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during login: " + e.getMessage());
        }
    } */

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDto loginDto) {
        try {
            User authenticatedUser = userService.loginUser(loginDto);

            if (authenticatedUser != null) {
                String token = generateToken(authenticatedUser);

                LoginResponseDto response = new LoginResponseDto();
                response.setUserId(authenticatedUser.getUserId());
                response.setName(authenticatedUser.getName());
                response.setEmail(authenticatedUser.getEmail());
                response.setRole(authenticatedUser.getRole());
                response.setToken(token);

                // Si el usuario es tipo COMMERCE y tiene una tienda asociada, incluimos sus datos
                if (authenticatedUser.getRole().equals("COMMERCE") && authenticatedUser.getShop() != null) {
                    LoginResponseDto.ShopDto shopDto = new LoginResponseDto.ShopDto(
                            authenticatedUser.getShop().getId(),
                            authenticatedUser.getShop().getShopName(),
                            authenticatedUser.getShop().getLocation()
                    );
                    response.setShop(shopDto);
                }

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Credenciales inválidas");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error durante el login: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public  ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUserById(id);
        if (deleted)
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        try {
            User existingUser = userService.getUserById(id);
            if (existingUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Usuario no encontrado"));
            }

            // Actualizar solo los campos proporcionados
            if (updatedUser.getName() != null) {
                existingUser.setName(updatedUser.getName());
            }
            if (updatedUser.getEmail() != null) {
                existingUser.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }

            boolean updated = userService.updateUser(id, existingUser);
            if (updated) {
                return ResponseEntity.ok(Map.of("message", "Usuario actualizado correctamente"));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "No se pudo actualizar el usuario"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al actualizar el usuario: " + e.getMessage()));
        }
    }


    // Codigo agregado para current user
    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            // Validar header
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Token no válido o no proporcionado"));
            }

            // Extraer y validar token
            String token = authHeader.substring(7);
            Claims claims = validateToken(token);
            if (claims == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Token inválido"));
            }

            // Buscar usuario
            User user = userService.findByEmail(claims.getSubject());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Usuario no encontrado"));
            }

            return ResponseEntity.ok(user);

        } catch (Exception e) {
            System.out.println("Error en getCurrentUser: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al procesar la solicitud"));
        }
    }

    private Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }
    private String generateToken(User user) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + (1000 * 60 * 60 * 10)); // 10 horas

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, Keys.hmacShaKeyFor(SECRET.getBytes()))
                .compact();
    }

}
