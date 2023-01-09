package com.example.food.services.serviceImpl;

import com.example.food.exceptions.ResourceNotFoundException;
import com.example.food.exceptions.UserNotFoundException;
import com.example.food.model.Favorites;
import com.example.food.model.Users;
import com.example.food.repositories.FavoriteRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.services.FavoritesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class FavoritesServiceImpl implements FavoritesService {

    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;

    @Override
    public ResponseEntity<String> removeProductFromFavorite(Long productId) {

        UserDetails loggedInUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info(loggedInUser.toString());

        Users user = userRepository.findByEmail(loggedInUser.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User with this id does not exist. " +
                        "Please check and try again."));

        Favorites favoriteProduct = favoriteRepository.findFavoritesByProduct_ProductIdAndUser_UserId(productId, user.getUsersId())
                .orElseThrow(() -> new ResourceNotFoundException("This product does not exist in your favorite"));

        favoriteRepository.delete(favoriteProduct);
        return new ResponseEntity<>(favoriteProduct.getProduct().getProductName() + " has been removed.", HttpStatus.OK);
    }
}
