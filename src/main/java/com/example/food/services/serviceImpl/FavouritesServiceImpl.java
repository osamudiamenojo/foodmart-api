package com.example.food.services.serviceImpl;

import com.example.food.exceptions.ResourceNotFoundException;
import com.example.food.exceptions.UserNotFoundException;
import com.example.food.model.Favourites;
import com.example.food.model.Product;
import com.example.food.model.Users;
import com.example.food.repositories.FavouritesRepository;
import com.example.food.repositories.ProductRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.services.FavouritesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FavouritesServiceImpl implements FavouritesService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final FavouritesRepository favouritesRepository;

    @Override
    public ResponseEntity addToFavourites(Long productId) {
        UserDetails loggedInUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info(loggedInUser.toString());

        Users user = userRepository.findByEmail(loggedInUser.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User does not exist. Please check and try again."));

        Product favouriteProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product does not exist"));

        Boolean alreadyInFavourite = favouritesRepository.existsByUsersAndProducts(user, favouriteProduct);

        if (!alreadyInFavourite) {
            Favourites favourites = new Favourites();
            favourites.setUsers(user);
            favourites.setProducts(favouriteProduct);
            favouritesRepository.save(favourites);
            return new ResponseEntity<>(favouriteProduct.getProductName() + "added to favourite", HttpStatus.OK);
        }

        return new ResponseEntity<>(favouriteProduct.getProductName() + " is already your favourite", HttpStatus.BAD_REQUEST);
    }
}
