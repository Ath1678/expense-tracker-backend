package com.example.expensetracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.example.expensetracker.model.User;
import com.example.expensetracker.repository.UserRepository;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile() {
        User user = getCurrentUser();
        return ResponseEntity.ok(user);
    }

    @PutMapping("/limit")
    public ResponseEntity<?> updateMonthlyLimit(@RequestBody Map<String, Double> payload) {
        Double newLimit = payload.get("limit");

        // Handle potential null or convert from Integer if needed (though Map<String,
        // Double> handles most)
        if (newLimit == null) {
            return ResponseEntity.badRequest().body("Limit is required");
        }

        User user = getCurrentUser();
        user.setMonthlyLimit(newLimit);
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "Monthly limit updated successfully!"));
    }

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
