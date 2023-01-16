package com.example.food.services.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.exceptions.ResourceNotFoundException;
import com.example.food.exceptions.UserNotFoundException;
import com.example.food.model.Favourites;
import com.example.food.model.Product;
import com.example.food.model.Users;
import com.example.food.repositories.FavouritesRepository;
import com.example.food.repositories.ProductRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.FavouritesService;
import com.example.food.util.ResponseCodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final ResponseCodeUtil responseCodeUtil;

    @Override
    public BaseResponse addToFavourites(Long productId) {
        UserDetails loggedInUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info(loggedInUser.toString());

        Users user = userRepository.findByEmail(loggedInUser.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User does not exist. Please check and try again."));

        Product favouriteProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product does not exist"));

        Boolean alreadyInFavourite = favouritesRepository.existsByUsersIdAndProductId(user.getUsersId(), favouriteProduct.getProductId());

        BaseResponse response = new BaseResponse();

        if (!alreadyInFavourite) {
            try {
                Favourites favourites = new Favourites();
                favourites.setUsersId(user.getUsersId());
                favourites.setProductId(favouriteProduct.getProductId());
                favouritesRepository.save(favourites);
                response.setCode(0);
                response.setDescription("added to favourite");
                return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.SUCCESS);
            } catch (Exception e) {
                log.error("Either User or Product not registered: {}", e.getMessage());
            }
        }
        response.setCode(-1);
        response.setDescription(favouriteProduct.getProductName() + " is already your favourite");
        return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.ERROR);
    }
}
